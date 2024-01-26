package com.ji.jichat.user.controller;

import com.ji.jichat.common.annotions.RequiresNone;
import com.ji.jichat.common.pojo.CommonResult;
import com.ji.jichat.user.api.ChatServerInfoRpc;
import com.ji.jichat.user.api.dto.ChatServerInfoDTO;
import com.ji.jichat.user.api.vo.ChatServerInfoVO;
import com.ji.jichat.user.service.IChatServerInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 聊天服务器信息表 前端控制器
 * </p>
 *
 * @author jisl
 * @since 2024-01-26
 */
@Controller
@RequestMapping("/chatServerInfo")
public class ChatServerInfoController implements ChatServerInfoRpc {

    @Resource
    private IChatServerInfoService chatServerInfoService;

    @PostMapping("/save")
    @RequiresNone
    @ApiOperation("保存")
    public CommonResult<Void> save(@RequestBody @Valid ChatServerInfoDTO dto) {
        chatServerInfoService.save(dto);
        return CommonResult.success();
    }


    @PostMapping("/getByIpAndPort")
    @RequiresNone
    @ApiOperation("获取服务信息")
    @Override
    public CommonResult<ChatServerInfoVO> getByIpAndPort(@RequestParam String innerIp, @RequestParam int httpPort) {
        return CommonResult.success(chatServerInfoService.getByIpAndPort(innerIp, httpPort));
    }

}
