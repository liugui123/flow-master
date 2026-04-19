package org.lg.engine.core.client.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * liugui
 * @Date 2022/4/14
 */
@Getter
@AllArgsConstructor
public enum BeforSignBackTypeEnum {

    LEVEL_BACK("level_back", "逐级返回"),
    TOP_BACK("top_back", "返回顶层");

    private String code;

    private String desc;

    public static BeforSignBackTypeEnum getByCode(String code) {
        for (BeforSignBackTypeEnum beforSignBackType : BeforSignBackTypeEnum.values()) {
            if (beforSignBackType.getCode().equals(code)) {
                return beforSignBackType;
            }
        }
        return null;
    }
}
