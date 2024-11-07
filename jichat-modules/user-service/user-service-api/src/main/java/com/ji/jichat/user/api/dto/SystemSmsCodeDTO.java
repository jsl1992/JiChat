package com.ji.jichat.user.api.dto;

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
 * 手机验证码DTO
 * </p>
 *
 * @author jisl
 * @since 2024-11-07
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "手机验证码DTO")
public class SystemSmsCodeDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Long id;

    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String mobile;

    @Schema(description = "验证码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String code;

    @Schema(description = "创建 IP", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String createIp;

    @Schema(description = "发送场景", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Byte scene;

    @Schema(description = "今日发送的第几条", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Byte todayIndex;

    @Schema(description = "是否使用", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Byte used;

    @Schema(description = "使用时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Date usedTime;

    @Schema(description = "使用 IP", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String usedIp;

    @Schema(description = "创建者", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String creator;

    @Schema(description = "更新者", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String updater;

    @Schema(description = "是否删除", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Boolean deleted;

    @Schema(description = "租户编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Long tenantId;


}
