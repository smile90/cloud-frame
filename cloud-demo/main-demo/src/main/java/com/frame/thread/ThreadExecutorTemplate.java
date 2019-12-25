package com.frame.thread;


import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class ThreadExecutorTemplate {

    /**
     * 执行体
     *
     * @param threadFlag     用于线程池标识
     * @param corePoolCount  核心池计数
     * @param maxThreadCount 最大线程数
     * @param maxQueueSize   最大队列大小
     * @param ws             操作数据
     * @param dealer         业务代码
     */
    public static <W> void execute(String threadFlag, int corePoolCount, int maxThreadCount, int maxQueueSize,
                                   List<W> ws,
                                   Dealer<W> dealer) {
        //取到对应的线程池
        SimpleMultiThreadExecutor simpleMultiThreadExecutor =
                new SimpleMultiThreadExecutor(threadFlag, corePoolCount, maxThreadCount, maxQueueSize);
        //设置 CountDownLatch
        CountDownLatch latch = new CountDownLatch(ws.size());
        //遍历集合 parallel() 并行执行
        ws.stream().parallel().forEach(w -> {
            if (w != null && !"".equals(w)) {
                try {
                    //创建线程，执行业务代码
                    simpleMultiThreadExecutor.submit(new ThreadTemplate<W>(threadFlag, w, latch, dealer));
                } catch (Exception e) {
                    log.error(threadFlag + " Exception error; ", e);
                }
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            log.error(threadFlag + " latch.await() InterruptedException; ", e);
        }
        log.info(threadFlag + " end ... date:" + new Date());
    }

    /**
     * 线程模版
     *
     * @param <W>
     */
    static class ThreadTemplate<W> extends Thread {
        private W w;
        private CountDownLatch latch;
        private Dealer<W> dealer;

        public ThreadTemplate(String threadFlag, W w, CountDownLatch latch, Dealer<W> dealer) {
            super(threadFlag);
            this.w = w;
            this.latch = latch;
            this.dealer = dealer;
        }

        @Override
        public void run() {
            try {
                dealer.deal(w);
            } catch (Exception e) {
                log.error("ThreadTemplate.run() error;", e);
            } finally {
                latch.countDown();    //使latch状态减1，最终减至0
            }
        }
    }

    /**
     * 执行函数
     *
     * @param <W>
     */
    @FunctionalInterface
    public interface Dealer<W> {
        void deal(W w);
    }
}
