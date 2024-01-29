package com.ji.jichat.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ji.jichat.common.enums.OnlineStatusEnum;
import com.ji.jichat.user.entity.Device;
import com.ji.jichat.user.mapper.DeviceMapper;
import com.ji.jichat.user.service.IDeviceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 设备表 服务实现类
 * </p>
 *
 * @author jisl
 * @since 2024-01-17
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements IDeviceService {

    @Override
    public List<Device> getOnlineDevices(Long userId) {
        return list(new LambdaQueryWrapper<Device>().eq(Device::getUserId, userId).eq(Device::getOnlineStatus, OnlineStatusEnum.ONLINE.getCode()));
    }
}
