package org.lg.engine.core.listener.event;

public class WfEventImpl implements WfEvent {

    protected WfEventType type;

    public WfEventImpl(WfEngineEventType type) {
        this.type = type;
    }

    @Override
    public WfEventType getType() {
        return type;
    }
}
