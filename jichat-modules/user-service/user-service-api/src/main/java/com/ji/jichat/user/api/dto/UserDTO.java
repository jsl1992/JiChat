package com.ji.jichat.user.api.dto;

import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 用户表DTO
 * </p>
 *
 * @author jisl
 * @since 2024-01-24
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "UserDTO", description = "用户表DTO")
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("帐号状态（0停用 1正常）")
    private Integer status;

    @ApiModelProperty("在线状态（0离线 1在线）")
    private Integer onlineStatus;

    @ApiModelProperty("最后登录IP")
    private String loginIp;

    @ApiModelProperty("最后登录时间")
    private Date loginDate;

    @ApiModelProperty("是否删除(0存在 null删除,解决唯一索引冲突)")
    private Integer delFlag;


}
