package com.ji.jichat.common.pojo;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

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


    public boolean isMatch(Integer code) {
        return Objects.equals(this.getCode(), code);
    }

}
