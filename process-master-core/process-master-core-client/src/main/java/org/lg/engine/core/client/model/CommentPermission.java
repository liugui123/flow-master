package org.lg.engine.core.client.model;

import lombok.Getter;
import lombok.Setter;

/**
 * liugui
 * @Date 2021/10/29
 */
@Getter
@Setter
public class CommentPermission {

    /**
     * 是否能查看其它节点的意见反馈
     */
    private Boolean otherActInstCommentVisible;
    /**
     * 是否能查看本节点其它处理人的意见反馈
     */
    private Boolean otherHandlerCommentVisible;
}
