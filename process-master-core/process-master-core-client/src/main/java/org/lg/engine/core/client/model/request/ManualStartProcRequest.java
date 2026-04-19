package org.lg.engine.core.client.model.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ManualStartProcRequest extends StartProcRequest {

    @NotNull(message = "手动启动传递的节点和处理人信息不能为空")
    @Valid
    List<ManualStartInfo> manualStartInfo;
}
