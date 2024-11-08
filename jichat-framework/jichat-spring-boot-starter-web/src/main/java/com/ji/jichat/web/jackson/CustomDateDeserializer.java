package com.ji.jichat.web.jackson;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Date;

public class CustomDateDeserializer extends JsonDeserializer<Date> {


    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return DateUtil.parse(p.getText());
    }
}