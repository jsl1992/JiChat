package com.ji.jichat.user.api;


import com.ji.jichat.common.constants.ServiceNameConstants;
import com.ji.jichat.common.pojo.CommonResult;
import com.ji.jichat.user.api.vo.LoginUser;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 商品 SPU Rpc 接口
 */
@FeignClient(path = "/user-api/user", value = ServiceNameConstants.USER_SERVICE)
public interface UserRpc {


    @ApiOperation("获取登录用户")
    @GetMapping(value = "/getLoginUserByLoginKey")
    CommonResult<LoginUser> getLoginUserByLoginKey(String loginKey);


}
