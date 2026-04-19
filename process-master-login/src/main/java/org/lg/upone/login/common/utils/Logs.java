package org.lg.upone.login.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Logs {
    private static final Logger logger = LoggerFactory.getLogger(Logs.class);

    public static void info(String message, Object... params) {
        logger.info(message, params);
    }

    public static void debug(String message, Object... params) {
        logger.debug(message, params);
    }

    public static void warn(String message, Object... params) {
        logger.warn(message, params);
    }

    public static void error(String message, Object... params) {
        logger.error(message,params);
    }
}
