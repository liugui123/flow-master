package org.lg.engine.core.client.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * liugui
 * @Date 2022/2/25
 */
@AllArgsConstructor
@Getter
public enum ActinstUrgeLevelEnum {


    ALL(0, "全部"),
    ONE(1, "特急"),

    TWO(2, "急件"),

    THREE(3, "一般"),
    ;
    private Integer id;
    private String name;

    public static ActinstUrgeLevelEnum getById(Integer id) {
        return Arrays.stream(ActinstUrgeLevelEnum.values()).filter(v -> v.getId().equals(id)).findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("不支持的枚举类型:" + id));
    }
}
