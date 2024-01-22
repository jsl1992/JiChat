package com.ji.jichat.common.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
  *
  * @author jisl on 2024/1/22 15:29
  **/
@Data
@NoArgsConstructor
public class UpMessage extends Message {


    /**
     * 版本号
     */
    private String version;


    public boolean isMatch(Integer code) {
        return Objects.equals(this.getCode(), code);
    }

}
