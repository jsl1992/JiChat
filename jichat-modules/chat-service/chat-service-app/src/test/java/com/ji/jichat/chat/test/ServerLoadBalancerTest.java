package com.ji.jichat.chat.test;

import com.ji.jichat.chat.kit.ServerLoadBalancer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ServerLoadBalancerTest {

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ZSetOperations<String, String> zSetOperations;

    @InjectMocks
    private ServerLoadBalancer serverLoadBalancer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetServer() {
        // Mock Redis response
        // 模拟从 Redis 中获取的服务器和连接数的数据
        Set<ZSetOperations.TypedTuple<String>> servers = new HashSet<>();
        servers.add(new DefaultTypedTuple<>("server1", 5.0));
        servers.add(new DefaultTypedTuple<>("server2", 10.0));
        servers.add(new DefaultTypedTuple<>("server3", 8.0));
        when(redisTemplate.opsForZSet()).thenReturn(zSetOperations);
        when(zSetOperations.rangeWithScores(anyString(), anyLong(), anyLong())).thenReturn(servers);

        // Execute
        // 调用要测试的逻辑
        String selectedServer = serverLoadBalancer.getServer();

        // Verify
        // 验证实际结果与预期结果是否一致
        assertEquals("server1", selectedServer);
        // 验证是否正确调用了模拟的 Redis 操作
        verify(redisTemplate.opsForZSet()).rangeWithScores(anyString(), anyLong(), anyLong());
    }


    @Test
    void testIncrementServerClientCount() {
        // Mock Redis response
        when(redisTemplate.opsForZSet()).thenReturn(zSetOperations);

        // Execute
        serverLoadBalancer.incrementServerClientCount("server1");

        // Verify
        verify(zSetOperations).incrementScore(anyString(), anyString(), eq(1.0));
    }

    @Test
    void testSubServerClientCount() {
        // Mock Redis response
        when(redisTemplate.opsForZSet()).thenReturn(zSetOperations);

        // Execute
        serverLoadBalancer.subServerClientCount("server1");

        // Verify
        verify(zSetOperations).incrementScore(anyString(), anyString(), eq(-1.0));
    }

    @Test
    void testSyncServer() {
        // Mock Redis response
        Set<String> cacheServers = new HashSet<>(Arrays.asList("server1", "server2", "server3"));
        when(redisTemplate.opsForZSet()).thenReturn(zSetOperations);
        when(zSetOperations.range(anyString(), anyLong(), anyLong())).thenReturn(cacheServers);

        // Execute
        serverLoadBalancer.syncServer(Arrays.asList("server2", "server3", "server4"));

        // Verify
        verify(zSetOperations, times(1)).remove(anyString(), anyString());
        verify(zSetOperations).add(anyString(), anyString(), eq(0.0));
    }
}
