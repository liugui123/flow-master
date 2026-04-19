package org.lg.engine.core;


import org.lg.engine.core.client.model.Assignee;
import org.lg.engine.core.client.model.Operator;
import org.lg.engine.core.client.model.request.*;
import org.lg.engine.core.client.model.response.*;
import org.lg.engine.core.context.CommandContext;
import org.lg.engine.core.db.model.*;
import org.lg.engine.core.service.manager.TreeNode;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface Convert {

    Convert INSTANCE = Mappers.getMapper(Convert.class);

    RuStartDraft editReqToRuStartDraft(SaveStartDraftRequest request);

    HiRuProcinst ruProcinstToHiRuProcinst(RuProcinst ruProcinst);

    HiRuTask ruTaskToHiRuTask(RuTask ruTask);

    RuProcinst hiRuProcinstToRuProcinst(HiRuProcinst hiRuProcinst);

    List<HiRuTask> ruTasksToHiRuTasks(List<RuTask> ruTasks);

    List<HiRuTaskUser> ruTaskUsersTohiRuTaskUsers(List<RuTaskUser> ruTaskUsers);

    HiRuTaskUser ruTaskUserToHiRuTaskUser(RuTaskUser ruTaskUser);

    RuTaskUser hiRuTaskUserToRuTaskUser(HiRuTaskUser hiRuTaskUser);

    Assignee operatorToAssignee(Operator operator);

    RuTaskUser ruTaskUserToRuTaskUser(RuTaskUser ruTaskUser);

    CommandContext commandContextToCommandContext(CommandContext commandContext);

    AppDetail saveAppManageRequestToAppDetail(SaveAppManageRequest request);

    DeServiceModel reqToDeServiceModel(ServiceModelRequest serviceModelRequest);

    DeProcess saveProcessRequestToDeProcess(SaveProcessRequest request);

    ProcessSaveResponse deProcessToProcessResponse(DeProcess deProcess);

    List<AppGroupDetailPcResponse> appDetailsToRes(List<AppDetail> appDetails);

    ProcessUpdateResponse deProcessToProcessUpdateRes(DeProcess processBean);

    AdminApp updateAppRequestToAdminApp(UpdateAppRequest updateAppRequest);

    ProcessDetailResponse processDetailToProcessDetailResponse(DeProcess processDetail);

    List<ProcessListResponse> deProcessesToProcessListResponse(List<DeProcess> deProcesses);

    ProcessDetailByProcResponse procinstToProcessDetailByProcinstIdRequest(RuProcinst ruProcinst);

    List<ProcessDetailActStatus> ruActinstsToProcessDetailActStatus(List<RuActinst> ruActinsts);

    List<ProcinstPageReponse> ruProcinstsToProcinstPageResponses(List<RuProcinst> ruProcinsts);

    List<ProcinstActinstResponse> ruActinstsToProcinstActinstResponses(List<RuActinst> ruActinsts);

    DeFormModel saveDeFormModelToDeFormModel(SaveDeFormModelRequest requestDeFormModel);

    DeFormModelDetail saveDeFormModelDetailToDeFormModelDetail(SaveDeFormModelDetailRequest saveDeFormModelDetail);

    List<PublishDeFormModelDetailRequest> deFormModelDetailsToPublish(List<DeFormModelDetail> deFormModelDetails);

    PublishDeFormModelRequest deFormModelToPublishDeFormModel(DeFormModel deFormModel);

    DeFromModelDetailResponse deFormModelToDeFromModelDetailResponse(DeFormModel deFormModel);

    List<DeFormModelDetailResponse> deFormModelDetailsToRes(List<DeFormModelDetail> deFormModelDetails);

    List<ReFormLatestModelListResponse> reFormModelsToResponseList(List<ReFormModel> reFormModels);

    List<ReFormModelDetailResponse> reFormModelDetailsToReFormModelDetailResponse(List<ReFormModelDetail> reFormModelDetails);

    Collection<ReFormModelPageResponse> reFormToRes(Collection<ReFormModel> reFormRows);

    ReFormModel publishDeFormModelToReFormModel(PublishDeFormModelRequest deFormModel);

    List<ReFormModelDetail> publishDeFormModelDetailsToReFormModelDetails(List<PublishDeFormModelDetailRequest> publishDeFormModelDetails);

    List<DeFormModelListResponse> deFormModelsToResponseList(List<DeFormModel> deFormModels);

    ReFormModelDetailByKeyResponse reFormModelToResponse(ReFormModel reFormModel);

    TaskUserTreeResponse treeNodeToTaskUsersResponse(TreeNode treeNode);

    RuTask hiRuTaskToRuTask(HiRuTask hiRuTask);

    List<AppDetailTreeDTO> appDetailsToAppDetailTreeDtos(List<AppDetail> appDetails);

    List<AppGroupDetailPcResponse> appDetailTreeDtosToAppGroupDetailPcResponses(List<AppDetailTreeDTO> appDetailTreeDTOS);

    List<TaskOpeLogPageResponse> ruTaskOperateLogsToTaskOpeLogPageResponses(List<RuTaskOperateLog> ruTaskOperateLogs);

    ReProcess deProcessToReProcess(DeProcess deProcess);

    TreeNode assigneeToTreeNode(Assignee assignee);
}
