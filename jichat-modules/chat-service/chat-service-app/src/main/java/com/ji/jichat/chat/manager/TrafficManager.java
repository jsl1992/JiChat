//package com.ji.jichat.chat.manager;
//
//
//import com.ji.jichat.chat.enums.CommandCodeEnum;
//import com.ji.jichat.chat.enums.MessageTypeEnum;
//import com.ji.jichat.chat.netty.ChannelRepository;
//import com.ji.jichat.chat.utils.ByteUtil;
//import com.ji.jichat.common.exception.ServiceException;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelFutureListener;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import java.nio.charset.StandardCharsets;
//import java.time.LocalDateTime;
//
///**
// * @author jisl on 2023/10/17 16:41
// */
//@Slf4j
//@Component
//public class TrafficManager {
//
//    private static final String DEFAULT_USERNAME = "12345678";
//
//    private static final String DEFAULT_PASSWORD = "12345678";
//
//    public void syncTime(String clientIp, String deviceSn) {
//        final Channel channel = ChannelRepository.get(clientIp);
//        if (null == channel || !channel.isOpen()) {
//            log.error("指令下发异常, 交调离线:clientIp=[{}]", clientIp);
//            throw new ServiceException("交调离线，同步时间失败");
//        }
//        final byte[] content = new byte[40];
//        int offset = 0;
//        content[offset++] = ByteUtil.hexStringToByte(CommandCodeEnum.SYNC_TIME.getCode());
//        offset = ByteUtil.fillBytes(content, deviceSn.getBytes(StandardCharsets.UTF_8), offset);
//        offset = ByteUtil.fillBytes(content, DEFAULT_USERNAME.getBytes(StandardCharsets.UTF_8), offset);
//        offset = ByteUtil.fillBytes(content, DEFAULT_PASSWORD.getBytes(StandardCharsets.UTF_8), offset);
//        final LocalDateTime now = LocalDateTime.now();
//        offset = ByteUtil.fillBytes(content, ByteUtil.intToBytes(now.getYear()), offset);
//        offset = ByteUtil.fillByte(content, (byte) now.getMonthValue(), offset);
//        offset = ByteUtil.fillByte(content, (byte) now.getDayOfMonth(), offset);
//        offset = ByteUtil.fillByte(content, (byte) now.getHour(), offset);
//        offset = ByteUtil.fillByte(content, (byte) now.getMinute(), offset);
//        offset = ByteUtil.fillByte(content, (byte) now.getSecond(), offset);
//        Message downMessage = new Message();
//        downMessage.setType(MessageTypeEnum.DOWN.getCode());
//        downMessage.setCode(CommandCodeEnum.SYNC_TIME.getCode());
//        downMessage.setContent(content);
//        downMessage.setClientIp(clientIp);
//        final long start = System.currentTimeMillis();
//        channel.writeAndFlush(downMessage).addListener((ChannelFutureListener) future -> {
//            final long time = System.currentTimeMillis() - start;
//            if (!future.isSuccess()) {
//                log.error("指令操作:code=[{}]下发失败,clientIp=[{}],耗时[{}]ms", downMessage.getCode(), clientIp, time);
//                return;
//            }
//            if (time > 10000) {
//                log.warn("指令操作:code=[{}]下发异常耗时,clientIp=[{}],耗时[{}]ms", downMessage.getCode(), clientIp, time);
//                return;
//            }
//            log.debug("指令操作:code=[{}]下发成功,clientIp=[{}],耗时[{}]ms", downMessage.getCode(), clientIp, time);
//        });
//    }
//}
