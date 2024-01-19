package com.ji.jichat.user.controller;


import com.ji.jichat.common.annotions.RequiresNone;
import com.ji.jichat.common.pojo.CommonResult;
import com.ji.jichat.user.api.UserRpc;
import com.ji.jichat.user.api.dto.AuthLoginDTO;
import com.ji.jichat.user.api.dto.UserRegisterDTO;
import com.ji.jichat.user.api.vo.AuthLoginVO;
import com.ji.jichat.user.api.vo.LoginUser;
import com.ji.jichat.user.service.IUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
public class UserController implements UserRpc {

    @Resource
    private IUserService userService;


    @PostMapping("/register")
    @RequiresNone
    @ApiOperation("用户注册")
    public CommonResult<Void> register(@RequestBody @Valid UserRegisterDTO dto) {
        userService.register(dto);
        return CommonResult.success();
    }

    @PostMapping("/login")
    @ApiOperation("使用账号密码登录")
    @RequiresNone
    public CommonResult<AuthLoginVO> login(@RequestBody @Valid AuthLoginDTO reqVO) {
        return CommonResult.success(userService.login(reqVO));
    }


    @PostMapping("/logout")
    @ApiOperation("登出系统")
    public CommonResult<Boolean> logout(HttpServletRequest request) {
//        String token = obtainAuthorization(request, securityProperties.getTokenHeader());
//        if (StrUtil.isNotBlank(token)) {
//            userService.logout(token, LoginLogTypeEnum.LOGOUT_SELF.getType());
//        }
        return CommonResult.success(true);
    }

    @PostMapping("/refresh-token")
    @ApiOperation("刷新令牌")
    @ApiImplicitParam(name = "refreshToken", value = "刷新令牌", required = true, dataTypeClass = String.class)
    public CommonResult<AuthLoginVO> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        return CommonResult.success(userService.refreshToken(refreshToken));
    }


    @Override
    public CommonResult<LoginUser> getLoginUserByLoginKey(@RequestParam String loginKey) {
        return CommonResult.success(userService.getLoginUserByLoginKey(loginKey));
    }
}
