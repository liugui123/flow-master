package org.lg.engine.core.service.impl;

//import com.shinemo.common.tools.lang.Dates;

import jakarta.validation.groups.Default;
import org.lg.engine.core.Convert;
import org.lg.engine.core.client.enumerate.AppDetailFlagsEnum;
import org.lg.engine.core.client.exception.WfExceptionCode;
import org.lg.engine.core.client.model.request.*;
import org.lg.engine.core.client.model.response.ProcessDetailResponse;
import org.lg.engine.core.client.model.response.ProcessListResponse;
import org.lg.engine.core.client.model.response.ProcessSaveResponse;
import org.lg.engine.core.client.model.response.ProcessUpdateResponse;
import org.lg.engine.core.client.utils.Assert;
import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.client.utils.WfConstant;
import org.lg.engine.core.cmd.impl.PublishProcessDefCmd;
import org.lg.engine.core.db.mapper.AppDetailMapper;
import org.lg.engine.core.db.mapper.DeProcessMapper;
import org.lg.engine.core.db.mapper.ReProcessMapper;
import org.lg.engine.core.db.model.AppDetail;
import org.lg.engine.core.db.model.DeProcess;
import org.lg.engine.core.db.service.AppDetailService;
import org.lg.engine.core.service.AbstractService;
import org.lg.engine.core.service.DeProcDefinitionService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class DeProcDefinitionServiceImpl extends AbstractService implements DeProcDefinitionService {

    @Resource
    private DeProcessMapper deProcessMapper;

    @Resource
    private ReProcessMapper reProcessMapper;

    @Resource
    private AppDetailService appDetailService;

    @Resource
    private AppDetailMapper appDetailMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProcessSaveResponse save(SaveProcessRequest request) {
        Utils.validateEntity(request, Default.class);
        if (request.getStatus() == null) {
            request.setStatus(0);
        }
        DeProcess deProcess = Convert.INSTANCE.saveProcessRequestToDeProcess(request);
        String sysId = Utils.getRandomNum(6);
        deProcess.setFlags(0);
        if (request.getProcKey() == null) {
            deProcess.setProcKey(
                    WfConstant.PROC_DEC_CODING + System.currentTimeMillis() + sysId);
        }
        deProcess.setGmtCreate(System.currentTimeMillis());
        deProcess.setGmtModified(System.currentTimeMillis());
        deProcessMapper.insert(deProcess);
        return Convert.INSTANCE.deProcessToProcessResponse(deProcess);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProcessUpdateResponse updateProcess(UpdateProcessRequest request) {
        DeProcess processBean = deProcessMapper.selectFirstByProcKey(request.getProcKey());
        Assert.isTrue(
                processBean != null,
                "更新流程定义:" + WfExceptionCode.PROCESS_DE_BLANK.getMsg(),
                WfExceptionCode.PROCESS_DE_BLANK.getCode()
        );
        processBean.setProcJson(request.getProcJson());
        processBean.setProcName(request.getProcName());
        processBean.setGmtModified(System.currentTimeMillis());
        processBean.setProcViewJson(request.getProcViewJson());
        processBean.setStatus(request.getStatus());
        processBean.setFormModelReId(request.getFormModelReId());
        deProcessMapper.updateByPrimaryKey(processBean);
        AppDetail appDetail = appDetailMapper.selectFirstByDetailKey(request.getProcKey());
        appDetail.setDetailName(request.getProcName());
        appDetailMapper.updateByPrimaryKey(appDetail);
        if (request.getStatus() == 1) {
            commandExecutor.execute(new PublishProcessDefCmd(
                    request.getOperator(),
                    request.getProcKey(),
                    request.getFormModelReId()));
            appDetailMapper.updateFlagsByDetailKey(
                    AppDetailFlagsEnum.PROCESS.getFlag() + AppDetailFlagsEnum.PUBLISHED.getFlag(),
                    request.getProcKey());
        }
        return Convert.INSTANCE.deProcessToProcessUpdateRes(processBean);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProcessStatus(UpdateStatusRequest req) {
        DeProcess deProcess = deProcessMapper.selectFirstByProcKey(req.getProcKey());

        Assert.isTrue(
                deProcess != null,
                "更新流程状态：" + WfExceptionCode.PROCESS_DE_BLANK.getMsg(),
                WfExceptionCode.PROCESS_DE_BLANK.getCode()
        );

        Date updateTime = Calendar.getInstance().getTime();
        deProcessMapper.updateProcessStatus(deProcess.getId(), req.getStatus(), updateTime);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publishProcessDef(PublishProcessDefRequest request) {
        commandExecutor.execute(new PublishProcessDefCmd(request.getOperator(), request.getDefProcessKey(), null));
    }

    @Override
    public List<DeProcess> businessTypeList(List<String> procKeys) {
        if (Utils.isEmpty(procKeys)) {
            return new ArrayList<>(0);
        }
        return deProcessMapper.businessTypeList(procKeys);
    }

    @Override
    public ProcessDetailResponse getProcessDetail(ProcessDetailRequest processDetailRequest) {
        DeProcess processDetail = deProcessMapper.selectFirstByProcKey(processDetailRequest.getProcKey());
        return Convert.INSTANCE.processDetailToProcessDetailResponse(processDetail);
    }

    @Override
    public List<ProcessListResponse> list(ProcessListRequest request) {
        List<String> procKeys = request.getProcKeys();
        if (Utils.isEmpty(procKeys)) {
            return new ArrayList<>(0);
        }
        List<DeProcess> deProcesses = deProcessMapper
                .selectByProcKeyInAndStatus(request.getProcKeys(), request.getStatus());
        if (Utils.isEmpty(deProcesses)) {
            return new ArrayList<>(0);
        }
        return Convert.INSTANCE.deProcessesToProcessListResponse(deProcesses);
    }

}
