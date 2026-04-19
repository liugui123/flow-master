package org.lg.engine.portal.common.utils;

import org.lg.engine.core.client.exception.WfException;
import org.lg.engine.core.client.utils.ApiResult;
import org.lg.engine.core.client.utils.WfConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 封装打印日志和抛出异常
 *
 * @author hezhenyang on 2021/8/4
 */
public class ApiCallExceptionUtil {
    private static final Logger logger = LoggerFactory.getLogger(ApiCallExceptionUtil.class);

    public static void ifErrorLogsAndThrowApiException(ApiResult apiResult, String errmsg) {
        if (!apiResult.isSuccess()) {
            logger.error(WfConstant.WF_LOG_PREFIX + errmsg + ":msg:{},code:{}", apiResult.getMsg(), apiResult.getCode());
            throw new WfException(errmsg, apiResult.getCode());
        }
    }
}
