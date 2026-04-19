package org.lg.engine.core.service;

import org.lg.engine.core.client.model.request.*;
import org.lg.engine.core.client.model.response.ProcessDetailResponse;
import org.lg.engine.core.client.model.response.ProcessListResponse;
import org.lg.engine.core.client.model.response.ProcessSaveResponse;
import org.lg.engine.core.client.model.response.ProcessUpdateResponse;
import org.lg.engine.core.db.model.DeProcess;

import java.util.List;

public interface DeProcDefinitionService extends CommonService {


    ProcessSaveResponse save(SaveProcessRequest request);


    ProcessUpdateResponse updateProcess(UpdateProcessRequest request);


    void updateProcessStatus(UpdateStatusRequest updateStatusRequest);


    void publishProcessDef(PublishProcessDefRequest request);

    List<DeProcess> businessTypeList(List<String> procKeys);

    ProcessDetailResponse getProcessDetail(ProcessDetailRequest processDetailRequest);

    List<ProcessListResponse> list(ProcessListRequest request);

}
