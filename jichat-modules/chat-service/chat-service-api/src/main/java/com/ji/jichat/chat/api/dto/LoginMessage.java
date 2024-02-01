package com.ji.jichat.chat.api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * @author jisl on 2024/2/1 9:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class LoginMessage extends Message {

    private String token;

    private String ip;


}
