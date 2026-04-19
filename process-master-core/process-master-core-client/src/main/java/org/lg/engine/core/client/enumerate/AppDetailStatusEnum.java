package org.lg.engine.core.client.enumerate;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AppDetailStatusEnum {

    
    MOBILE(1,"手机端可见"),
    
    PC(2,"PC端可见"),
    ;

    private Integer id;
    private String name;



}
