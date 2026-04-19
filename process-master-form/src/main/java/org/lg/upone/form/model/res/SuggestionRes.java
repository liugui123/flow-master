package org.lg.upone.form.model.res;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuggestionRes {

    /**
     *
     */
    private Long gmtCreate;

    /**
     *
     */
    private Long gmtModified;
    /**
     * 内容
     */
    private String content;

    /**
     * 标题
     */
    private String title;

    /**
     * 操作人id
     */
    private String operatorId;

    /**
     * 操作人名字
     */
    private String operatorName;
}
