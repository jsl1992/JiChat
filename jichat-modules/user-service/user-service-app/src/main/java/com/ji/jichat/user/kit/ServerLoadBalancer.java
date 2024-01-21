package com.ji.jichat.user.kit;

import com.ji.jichat.common.constants.CacheConstant;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
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

    public void syncServer(List<String> curServers) {
        Set<String> cacheServers = new HashSet<>(redisTemplate.opsForZSet().range(CacheConstant.CHAT_SERVER_CLIENT_COUNT, 0, -1));
        // 删除在缓存中但不在当前服务器中的元素
        cacheServers.removeAll(curServers);
        redisTemplate.opsForZSet().remove(CacheConstant.CHAT_SERVER_CLIENT_COUNT, cacheServers.toArray());

        // 添加在当前服务器中但不在缓存中的元素
        for (String s : curServers) {
//            需要添加
            if (!cacheServers.contains(s)) {
                redisTemplate.opsForZSet().add(CacheConstant.CHAT_SERVER_CLIENT_COUNT, s, 0);
            }
        }
    }

}
