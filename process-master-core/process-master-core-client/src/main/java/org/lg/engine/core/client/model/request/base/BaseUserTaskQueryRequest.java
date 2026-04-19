/**
 * 我发起的任务查询入参
 *
 * liugui
 * @date 2021/06/04 15:29
 **/
package org.lg.engine.core.client.model.request.base;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.Operator;
import org.lg.engine.core.client.utils.Query;

@Getter
@Setter
public class BaseUserTaskQueryRequest extends Query {
    /**
     * 用户信息
     */
    @NotNull(message = "操作人不能为空")
    @Valid
    protected Operator operator;

    /**
     * 流程实例名字
     * 支持查询某个流程实例下的任务
     */
    private String procinstName;

    /**
     * 节点实例名字
     */
    private String actinstName;
    /**
     * 创建开始时间
     */
    Long minGmtCreate;
    /**
     * 创建结束时间
     */
    Long maxGmtCreate;
}
