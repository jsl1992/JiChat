package com.ji.jichat.user.controller;


import cn.hutool.core.bean.BeanUtil;
import com.ji.jichat.common.pojo.CommonResult;
import com.ji.jichat.user.api.DeviceRpc;
import com.ji.jichat.user.api.vo.DeviceVO;
import com.ji.jichat.user.entity.Device;
import com.ji.jichat.user.service.IDeviceService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 设备表 前端控制器
 * </p>
 *
 * @author jisl
 * @since 2024-01-17
 */
@RestController
@RequestMapping("/device")
public class DeviceController implements DeviceRpc {

    @Resource
    private IDeviceService deviceService;


    @Override
    public CommonResult<List<DeviceVO>> getOnlineDevices(@RequestParam long userId) {
        final List<Device> onlineDevices = deviceService.getOnlineDevices(userId);
        final List<DeviceVO> deviceVOList = onlineDevices.stream().map(t -> BeanUtil.toBean(t, DeviceVO.class)).collect(Collectors.toList());
        return CommonResult.success(deviceVOList);
    }

}
