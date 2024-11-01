package com.ji.jichat.common.util;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class GuardedObjectTest {

    @Test
    void testGuardedObject() throws InterruptedException {
        String key = "testKey";
        GuardedObject<String> guardedObject = GuardedObject.create(key);

        // 启动一个异步线程，模拟事件触发
        new Thread(() -> {
            try {
                Thread.sleep(500); // 模拟异步操作
                GuardedObject.fireEvent(key, "TestEvent");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // 在主线程中等待事件触发
        String result = guardedObject.getAndThrow(obj -> obj != null && obj.startsWith("Test"), 5, java.util.concurrent.TimeUnit.SECONDS);

        // 验证结果
        assertNotNull(result);
        assertTrue(result.startsWith("Test"));
    }

    @Test
    void testTimeout() {
        String key = "timeoutKey";
        GuardedObject<String> guardedObject = GuardedObject.create(key);

        // 在主线程中等待事件触发，但设置一个很短的超时时间
        assertThrows(InterruptedException.class, () ->
                guardedObject.getAndThrow(obj -> obj != null, 1, TimeUnit.SECONDS)
        );
    }

    @Test
    void testChangeEvent() throws InterruptedException {
        String key = "changeEventKey";
        GuardedObject<String> guardedObject = GuardedObject.create(key);

        // 启动一个异步线程，模拟事件触发
        new Thread(() -> {
            try {
                Thread.sleep(500); // 模拟异步操作
                GuardedObject.fireEvent(key, "TestChangeEvent");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // 在主线程中等待事件触发
        String result = guardedObject.getAndThrow(obj -> obj != null && obj.startsWith("Test"), 5, java.util.concurrent.TimeUnit.SECONDS);

        // 验证结果
        assertNotNull(result);
        assertEquals("TestChangeEvent", result);
    }


    private final Map<String, String> map = new HashMap<>();

    @Test
    public void testBatch() throws InterruptedException {
        ConcurrentTestUtil.runConcurrentTest(5, () -> {
            final String key = IdUtil.objectId();
            ThreadUtil.execAsync(() -> fireEvent(key));
            final GuardedObject<JSONObject> guardedObject = GuardedObject.create(key);
            JSONObject target = null;
            try {
                target = guardedObject.getAndThrow(t -> Objects.equals(key, t.getString("key")), 3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Assertions.assertEquals(map.get(key), target.getString("value"));
        });


    }

    private void fireEvent(String key) {
        ThreadUtil.sleep(2, TimeUnit.SECONDS);
        String value = IdUtil.nanoId(12);
        map.put(key, value);
        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("key", key);
        jsonObject.put("value", value);
        GuardedObject.fireEvent(key, jsonObject);
    }
}
