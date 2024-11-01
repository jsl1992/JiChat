package com.ji.jichat.user.api;


import com.ji.jichat.common.constants.ServiceNameConstants;
import com.ji.jichat.common.pojo.CommonResult;
import com.ji.jichat.user.api.vo.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 商品 SPU Rpc 接口
 */
@FeignClient(path = "/user-api/user", value = ServiceNameConstants.USER_SERVICE)
public interface UserRpc {


    @Operation(summary = "getLoginUserByLoginKey")
    @GetMapping(value = "/getLoginUserByLoginKey")
    CommonResult<LoginUser> getLoginUserByLoginKey(@RequestParam String loginKey);


}
