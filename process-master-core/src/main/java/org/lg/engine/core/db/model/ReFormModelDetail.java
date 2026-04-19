package org.lg.engine.core.db.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class ReFormModelDetail {

    private Long id;


    private Long gmtCreate;


    private Long gmtModified;


    private Long formModelId;


    private String fieldName;


    private String fieldKey;


    private String fieldCptType;


    private Integer status;


    private Integer flags;
}