package com.ji.jichat.chat.netty.protocol;

import cn.hutool.core.io.checksum.CRC16;

import com.ji.jichat.chat.dto.Message;
import com.ji.jichat.chat.enums.MessageTypeEnum;
import com.ji.jichat.chat.utils.ByteUtil;
import com.ji.jichat.chat.utils.CRC16Util;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;


/**
 * 协议编解码
 *
 * @author jishenglong on 2023/8/15 14:08
 **/
@Slf4j
public class ProtocolCodec {


    public static final int PACKAGE_HEAD = 0xAAAA;

    public static final int PACKAGE_TAIL = 0xEEEE;


    public static void encode(ByteBuf byteBuf, Message downMessage) {
        byte[] packageHead = ByteUtil.intToBytes(PACKAGE_HEAD);
        byte[] packageTail = ByteUtil.intToBytes(PACKAGE_TAIL);

        final byte[] content = downMessage.content;
        final byte[] pkLenBytes = ByteUtil.intToBytes(content.length);
        final byte[] crcContent = ByteUtil.mergeBytes(pkLenBytes, content);
        byte[] packageCRC = CRC16Util.CRC16Bytes(crcContent);
//        // 写入固定标识
        byteBuf.writeBytes(packageHead);
//        // 写入字节数组长度
        byteBuf.writeBytes(pkLenBytes);
        byteBuf.writeBytes(content);
        byteBuf.writeBytes(packageCRC);
        byteBuf.writeBytes(packageTail);
////        // 读取ByteBuf中的字节到byte数组
//        byte[] bytes = new byte[byteBuf.readableBytes()];
//        byteBuf.getBytes(bytes.length, bytes);
//        System.out.println("下发数据:" + ByteUtil.bytesToHexString(bytes));
    }


    public static Message decode(ByteBuf byteBuf) {
        int contentLen = byteBuf.getShortLE(2);
        byte[] content = new byte[contentLen];
        byteBuf.getBytes(4, content); // 从位置4开始读取contentLen个字节的数据
        final String commandCode = ByteUtil.byteToHexString(content[0]);
        // 获取命令码
        final Message message = new Message();
        message.setCode(commandCode);
        message.setType(MessageTypeEnum.UP.getCode());
        message.setContent(content);
        return message;
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