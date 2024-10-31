package com.ji.jichat.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ji.jichat.mybatis.core.dataobject.BaseDO;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * <p>
 * 设备表
 * </p>
 *
 * @author jisl
 * @since 2024-01-24
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("t_device")
@Schema( description = "设备表")
public class Device extends BaseDO {

    private static final long serialVersionUID = 1L;

    @Schema(description="id主键")
    private Long id;

    @Schema(description="设备标识")
    private String deviceIdentifier;

    @Schema(description="设备名称")
    private String deviceName;

    @Schema(description="设备类型（1手机 2电脑 3平板）")
    private Integer deviceType;

    @Schema(description="操作系统类型")
    private Integer osType;


    @Schema(description="在线状态（0离线 1在线）")
    private Integer onlineStatus;

    @Schema(description="最后登录IP")
    private String loginIp;

    @Schema(description="最后登录时间")
    private Date loginDate;

    @Schema(description="用户id")
    private Long userId;


}
