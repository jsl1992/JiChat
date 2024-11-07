package com.ji.jichat.user.convert;

import java.util.*;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.ji.jichat.user.entity.SystemSmsCode;
import com.ji.jichat.user.api.dto.SystemSmsCodeDTO;
import com.ji.jichat.user.api.vo.SystemSmsCodeVO;

/**
 * 手机验证码 Convert
 *
 * @author jisl
 * @since 2024-11-07
 */
@Mapper
public interface SystemSmsCodeConvert {

    SystemSmsCodeConvert INSTANCE = Mappers.getMapper(SystemSmsCodeConvert.class);

    SystemSmsCode convert(SystemSmsCodeVO bean);

    SystemSmsCode convert(SystemSmsCodeDTO bean);

    SystemSmsCodeVO convert(SystemSmsCode bean);

    List<SystemSmsCodeVO> convertList(List<SystemSmsCode> list);




}
