package org.lg.engine.core.client.utils;

public class ErrorCode {
    public final int code;
    public final String msg;

    public ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"code\":").append(this.code);
        sb.append(",\"msg\":\"").append(this.msg).append('"');
        sb.append('}');
        return sb.toString();
    }
}
