package org.lg.engine.core.client.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 节点催办提醒类型配置
 *
 * liugui
 * @Date 2022/2/15
 */
@AllArgsConstructor
@Getter
public enum ActInstUrgeTypeEnum {


    AUTO_REMIND(1, "自动提醒"),
    AUTO_COMMIT(2, "自动提交"),
    AUTO_BACK(4, "自动退回"),
    AUTO_DELIVER(8, "自动转交"),
    ;
    private Integer code;
    private String name;

    public static ActInstUrgeTypeEnum getByCode(Integer code) {

        return Arrays.stream(ActInstUrgeTypeEnum.values()).filter(v -> v.getCode().equals(code)).findFirst()
                .get();


    }
}
