package com.frame.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 简化的线程池
 * @author: smile
 * @date: 2019/04/09
 */
public class SimpleMultiThreadExecutor extends ThreadPoolExecutor {

    /**
     * 初始化
     * @param threadName 线程名称
     * @param corePoolCount 核心线程数
     * @param maxThreadCount 最大线程数
     * @param maxQueueSize 最大队列数
     */
    public SimpleMultiThreadExecutor(String threadName, int corePoolCount, int maxThreadCount, int maxQueueSize) {
        super(corePoolCount, maxThreadCount, 60L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(maxQueueSize),
                new NamedThreadFactory(threadName), new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
