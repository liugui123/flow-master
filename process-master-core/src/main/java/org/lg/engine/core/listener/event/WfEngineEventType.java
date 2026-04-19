package org.lg.engine.core.listener.event;


public enum WfEngineEventType implements WfEventType {

    USER_TASK_COMPLETED,

    AUTO_ACT_COMPLETED,

    MANUAL_ACT_COMPLETED,

    PROCINST_COMPLETED,

    PROCINST_ABORTED,

    PROCINST_STARTED,

    PROCINST_ERROR,

    PROCINST_REFUSE,

    ACT_BACK,

    ACT_REVOKE,

    ACT_CANCEL,

    ACT_RETRY
}
