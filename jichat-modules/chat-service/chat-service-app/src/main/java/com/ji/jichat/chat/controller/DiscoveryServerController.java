package com.ji.jichat.chat.controller;


import cn.hutool.core.util.IdUtil;
import com.ji.jichat.chat.api.vo.RouteServerVO;
import com.ji.jichat.chat.kit.ConsistentHashing;
import com.ji.jichat.common.pojo.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jisl on 2023/10/10 11:05
 **/
@RestController
@Api(tags = {"发现服务Controller "})
@RequestMapping("/discoveryServer/")
@Slf4j
public class DiscoveryServerController {
//
//    @Resource
//    private IDeviceComparisonService deviceComparisonService;
//


    @PostMapping("/routeServer")
    @ApiOperation("路由服务")
    public CommonResult<RouteServerVO> routeServer() {
//        异步方法，防止定时器调用超时
        final String node = ConsistentHashing.getNode(IdUtil.simpleUUID());
        final String[] ipAndPort = node.split(":");
        final RouteServerVO routeServerVO = RouteServerVO.builder()
                .innerIp(ipAndPort[0]).outsideIp(ipAndPort[0]).port(ipAndPort[1])
                .build();
        return CommonResult.success(routeServerVO);
    }


}
