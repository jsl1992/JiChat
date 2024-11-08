package com.ji.jichat.web.jackson;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 * 自定义Date转成string类型
 *
 * @author jisl on 2024/10/11 8:35
 **/
public class CustomDateSerializer extends JsonSerializer<Date> {


    @Override
    public void serialize(Date date, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(DateUtil.formatDateTime(date));
    }
}