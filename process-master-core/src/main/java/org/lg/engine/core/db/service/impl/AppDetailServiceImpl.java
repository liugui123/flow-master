package org.lg.engine.core.db.service.impl;

import com.alibaba.fastjson2.JSON;
import jakarta.validation.groups.Default;
import org.apache.commons.lang3.StringUtils;
import org.lg.engine.core.Convert;
import org.lg.engine.core.client.enumerate.AppDetailFlagsEnum;
import org.lg.engine.core.client.enumerate.AppDetailStatusEnum;
import org.lg.engine.core.client.exception.WfException;
import org.lg.engine.core.client.exception.WfExceptionCode;
import org.lg.engine.core.client.model.request.AppDetailRequest;
import org.lg.engine.core.client.model.request.SaveAppManageRequest;
import org.lg.engine.core.client.model.request.SaveProcessRequest;
import org.lg.engine.core.client.model.request.ServiceModelRequest;
import org.lg.engine.core.client.model.response.AppGroupDetailPcResponse;
import org.lg.engine.core.client.service.FormService;
import org.lg.engine.core.client.service.ServiceModelService;
import org.lg.engine.core.client.utils.Assert;
import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.client.utils.WfConstant;
import org.lg.engine.core.db.mapper.AppDetailMapper;
import org.lg.engine.core.db.mapper.DeProcessMapper;
import org.lg.engine.core.db.mapper.DeServiceModelMapper;
import org.lg.engine.core.db.model.*;
import org.lg.engine.core.db.service.AppDetailService;
import org.lg.engine.core.db.service.DeFormModelService;
import org.lg.engine.core.db.service.ReFormModelService;
import org.lg.engine.core.db.service.ReProcessService;
import org.lg.engine.core.service.AppService;
import org.lg.engine.core.service.DeProcDefinitionService;
import org.lg.engine.core.service.manager.KeyHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class AppDetailServiceImpl implements AppDetailService {

    @Resource
    private AppDetailMapper appDetailMapper;

    @Resource
    private AppService appService;

    @Resource
    private DeProcessMapper deProcessMapper;

    @Resource
    private ReProcessService reProcessService;

    @Resource
    private DeProcDefinitionService deProcDefinitionService;

    @Resource
    private ReFormModelService reFormModelService;

    @Resource
    private FormService formService;

    @Resource
    private DeServiceModelMapper deServiceModelMapper;

    @Resource
    private DeFormModelService deFormModelService;

    @Resource
    private ServiceModelService serviceModelService;


    @Override
    public int deleteByPrimaryKey(Long id) {
        return appDetailMapper.deleteByPrimaryKey(id);
    }

    @Override
    public AppDetail selectByPrimaryKey(Long id) {
        return appDetailMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(AppDetail record) {
        return appDetailMapper.updateByPrimaryKey(record);
    }

    /**
     * 保存应用里的流程、表单、接口
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(SaveAppManageRequest request) {
        AppDetail appDetail = Convert.INSTANCE.saveAppManageRequestToAppDetail(request);

        String parentAppDetailKey = request.getParentAppDetailKey();
        if (Utils.isEmpty(parentAppDetailKey)) {
            //默认pid -1
            appDetail.setPid(-1L);
        } else {
            Long pid = appDetailMapper.selectIdByAppDetailKey(parentAppDetailKey);
            Assert.isTrue(pid != null,
                    WfExceptionCode.ILLEGAL_PARENT_KEY.getMsg(),
                    WfExceptionCode.ILLEGAL_PARENT_KEY.getCode());
            appDetail.setPid(pid);
        }

        Integer flags = request.getFlags();

        if (request.getAppDetailKey() == null) {
            insertAppDetail(request, flags, appDetail);
        } else {
            AppDetail dbAppDetail = appDetailMapper.selectFirstByAppDetailKey(request.getAppDetailKey());
            if (dbAppDetail == null) {
                insertAppDetail(request, flags, appDetail);
            } else {
                dbAppDetail.setDetailName(request.getDetailName());
                dbAppDetail.setPid(appDetail.getPid());
                appDetailMapper.updateByPrimaryKey(dbAppDetail);
            }

        }

    }

    private void insertAppDetail(SaveAppManageRequest request, Integer flags, AppDetail appDetail) {

        String detailKey = request.getDetailKey();
        //自定义key不能时预设里面的一种
        Assert.isTrue(!WfConstant.DEFAULT_PROC_GROUP_ID.equals(detailKey) &&
                        !WfConstant.DEFAULT_FROM_GROUP_ID.equals(detailKey) &&
                        !WfConstant.DEFAULT_SERVICE_GROUP_ID.equals(detailKey),
                WfExceptionCode.ILLEGAL_APP_DETAIL_KEY.getMsg(),
                WfExceptionCode.ILLEGAL_APP_DETAIL_KEY.getCode());

        //父节点固定分组时不需要外部给key
        String parentAppDetailKey = request.getParentAppDetailKey();
        if (WfConstant.DEFAULT_PROC_GROUP_ID.equals(parentAppDetailKey) ||
                WfConstant.DEFAULT_FROM_GROUP_ID.equals(parentAppDetailKey) ||
                WfConstant.DEFAULT_SERVICE_GROUP_ID.equals(parentAppDetailKey)) {
            appDetail.setDetailKey(KeyHelper.randomKey("APP_DETAIL"));
        } else {
            //如果没有给应用详情自定义key，给一个
            String sysDetailKey = getDetailKey(flags);
            appDetail.setDetailKey(Utils.isEmpty(request.getDetailKey()) ? sysDetailKey : request.getDetailKey());
        }
        Long firstIdByAppKey = appService.selectFirstIdByAppKey(request.getAppKey());
        appDetail.setGmtCreate(System.currentTimeMillis());
        appDetail.setGmtModified(System.currentTimeMillis());
        appDetail.setStatus(AppDetailStatusEnum.MOBILE.getId() + AppDetailStatusEnum.PC.getId());
        appDetail.setAppId(firstIdByAppKey);
        appDetail.setSortScore(0);
        appDetail.setAppDetailKey(UUID.randomUUID().toString().replace("-", ""));
        appDetailMapper.insert(appDetail);

        if (AppDetailFlagsEnum.PROCESS.hasTag(flags)) {
            //保存流程
            SaveProcessRequest saveProcessRequest = new SaveProcessRequest(
                    appDetail.getDetailKey(),
                    request.getDetailName(),
                    "{}",
                    "{}",
                    null,
                    request.getAppKey()
            );
            deProcDefinitionService.save(saveProcessRequest);
        } else if (AppDetailFlagsEnum.FORM.hasTag(flags)) {
            //保存表单
            DeFormModel deFormModel = new DeFormModel();
            //modelkey是模型的标识
            deFormModel.setModelKey(appDetail.getDetailKey());
            //formmodelkey是表单的标识
            deFormModel.setFormModelKey(appDetail.getDetailKey());
            deFormModel.setFormModelName(appDetail.getDetailName());
            deFormModel.setBizId(KeyHelper.randomKey(WfConstant.FROM_BIZ_DEC_CODING));
            deFormModel.setGmtCreate(System.currentTimeMillis());
            deFormModel.setGmtModified(System.currentTimeMillis());
            deFormModel.setStatus(0);
            deFormModel.setFlags(0);
            deFormModel.setAppKey(request.getAppKey());

            List<Long> ids = deFormModelService.selectIdByFormModelKey(deFormModel.getFormModelKey());
            Assert.isTrue(Utils.isEmpty(ids),
                    WfExceptionCode.REPEAT_FORM_MODEL_KEY.getMsg(),
                    WfExceptionCode.REPEAT_FORM_MODEL_KEY.getCode());

            deFormModelService.insert(deFormModel);
        } else if (AppDetailFlagsEnum.SERVICE.hasTag(flags)) {
            //保存接口
            ServiceModelRequest serviceModelRequest = new ServiceModelRequest();
            serviceModelRequest.setName(appDetail.getDetailName());
            serviceModelRequest.setModelKey(appDetail.getDetailKey());
            serviceModelRequest.setServiceModelKey(appDetail.getDetailKey());

            DeServiceModel deServiceModel = Convert.INSTANCE.reqToDeServiceModel(serviceModelRequest);
            List<Long> ids = deServiceModelMapper.selectFirstIdsByServiceModelKey(deServiceModel.getServiceModelKey());
            Assert.isTrue(Utils.isEmpty(ids),
                    WfExceptionCode.REPEAT_SERVICE_MODEL_KEY.getMsg(),
                    WfExceptionCode.REPEAT_SERVICE_MODEL_KEY.getCode());
            deServiceModel.setGmtCreate(System.currentTimeMillis());
            deServiceModel.setGmtModified(System.currentTimeMillis());
            deServiceModel.setAppKey(request.getAppKey());
            deServiceModelMapper.insert(deServiceModel);
        }
    }

    private static String getDetailKey(Integer flags) {
        String sysDetailKey = KeyHelper.randomKey(WfConstant.APP_DETAIL_GROUP);
        if (AppDetailFlagsEnum.PROCESS.hasTag(flags)) {
            //流程标识
            sysDetailKey = KeyHelper.randomKey(WfConstant.PROC_DEC_CODING);
        }
        if (AppDetailFlagsEnum.FORM.hasTag(flags)) {
            //表单标识
            sysDetailKey = KeyHelper.randomKey(WfConstant.FROM_DEC_CODING);
        }
        if (AppDetailFlagsEnum.SERVICE.hasTag(flags)) {
            //接口标识
            sysDetailKey = KeyHelper.randomKey(WfConstant.FACE_DEC_CODING);
        }
        return sysDetailKey;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByAppDetailKey(String appDetailKey) {
        if (Utils.isEmpty(appDetailKey)) {
            return;
        }

        Long appDetailId = appDetailMapper.selectIdByAppDetailKey(appDetailKey);

        if (appDetailId == null) {
            return;
        }

        Assert.isTrue(Utils.isEmpty(appDetailMapper.selectAllByPid(appDetailId)),
                WfExceptionCode.APP_DELETE_ERROR.getMsg(),
                WfExceptionCode.APP_DELETE_ERROR.getCode()
        );

        AppDetail appDetail = appDetailMapper.selectByPrimaryKey(appDetailId);
        if (appDetail == null) {
            throw new WfException("应用详情不存在,请检查ID是否准确");
        }
        if (AppDetailFlagsEnum.PROCESS.hasTag(appDetail.getFlags())) {
            //删除流程
            deProcessMapper.deleteByProcKey(appDetail.getDetailKey());
        } else if (AppDetailFlagsEnum.FORM.hasTag(appDetail.getFlags())) {
            deFormModelService.delDeFromModel(appDetail.getDetailKey());
        } else if (AppDetailFlagsEnum.SERVICE.hasTag(appDetail.getFlags())) {
            serviceModelService.delete(appDetail.getDetailKey());
        }
        appDetailMapper.deleteByPrimaryKey(appDetailId);
    }

    @Override
    public List<AppGroupDetailPcResponse> appDetailTree(AppDetailRequest appDetailRequest) {
        List<AppDetailTreeDTO> appDetailTreeDTOS = doInitAppDetailTree(appDetailRequest);
        return Convert.INSTANCE.appDetailTreeDtosToAppGroupDetailPcResponses(appDetailTreeDTOS);
    }

    private List<AppDetailTreeDTO> doInitAppDetailTree(AppDetailRequest request) {
        Utils.validateEntity(request, Default.class);
        List<AppDetailTreeDTO> res = new ArrayList<>(64);
        List<AppDetail> appDetails;
        AdminApp app = appService.selectFirstByAppKey(request.getAppKey());
        if (app == null) {
            return new ArrayList<>(0);
        }
        if (Utils.isNotEmpty(request.getName())) {

            appDetails = appDetailMapper.selectByFlagsAndAppIdAndStatusAndDetailNameLike(
                    request.getFlags(),
                    app.getId(),
                    request.getStatus(),
                    request.getName());
            return Convert.INSTANCE.appDetailsToAppDetailTreeDtos(appDetails);
        }
        //只找分组PID是groupId的分组，如果没有不查
        String groupAppDetailKey = request.getGroupAppDetailKey();

        if (groupAppDetailKey == null) {
            return new ArrayList<>(0);
        }
        Long groupId = appDetailMapper.selectIdByAppDetailKey(groupAppDetailKey);

        if (groupId == null) {
            return new ArrayList<>(0);
        }

        Long appIdByKey = app.getId();
        String appName = app.getAppName();
        appDetails = appDetailMapper.selectByFlagsAndAppIdAndStatusAndDetailNameLike(
                request.getFlags(),
                appIdByKey,
                request.getStatus(), null);
        if (Utils.isEmpty(appDetails)) {
            return new ArrayList<>(0);
        }
        //发布的流程添加额外启动信息
        initReProcessInfo(request, appDetails);
        //放入流程待办类型
        initBusinessType(appDetails);
        //排序
        appDetails.sort(Comparator.comparing(AppDetail::getSortScore).reversed());

        List<AppDetailTreeDTO> appDetailTreeDtos = Convert.INSTANCE.appDetailsToAppDetailTreeDtos(appDetails);
        List<AppDetailTreeDTO> appGroupList = appDetailTreeDtos
                .stream()
                .filter(a -> AppDetailFlagsEnum.APP_GROUP.hasTag(a.getFlags()))
                .collect(Collectors.toList());

        appGroupList = appGroupList
                .stream()
                .filter(appGroup -> groupId.equals(appGroup.getPid()))
                .collect(Collectors.toList());

        for (AppDetailTreeDTO appDetailTreeDTO : appGroupList) {
            if (appDetailTreeDTO == null) {
                continue;
            }
            if (appDetailTreeDTO.getPid().equals(-4L)) {
                appDetailTreeDTO.setParentAppDetailKey(WfConstant.DEFAULT_SERVICE_GROUP_ID);
            } else if (appDetailTreeDTO.getPid().equals(-3L)) {
                appDetailTreeDTO.setParentAppDetailKey(WfConstant.DEFAULT_FROM_GROUP_ID);
            } else if (appDetailTreeDTO.getPid().equals(-2L)) {
                appDetailTreeDTO.setParentAppDetailKey(WfConstant.DEFAULT_PROC_GROUP_ID);
            }
        }

        for (AppDetailTreeDTO appDetailTreeDto : appGroupList) {
            appDetailTreeDto.setAppName(appName);
            res.add(appDetailTreeDto);
            initAppDetailTree(appDetailTreeDto, appDetailTreeDtos);
        }
        //去掉所有id
        delAppDetailTreeId(res);
        return res;
    }

    /**
     * 发布的流程添加额外启动信息
     */
    private void initReProcessInfo(AppDetailRequest request, List<AppDetail> appDetails) {
        List<String> publishedProcessKeys = appDetails.stream()
                .filter(a -> (AppDetailFlagsEnum.PUBLISHED.hasTag(a.getFlags())
                        && AppDetailFlagsEnum.PROCESS.hasTag(a.getFlags())) || AppDetailFlagsEnum.HTML
                        .hasTag(a.getFlags()))
                .map(AppDetail::getDetailKey)
                .collect(Collectors.toList());
        if (Utils.isNotEmpty(publishedProcessKeys)) {
            if (request.getNeedPermission() != null && request.getNeedPermission()) {
                Iterator<AppDetail> iterator = appDetails.iterator();
                while (iterator.hasNext()) {
                    AppDetail appDetail = iterator.next();
                    if (((AppDetailFlagsEnum.PUBLISHED.hasTag(appDetail.getFlags())
                            && AppDetailFlagsEnum.PROCESS.hasTag(appDetail.getFlags())) || AppDetailFlagsEnum.HTML
                            .hasTag(appDetail.getFlags()))) {
                        iterator.remove();
                    }
                }
            }
            List<ReProcess> reProcesses = reProcessService.selectLastByProcKeyIn(publishedProcessKeys);
            if (Utils.isNotEmpty(reProcesses)) {
                //流程key和对应的流程信息
                Map<String, List<ReProcess>> procKeyAndReprocess = reProcesses.stream()
                        .collect(Collectors.groupingBy(ReProcess::getProcKey));
                //表单模型id和对应的表单模型信息
                List<Long> reModelIds = reProcesses.stream().map(ReProcess::getFormModelReId).collect(Collectors.toList());
                Map<Long, ReFormModel> idAndFormModel = reFormModelService.getIdAndFormModelRel(reModelIds);
                for (AppDetail appDetail : appDetails) {
                    List<ReProcess> reProcesses1 = procKeyAndReprocess.get(appDetail.getDetailKey());
                    if (Utils.isEmpty(reProcesses1)) {
                        continue;
                    }
                    ReProcess reProcess = reProcesses1.get(0);
                    Map<String, String> map = new HashMap<>(2);
                    ReFormModel reFormModel = idAndFormModel.get(reProcess.getId());
                    if (reFormModel != null) {

                        String formModelKey = reFormModel.getFormModelKey();
                        String formModelUrl = reFormModel.getFormModelUrl();
                        String formModelUrlApp = reFormModel.getFormModelUrlApp();

                        String startUrl = reFormModel.getFormModelStartUrl()
                                + "?"
                                + "procFormKey=" + formModelKey;
                        map.put("procStartUrl", startUrl);
                        map.put("procStartAppUrl", reFormModel.getFormModelStartUrlApp() == null ? null
                                : (reFormModel.getFormModelStartUrlApp() + "?" + "procFormKey=" + formModelKey));
                        map.put("procFormUrl", formModelUrl == null ? null
                                : (formModelUrl + "?" + "procFormKey=" + formModelKey));
                        map.put("procFormAppUrl", formModelUrlApp == null ? null
                                : (formModelUrlApp + "?" + "procFormKey=" + formModelKey));
                        map.put("procFormKey", formModelKey);
                    }

                    appDetail.setExtra(JSON.toJSONString(map));
                }
            }
        }
    }


    private void initAppDetailTree(AppDetailTreeDTO root, List<AppDetailTreeDTO> appDetailTreeDtos) {
        List<AppDetailTreeDTO> children = findChildren(root, appDetailTreeDtos);
        root.setChildren(children);
        for (AppDetailTreeDTO child : children) {
            initAppDetailTree(child, appDetailTreeDtos);
        }
    }

    private void delAppDetailTreeId(List<AppDetailTreeDTO> res) {

        if (Utils.isEmpty(res)) {
            return;
        }

        for (AppDetailTreeDTO re : res) {
            re.setId(null);
            List<AppDetailTreeDTO> children = re.getChildren();
            delAppDetailTreeId(children);
        }
    }

    private void initBusinessType(List<AppDetail> appDetails) {
        List<String> procKeys = appDetails.stream()
                .filter(a -> AppDetailFlagsEnum.PROCESS.hasTag(a.getFlags()))
                .map(AppDetail::getDetailKey)
                .collect(Collectors.toList());
        if (Utils.isNotEmpty(procKeys)) {
            List<DeProcess> deProcesses = deProcDefinitionService.businessTypeList(procKeys);
            Map<String, Integer> businessTypeMap = deProcesses.stream().filter(i -> i.getBusinessType() != null)
                    .collect(Collectors.toMap(DeProcess::getProcKey, DeProcess::getBusinessType, (o, n) -> o));
            for (AppDetail appDetail : appDetails) {
                if (appDetail == null) {
                    continue;
                }
                if (AppDetailFlagsEnum.PROCESS.hasTag(appDetail.getFlags())) {
                    Integer businessType = businessTypeMap.get(appDetail.getDetailKey());
                    if (businessType != null) {
                        String extra = appDetail.getExtra();
                        Map<String, String> json;
                        if (StringUtils.isNotBlank(extra)) {
                            json = JSON.parseObject(extra, Map.class);
                        } else {
                            json = new HashMap<>();
                        }
                        if (Utils.isNotEmpty(json)) {
                            json.put("businessType", String.valueOf(businessType));
                            appDetail.setExtra(JSON.toJSONString(json));
                        }

                    }
                }
            }

        }
    }

    private List<AppDetailTreeDTO> findChildren(AppDetailTreeDTO pAppDetailTreeDTO,
                                                List<AppDetailTreeDTO> appDetailTreeDtos) {
        List<AppDetailTreeDTO> res = new ArrayList<>(64);
        for (AppDetailTreeDTO appDetailTreeDto : appDetailTreeDtos) {
            Long pid = appDetailTreeDto.getPid();
            Long id = pAppDetailTreeDTO.getId();
            if (pid == null) {
                continue;
            }
            if (pid.equals(id)) {
                appDetailTreeDto.setParentAppDetailKey(pAppDetailTreeDTO.getAppDetailKey());
                appDetailTreeDto.setPid(null);
                res.add(appDetailTreeDto);
            }
        }
        return res;

    }
}
