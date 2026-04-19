/**
 * 节点上人员信息
 * liugui
 **/
package org.lg.engine.core.client.model.response;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.model.WfUser;

import java.util.List;

@Getter
@Setter
public class TaskUserTreeResponse extends WfUser {
    /**
     * 标题
     * 组织/部门/人员名称
     */
    private String title;

    /**
     * 组织/部门/人员ID
     */
    private String key;
    /**
     * 子节点
     */
    private List<TaskUserTreeResponse> children;

}
