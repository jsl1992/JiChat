package com.ji.jichat.user.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ji.jichat.common.constants.CacheConstant;
import com.ji.jichat.common.enums.CommonStatusEnum;
import com.ji.jichat.common.enums.OnlineStatusEnum;
import com.ji.jichat.common.exception.ServiceException;
import com.ji.jichat.security.admin.utils.JwtUtil;
import com.ji.jichat.user.api.dto.AuthLoginDTO;
import com.ji.jichat.user.api.dto.UserRegisterDTO;
import com.ji.jichat.user.api.vo.AuthLoginVO;
import com.ji.jichat.user.api.vo.LoginUser;
import com.ji.jichat.user.convert.UserConvert;
import com.ji.jichat.user.entity.Device;
import com.ji.jichat.user.entity.User;
import com.ji.jichat.user.mapper.UserMapper;
import com.ji.jichat.user.service.IDeviceService;
import com.ji.jichat.user.service.IUserService;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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

    @Resource
    private IDeviceService deviceService;

    @Resource
    private RedisTemplate<String, LoginUser> redisTemplate;


    @Override
    @Transactional
    public AuthLoginVO login(AuthLoginDTO loginDTO) {
        final User user = getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, loginDTO.getUsername()));
        if (Objects.isNull(user)) {
            throw new ServiceException("账户名或者密码错误");
        }
        if (!BCrypt.checkpw(loginDTO.getPassword(), user.getPassword())) {
            throw new ServiceException("账户名或者密码错误");
        }
        if (Objects.equals(user.getStatus(), CommonStatusEnum.DISABLE.getStatus())) {
            throw new ServiceException("登录失败，账号被禁用");
        }
//        final String clientIp = ServletUtil.getClientIP(HttpContextUtil.getHttpServletRequest());
        final Date now = new Date();
        user.toBuilder().onlineStatus(OnlineStatusEnum.ONLINE.getCode()).loginIp("clientIp").loginDate(now);
        updateById(user);
        loginDevice(loginDTO, user);
        return buildAuthLoginVO(user, loginDTO.getDeviceType());
    }

    private AuthLoginVO buildAuthLoginVO(User user, Integer deviceType) {
        //        使用loginkey 这样登录用户不容易被猜到和伪造
        final String loginKey = IdUtil.simpleUUID();
        final Date accessTokenExpirationTime = JwtUtil.getAccessTokenExpirationTime();
        final String accessToken = JwtUtil.createAccessToken(loginKey);
        final String refreshToken = JwtUtil.createRefreshToken(loginKey);
        final AuthLoginVO authLoginVO = AuthLoginVO.builder()
                .userId(user.getId()).accessToken(accessToken).refreshToken(refreshToken)
                .expiresTime(accessTokenExpirationTime)
                .build();
        cacheLoginUer(user, loginKey, deviceType);
        return authLoginVO;
    }

    private void cacheLoginUer(User user, String loginKey, Integer deviceType) {
        //将登录用户保存到redis中
        final LoginUser loginUser = LoginUser.builder().userId(user.getId())
                .username(user.getUsername()).nickname(user.getNickname())
                .deviceType(deviceType).loginKey(loginKey)
                .build();
//        用户缓存比accessToken长一点，这样不至于请求刷新token的时候没有了。
        redisTemplate.opsForValue().set(CacheConstant.LOGIN_USER + loginKey, loginUser, 8, TimeUnit.DAYS);
    }


    @Override
    @Transactional
    public AuthLoginVO refreshToken(String refreshToken) {
        final String loginKey = JwtUtil.validateJwtWithGetSubject(refreshToken);
        if (Objects.isNull(loginKey)) {
            throw new ServiceException("无效的刷新令牌");
        }
        LoginUser loginUser = redisTemplate.opsForValue().get(CacheConstant.LOGIN_USER + loginKey);
        if (Objects.isNull(loginUser)) {
            throw new ServiceException("无效的刷新令牌");
        }

        final User user = getById(loginUser.getUserId());
        final AuthLoginVO authLoginVO = buildAuthLoginVO(user, loginUser.getDeviceType());
//        同时将tcp连接的redis时间也刷新
        redisTemplate.expire(CacheConstant.USER_CHAT_SERVER + loginUser.getUserId() + "_" + loginUser.getDeviceType(), 8, TimeUnit.DAYS);
        //新的token创建完毕，将旧的loginKey作废
        redisTemplate.delete(CacheConstant.LOGIN_USER + loginKey);
        return authLoginVO;
    }

    @Override
    @Transactional
    public void register(UserRegisterDTO dto) {
        final User dbUser = getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, dto.getUsername()));
        if (Objects.nonNull(dbUser)) {
            throw new ServiceException("用户名已存在");
        }
        final User user = UserConvert.INSTANCE.convert(dto).toBuilder()
                .id(IdUtil.getSnowflake(1, 1).nextId()).status(CommonStatusEnum.ENABLE.getStatus())
                .password(BCrypt.hashpw(dto.getPassword())).onlineStatus(OnlineStatusEnum.OFFLINE.getCode())
                .build();
        save(user);
    }

    @Override
    public LoginUser getLoginUserByLoginKey(String loginKey) {
        return redisTemplate.opsForValue().get(CacheConstant.LOGIN_USER + loginKey);
    }

    @Override
    @Transactional
    public void logout(LoginUser loginUser) {
        redisTemplate.delete(CacheConstant.LOGIN_USER + loginUser.getLoginKey());
        final User user = getById(loginUser.getUserId());
        final List<Device> devices = deviceService.getOnlineDevices(loginUser.getUserId());
        for (Device device : devices) {
            if (Objects.equals(device.getDeviceType(), loginUser.getDeviceType())) {
//                和当前用户设备类型一致那么退出
                device.setOnlineStatus(OnlineStatusEnum.OFFLINE.getCode());
                deviceService.updateById(device);
            }
        }
        if (devices.size() == 1) {
            //就一个设备登录，那么将用户状态改为下线
            user.setOnlineStatus(OnlineStatusEnum.OFFLINE.getCode());
            updateById(user);
        }
    }


    private void loginDevice(AuthLoginDTO loginDTO, User user) {
//        final String clientIp = ServletUtil.getClientIP(HttpContextUtil.getHttpServletRequest());
        final Date now = new Date();
        final Device onlineDevice = deviceService.getOne(new LambdaQueryWrapper<Device>().eq(Device::getUserId, user.getId())
                .eq(Device::getDeviceType, loginDTO.getDeviceType()).eq(Device::getOnlineStatus, OnlineStatusEnum.ONLINE.getCode()));
        if (Objects.nonNull(onlineDevice)) {
            //同一个设备类型在线，那么要把前面登录的设备下线。
            onlineDevice.setOnlineStatus(OnlineStatusEnum.OFFLINE.getCode());
            deviceService.updateById(onlineDevice);
            // todo netty连接也要断开
        }
        final Device device = Device.builder()
                .deviceIdentifier(loginDTO.getDeviceIdentifier()).deviceName(loginDTO.getDeviceName())
                .deviceType(loginDTO.getDeviceType()).onlineStatus(loginDTO.getDeviceType())
                .onlineStatus(OnlineStatusEnum.ONLINE.getCode()).osType(loginDTO.getOsType()).loginIp("clientIp")
                .loginDate(now).userId(user.getId())
                .build();
        final Device dbDevice = deviceService.getOne(new LambdaQueryWrapper<Device>().eq(Device::getUserId, user.getId()).eq(Device::getDeviceIdentifier, loginDTO.getDeviceIdentifier()));
        if (Objects.isNull(dbDevice)) {
            deviceService.save(device);
        } else {
            device.setId(dbDevice.getId());
            deviceService.updateById(device);
        }
    }
}
