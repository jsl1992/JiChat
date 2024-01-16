//package com.ji.jichat.chat.strategy.receive;
//
//import cn.hutool.core.date.DateUtil;
//
//import com.ji.jichat.chat.dto.Message;
//import com.ji.jichat.chat.enums.CommandCodeEnum;
//import com.ji.jichat.chat.strategy.CommandStrategy;
//import com.ji.jichat.chat.utils.ByteUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * 过车服务实现
// *
// * @author jishenglong on 2023/3/27 10:02
// **/
//@Component
//@Slf4j
//public class DynamicsTrafficDataProcessor implements CommandStrategy {
//
//    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
//
//    @Resource
//    private ITrafficSurveyService trafficSurveyService;
//
//
//    @Resource
//    private IDeviceComparisonService deviceComparisonService;
//
//    @Override
//    public CommandCodeEnum getCommandCode() {
//        return CommandCodeEnum.DYNAMICS_TRAFFIC_DATA;
//    }
//
//    @Override
//    public byte[] execute(Message message) {
//        log.info("动态交通数据:{}", message.getClientIp());
//        final DeviceComparison deviceComparison = deviceComparisonService.getByIp(message.getClientIp());
//        final byte[] content = message.getContent();
////        设备识别编码
//        int offSet = 1;
//        final String deviceSn = ByteUtil.bytesToString(content, offSet, 16);
////       站点编号
//        final String siteSn = ByteUtil.bytesToString(content, offSet += 16, 15);
//        int year = ByteUtil.bytesToInt(content, offSet += 15 + 2);
//        offSet += 2;
//        final int month = ByteUtil.byteToInt(content[offSet++]);
//        final int day = ByteUtil.byteToInt(content[offSet++]);
//        //处理周期
//        int processCycle = ByteUtil.byteToInt(content[offSet++]);
////        两个字节
//        int hourAndMinute = ByteUtil.bytesToInt(content, offSet);
//        offSet += 2;
//        //车道数
//        final int laneCount = ByteUtil.byteToInt(content[offSet++]);
//        for (int i = 0; i < laneCount; i++) {
////            单车道上行:01 ,单车逍下行:03
//            //        车道编号 ，2车道以上公路上行:0B-13(十进制11-19)，2车道公路下行:1F-27(十进制:31-39)
//            final int laneNum = ByteUtil.byteToInt(content[offSet++]);
//            // 跟车百分比
//            final int dataCarFollowRate = ByteUtil.byteToInt(content[offSet++]);
//            //车头间距
//            final int dataCarDistance = ByteUtil.bytesToInt(content, offSet);
//            offSet += 2;
////        时间占有率
//            final int dataLaneUsedRate = ByteUtil.byteToInt(content[offSet++]);
//
//            final List<Integer> carCounts = new ArrayList<>();
//            final List<Integer> carSpeeds = new ArrayList<>();
//            for (int j = 0; j < 9; j++) {
//                final int carCount = ByteUtil.bytesToInt(content, offSet);
//                final int carSpeed = ByteUtil.byteToInt(content[offSet + 2]);
//                carCounts.add(carCount);
//                carSpeeds.add(carSpeed);
//                offSet += 3;
//            }
//            // 平均车速
//            final double dataCarSpeed = carSpeeds.stream().mapToInt(Integer::intValue).average().getAsDouble();
//            final TrafficSurvey trafficSurvey = new TrafficSurvey();
//            trafficSurvey.setRegionType(deviceComparison.getRegionType().longValue());
//            trafficSurvey.setLaneNum((laneNum==1 || laneNum==31) ?1L:0L);
////            第一条记录是1，那么实际统计的时间从0开始，这样不会出现最后一条时间等于24
//            trafficSurvey.setTime(getTrafficTime(year, month, day, (hourAndMinute - 1) * processCycle));
//            trafficSurvey.setTimeNum((long) hourAndMinute);
//            trafficSurvey.setStaticDate(DateUtil.beginOfDay(trafficSurvey.getTime()));
//            trafficSurvey.setDataLaneUsedRate(getRate(dataLaneUsedRate));
//            trafficSurvey.setDataCarSpeed(new BigDecimal(dataCarSpeed));
//            trafficSurvey.setDataCarDistance(new BigDecimal(dataCarDistance));
////        除数为0判断
//            BigDecimal dataCarDistanceTime = trafficSurvey.getDataCarSpeed().equals(BigDecimal.ZERO) ? BigDecimal.ZERO : trafficSurvey.getDataCarDistance().divide(trafficSurvey.getDataCarSpeed(), RoundingMode.HALF_UP);
//            trafficSurvey.setDataCarDistanceTime(dataCarDistanceTime);
//            trafficSurvey.setDataCarFollowRate(getRate(dataCarFollowRate));
//            trafficSurvey.setRecordId(deviceSn);
//            trafficSurveyService.saveTrafficSurvey(trafficSurvey);
//        }
//        return buildReturnContent(hourAndMinute);
//    }
//
//    private byte[] buildReturnContent(int hourAndMinute) {
//        final byte[] bytes = new byte[]{(byte) 0x0A, (byte) 0X00, (byte) 0X00, (byte) 0xFF, (byte) 0xFF};
//        final byte[] hourAndMinuteBytes = ByteUtil.intToBytes(hourAndMinute);
//        bytes[1] = hourAndMinuteBytes[0];
//        bytes[2] = hourAndMinuteBytes[1];
//        return bytes;
//    }
//
//    private BigDecimal getRate(int val) {
//        return new BigDecimal(val).divide(new BigDecimal(100));
//    }
//
//    private Date getTrafficTime(int year, int month, int day, int hourAndMinute) {
//        int hour = hourAndMinute / 60;
//        int minute = hourAndMinute % 60;
//        final String dateStr = LocalDateTime.of(year, month, day, hour, minute).format(DATE_FORMATTER);
//        return DateUtil.parse(dateStr);
//    }
//
//
//}
