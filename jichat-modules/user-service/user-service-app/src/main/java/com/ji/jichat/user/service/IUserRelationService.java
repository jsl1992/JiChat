package com.ji.jichat.user.service;

import com.ji.jichat.user.api.dto.UserRelationDTO;
import com.ji.jichat.user.api.vo.UserRelationVO;
import com.ji.jichat.user.entity.UserRelation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 好友关系表 服务类
 * </p>
 *
 * @author jisl
 * @since 2024-01-28
 */
public interface IUserRelationService extends IService<UserRelation> {

    void addFriend(UserRelationDTO userRelationDTO);

    List<UserRelationVO> listUserRelation();

}
