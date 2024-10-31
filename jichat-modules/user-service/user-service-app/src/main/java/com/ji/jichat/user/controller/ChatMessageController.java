package com.ji.jichat.user.controller;

import com.ji.jichat.common.pojo.CommonResult;
import com.ji.jichat.common.pojo.PageDTO;
import com.ji.jichat.common.pojo.PageVO;
import com.ji.jichat.mybatis.util.JiPageHelper;
import com.ji.jichat.user.api.dto.ChatMessageDTO;
import com.ji.jichat.user.api.vo.ChatMessageVO;
import com.ji.jichat.user.service.IChatMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 聊天信息表 前端控制器
 * </p>
 *
 * @author jisl
 * @since 2024-01-28
 */
@RestController
@Tag(name = "ChatMessageController")
@RequestMapping("/chatMessage")
public class ChatMessageController {

    @Resource
    private IChatMessageService chatMessageService;


    @GetMapping("/query")
    @Operation(summary = "获取当前好友/群组的最后聊天消息id")
    public CommonResult<PageVO<ChatMessageVO>> query(ChatMessageDTO chatMessageDTO, PageDTO pageDTO) {
        final PageVO<ChatMessageVO> pageVO = JiPageHelper.doSelectPageInfo(pageDTO, () -> chatMessageService.query(chatMessageDTO));
        return CommonResult.success(pageVO);
    }

}
