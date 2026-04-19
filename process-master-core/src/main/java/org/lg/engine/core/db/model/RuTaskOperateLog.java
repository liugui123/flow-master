package org.lg.engine.core.db.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class RuTaskOperateLog {
    private Long id;

    private Long gmtCreate;


    private Long taskId;


    private Integer status;


    private Integer flags;


    private String taskDesc;


    private String procinstName;


    private String procinstKey;


    private String operatorId;


    private String operatorName;


    private String operatorOrgId;


    private String operatorOrgName;


    private String operatorDeptId;


    private String operatorDeptName;
}