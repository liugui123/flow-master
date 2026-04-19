/**
 * 我发起的任务查询入参
 *
 * liugui
 * @date 2021/06/04 15:29
 **/
package org.lg.engine.core.client.model.request;

import lombok.Getter;
import lombok.Setter;
import org.lg.engine.core.client.utils.Query;

@Getter
@Setter
public class MyStartDraftRequest extends Query {
    /**
     * 草稿标题
     */
    private String draftTitle;
}
