package com.ji.jichat.user.controller;


import com.ji.jichat.user.entity.User;
import com.ji.jichat.user.service.IUserService;
import framework.pojo.CommonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author jisl
 * @since 2024-01-17
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @GetMapping("/list")
    @ApiOperation("list")
    public CommonResult<List<User>> list() {
        final List<User> list = userService.list();
        return CommonResult.success(list);
    }

}
