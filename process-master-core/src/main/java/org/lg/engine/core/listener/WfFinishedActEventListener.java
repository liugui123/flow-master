package org.lg.engine.core.listener;

import org.lg.engine.core.listener.event.WfEngineEventImpl;
import org.lg.engine.core.listener.event.WfEvent;

public abstract class WfFinishedActEventListener implements WfEventListener {


    @Override
    public void onEvent(WfEvent event) {
        if (event instanceof WfEngineEventImpl) {
//do something
        }
        onSubEvent(event);
    }

    protected abstract void onSubEvent(WfEvent event);
}
