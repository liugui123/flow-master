package org.lg.engine.core.client.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class SaveDeFormModelDetailRequest implements Serializable {


    @NotNull(message = "表单字段名字不能为空")
    private String fieldName;


    @NotNull(message = "表单字段标识不能为空")
    private String fieldKey;


    private String fieldCptType;

}
