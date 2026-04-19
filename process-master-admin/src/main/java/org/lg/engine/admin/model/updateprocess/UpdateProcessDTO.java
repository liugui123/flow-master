package org.lg.engine.admin.model.updateprocess;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.request.base.BaseWfRequest;

@Getter
@Setter
public class UpdateProcessDTO extends BaseWfRequest {

    /**
     * 0草稿 1发布并启用2停用
     */
    private Integer status = 0;

    /**
     * 标志位
     */
    protected Integer flags;

    /**
     * 流程编码
     */
    @NotNull(message = "流程编码不能为空")
    protected String procKey;
    /**
     * 流程定义名称
     */
    @NotNull(message = "流程定义名称不能为空")
    protected String procName;

//    /**
//     * 流程json数据
//     */
//    @NotNull(message = "流程json数据不能为空")
//    protected String procJson;

    /**
     * 流程viewJson数据
     */
    protected String procViewJson;

//    /**
//     * 流程配置数据
//     */
//    protected String procConfJson;

    /**
     * 版本
     */
    protected Integer procVersion;

    /**
     * 流程表单ID
     */
    private Long formModelReId;


}
