package org.lg.engine.core.listener.dispatcher;

import org.lg.engine.core.listener.event.WfEvent;

public interface WfEventDispatcher {

    void dispatchEvent(WfEvent event);

}
