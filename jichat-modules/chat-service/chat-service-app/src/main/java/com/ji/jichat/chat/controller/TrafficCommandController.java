package com.ji.jichat.chat.controller;


import com.ji.jichat.common.pojo.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jisl on 2023/10/10 11:05
 **/
@RestController
@Api(tags = {"指令下发Controller "})
@RequestMapping("/command/traffic")
@Slf4j
public class TrafficCommandController {
//
//    @Resource
//    private IDeviceComparisonService deviceComparisonService;
//
//    @Resource
//    private TrafficManager trafficManager;

    @Value("${server.port}")
    private Integer port;

    @PostMapping("/syncTime")
    @ApiOperation("事件订阅")
    public CommonResult<Void> syncTime() {
//        异步方法，防止定时器调用超时
//        CompletableFuture.runAsync(() -> syncTimeHelper());
        return CommonResult.success();
    }

//    private void syncTimeHelper() {
//        final List<DeviceComparison> list = deviceComparisonService.list(new LambdaQueryWrapper<DeviceComparison>().eq(DeviceComparison::getDeviceType,"201"));
//        log.info("开始下发交调时间同步{}个", list.size());
//        int failCount = 0;
//        for (DeviceComparison deviceComparison : list) {
//            try {
//                trafficManager.syncTime(deviceComparison.getIp(),deviceComparison.getDeviceId());
//            } catch (Exception e) {
//               log.info("时间同步指令下发异常e",e);
//                failCount+=1;
//            }
//        }
//        log.info("开始下发交调时间同步,总数:{},失败:{}", list.size(), failCount);
//    }


}
