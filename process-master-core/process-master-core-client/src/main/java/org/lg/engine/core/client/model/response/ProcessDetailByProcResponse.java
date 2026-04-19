package org.lg.engine.core.client.model.response;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.response.base.BaseWfResponse;

import java.util.List;


@Getter
@Setter
public class ProcessDetailByProcResponse extends BaseWfResponse {
    /**
     * 流程实例名
     */
    private String procName;
    /**
     * 视图数据
     */
    private String procViewJson;

    /**
     * 流程表单key
     */
    private String procFormKey;

    /**
     * 节点使用详情,返回节点是否结束
     */
    List<ProcessDetailActStatus> statuses;

}
