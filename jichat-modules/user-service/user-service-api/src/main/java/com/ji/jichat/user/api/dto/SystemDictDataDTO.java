package com.ji.jichat.user.api.dto;

import java.io.Serializable;
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
 * 字典数据表DTO
 * </p>
 *
 * @author jisl
 * @since 2025-04-22
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "字典数据表DTO")
public class SystemDictDataDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "字典编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Long id;

    @Schema(description = "字典排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Integer sort;

    @Schema(description = "字典标签", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String label;

    @Schema(description = "字典键值", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String value;

    @Schema(description = "字典类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String dictType;

    @Schema(description = "状态（0正常 1停用）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Integer status;

    @Schema(description = "颜色类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String colorType;

    @Schema(description = "css 样式", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String cssClass;

    @Schema(description = "备注", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String remark;

    @Schema(description = "创建者", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String creator;

    @Schema(description = "更新者", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank
    private String updater;

    @Schema(description = "是否删除", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Boolean deleted;


}
