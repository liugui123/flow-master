package org.lg.engine.core.client.enumerate;

import lombok.Getter;
import org.lg.engine.core.client.exception.WfException;

/**
 * 流程节点类型
 */
@Getter
public enum ActInstTypeEnum {
    /**
     * 线
     */
    SEQUENCE_FLOW("SequenceFlow", 0),

    /**
     * 开始节点
     */
    START_EVENT("StartNoneEvent", 1),

    /**
     * 服务节点
     * 这个节点注册外部服务接口，并在到达这个节点时尝试调用
     */
    SERVICE("Service", 2),

    /**
     * 用户节点
     */
    USER_TASK("UserTask", 3),

    /**
     * 分发节点
     */
    READ("Read", 4),

    /**
     * 抄送节点
     */
    COPY("Copy", 5),

    /**
     * 包容网关
     */
    INCLUSIVE_GATEWAY("InclusiveGateway", 6),
    /**
     * 分发节点网关
     */
    READ_GATEWAY("ReadGateway", 7),

    /**
     * 结束节点
     */
    END_EVENT("EndNoneEvent", 8),
    ;
    private final String type;

    /**
     * 排序
     */
    private final Integer sort;

    ActInstTypeEnum(String type, Integer sort) {
        this.type = type;
        this.sort = sort;
    }

    // 若要通过 'type' 获取 'sort'
    public static Integer getSortByType(String type) {
        for (ActInstTypeEnum e : ActInstTypeEnum.values()) {
            if (e.getType().equals(type)) {
                return e.getSort();
            }
        }
        throw new WfException("No enum constant found for given " + type + " value.");
    }

}
