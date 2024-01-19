package com.ji.jichat.user.service;

import com.ji.jichat.user.api.dto.AuthLoginDTO;
import com.ji.jichat.user.api.dto.UserRegisterDTO;
import com.ji.jichat.user.api.vo.AuthLoginVO;
import com.ji.jichat.user.api.vo.LoginUser;
import com.ji.jichat.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author jisl
 * @since 2024-01-17
 */
public interface IUserService extends IService<User> {

    AuthLoginVO login(AuthLoginDTO loginDTO);

    AuthLoginVO refreshToken(String refreshToken);

    void register(UserRegisterDTO dto);

    LoginUser getLoginUserByLoginKey(String loginKey);
}
