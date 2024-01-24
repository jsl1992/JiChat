package com.ji.jichat.user.convert;

import java.util.*;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.ji.jichat.user.entity.User;
import com.ji.jichat.user.api.dto.UserDTO;
import com.ji.jichat.user.api.vo.UserVO;

/**
 * 用户表 Convert
 *
 * @author jisl
 * @since 2024-01-23
 */
@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    User convert(UserVO bean);

    User convert(UserDTO bean);

    UserVO convert(User bean);

    List<UserVO> convertList(List<User> list);




}
