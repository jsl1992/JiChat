package com.ji.jichat.user.service.impl;

import com.ji.jichat.user.entity.User;
import com.ji.jichat.user.mapper.UserMapper;
import com.ji.jichat.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author jisl
 * @since 2024-01-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
