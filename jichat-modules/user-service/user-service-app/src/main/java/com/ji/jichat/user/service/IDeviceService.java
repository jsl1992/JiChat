package com.ji.jichat.user.service;

import com.ji.jichat.user.entity.Device;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 设备表 服务类
 * </p>
 *
 * @author jisl
 * @since 2024-01-17
 */
public interface IDeviceService extends IService<Device> {

    List<Device> getOnlineDevices(Long userId);
}
