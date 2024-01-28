package com.ji.jichat.user.api.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 好友关系表VO
 * </p>
 *
 * @author jisl
 * @since 2024-01-28
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "UserRelationVO", description = "好友关系表VO")
public class UserRelationVO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("关联id(用户id/群id)")
    private Long relationId;

    @ApiModelProperty("关联类型(1:用户 2:群)")
    private Integer relationType;

    @ApiModelProperty("消息id")
    private Long messageId;


}
