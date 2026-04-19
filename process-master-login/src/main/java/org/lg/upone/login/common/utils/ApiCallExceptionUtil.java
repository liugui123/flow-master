package org.lg.upone.login.common.utils;

import org.lg.engine.core.client.exception.WfException;
import org.lg.engine.core.client.utils.ApiResult;
import org.lg.upone.login.common.constant.Constant;

/**
 * 封装打印日志和抛出异常
 *
 * @author hezhenyang on 2021/8/4
 */
public class ApiCallExceptionUtil {
    public static void ifErrorLogsAndThrowApiException(ApiResult apiResult, String errmsg){
        if(!apiResult.isSuccess()) {
            Logs.error(Constant.UPONE_FORM_LOG_PREFIX +errmsg+":msg:{},code:{}",apiResult.getMsg(),apiResult.getCode());
            throw new WfException(errmsg,apiResult.getCode());
        }
    }
}
