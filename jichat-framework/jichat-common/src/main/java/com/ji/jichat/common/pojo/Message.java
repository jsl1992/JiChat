package com.ji.jichat.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
     * 消息体内容
     */
    private String content;

    /**
     * 消息类型  1 上传 request , 2 下发 response
     */
    private int type = 2;

    /**
     * 随机字符串
     */
    private String nonce;


}
