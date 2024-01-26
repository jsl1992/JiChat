package com.ji.jichat.user.api;


import com.ji.jichat.common.annotions.RequiresNone;
import com.ji.jichat.common.constants.ServiceNameConstants;
import com.ji.jichat.common.pojo.CommonResult;
import com.ji.jichat.user.api.vo.ChatServerInfoVO;
import com.ji.jichat.user.api.vo.DeviceVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 商品 SPU Rpc 接口
 */
@FeignClient(path = "/user-api/chatServerInfo", value = ServiceNameConstants.USER_SERVICE)
public interface ChatServerInfoRpc {

    @PostMapping("/getByIpAndPort")
    @RequiresNone
    @ApiOperation("获取服务信息")
    CommonResult<ChatServerInfoVO> getByIpAndPort(@RequestParam String innerIp, @RequestParam int httpPort);


}
