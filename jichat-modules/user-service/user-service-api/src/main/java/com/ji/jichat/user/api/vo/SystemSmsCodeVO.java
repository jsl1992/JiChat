package com.ji.jichat.user.api.vo;

import java.io.Serializable;
import java.util.Date;
import java.io.Serial;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 手机验证码VO
 * </p>
 *
 * @author jisl
 * @since 2024-11-08
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "手机验证码VO")
public class SystemSmsCodeVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "编号")
    @NotNull
    private Long id;

    @Schema(description = "手机号")
    @NotBlank
    private String mobile;

    @Schema(description = "验证码")
    @NotBlank
    private String code;

    @Schema(description = "创建 IP")
    @NotBlank
    private String createIp;

    @Schema(description = "发送场景")
    @NotNull
    private Integer scene;

    @Schema(description = "今日发送的第几条")
    @NotNull
    private Integer todayIndex;

    @Schema(description = "是否使用")
    @NotNull
    private Integer used;

    @Schema(description = "使用时间")
    @NotNull
    private Date usedTime;

    @Schema(description = "使用 IP")
    @NotBlank
    private String usedIp;


}
