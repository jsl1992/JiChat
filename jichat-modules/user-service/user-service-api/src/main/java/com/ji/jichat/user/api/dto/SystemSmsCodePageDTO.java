package com.ji.jichat.user.api.dto;

import java.io.Serializable;
import java.util.Date;
import java.io.Serial;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 手机验证码PageDTO
 * </p>
 *
 * @author jisl
 * @since 2024-11-08
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "手机验证码PageDTO")
public class SystemSmsCodePageDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Schema(description = "编号")
    private Long id;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "验证码")
    private String code;

    @Schema(description = "创建 IP")
    private String createIp;

    @Schema(description = "发送场景")
    private Integer scene;

    @Schema(description = "今日发送的第几条")
    private Integer todayIndex;

    @Schema(description = "是否使用")
    private Integer used;

    @Schema(description = "使用时间-开始")
    private Date usedTimeStart;


    @Schema(description = "使用时间-结束")
    private Date usedTimeEnd;

    @Schema(description = "使用 IP")
    private String usedIp;



}
