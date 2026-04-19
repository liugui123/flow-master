package org.lg.engine.core.client.utils;

public interface Results<T> {
    int getCode();

    T getData();

    String getMsg();
}
