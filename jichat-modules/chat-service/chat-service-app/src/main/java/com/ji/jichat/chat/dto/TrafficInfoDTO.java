package com.ji.jichat.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息内容
 *
 * @author jishenglong on 2023/8/15 9:33
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrafficInfoDTO {
    /**
     * 客户端ip
     */
    private String clientIp;

    /**
      *设备身份识别码
      **/
    private String deviceSn;

    /**
      * 站点编号
      * @author jisl on 2023/10/18 8:56
      **/
    private String siteSn;






}
