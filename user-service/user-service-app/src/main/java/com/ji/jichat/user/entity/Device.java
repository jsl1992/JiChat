package com.ji.jichat.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 设备表
 * </p>
 *
 * @author jisl
 * @since 2024-01-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_device")
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id主键
     */
    private Long id;

    /**
     * 设备标识
     */
    private String deviceIdentifier;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备类型（0手机 1电脑 2平板）
     */
    private Integer deviceType;

    /**
     * 在线状态（0离线 1在线）
     */
    private Integer onlineStatus;

    /**
     * 操作系统类型
     */
    private Integer osType;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private Date loginDate;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateUser;


}
