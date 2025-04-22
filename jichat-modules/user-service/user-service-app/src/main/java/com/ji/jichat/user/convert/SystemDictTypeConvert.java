package com.ji.jichat.user.convert;

import java.util.*;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.ji.jichat.user.entity.SystemDictType;
import com.ji.jichat.user.api.dto.SystemDictTypeDTO;
import com.ji.jichat.user.api.vo.SystemDictTypeVO;

/**
 * 字典类型表 Convert
 *
 * @author jisl
 * @since 2025-04-22
 */
@Mapper
public interface SystemDictTypeConvert {

    SystemDictTypeConvert INSTANCE = Mappers.getMapper(SystemDictTypeConvert.class);

    SystemDictType convert(SystemDictTypeVO bean);

    SystemDictType convert(SystemDictTypeDTO bean);

    SystemDictTypeVO convert(SystemDictType bean);

    List<SystemDictTypeVO> convertList(List<SystemDictType> list);




}
