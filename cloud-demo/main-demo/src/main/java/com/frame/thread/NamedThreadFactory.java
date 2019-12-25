package com.frame.thread;

import java.util.concurrent.ThreadFactory;

public class NamedThreadFactory implements ThreadFactory {

    private String prefix;

    private int seq = 0;

    public NamedThreadFactory(String threadNamePrefix) {
        prefix = threadNamePrefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, prefix + "-" + (seq++));
    }
}
