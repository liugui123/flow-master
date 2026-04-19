package org.lg.engine.core.listener.event;

public class WfEventBuilder {
    /**
     * 创建事件实例
     */
    public static WfEvent createEvent(WfEngineEventType type,
                                      Long procinstId,
                                      Long taskId,
                                      Long actinstId) {
        return new WfEngineEventImpl(type, procinstId, actinstId, taskId);
    }
}
