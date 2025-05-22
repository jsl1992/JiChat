package com.ji.jichat.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ji.jichat.common.constants.CacheConstant;
import com.ji.jichat.common.util.MessageIdUtil;
import com.ji.jichat.security.admin.core.context.UserContext;
import com.ji.jichat.user.api.dto.UserRelationDTO;
import com.ji.jichat.user.api.vo.UserRelationVO;
import com.ji.jichat.user.convert.UserRelationConvert;
import com.ji.jichat.user.entity.UserRelation;
import com.ji.jichat.user.mapper.UserRelationMapper;
import com.ji.jichat.user.service.IUserRelationService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;

import java.util.List;

/**
 * <p>
 * 好友关系表 服务实现类
 * </p>
 *
 * @author jisl
 * @since 2024-01-28
 */
@Service
public class UserRelationServiceImpl extends ServiceImpl<UserRelationMapper, UserRelation> implements IUserRelationService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addFriend(UserRelationDTO userRelationDTO) {
        if (userRelationDTO.getUserId().equals(userRelationDTO.getRelationId())) {
            throw new RuntimeException("不能添加自己为好友");
        }
        if (exists(new LambdaQueryWrapper<UserRelation>().eq(UserRelation::getUserId, userRelationDTO.getUserId()).eq(UserRelation::getRelationId, userRelationDTO.getRelationId()))) {
            throw new RuntimeException("已经是好友");
        }
        final String channelKey = MessageIdUtil.getChannelKey(userRelationDTO.getUserId(), userRelationDTO.getRelationId());
        final UserRelation userRelation = UserRelationConvert.INSTANCE.convert(userRelationDTO).toBuilder().userId(userRelationDTO.getUserId()).relationType(1).channelKey(channelKey).build();
        save(userRelation);
        //添加两个，方便将来数据库分片，可以按照用户id分。同时微信也是删除好友，对方还是有自己的好友。
        if (!exists(new LambdaQueryWrapper<UserRelation>().eq(UserRelation::getUserId, userRelationDTO.getRelationId()).eq(UserRelation::getRelationId, userRelationDTO.getUserId()))){
            final UserRelation userRelation2 = UserRelation.builder().userId(userRelationDTO.getRelationId()).relationId(userRelationDTO.getUserId()).relationType(userRelation.getRelationType()).channelKey(channelKey).build();
            save(userRelation2);
        }

    }

    @Override
    public List<UserRelationVO> listUserRelation() {
        final Long userId = UserContext.getUserId();
        final List<UserRelationVO> userRelations = UserRelationConvert.INSTANCE.convertList(list(new LambdaQueryWrapper<UserRelation>().eq(UserRelation::getUserId, userId)));
        for (UserRelationVO vo : userRelations) {
            final Long messageId = redisTemplate.opsForValue().increment(CacheConstant.MESSAGE_ID + vo.getChannelKey(), 0);
            vo.setMessageId(messageId);
        }
        return userRelations;
    }
}
