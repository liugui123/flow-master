package org.lg.engine.core.listener.dispatcher;

import org.lg.engine.core.client.exception.WfExceptionCode;
import org.lg.engine.core.client.utils.Assert;
import org.lg.engine.core.client.utils.Utils;
import org.lg.engine.core.listener.*;
import org.lg.engine.core.listener.event.WfEngineEventType;
import org.lg.engine.core.listener.event.WfEvent;
import org.lg.engine.core.listener.event.WfEventType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class WfEventDispatcherImpl implements WfEventDispatcher {

    protected final Map<WfEventType, List<WfEventListener>> typedListeners;

    public WfEventDispatcherImpl() {
        typedListeners = new HashMap<>();
        addEventListener(new WfFinishedUserTaskEventListener(), WfEngineEventType.USER_TASK_COMPLETED);
        addEventListener(new WfAutoFinishedActEventListener(), WfEngineEventType.AUTO_ACT_COMPLETED);
        addEventListener(new WfManualFinishedActEventListener(), WfEngineEventType.MANUAL_ACT_COMPLETED);
        addEventListener(new WfFinishedProcinstEventListener(), WfEngineEventType.PROCINST_COMPLETED);
        addEventListener(new WfEndProcinstEventListener(), WfEngineEventType.PROCINST_ABORTED);
        addEventListener(new WfGoBackActEventListener(), WfEngineEventType.ACT_BACK);
        addEventListener(new WfRevokeActEventListener(), WfEngineEventType.ACT_REVOKE);
        addEventListener(new WfRetryActEventListener(), WfEngineEventType.ACT_RETRY);
        addEventListener(new WfCancelActEventListener(), WfEngineEventType.ACT_CANCEL);
        addEventListener(new WfStartedProcinstEventListener(), WfEngineEventType.PROCINST_STARTED);
        addEventListener(new WfErrorProcinstEventListener(), WfEngineEventType.PROCINST_ERROR);
        addEventListener(new WfRefuseProcinstEventListener(), WfEngineEventType.PROCINST_REFUSE);
    }

    @Override
    public void dispatchEvent(WfEvent event) {
        List<WfEventListener> typed = typedListeners.get(event.getType());
        Assert.isTrue(Utils.isNotEmpty(typed),
                WfExceptionCode.EVENT_TYPE_BLANK.getMsg(),
                WfExceptionCode.EVENT_TYPE_BLANK.getCode()
        );
        for (WfEventListener listener : typed) {
            dispatchNormalEventListener(event, listener);
        }
    }

    protected void dispatchNormalEventListener(WfEvent event, WfEventListener listener) {
        listener.onEvent(event);
    }


    private void addEventListener(WfEventListener listenerToAdd, WfEventType... types) {
        for (WfEventType type : types) {
            addTypedEventListener(listenerToAdd, type);
        }
    }

    protected void addTypedEventListener(WfEventListener listener, WfEventType type) {
        List<WfEventListener> listeners = typedListeners.get(type);
        if (listeners == null) {
            listeners = new CopyOnWriteArrayList<>();
            typedListeners.put(type, listeners);
        }

        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }
}
