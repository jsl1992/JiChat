package com.ji.jichat.client.client;


import com.ji.jichat.chat.api.dto.Message;
import com.ji.jichat.chat.api.vo.UserChatServerVO;
import com.ji.jichat.user.api.vo.AuthLoginVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2019-01-21 23:35
 * @since JDK 1.8
 */
@Data
@Component
@ConfigurationProperties("jichat.user")
public class ClientInfo {

    private long userId;

    private String userName;


    private String password;

    private String version;


    @ApiModelProperty(value = "设备标识", required = true, example = "jpOKnf5vGaW9IcitjEPrUGINjDSSEvXd")
    private String deviceIdentifier;

    @ApiModelProperty(value = "设备名称", required = true, example = "iPhoneX")
    private String deviceName;

    @ApiModelProperty(value = "设备类型 1手机 2电脑 3平板", required = true, example = "1")
    private Integer deviceType;

    @ApiModelProperty(value = "操作系统类型 101安卓，102iOS,201 Windows，202 Linux，301 iPad,302 安卓平板 ", required = true, example = "102")
    private Integer osType;


    private String ip;

    private Date startDate;

    private AuthLoginVO authLoginVO;

    private UserChatServerVO userChatServerVO;

    public String getUserKey() {
        return userId + "_" + deviceType;
    }


    public void fillMessage(Message message) {
        message.setUserKey(this.getUserKey());
        message.setNonce(RandomStringUtils.randomAlphanumeric(16));
    }


}
