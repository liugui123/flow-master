package org.lg.engine.core;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WfThreadPool {
    private volatile static ExecutorService threadPool;

    public static ExecutorService getInstance() {
        if (threadPool == null) {
            synchronized (WfThreadPool.class) {
                if (threadPool == null) {
                    threadPool = new ThreadPoolExecutor(
                            40,
                            40,
                            60L,
                            TimeUnit.MILLISECONDS,
                            new ArrayBlockingQueue<Runnable>(100000),
                            new ThreadFactoryBuilder().setNameFormat("WfThreadPoolThread-%s").build());
                    return threadPool;
                }
            }
        }
        return threadPool;
    }


}
