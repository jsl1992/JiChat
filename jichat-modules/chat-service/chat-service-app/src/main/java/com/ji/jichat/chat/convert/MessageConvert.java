package com.ji.jichat.chat.convert;

import com.ji.jichat.common.pojo.DownMessage;
import com.ji.jichat.common.pojo.UpMessage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Message Convert
 *
 * @author jisl
 * @since 2024-01-24
 */
@Mapper
public interface MessageConvert {

    MessageConvert INSTANCE = Mappers.getMapper(MessageConvert.class);

    DownMessage convert(UpMessage bean);

    UpMessage convert(DownMessage bean);


}
