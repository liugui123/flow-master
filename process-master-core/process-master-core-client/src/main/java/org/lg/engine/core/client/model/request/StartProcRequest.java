
package org.lg.engine.core.client.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;

@Getter
@Setter
public class StartProcRequest extends BaseWfRequest {
    /**
     * 流程定义编码
     */
    @NotNull(message = "流程编码不能为空")
    private String procKey;
    /**
     * 发起人部门ID
     * 确定发起人部门，后续所有审批默认和发起人一个部门处理
     * 如果需要跨部门审批，可以使用岗位
     */
//    @NotNull(message = "发起人部门ID不能为空")
//    private String starterDeptId;
    /**
     * 发起人部门名称
     */
//    private String starterDeptName;
    /**
     * 是否启动后执行自动执行开始节点，默认执行开始节点
     * 是 启动后生成流程实例，自动执行开始节点及开始节点后的第一个节点
     * 否 只生成流程实例，不会执行开始节点，后续需要主动触发开始节点
     */
    private Boolean runFirstElement = true;
    /**
     * 父流程实例id
     */
    private Long pid;
    /**
     * 流程表单数据标识
     */
    private String procFormDataKey;

}
