package com.ji.jichat.user.api.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录用户信息
 *
 * @author 芋道源码
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUser implements Serializable {
    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 设备类型 1手机 2电脑 3平板
     */
    private Integer deviceType;

    /**
     * 登录key
     */
    private String loginKey;

}
