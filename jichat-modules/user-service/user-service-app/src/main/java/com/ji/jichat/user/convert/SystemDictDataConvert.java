package com.ji.jichat.user.convert;

import java.util.*;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.ji.jichat.user.entity.SystemDictData;
import com.ji.jichat.user.api.dto.SystemDictDataDTO;
import com.ji.jichat.user.api.vo.SystemDictDataVO;

/**
 * 字典数据表 Convert
 *
 * @author jisl
 * @since 2025-04-22
 */
@Mapper
public interface SystemDictDataConvert {

    SystemDictDataConvert INSTANCE = Mappers.getMapper(SystemDictDataConvert.class);

    SystemDictData convert(SystemDictDataVO bean);

    SystemDictData convert(SystemDictDataDTO bean);

    SystemDictDataVO convert(SystemDictData bean);

    List<SystemDictDataVO> convertList(List<SystemDictData> list);




}
