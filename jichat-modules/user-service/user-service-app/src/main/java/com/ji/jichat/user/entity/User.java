package com.ji.jichat.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ji.jichat.mybatis.core.dataobject.BaseDO;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * <p>
 * 用户表
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
@TableName("t_user")
@Schema( description = "用户表")
public class User extends BaseDO {

    private static final long serialVersionUID = 1L;

   @Schema(description="id主键")
    private Long id;

   @Schema(description="用户名")
    private String username;

   @Schema(description="密码")
    private String password;

   @Schema(description="用户昵称")
    private String nickname;

   @Schema(description="手机号")
    private String mobile;

   @Schema(description="帐号状态（0停用 1正常）")
    private Integer status;

   @Schema(description="在线状态（0离线 1在线）")
    private Integer onlineStatus;

   @Schema(description="最后登录IP")
    private String loginIp;

   @Schema(description="最后登录时间")
    private Date loginDate;

   @Schema(description="是否删除(0存在 1已删除)")
    private Integer delFlag;


}
