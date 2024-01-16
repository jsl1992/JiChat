package com.ji.jichat.chat.netty;

import com.ji.jichat.chat.dto.TrafficInfoDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TrafficInfoDTORepository
 *
 * @author lintengyue
 */
@Slf4j
@Deprecated
public class TrafficInfoRepository {


    private static final Map<String, TrafficInfoDTO> trafficInfoCache = new ConcurrentHashMap<>(16);

    private TrafficInfoRepository() {
    }

    public static TrafficInfoDTO put(String clientIP, String deviceSn, String siteSn) {
        final TrafficInfoDTO trafficInfoDTO = TrafficInfoDTO.builder().clientIp(clientIP).deviceSn(deviceSn).siteSn(siteSn).build();
        log.debug("[{}]更新trafficInfoDTO:[{}]", clientIP, trafficInfoDTO);
        return trafficInfoCache.put(clientIP, trafficInfoDTO);
    }

    public static TrafficInfoDTO get(String clientIP) {
        return trafficInfoCache.get(clientIP);
    }


    public static Map<String, TrafficInfoDTO> getTrafficInfoDTOCache() {
        return trafficInfoCache;
    }


}
