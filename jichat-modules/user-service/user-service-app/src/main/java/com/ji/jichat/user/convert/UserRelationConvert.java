package com.ji.jichat.user.convert;

import java.util.*;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.ji.jichat.user.entity.UserRelation;
import com.ji.jichat.user.api.dto.UserRelationDTO;
import com.ji.jichat.user.api.vo.UserRelationVO;

/**
 * 好友关系表 Convert
 *
 * @author jisl
 * @since 2024-01-28
 */
@Mapper
public interface UserRelationConvert {

    UserRelationConvert INSTANCE = Mappers.getMapper(UserRelationConvert.class);

    UserRelation convert(UserRelationVO bean);

    UserRelation convert(UserRelationDTO bean);

    UserRelationVO convert(UserRelation bean);

    List<UserRelationVO> convertList(List<UserRelation> list);




}
