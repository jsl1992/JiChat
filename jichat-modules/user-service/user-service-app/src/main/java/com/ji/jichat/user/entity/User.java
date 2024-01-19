package com.ji.jichat.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.io.Serializable;

import lombok.*;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author jisl
 * @since 2024-01-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id主键
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 帐号状态（0停用 1正常）
     */
    private Integer status;

    /**
     * 在线状态（0离线 1在线）
     */
    private Integer onlineStatus;

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private Date loginDate;

    /**
     * 是否删除(0存在 null删除,解决唯一索引冲突)
     */
    private Integer delFlag;

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
