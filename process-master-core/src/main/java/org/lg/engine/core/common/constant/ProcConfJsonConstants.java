package org.lg.engine.core.common.constant;


import org.lg.engine.core.client.enumerate.BeforSignBackTypeEnum;

public class ProcConfJsonConstants {

    public static final String PROC_CONF_JSON = "procConfJson";
    public static final String PROCESS_GENERAL_CONF = "processGeneralConf";
    public static final String ENABLE_Revoke = "enableRevoke";
    public static final String ENABLE_AUTO_PASS_ON_SELF_STARTER = "enableAutoPassOnSelfStarer";
    public static final String ENABLE_AUTO_PASS_ON_SELF_PASSER = "enableAutoPassOnSelfPasser";

    public static final String VERSION_CONF = "versionConf";
    public static final String USER_VERSION = "userVersion";

    public static final String CONDITION_CONF = "conditionConf";
    public static final String FORM_CONF = "formConf";
    public static final String FIELD_KEY = "fieldKey";
    public static final String FIELD_NAME = "fieldName";
    public static final String FIELD_TYPE = "fieldType";
    public static final String FIELD_TYPE_CN = "fieldTypeCn";

    public static final String CONDITION_PROC_STARTER = "__starter";

    public static final String CONDITION_PROC_HANDLER = "__handler";

    public static final String CONDITION_PROC_STARTER_DEPT = "__starter_dept";

    public static final String CONDITION_PROC_HANDLER_DEPT = "__handler_dept";

    public static final String CONDITION_PROC_STARTER_ORG = "__starter_org";

    public static final String CONDITION_PROC_HANDLER_ORG = "__handler_org";


    public static final String CONDITION_BELONGTO_RANK = "belongto_rank";

    /**
     * 前加签默认返回类型
     */
    public static final BeforSignBackTypeEnum BEFORE_SIGN_DEFAULT_BACK_TYPE = BeforSignBackTypeEnum.LEVEL_BACK;

}
