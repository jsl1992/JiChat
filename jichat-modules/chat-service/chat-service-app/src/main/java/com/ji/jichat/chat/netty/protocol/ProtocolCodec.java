package com.ji.jichat.chat.netty.protocol;

import com.alibaba.fastjson2.JSON;
import com.ji.jichat.chat.api.dto.Message;
import com.ji.jichat.chat.api.enums.CommandCodeEnum;
import com.ji.jichat.chat.utils.ByteUtil;
import com.ji.jichat.common.exception.ServiceException;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Objects;


/**
 * 协议编解码
 *
 * @author jisl on 2023/8/15 14:08
 **/
@Slf4j
public class ProtocolCodec {


    public static final int PACKAGE_HEAD = 0xAAAABBBB;

    public static final int PACKAGE_TAIL = 0xEEEEFFFF;

    // 0xAAAABBBB| “版本号+code+数据内容”的长度| 版本号 |code| 数据内容| 0xEEEEFFFF
    public static final int PROTOCOL_VERSION = 1001;


    public static void encode(ByteBuf byteBuf, Message message) {
        byte[] packageHead = ByteUtil.intToBytes(PACKAGE_HEAD);
        byte[] protocolVersion = ByteUtil.intToBytes(PROTOCOL_VERSION);
        byte[] code = ByteUtil.intToBytes(message.getCode());
        byte[] packageTail = ByteUtil.intToBytes(PACKAGE_TAIL);
        final byte[] content = JSON.toJSONString(message).getBytes(StandardCharsets.UTF_8);
        final byte[] pkLenBytes = ByteUtil.intToBytes(protocolVersion.length + code.length + content.length);
//        // 写入固定标识
        byteBuf.writeBytes(packageHead);
//        // 写入字节数组长度
        byteBuf.writeBytes(pkLenBytes);
        byteBuf.writeBytes(protocolVersion);
        byteBuf.writeBytes(code);
        byteBuf.writeBytes(content);
        byteBuf.writeBytes(packageTail);
    }


    public static Message decode(ByteBuf byteBuf) {
        // 获取版本号
        int protocolVersion = byteBuf.getInt(8);
        if (Objects.equals(protocolVersion, PROTOCOL_VERSION)) {
            int contentLen = byteBuf.getInt(4);
            final int code = byteBuf.getInt(12);
            byte[] content = new byte[contentLen - 8];
            byteBuf.getBytes(16, content); //从位置12开始读取contentLen个字节的数据
            return JSON.parseObject(new String(content, StandardCharsets.UTF_8), CommandCodeEnum.getClazz(code));
        } else {
            throw new ServiceException("当前版本号暂不支持");
        }
    }


}
