package com.ji.jichat.user.api.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 设备表
 * </p>
 *
 * @author jisl
 * @since 2024-01-21
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "Device对象", description = "设备表")
public class DeviceVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("设备标识")
    private String deviceIdentifier;

    @ApiModelProperty("设备名称")
    private String deviceName;

    @ApiModelProperty("设备类型（1手机 2电脑 3平板）")
    private Integer deviceType;

    @ApiModelProperty("操作系统类型")
    private Integer osType;

    @ApiModelProperty("设备状态(0:离线 1:在线)")
    private Integer deviceStatus;

    @ApiModelProperty("在线状态（0离线 1在线）")
    private Integer onlineStatus;

    @ApiModelProperty("最后登录IP")
    private String loginIp;

    @ApiModelProperty("最后登录时间")
    private Date loginDate;

    @ApiModelProperty("用户id")
    private Long userId;


}
