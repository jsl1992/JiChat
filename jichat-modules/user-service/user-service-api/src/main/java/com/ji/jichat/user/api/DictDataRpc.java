package com.ji.jichat.user.api;


import com.ji.jichat.common.constants.ServiceNameConstants;
import com.ji.jichat.common.pojo.CommonResult;
import com.ji.jichat.user.api.vo.LoginUser;
import com.ji.jichat.user.api.vo.SystemDictDataVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 商品 SPU Rpc 接口
 */
@FeignClient(path = "/user-api/systemDictData", value = ServiceNameConstants.USER_SERVICE)
public interface DictDataRpc {

    @GetMapping("/getDictData")
    @Operation(summary = "获得指定的字典数据")
    @Parameters({
            @Parameter(name = "dictType", description = "字典类型", example = "system_user_sex", required = true),
            @Parameter(name = "value", description = "字典数据值", example = "1", required = true)
    })
    CommonResult<SystemDictDataVO> getDictData(@RequestParam("dictType") String dictType,
                                               @RequestParam("value") String value);


}
