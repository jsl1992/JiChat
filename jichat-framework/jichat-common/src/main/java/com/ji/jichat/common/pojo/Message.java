package com.ji.jichat.common.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public abstract class Message {

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


    public String getUserKey() {
        return this.getUserId() + "_" + this.getDeviceType();
    }


}
