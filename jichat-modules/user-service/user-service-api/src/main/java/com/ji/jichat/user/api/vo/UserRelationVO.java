package com.ji.jichat.user.api.vo;

import java.io.Serializable;


import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "好友关系表VO")
public class UserRelationVO implements Serializable {

    private static final long serialVersionUID = 1L;


    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "关联id(用户id/群id)")
    private Long relationId;

    @Schema(description = "关联类型(1:用户 2:群)")
    private Integer relationType;

    @Schema(description = "频道key")
    private String channelKey;

    @Schema(description = "消息id")
    private Long messageId;


}
