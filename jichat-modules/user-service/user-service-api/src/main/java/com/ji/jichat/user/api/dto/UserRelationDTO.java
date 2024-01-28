package com.ji.jichat.user.api.dto;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 好友关系表DTO
 * </p>
 *
 * @author jisl
 * @since 2024-01-28
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "UserRelationDTO", description = "好友关系表DTO")
public class UserRelationDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("关联id(用户id/群id)")
    private Long relationId;




}
