package org.lg.engine.core.provider;


import com.alibaba.dubbo.config.annotation.Service;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import org.lg.engine.core.Convert;
import org.lg.engine.core.aspect.RepeatSubmit;
import org.lg.engine.core.client.exception.WfExceptionCode;
import org.lg.engine.core.client.model.request.*;
import org.lg.engine.core.client.model.response.*;
import org.lg.engine.core.client.service.FormService;
import org.lg.engine.core.client.utils.ApiResult;
import org.lg.engine.core.client.utils.Assert;
import org.lg.engine.core.client.utils.Page;
import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.db.mapper.AppDetailMapper;
import org.lg.engine.core.db.model.DeFormModel;
import org.lg.engine.core.db.model.DeFormModelDetail;
import org.lg.engine.core.db.model.ReFormModel;
import org.lg.engine.core.db.model.ReFormModelDetail;
import org.lg.engine.core.db.service.DeFormModelDetailService;
import org.lg.engine.core.db.service.DeFormModelService;
import org.lg.engine.core.db.service.ReFormModelDetailService;
import org.lg.engine.core.db.service.ReFormModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Component
public class FormServiceProvider implements FormService {

    @Autowired
    private DeFormModelService deFormModelService;

    @Autowired
    private ReFormModelService reFormModelService;

    @Autowired
    private DeFormModelDetailService deFormModelDetailService;

    @Autowired
    private ReFormModelDetailService reFormModelDetailService;

    @Resource
    private AppDetailMapper appDetailMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @RepeatSubmit
    public ApiResult<Void> edit(@Valid EditDeFromModelAndDetailRequest request) {
        Utils.validateEntity(request, Default.class);
        SaveDeFormModelRequest requestDeFormModel = request.getDeFormModel();
        DeFormModel deFormModel = Convert.INSTANCE.saveDeFormModelToDeFormModel(requestDeFormModel);
        deFormModel.setAppKey(request.getAppKey());
        //库中的表单模型
        DeFormModel dbDeFormModel = deFormModelService.selectFirstByModelKey(deFormModel.getModelKey());
        if (dbDeFormModel != null) {
            deFormModel.setId(dbDeFormModel.getId());
            deFormModel.setModelKey(dbDeFormModel.getModelKey());
            deFormModel.setGmtCreate(System.currentTimeMillis());
            deFormModel.setStatus(0);
            deFormModel.setFlags(0);
            deFormModel.setGmtModified(System.currentTimeMillis());
            deFormModelService.updateByPrimaryKey(deFormModel);
            deFormModelDetailService.deleteByFormModelId(deFormModel.getId());
        } else {
            List<Long> ids = deFormModelService.selectIdByFormModelKey(deFormModel.getFormModelKey());
            Assert.isTrue(Utils.isEmpty(ids),
                    WfExceptionCode.REPEAT_FORM_MODEL_KEY.getMsg(),
                    WfExceptionCode.REPEAT_FORM_MODEL_KEY.getCode());

            deFormModel.setGmtCreate(System.currentTimeMillis());
            deFormModel.setGmtModified(System.currentTimeMillis());
            deFormModel.setStatus(0);
            deFormModel.setFlags(0);
            deFormModelService.insert(deFormModel);
        }
        List<DeFormModelDetail> deFormModelDetails = null;
        List<SaveDeFormModelDetailRequest> saveDeFormModelDetails = request.getDeFormModelDetails();
        if (Utils.isNotEmpty(saveDeFormModelDetails)) {
            deFormModelDetails = new ArrayList<>(saveDeFormModelDetails.size());
            if (Utils.isNotEmpty(saveDeFormModelDetails)) {
                for (SaveDeFormModelDetailRequest saveDeFormModelDetail : saveDeFormModelDetails) {
                    if (saveDeFormModelDetail == null) {
                        continue;
                    }
                    DeFormModelDetail deFormModelDetail = Convert.INSTANCE
                            .saveDeFormModelDetailToDeFormModelDetail(saveDeFormModelDetail);
                    deFormModelDetail.setGmtCreate(System.currentTimeMillis());
                    deFormModelDetail.setGmtModified(System.currentTimeMillis());
                    deFormModelDetail.setStatus(0);
                    deFormModelDetail.setFlags(0);
                    deFormModelDetail.setFormModelId(deFormModel.getId());
                    deFormModelDetails.add(deFormModelDetail);
                }
                deFormModelDetailService.batchInsert(deFormModelDetails);
            }
        }

        //更新应用详情名字
        appDetailMapper.updateDetailNameByDetailKey(deFormModel.getFormModelName(), deFormModel.getModelKey());


        if (request.getPublish()) {
            PublishDeFromModelAndDetailRequest publishRequest = new PublishDeFromModelAndDetailRequest();
            PublishDeFormModelRequest publishDeFormModel = Convert.INSTANCE
                    .deFormModelToPublishDeFormModel(deFormModel);
            publishRequest.setDeFormModelReq(publishDeFormModel);

            List<PublishDeFormModelDetailRequest> publishDeFormModelDetails = Convert.INSTANCE
                    .deFormModelDetailsToPublish(deFormModelDetails);
            publishRequest.setDeFormModelDetailsReq(publishDeFormModelDetails);
            publishRequest.setOperator(request.getOperator());
            publishDeFromModel(publishRequest);
        }
        return ApiResult.success();
    }

    @Override
    public ApiResult<DeFromModelDetailResponse> detail(DeFormModelDetailRequest request) {
        DeFormModel deFormModel = deFormModelService.selectFirstByModelKey(request.getKey());

        if (deFormModel != null) {
            Long id = deFormModel.getId();

            DeFromModelDetailResponse response = Convert.INSTANCE.deFormModelToDeFromModelDetailResponse(deFormModel);

            List<DeFormModelDetail> deFormModelDetails = deFormModelDetailService.selectByFormModelId(id);

            List<DeFormModelDetailResponse> deFormModelDetailsRes = Convert.INSTANCE.deFormModelDetailsToRes(deFormModelDetails);

            response.setDeFormModelDetailRes(deFormModelDetailsRes);

            return ApiResult.success(response);
        }

        return ApiResult.success();
    }

    @Override
    public ApiResult<List<ReFormLatestModelListResponse>> latestReFromModelList(@Valid ReFormLatestModelListRequest request) {
        Utils.validateEntity(request, Default.class);
        //所有的发布表单
        List<ReFormModel> reFormModels = reFormModelService.selectMaxVersionFormByAppKey(request.getFormModelName(), request.getAppKey());
        List<ReFormLatestModelListResponse> responseList = Convert.INSTANCE.reFormModelsToResponseList(reFormModels);
        //获取到所有的参数详情
        if (Utils.isNotEmpty(responseList)) {

            Set<Long> modelFormIds = responseList.stream().map(ReFormLatestModelListResponse::getId).collect(Collectors.toSet());

            List<ReFormModelDetail> reFormModelDetails = reFormModelDetailService.selectByFormModelIdIn(modelFormIds);
            List<ReFormModelDetailResponse> detailResponses = Convert.INSTANCE
                    .reFormModelDetailsToReFormModelDetailResponse(reFormModelDetails);
            Map<Long, List<ReFormModelDetailResponse>> idAndDetails = detailResponses.stream().collect(Collectors.groupingBy(ReFormModelDetailResponse::getFormModelId));

            if (Utils.isNotEmpty(idAndDetails)) {
                //设置到内容中
                for (ReFormLatestModelListResponse detail : responseList) {
                    detail.setDetails(idAndDetails.get(detail.getId()));
                }
            }
        }
        return ApiResult.success(responseList);
    }

    @Override
    public ApiResult<Page<ReFormModelPageResponse>> pageReForm(@Valid ReFormModelPageRequest request) {
        Page<ReFormModelPageResponse> res = new Page<>();
        //查询
        Page<ReFormModel> reForm = reFormModelService.page(request);
        Collection<ReFormModel> reFormRows = reForm.getRows();
        Collection<ReFormModelPageResponse> taskUsers = Convert.INSTANCE.reFormToRes(reFormRows);
        res.setRows(taskUsers);
        res.setTotal(reForm.getTotal());
        return ApiResult.success(res);
    }

    @Override
    public ApiResult<Void> disableOrEnable(@Valid ReFormModelDisableEnableRequest request) {
        reFormModelService.disableOrEnable(request.getStatus(), request.getId());
        return ApiResult.success();
    }


    @Transactional(rollbackFor = Exception.class)
    @RepeatSubmit
    @Override
    public ApiResult<Void> publishDeFromModel(@Valid PublishDeFromModelAndDetailRequest request) {
        Utils.validateEntity(request, Default.class);
        PublishDeFormModelRequest deFormModel = request.getDeFormModelReq();
        ReFormModel reFormModel = Convert.INSTANCE.publishDeFormModelToReFormModel(deFormModel);
        reFormModel.setId(null);
        reFormModel.setDeFormId(deFormModel.getId() == null ? -1 : deFormModel.getId());
        reFormModel.setGmtCreate(System.currentTimeMillis());
        reFormModel.setGmtModified(System.currentTimeMillis());
        reFormModel.setStatus(0);
        reFormModel.setFlags(0);
        Integer version = reFormModelService
                .selectFirstModelVersionByModelKeyOrderByModelVersionDesc(deFormModel.getModelKey());
        if (version != null) {
            reFormModel.setModelVersion(version + 1);
        } else {
            reFormModel.setModelVersion(0);
        }
        reFormModelService.insert(reFormModel);
        List<PublishDeFormModelDetailRequest> publishDeFormModelDetails = request.getDeFormModelDetailsReq();
        if (Utils.isNotEmpty(publishDeFormModelDetails)) {
            List<ReFormModelDetail> reFormModelDetails = Convert.INSTANCE
                    .publishDeFormModelDetailsToReFormModelDetails(publishDeFormModelDetails);
            for (ReFormModelDetail reFormModelDetail : reFormModelDetails) {
                reFormModelDetail.setFormModelId(reFormModel.getId());
                reFormModelDetail.setGmtModified(System.currentTimeMillis());
                reFormModelDetail.setGmtCreate(System.currentTimeMillis());
                reFormModelDetail.setFlags(0);
                reFormModelDetail.setStatus(0);
            }

            reFormModelDetailService.batchInsert(reFormModelDetails);
        }
        return ApiResult.success();
    }

    @Override
    public ApiResult<List<DeFormModelListResponse>> deFromModelList(@Valid DeFormModelListRequest request) {
        Utils.validateEntity(request, Default.class);
        //查询表单列表
        List<DeFormModel> deFormModels = deFormModelService.selectAllByFormModelNameLike
                (request.getFormModelName(), request.getFormModelKey(), request.getAppKey());
        List<DeFormModelListResponse> responseList = Convert.INSTANCE.deFormModelsToResponseList(deFormModels);
        if (Utils.isEmpty(responseList)) {
            //为空直接返回
            return ApiResult.success();
        }
        return ApiResult.success(responseList);
    }


    @Override
    public ApiResult<List<ReFormModelDetailResponse>> reFromModelDetail(@Valid ReFormModelDetailRequest request) {
        Utils.validateEntity(request, Default.class);
        List<ReFormModelDetail> reFormModelDetails = reFormModelDetailService
                .selectByFormModelId(request.getReFormModelId());
        List<ReFormModelDetailResponse> response = Convert.INSTANCE
                .reFormModelDetailsToReFormModelDetailResponse(reFormModelDetails);
        return ApiResult.success(response);
    }

    @Override
    public ApiResult<ReFormModelDetailByKeyResponse> selectFirstByFormModelKeyOrderByModelVersionDesc(
            @Valid ReFormModelDetailByKeyRequest request) {
        Utils.validateEntity(request, Default.class);
        ReFormModel reFormModel = reFormModelService
                .selectFirstByFormModelKeyOrderByModelVersionDesc(request.getFormModelKey());
        ReFormModelDetailByKeyResponse response = Convert.INSTANCE.reFormModelToResponse(reFormModel);
        return ApiResult.success(response);
    }

}
