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
     * 用户id
     */
    private Long userId;


    /**
     * 设备类型 1手机 2电脑 3平板
     */
    private Integer deviceType;

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
