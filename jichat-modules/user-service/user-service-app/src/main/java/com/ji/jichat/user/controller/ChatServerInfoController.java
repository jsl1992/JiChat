package com.ji.jichat.user.controller;

import com.ji.jichat.common.annotions.RequiresNone;
import com.ji.jichat.common.pojo.CommonResult;
import com.ji.jichat.user.api.ChatServerInfoRpc;
import com.ji.jichat.user.api.dto.ChatServerInfoDTO;
import com.ji.jichat.user.api.vo.ChatServerInfoVO;
import com.ji.jichat.user.service.IChatServerInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 聊天服务器信息表 前端控制器
 * </p>
 *
 * @author jisl
 * @since 2024-01-26
 */
@RestController
@Tag(name = "ChatServerInfoController")
@RequestMapping("/chatServerInfo")
public class ChatServerInfoController implements ChatServerInfoRpc {

    @Resource
    private IChatServerInfoService chatServerInfoService;

    @PostMapping("/save")
    @RequiresNone
    @Operation(summary = "保存服务信息")
    public CommonResult<Void> save(@RequestBody @Valid ChatServerInfoDTO dto) {
        chatServerInfoService.save(dto);
        return CommonResult.success();
    }


    @GetMapping("/getByIpAndPort")
    @RequiresNone
    @Operation(summary = "获取服务信息")
    @Override
    public CommonResult<ChatServerInfoVO> getByIpAndPort(@RequestParam String innerIp, @RequestParam int httpPort) {
        return CommonResult.success(chatServerInfoService.getByIpAndPort(innerIp, httpPort));
    }


}
