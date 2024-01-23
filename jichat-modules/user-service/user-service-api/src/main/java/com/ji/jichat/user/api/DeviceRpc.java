package com.ji.jichat.user.api;


import com.ji.jichat.common.annotions.RequiresNone;
import com.ji.jichat.common.constants.ServiceNameConstants;
import com.ji.jichat.common.pojo.CommonResult;
import com.ji.jichat.user.api.vo.DeviceVO;
import com.ji.jichat.user.api.vo.LoginUser;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 商品 SPU Rpc 接口
 */
@FeignClient(path = "/user-api/device", value = ServiceNameConstants.USER_SERVICE)
public interface DeviceRpc {

    @GetMapping("/getOnlineDevices")
    @ApiOperation("获取用户登录设备")
    @RequiresNone
    CommonResult<List<DeviceVO>> getOnlineDevices(@RequestParam long userId);


}
