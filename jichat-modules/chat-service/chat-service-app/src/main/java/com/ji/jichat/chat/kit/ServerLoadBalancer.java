package com.ji.jichat.chat.kit;

import com.ji.jichat.common.constants.CacheConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class ServerLoadBalancer {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 返回连接数最少的服务节点 （最少连接（Least Connections））
     *
     * @return java.lang.String  服务节点信息
     * @author jisl on 2024/1/21 22:50
     **/
    public String getServer() {
        String selectedServer = null;
        // 获取所有服务器和连接数
        Set<ZSetOperations.TypedTuple<String>> servers = redisTemplate.opsForZSet().rangeWithScores(CacheConstant.CHAT_SERVER_CLIENT_COUNT, 0, -1);
        // 选择连接数最少的服务器
        double minConnections = Double.MAX_VALUE;
        for (ZSetOperations.TypedTuple<String> server : servers) {
            if (server.getScore() < minConnections) {
                minConnections = server.getScore();
                selectedServer = server.getValue();
            }
        }
        return selectedServer;
    }


    public void incrementServerClientCount(String selectedServer) {
        redisTemplate.opsForZSet().incrementScore(CacheConstant.CHAT_SERVER_CLIENT_COUNT, selectedServer, 1);
    }

    public void subServerClientCount(String selectedServer) {
        redisTemplate.opsForZSet().incrementScore(CacheConstant.CHAT_SERVER_CLIENT_COUNT, selectedServer, -1);
    }

    public void syncServer(List<String> curServers) {
        log.info("当前服务列表{}",curServers);
        Set<String> cacheServers = new HashSet<>(redisTemplate.opsForZSet().range(CacheConstant.CHAT_SERVER_CLIENT_COUNT, 0, -1));
        log.info("缓存服务列表{}",cacheServers);
        // 删除在缓存中但不在当前服务器中的元素
        for (String cs : cacheServers) {
            if (!curServers.contains(cs)) {
                redisTemplate.opsForZSet().remove(CacheConstant.CHAT_SERVER_CLIENT_COUNT, cs);
            }
        }
        // 添加在当前服务器中但不在缓存中的元素
        for (String s : curServers) {
//            需要添加
            if (!cacheServers.contains(s)) {
                redisTemplate.opsForZSet().add(CacheConstant.CHAT_SERVER_CLIENT_COUNT, s, 0);
            }
        }
    }

}
