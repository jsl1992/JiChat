package com.ji.jichat.user.controller;


import com.ji.jichat.common.annotions.RequiresNone;
import com.ji.jichat.common.pojo.CommonResult;
import com.ji.jichat.security.admin.core.context.UserContext;
import com.ji.jichat.user.api.UserRpc;
import com.ji.jichat.user.api.dto.AuthLoginDTO;
import com.ji.jichat.user.api.dto.UserRegisterDTO;
import com.ji.jichat.user.api.vo.AuthLoginVO;
import com.ji.jichat.user.api.vo.LoginUser;
import com.ji.jichat.user.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


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
@Tag(name = "UserController")
public class UserController implements UserRpc {

    @Resource
    private IUserService userService;


    @PostMapping("/register")
    @RequiresNone
    @Operation(summary = "用户注册")
    public CommonResult<Void> register(@RequestBody @Valid UserRegisterDTO dto) {
        userService.register(dto);
        return CommonResult.success();
    }


    @PostMapping("/login")
    @Operation(summary = "使用账号密码登录")
    @RequiresNone
    public CommonResult<AuthLoginVO> login(@RequestBody @Valid AuthLoginDTO reqVO) {
        return CommonResult.success(userService.login(reqVO));
    }

    @DeleteMapping("/del")
    @Operation(summary = "del")
    public CommonResult<Void> del(@RequestParam long userId) {
        userService.removeById(userId);
        return CommonResult.success();
    }


    @PostMapping("/logout")
    @Operation(summary = "登出系统")
    public CommonResult<Boolean> logout() {
        userService.logout(UserContext.get());
        return CommonResult.success(true);
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "刷新令牌")
    @RequiresNone
//    @ApiImplicitParam(name = "refreshToken", value = "刷新令牌", required = true, dataTypeClass = String.class)
    public CommonResult<AuthLoginVO> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        return CommonResult.success(userService.refreshToken(refreshToken));
    }


    @Override
    public CommonResult<LoginUser> getLoginUserByLoginKey(@RequestParam String loginKey) {
        return CommonResult.success(userService.getLoginUserByLoginKey(loginKey));
    }
}
