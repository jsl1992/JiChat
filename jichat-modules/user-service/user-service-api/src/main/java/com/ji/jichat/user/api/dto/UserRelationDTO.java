package com.ji.jichat.user.api.dto;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(  description = "好友关系表DTO")
public class UserRelationDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema( description = "用户id",hidden = true)
    private Long userId;

    @Schema( description = "关联id(用户id/群id)")
    private Long relationId;




}
