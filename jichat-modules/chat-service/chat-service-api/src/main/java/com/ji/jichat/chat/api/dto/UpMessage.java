package com.ji.jichat.chat.api.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author jisl on 2024/1/22 15:29
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class UpMessage extends Message {


    /**
     * 客户端ip
     */
    private String clientIp;


    /**
     * 版本号
     */
    private String version;



}
