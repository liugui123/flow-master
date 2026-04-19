package org.lg.engine.core.client.utils;

import java.util.regex.Pattern;

public class ApiException extends RuntimeException {
    private static final Pattern PATTERN = Pattern.compile("\\$\\{(\\w+)}");
    private static final long serialVersionUID = 4666861106427943972L;
    private int code = 500;
    private StringBuilder logMsg = new StringBuilder();

    public ApiException() {
    }

    public ApiException(Throwable e) {
        super(e);
    }

    public ApiException(ErrorCode errorCode) {
        super(ApiResult.messageLoader().getMessage(errorCode, new Object[0]), (Throwable)null, false, false);
        this.code = errorCode.code;
    }

    public ApiException(ErrorCode errorCode, Object... args) {
        super(ApiResult.messageLoader().getMessage(errorCode, args), (Throwable)null, false, false);
        this.code = errorCode.code;
    }

    public ApiException(int errorCode, Object... args) {
        super(ApiResult.messageLoader().getMessage(errorCode, args), (Throwable)null, false, false);
        this.code = errorCode;
    }

    public ApiException(String message) {
        super(message, (Throwable)null, false, false);
    }

    public ApiException(String message, int code) {
        super(message, (Throwable)null, false, false);
        this.code = code;
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause, false, false);
    }

    public ApiException(String message, int code, Throwable cause) {
        super(message, cause, false, false);
        this.code = code;
    }

    public ApiException(Throwable cause, String message, int code) {
        super(message, cause);
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLogMsg() {
        return this.logMsg.toString();
    }

    public ApiException log(Object... logs) {
        if (logs != null && logs.length != 0) {
            if (logs.length == 1) {
                this.logMsg.append(logs[0]);
            } else {
                Object[] var2 = logs;
                int var3 = logs.length;

                for(int var4 = 0; var4 < var3; ++var4) {
                    Object o = var2[var4];
                    this.logMsg.append(o);
                }
            }

            return this;
        } else {
            return this;
        }
    }

    public ApiException log(String format, final Object... args) {
        if (format == null) {
            return this;
        } else {
            this.logMsg.append(args.length == 0 ? format : Strings.replace(format, PATTERN, new Strings.ReplaceCallback() {
                public String replace(String text, int index) {
                    return index > args.length ? text : String.valueOf(args[index]);
                }
            }));
            return this;
        }
    }
}
