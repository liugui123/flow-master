package org.lg.engine.core.db.model;

import lombok.Getter;
import lombok.Setter;

/**
 * liugui
 * @Date 2021/11/25
 */
@Getter
@Setter
public class AssigneeOrgAndDept {

    private String orgId;
    private String orgName;
    /**
     * 前端选人组件不好处理，当com.shinemo.baas.wf.client.aace.model.AssigneeCateFilter=3时表示部门id，=4时表示企业id
     */
    private String id;
    private String name;
}
