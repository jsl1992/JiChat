package com.ji.jichat.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ji.jichat.mybatis.core.dataobject.BaseDO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

/**
 * <p>
 * 聊天信息表
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
@TableName("t_chat_message")
@Schema(description = "聊天信息表")
public class ChatMessage extends BaseDO {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id主键")
    private Long id;

    @Schema(description = "信息id")
    private Long messageId;

    @Schema(description = "频道key")
    private String channelKey;

    @Schema(description = "信息来源")
    private Long messageFrom;

    @Schema(description = "信息到达")
    private Long messageTo;

    @Schema(description = "消息类型 1：文字 2：图片 3：语音 4：视频 5：文件")
    private Integer messageType;

    @Schema(description = "信息内容")
    private String messageContent;

    @Schema(description = "消息创建时间")
    private Date createTime;


}
