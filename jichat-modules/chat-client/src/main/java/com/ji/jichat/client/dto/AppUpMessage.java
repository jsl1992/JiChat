package com.ji.jichat.client.dto;

import cn.hutool.core.util.RandomUtil;
import com.ji.jichat.client.client.ClientInfo;
import com.ji.jichat.common.pojo.UpMessage;
import lombok.Data;


/**
 * @author jisl on 2024/1/22 15:20
 */
@Data
public class AppUpMessage extends UpMessage {

    public AppUpMessage(ClientInfo clientInfo) {
        this.setClientIp(clientInfo.getIp());
        this.setUserId(clientInfo.getUserId());
        this.setDeviceType(clientInfo.getDeviceType());
        this.setType(1);
        this.setNonce(RandomUtil.randomString(18));
        this.setVersion(clientInfo.getVersion());
    }
}
