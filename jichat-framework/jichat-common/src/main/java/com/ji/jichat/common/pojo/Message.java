package com.ji.jichat.common.pojo;

import lombok.Data;

@Data
public abstract class Message {
    /**
     * 客户端ip
     */
    private String clientIp;

    /**
     * 用户id
     */
    private Long userId;


    private Integer deviceType;

    /**
     * 功能码
     */
    private Integer code;

    /**
     * 消息类型  1 上传 request , 2 下发 response
     */
    private int type;

    /**
     * 消息体内容
     */
    private String content;

    /**
     * 随机字符串
     */
    private String nonce;


}
