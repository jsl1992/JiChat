package com.ji.jichat.chat.netty.handler;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteOrder;

/**
  * LengthFieldBasedFrameDecoder：自定义长度解码器，通过在消息头中定义消息长度字段来标志消息体的长度，然后根据消息的总长度来读取消息
  * @author jisl on 2023/8/15 13:47
  **/
public class LengthFieldDecoderHandler extends LengthFieldBasedFrameDecoder {

    public static final String NAME = "LengthFieldDecoderHandler";

    private static final int MAX_FRAME_LENGTH = Integer.MAX_VALUE; // 最大数据帧长度
    private static final int LENGTH_FIELD_OFFSET = 4; // 长度字段在数据帧中的偏移量
    private static final int LENGTH_FIELD_LENGTH = 4; // 长度字段的字节长度
    private static final int LENGTH_ADJUSTMENT = 4; // 长度字段的值加上的调整值,因为字节长度不包括包尾，四字节那么要加上这4个字节
    private static final int INITIAL_BYTES_TO_STRIP = 0; // 解码后跳过的字节数
    private static final  boolean FAIL_FAST = true; // 这里是是否快速失败


    public LengthFieldDecoderHandler() {
        super(ByteOrder.BIG_ENDIAN,MAX_FRAME_LENGTH, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH,LENGTH_ADJUSTMENT,INITIAL_BYTES_TO_STRIP, FAIL_FAST);
    }


}
