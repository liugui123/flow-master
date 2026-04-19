package org.lg.engine.core.client.utils;

public interface I18nMessageLoader {
    Noop NOOP = new Noop();

    String getMessage(String var1, String var2, Object... var3);

    default String getMessage(ErrorCode errorCode, Object... args) {
        return this.getMessage(errorCode.toString().toLowerCase(), errorCode.msg, args);
    }

    default String getMessage(int errorCode, Object... args) {
        return this.getMessage("ErrorCode." + errorCode, "", args);
    }

    public static class Noop implements I18nMessageLoader {
        public Noop() {
        }

        public String getMessage(String errorCode, String defaultMsg, Object... args) {
            return defaultMsg;
        }
    }
}
