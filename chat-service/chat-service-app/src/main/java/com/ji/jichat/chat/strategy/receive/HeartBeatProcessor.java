//package com.ji.jichat.chat.strategy.receive;
//
//
//import com.ji.jichat.chat.dto.Message;
//import com.ji.jichat.chat.enums.CommandCodeEnum;
//import com.ji.jichat.chat.strategy.CommandStrategy;
//import com.ji.jichat.chat.utils.ByteUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//
///**
// * 心跳实现
// *
// * @author jishenglong on 2023/3/27 10:02
// **/
//@Component
//@Slf4j
//public class HeartBeatProcessor implements CommandStrategy {
//
//    private long count = 0;
//
//    @Resource
//    private IDeviceComparisonService deviceComparisonService;
//
//    @Override
//    public CommandCodeEnum getCommandCode() {
//        return CommandCodeEnum.HEARTBEAT;
//    }
//
//    @Override
//    public byte[] execute(Message message) {
//        final byte[] content = message.getContent();
//        final String deviceSn = ByteUtil.bytesToString(content, 1, 16);
//        final DeviceComparison deviceComparison = deviceComparisonService.getByIp(message.getClientIp(), false);
//        deviceComparisonService.updateSyncTime(message.clientIp);
//        log.info("心跳包[{}][{}][{}]", deviceSn, message.clientIp, ++count);
//        content[content.length - 1] = (byte) (deviceComparison != null ? 0x02 : 0x03);
//        return content;
//    }
//
//
//}
