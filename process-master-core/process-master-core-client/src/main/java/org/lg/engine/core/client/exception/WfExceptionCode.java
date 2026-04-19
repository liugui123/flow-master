package org.lg.engine.core.client.exception;

import lombok.Getter;


@Getter
public enum WfExceptionCode {

    PARAMTER_ERROR(400, "参数错误"),

    TASK_BLANK(-101, "任务不存在或已结束"),

    USER_TASK_BLANK(-102, "用户任务不存在或已结束"),

    ACTINST_BLANK(-103, "流程节点实例不存在或已结束"),

    PROCINST_BLANK(-104, "流程实例不存在或已结束"),

    HI_PROCINST_BLANK(-153, "历史流程实例不存在"),

    HI_RU_PROCINST_BLANK(-153, "历史或运行流程实例不存在"),

    ACT_PROPERTIES_BLANK(-105, "节点属性不存在"),

    ACT_CONF_BLANK(-106, "节点配置不存在"),

    PROCESS_CHILD_SHAPE_BLANK(-107, "流程节点不存在"),

    SUB_PROCESS_CONF_BLANK(-108, "子流程配置信息不存在"),


    ASSIGNEE_BLANK(-109, "处理人员和候选人为空"),


    PROCESS_RE_BLANK(-110, "发布流程定义不存在"),


    PROCESS_DE_BLANK(-111, "流程定义不存在"),


    EVENT_TYPE_BLANK(-112, "事件不存在"),


    USER_ID_BLANK(-113, "用户ID不存在"),

    USER_NAME_BLANK(-114, "用户名称不存在"),

    ORG_ID_BLANK(-115, "组织ID不存在"),

    APP_BLANK(-116, "管理应用不存在"),

    NEXT_USER_ACT_BLANK(-118, "下个提交节点不存在"),

    NEXT_USER_ACT_CAN_NOT_FOUND(-119, "下个提交节点不存在"),

    HI_TASK_BLANK(-120, "历史任务不存在"),

    COMMAND_CONTEXT_BLANK(-121, "应用上下文为空"),

    DE_PROC_START_ACT_BLANK(-122, "流程开始节点不存在"),


    ADD_ASSIGNEE_BLANK(-123, "添加的处理人不存在"),

    MANUAL_ASSIGNEE_BLANK(-124, "手动选择的处理人不存在"),


    APP_ID_BLANK(-125, "应用ID不存在"),


    USER_TASK_AND_HI_BLANK(-126, "用户任务或历史用户任务不存在"),


    EXIST_MODEL_KEY(-127, "草稿模型标识已经存在"),

    REVOKE_AUTH_AGENT_BLANK(-128, "需要撤销的授权记录不存在"),

    MANUAL_COMPLETE_ACT_ASSIGNEE_BLANK(-129, "手动处理节点和人员不存在"),

    GET_PROC_STARTER_ERROR(-130, "获取发起人信息失败"),

    GET_PROC_HANDLER_ERROR(-131, "获取处理人信息失败"),

    PARENT_USER_TASK_BLANK(-132, "用户任务父任务不存在"),

    REVOKE_AUTH_AGENT_INVALID(-133, "仅能撤销未失效的授权记录"),

    NEED_ACTIVE_USER_TASK_BLANK(-134, "需要恢复的用户任务不存在"),

    REVOKE_AUTH_AGENT_TIME_INVALID(-135, "时间与已有记录冲突，请重新选择"),

    MANUAL_SOURCE_SEC_FLOW_ILLEGAL(-136, "手动选择节点信息上游节点非法"),

    ILLEGAL_TASK_USER_KEY(-137, "无效的任务标识"),

    ILLEGAL_APP_KEY(-138, "无效的应用标识"),

    ILLEGAL_ACT_KEY(-139, "无效的流程节点标识"),

    CUSTOM_CONDITION_BLANK(-140, "自定义条件为空"),

    ACT_PROPERTIES_NAME_BLANK(-141, "节点属性名称不存在"),
    ACT_PROPERTIES_RESOURCE_ID_BLANK(-142, "节点属性ID不存在"),
    ACT_PROPERTIES_ACT_TYPE_BLANK(-143, "节点属性名称不存在"),

    API_CONDITION_BLANK(-144, "接口条件为空"),

    API_CONDITION_FIELD_INVALID(-145, "接口条件字段配置有误"),

    GET_ORG_AREA_TYPE_ERROR(-146, "获取企业类型失败"),

    TASK_ACT_ID_BLANK(-147, "任务对应的节点id不存在"),
    ACT_URGE_REMIND_ASSIGNEE_BLANK(-148, "提醒人员为空"),

    READ_ACT_NOT_REL_GATEWAY(-149, "分发节点下无汇聚节点"),

    ACT_CUSTOM_CONDITION_GROUP_EMPTY(-150, "节点自定义条件组为空"),

    DE_FORM_MODEL_EMPTY(-151, "表单模型不存在"),


    DE_SERVICE_MODEL_EMPTY(-152, "服务模型不存在"),

    DE_SERVICE_MODEL_URL_EMPTY(-153, "服务模型中地址不存在"),

    SAVE_START_DRAFT_DATA_KEY_EMPTY(-154, "保存表单草稿的数据key不存在"),
    RE_FORM_MODEL_EMPTY(-155, "发布表单模型不存在"),

    DE_APP_DETAIL_EMPTY(-156, "应用详情不存在"),

    REPEAT_FORM_MODEL_KEY(-157, "重复的表单标识"),
    REPEAT_SERVICE_MODEL_KEY(-158, "重复的接口标识"),

    ILLEGAL_PARENT_KEY(-159, "不能识别的父节点"),
    ILLEGAL_USER_EMPTY(-160, "用户信息不存在"),


    TASK_DESC_BACK_ACT_NOT_FINISHED(-201, "退回节点未完成，不支持退回"),

    PROCESS_LOOP_DEPENDENCY(-202, "父子流程循环依赖错误"),

    TOO_MANY_SUB_PROCESS_DEPENDENCY(-203, "子流程嵌套过多"),

    PROCINST_DELIVER_LIMIT(-204, "流程用户任务批量转交人员必须是单个人"),

    RECALL_NOT_SUP_ACTINST_DONE(-205, "节点已经处理，不支持撤回"),

    REVOKE_NOT_SUP_ACTINST_DONE(-206, "当前节点已经处理，不支持撤销"),

    RETRY_NOT_SUP_ACTINST_NOT_RUNNING(-207, "流程节点实例没有正在运行，不允许操作"),

    OPERATOR_ERROR(-208, "操作人员不是任务处理人，不允许处理"),

    EXIST_SUB_TASK_ERROR(-209, "当前任务存在子任务信息，请先完成子任务"),

    SUB_PROCESS_NOT_SUP(-210, "暂时不支持子流程"),

    ACT_TYPE_NOT_SUP(-211, "不支持的节点类型"),

    HI_USER_TASK_NULL(-214, "历史用户任务为空"),

    APP_DELETE_ERROR(-215, "分组下有应用，不允许删除"),

    USER_NOT_ACCESSABLE(-216, "当前用户无法发起该流程"),

    SIGN_TYPE_ERROR(-217, "加签类型错误"),

    REVOKE_ERROR_PROCINST_NOT_RUNNING(-218, "流程实例不在运行中不允许撤销"),

    FORM_USER_ERROR(-219, "表单人员数据非法"),

    USER_REPEAT_START_PROCESS(-210, "用户重复发起流程"),

    START_PROCESS_NOT_IN_EFFECT_TIME(-221, "不在流程发起有效期内"),

    DEL_AUTH_AGENT_INVALID(-222, "仅能删除已失效的授权记录"),

    ACT_NOT_PENDING(-223, "节点已经处理"),

    CANCEL_ERROR_PROCINST_NOT_START_NODE(-224, "流程非开始节点不允许注销"),

    ILLEGAL_APP_DETAIL_KEY(-225, "不允许使用的应用标识，请更换"),
    BACK_ACT_HI_USER_EMPTY(-226, "退回节点历史处理人为空"),

    REPEAT_SUBMIT(-227, "请勿重复提交"),


    CALL_TODO_CENTER_INSERT_TASK_ERROR(-301, "待办中心加入待办错误"),

    CALL_TODO_CENTER_INSERT_CPOY_TASK_ERROR(-302, "待办中心加入抄送错误"),

    CALL_TODO_CENTER_BATCH_FINISH_TASK_ERROR(-303, "待办中心批量完成待办错误"),

    CALL_TODO_CENTER_BATCH_DEL_TASK_ERROR(-304, "批量删除待办中心任务错误"),

    CALL_OPENLAB_EVENT_LIST_ERROR(-305, "查询开放平台事件列表接口错误"),

    CALL_EVENT_CENTER_INSERT_EVENT_ERROR(-306, "事件中心持久化事件错误"),

    CALL_GET_USERLAB_ERROR(-307, "获取用户标签失败"),

    CALL_BATCH_GET_USERS_BY_LABEL_ERROR(-308, "根据标签获取用户失败"),


    CAL_GETLEADERS_ERROR(-309, "获取用户上级信息报错"),
    CALL_CONDITION_API_ERROR(-310, "条件判断调用三方接口失败"),
    CALL_GETORGGROUP_ERROR(-311, "获取企业类型接口失败"),
    CALL_GETUSER_DEPTIDS_ERROR(-312, "获取用户部门列表失败"),
    CALL_GETDEPTNAMEMAPBYDEPTIDS_ERROR(-313, "批量根据部门id获取部门名称失败"),
    CALL_USER_API_ERROR(-314, "调用三方用户接口失败"),
    CALL_API_ERROR(-315, "调用三方接口失败"),
    CALL_API_RESULT_ERROR(-316, "获取三方接口数据失败"),
    CALL_BATCH_GET_USERS_BY_SUP_ERROR(-317, "根据上下级获取用户失败"),

    AUTH_AGENT_ASYNC_GX_ERROR(-401, "外出授权同步广西MOA错误"),

    GET_BEAN_ERROR(-501, "获取bean失败");

    public final Integer code;
    public final String msg;

    WfExceptionCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
