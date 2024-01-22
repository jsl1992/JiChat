package com.ji.jichat.client.netty.protocol;

import cn.hutool.core.io.checksum.CRC16;
import com.alibaba.fastjson.JSON;
import com.ji.jichat.client.utils.ByteUtil;
import com.ji.jichat.common.exception.ServiceException;
import com.ji.jichat.common.pojo.DownMessage;
import com.ji.jichat.common.pojo.Message;
import com.ji.jichat.common.pojo.UpMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Objects;


/**
 * 协议编解码
 *
 * @author jishenglong on 2023/8/15 14:08
 **/
@Slf4j
public class ClentProtocolCodec {


    public static final int PACKAGE_HEAD = 0xAAAABBBB;

    public static final int PACKAGE_TAIL = 0xEEEEFFFF;

    // 0xAAAABBBB| “版本号+数据内容”的长度| 版本号 | 数据内容| 0xEEEEFFFF
    public static final int PROTOCOL_VERSION = 1001;


    public static void encode(ByteBuf byteBuf, Message downMessage) {
        byte[] packageHead = ByteUtil.intToBytes(PACKAGE_HEAD);
        byte[] protocolVersion = ByteUtil.intToBytes(PROTOCOL_VERSION);
        byte[] packageTail = ByteUtil.intToBytes(PACKAGE_TAIL);
        final byte[] content = JSON.toJSONString(downMessage).getBytes(StandardCharsets.UTF_8);
        final byte[] pkLenBytes = ByteUtil.intToBytes(protocolVersion.length + content.length);
//        // 写入固定标识
        byteBuf.writeBytes(packageHead);
//        // 写入字节数组长度
        byteBuf.writeBytes(pkLenBytes);
        byteBuf.writeBytes(protocolVersion);
        byteBuf.writeBytes(content);
        byteBuf.writeBytes(packageTail);
    }


    public static UpMessage decode(ByteBuf byteBuf) {
        // 获取版本号
        int protocolVersion = byteBuf.getInt(8);
        if (Objects.equals(protocolVersion, PROTOCOL_VERSION)) {
            int contentLen = byteBuf.getInt(4);
            byte[] content = new byte[contentLen - 4];
            byteBuf.getBytes(12, content); //从位置4开始读取contentLen个字节的数据
            final UpMessage message = JSON.parseObject(new String(content, StandardCharsets.UTF_8), UpMessage.class);
            return message;
        } else {
            throw new ServiceException("当前版本号暂不支持");
        }
    }

    public static String getClientIp(ChannelHandlerContext ctx) {
        final InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        return socketAddress.getAddress().getHostAddress();
    }

    public static void main(String[] args) {
        final byte[] bytes = getBytes();
        CRC16 crc = new CRC16();
        crc.update(bytes, 0, bytes.length);
        final Long crcValue = crc.getValue();// 返回计算出来的 CRC 校验值
        System.out.println(crcValue);
        System.out.println(Long.toHexString(crcValue));

    }

    private static byte[] getBytes() {
        final String s = "AA AA 28 00 05 30 30 37 31 31 35 34 33 31 39 30 38 30 30 30 31 6C 6E 6A 79 78 78 67 73 38 38 38 38 38 38 38 38 E7 07 0A 12 0A 37 35 F6 58 EE EE ";
        final String[] strings = s.split(" ");
        final byte[] bytes = new byte[strings.length];
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            final int val = Integer.parseInt(strings[i], 16);
            bytes[i] = (byte) val;
            sb.append("0x").append(strings[i]).append(",");
        }
        System.out.println(ByteUtil.bytesToHexString(bytes));
        System.out.println(sb);
        return bytes;
    }


}
