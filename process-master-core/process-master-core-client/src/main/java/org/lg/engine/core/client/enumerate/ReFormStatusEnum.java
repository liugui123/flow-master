package org.lg.engine.core.client.enumerate;


import lombok.Getter;

@Getter
public enum ReFormStatusEnum {

    
    ENABLE(0,"启用"),
    
    DISABLED(1,"停用"),
    ;

    private Integer status;
    private String desc;


    ReFormStatusEnum(int status, String desc) {
        this.status =status;
        this.desc =desc;
    }
}
