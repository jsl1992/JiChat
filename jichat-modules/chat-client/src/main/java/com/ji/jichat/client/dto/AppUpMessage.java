package com.ji.jichat.client.dto;

import cn.hutool.core.util.IdUtil;
import com.ji.jichat.client.client.ClientInfo;
import com.ji.jichat.common.pojo.UpMessage;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.RandomStringUtils;


/**
 * @author jisl on 2024/1/22 15:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class AppUpMessage extends UpMessage {

    public AppUpMessage(ClientInfo clientInfo) {
        this.setClientIp(clientInfo.getIp());
        this.setUserKey(clientInfo.getUserKey());
        this.setType(1);
        this.setNonce(RandomStringUtils.randomAlphanumeric(16));
        this.setVersion(clientInfo.getVersion());
    }
}
