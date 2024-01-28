package com.ji.jichat.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ji.jichat.mybatis.core.dataobject.BaseDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "ChatMessage对象", description = "聊天信息表")
public class ChatMessage extends BaseDO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id主键")
    private Long id;

    @ApiModelProperty("信息id")
    private Long messageId;

    @ApiModelProperty("频道key")
    private String channelKey;

    @ApiModelProperty("信息来源")
    private Long messageFrom;

    @ApiModelProperty("信息到达")
    private Long messageTo;

    @ApiModelProperty("消息类型 1：文字 2：图片 3：语音 4：视频 5：文件")
    private Integer messageType;

    @ApiModelProperty("信息内容")
    private String messageContent;

    @ApiModelProperty("消息创建时间")
    private Date createTime;


}
