package com.ji.jichat.common.util;

import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

/**
 *
 */
@Slf4j
public class GuardedObject<T> {


    private static final Map<Object, GuardedObject> gos = new ConcurrentHashMap<>();
    private static final int awaitTimeOut = 100;
    private final Lock lock = new ReentrantLock();
    private final Condition done = lock.newCondition();
    private T obj;

    private Object key;

    private GuardedObject(Object key) {
        this.key = key;
    }

    public static <T> GuardedObject<T> create(@NotNull Object key) {
        GuardedObject<T> go = new GuardedObject<>(key);
        gos.put(key, go);
        return go;
    }

    public static boolean containsEvent(Object key) {
        return gos.containsKey(key);
    }

    public static <T> void fireEvent(Object key, T obj) {
        final GuardedObject go = gos.remove(key);
        if (go != null) {
            go.onChange(obj);
        }
    }

    public T getAndThrow(Predicate<T> p, int timeOut) throws InterruptedException {
        return getAndThrow(p, timeOut, TimeUnit.SECONDS);
    }

    public T getAndThrow(Predicate<T> p, int duration, TimeUnit timeUnit) throws InterruptedException {
        try {
            // 获取锁以确保线程安全
            lock.lock();
            // 计算最大等待时间
            final long maxTime = System.currentTimeMillis() + timeUnit.toMillis(duration);
            // 进入循环等待条件满足
            while (obj == null || !p.test(obj)) {
                // 如果超过最大等待时间，中断当前线程
                if (System.currentTimeMillis() > maxTime) {
                    Thread.currentThread().interrupt();
                }
                // 等待信号，带有超时时间
                done.await(awaitTimeOut, TimeUnit.MILLISECONDS);
            }
            // 当条件满足时返回对象
            return obj;
        } finally {
            // 确保释放锁
            lock.unlock();
            // 从映射中移除 GuardedObject 实例
            gos.remove(this.key);
        }
    }

    private void onChange(T obj) {
        lock.lock();
        try {
            this.obj = obj;
            done.signalAll();
        } finally {
            lock.unlock();
        }
    }


}
