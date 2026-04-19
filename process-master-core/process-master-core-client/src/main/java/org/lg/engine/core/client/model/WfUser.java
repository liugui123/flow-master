
package org.lg.engine.core.client.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class WfUser implements Serializable {


    @NotNull(message = "用户ID不能为空")
    private String id;

    @NotNull(message = "用户名称不能为空")
    private String name;

    private String deptId;

    private String deptName;

    @NotNull(message = "用户组织ID不能为空")
    private String orgId;

    private String orgName;


    private String mobile;

    /**
     * 代理人 这个记录的是 真实的处理人 上面是代理人的信息
     */
    @Valid
    private WfUser replace;

}
