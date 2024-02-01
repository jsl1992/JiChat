package com.ji.jichat.chat.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

/**
 * @author jisl on 2024/1/29 10:45
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class Message {

    /**
     * 用户id+deviceType (格式：1749693821463060481_1)
     */
    private String userKey;


    /**
     * 功能码
     */
    private Integer code;


    /**
     * 随机字符串
     */
    private String nonce;

    public boolean isMatch(Integer code) {
        return Objects.equals(this.getCode(), code);
    }


}
