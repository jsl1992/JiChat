package com.ji.jichat.user.controller;


import cn.hutool.core.util.IdUtil;

import com.ji.jichat.common.pojo.CommonResult;
import com.ji.jichat.security.admin.core.context.UserContext;
import com.ji.jichat.security.admin.utils.JwtUtil;
import com.ji.jichat.user.api.vo.RouteServerVO;
import com.ji.jichat.user.kit.ConsistentHashing;
import com.ji.jichat.user.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author jisl on 2023/10/10 11:05
 **/
@RestController
@Api(tags = {"发现服务Controller "})
@RequestMapping("/discoveryServer/")
@Slf4j
public class DiscoveryServerController {

    @Resource
    private IUserService userService;


    @PostMapping("/routeServer")
    @ApiOperation("路由服务")
    public CommonResult<RouteServerVO> routeServer() {
//        异步方法，防止定时器调用超时
        final String loginKey = UserContext.getLoginKey();
        return CommonResult.success(userService.routeServer(loginKey));
    }


}
