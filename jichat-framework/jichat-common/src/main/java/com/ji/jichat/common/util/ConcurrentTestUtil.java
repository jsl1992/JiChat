package com.ji.jichat.common.util;

import java.util.concurrent.CountDownLatch;

/**
 * 使用 CountDownLatch进行并发测试
 *
 * @author jisl on 2024/1/31 10:29
 **/
public class ConcurrentTestUtil {

    public static void runConcurrentTest(int concurrentThreads, Runnable task) throws InterruptedException {
//        CountDownLatch 维护一个计数器，该计数器在构造时被初始化，然后通过调用 countDown() 方法递减。当计数器的值变为零时，所有在 await() 方法上等待的线程都将被释放，可以继续执行
        CountDownLatch latch = new CountDownLatch(concurrentThreads);

        for (int i = 0; i < concurrentThreads; i++) {
            Thread thread = new Thread(() -> {
                try {
                    task.run(); // 执行传入的任务
                } finally {
                    latch.countDown();
                }
            });
            // 设置线程名
            thread.setName("Thread-" + i);
            thread.start();
        }
        System.out.println("start work");
        latch.await();
        System.out.println("All threads have finished their work.");
    }

    public static void main(String[] args) throws InterruptedException {
        int concurrentThreads = 5;

        ConcurrentTestUtil.runConcurrentTest(concurrentThreads, () -> {
            // 在这里编写你的并发测试逻辑
            System.out.println(Thread.currentThread().getName() + " is working...");
            // 模拟工作耗时
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
