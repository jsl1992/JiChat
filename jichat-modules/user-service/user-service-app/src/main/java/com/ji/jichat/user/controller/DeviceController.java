package com.ji.jichat.user.controller;


import com.ji.jichat.common.pojo.CommonResult;
import com.ji.jichat.user.api.DeviceRpc;
import com.ji.jichat.user.api.vo.DeviceVO;
import com.ji.jichat.user.convert.DeviceConvert;
import com.ji.jichat.user.service.IDeviceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.List;

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
@Tag(name = "DeviceController")
public class DeviceController implements DeviceRpc {

    @Resource
    private IDeviceService deviceService;


    @Override
    public CommonResult<List<DeviceVO>> getOnlineDevices(@RequestParam long userId) {
        final List<DeviceVO> onlineDevices = DeviceConvert.INSTANCE.convertList(deviceService.getOnlineDevices(userId));
        return CommonResult.success(onlineDevices);
    }

}
