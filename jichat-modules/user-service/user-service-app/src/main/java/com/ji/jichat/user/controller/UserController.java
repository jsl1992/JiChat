package com.ji.jichat.user.controller;


import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import com.google.code.kaptcha.Producer;
import com.ji.jichat.common.annotions.RequiresNone;
import com.ji.jichat.common.constants.CacheConstant;
import com.ji.jichat.common.pojo.CommonResult;
import com.ji.jichat.security.admin.core.context.UserContext;
import com.ji.jichat.user.api.UserRpc;
import com.ji.jichat.user.api.dto.AuthLoginDTO;
import com.ji.jichat.user.api.dto.UserRegisterDTO;
import com.ji.jichat.user.api.vo.AuthLoginVO;
import com.ji.jichat.user.api.vo.CaptchaVO;
import com.ji.jichat.user.api.vo.LoginUser;
import com.ji.jichat.user.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;


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
@Tag(name = "用户管理接口")
public class UserController implements UserRpc {

    @Resource
    private IUserService userService;

    @Autowired
    private Producer kaptchaProducer;

    @Resource
    private RedisTemplate redisTemplate;

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

    @GetMapping("/getCaptcha")
    @RequiresNone
    public CommonResult<CaptchaVO> getCaptcha() {
        // 生成验证码文本
        String captchaText = kaptchaProducer.createText();
        // 生成验证码图片
        final int indexOf = captchaText.lastIndexOf("?") + 1;
        String codeStr = captchaText.substring(indexOf);
        final CaptchaVO captchaVO = CaptchaVO.builder().uuid(IdUtil.simpleUUID()).build();
        redisTemplate.opsForValue().set(CacheConstant.CAPTCHA + captchaVO.getUuid(), Integer.valueOf(codeStr), 30, TimeUnit.MINUTES);
        BufferedImage captchaImage = kaptchaProducer.createImage(captchaText.substring(0, indexOf));
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // Write BufferedImage to ByteArrayOutputStream as PNG
            ImageIO.write(captchaImage, "png", outputStream);
            // Convert byte array to Base64 string
            byte[] imageBytes = outputStream.toByteArray();
            String imgBase64 = Base64.encode(imageBytes);
            captchaVO.setImgBase64(imgBase64);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("验证码生成异常");
        }
        return CommonResult.success(captchaVO);
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
//    @ApiImplicitParam(name = "refreshToken", value = "刷新令牌", requiredMode = Schema.RequiredMode.REQUIRED, dataTypeClass = String.class)
    public CommonResult<AuthLoginVO> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        return CommonResult.success(userService.refreshToken(refreshToken));
    }


    @Override
    public CommonResult<LoginUser> getLoginUserByLoginKey(@RequestParam String loginKey) {
        return CommonResult.success(userService.getLoginUserByLoginKey(loginKey));
    }
}
