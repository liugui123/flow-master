package org.lg.engine.core.listener.event;

import lombok.Getter;

@Getter
public class WfEngineEventImpl extends WfEventImpl {


    protected final Long procinstId;


    protected final Long taskId;


    protected final Long actinstId;

    public WfEngineEventImpl(WfEngineEventType type, Long procinstId, Long actinstId, Long taskId) {
        super(type);
        this.procinstId = procinstId;
        this.taskId = taskId;
        this.actinstId = actinstId;
    }


}
