package com.ji.jichat.user.controller;

import com.ji.jichat.common.pojo.CommonResult;
import com.ji.jichat.user.api.dto.UserRelationDTO;
import com.ji.jichat.user.api.vo.UserRelationVO;
import com.ji.jichat.user.service.IUserRelationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 好友关系表 前端控制器
 * </p>
 *
 * @author jisl
 * @since 2024-01-28
 */
@RestController
@RequestMapping("/userRelation")
public class UserRelationController {

    @Resource
    private IUserRelationService userRelationService;

    @PostMapping("/addFriend")
    @ApiOperation("添加朋友")
    public CommonResult<Void> addFriend(@RequestBody @Valid UserRelationDTO userRelationDTO) {
        userRelationService.addFriend(userRelationDTO);
        return CommonResult.success();
    }

    @GetMapping("/listUserRelation")
    @ApiOperation("获取当前好友/群组的最后聊天消息id")
    public CommonResult<List<UserRelationVO>> listUserRelation() {
        return CommonResult.success(userRelationService.listUserRelation());
    }

}
