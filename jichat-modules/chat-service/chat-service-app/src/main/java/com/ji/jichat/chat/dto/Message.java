package com.ji.jichat.chat.dto;

import com.ji.jichat.chat.utils.ByteUtil;
import lombok.Data;

/**
 * 消息内容
 *
 * @author jishenglong on 2023/8/15 9:33
 **/
@Data
public class Message {
    /**
     * 客户端ip
     */
    public String clientIp;


    /**
     * 功能码
     */
    public String code;


    /**
     * 消息类型  1 上传 request , 2 下发 response
     */
    public int type;

    /**
     * 消息体内容
     */
    public byte[] content;

    @Override
    public String toString() {
        return "Message{" +
            "clientIp=" + this.clientIp +
            ", code='" + this.code + '\'' +
            ", content='" + ByteUtil.bytesToHexString(content) + '\'' +
            '}';
    }


}
