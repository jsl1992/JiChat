package com.ji.jichat.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ji.jichat.mybatis.core.dataobject.BaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;

/**
 * <p>
 * 好友关系表
 * </p>
 *
 * @author jisl
 * @since 2024-01-28
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("t_user_relation")
@Schema(description = "好友关系表")
public class UserRelation extends BaseDO {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "关联id(用户id/群id)")
    private Long relationId;

    @Schema(description = "频道key")
    private String channelKey;

    @Schema(description = "关联类型(1:用户 2:群)")
    private Integer relationType;


}
