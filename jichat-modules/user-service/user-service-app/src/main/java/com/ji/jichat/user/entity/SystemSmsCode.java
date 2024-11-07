package com.ji.jichat.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ji.jichat.mybatis.core.dataobject.BaseDO;
import java.util.Date;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
* <p>
    * 手机验证码
    * </p>
*
* @author jisl
* @since 2024-11-07
*/
@EqualsAndHashCode(callSuper = true)
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@TableName("system_sms_code")
@Schema( description = "手机验证码")
public class SystemSmsCode extends BaseDO {


    @Schema(description = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "验证码")
    private String code;

    @Schema(description = "创建 IP")
    private String createIp;

    @Schema(description = "发送场景")
    private Byte scene;

    @Schema(description = "今日发送的第几条")
    private Byte todayIndex;

    @Schema(description = "是否使用")
    private Byte used;

    @Schema(description = "使用时间")
    private Date usedTime;

    @Schema(description = "使用 IP")
    private String usedIp;

    @Schema(description = "创建者")
    private String creator;

    @Schema(description = "更新者")
    private String updater;

    @Schema(description = "是否删除")
    private Boolean deleted;

    @Schema(description = "租户编号")
    private Long tenantId;


}
