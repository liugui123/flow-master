package org.lg.engine.core.client.utils;


import org.lg.engine.core.client.exception.WfException;

/**
 * liugui
 * @date 2021/08/11 15:16
 **/
public class Assert {
    /**
     * 弹出并记录具体日志
     *
     * liugui
     * @date 2021/08/11 15:23
     * @param: notEmpty 是否满足弹出条件
     * @param: msg 异常信息
     * @param: code 编码
     * @param: logMsg 异常日志信息
     * @param: args 异常日志信息入参
     */
    public static void isTrue(boolean notAssert, String msg, Integer code, String logMsg, Object... args) {
        if (!notAssert) {
           // log.error(WfConstant.WF_LOG_PREFIX + logMsg, args);
            throw new WfException(code, msg);
        }
    }

    public static void isTrue(boolean notAssert, String msg, Integer code) {
        if (!notAssert) {
            // log.error(WfConstant.WF_LOG_PREFIX + msg);
            throw new WfException(code, msg);
        }
    }

    public static void isTrue(boolean notAssert, String msg) {
        if (!notAssert) {
            // log.error(WfConstant.WF_LOG_PREFIX + msg);
        }
        if (!notAssert) {
            throw new WfException(-9008, msg);
        }
    }

    public static void notNull(Object o, String msg) {
        if (o==null) {
            // log.error(WfConstant.WF_LOG_PREFIX + msg);
            throw new WfException(-9008, msg);
        }

    }
}
