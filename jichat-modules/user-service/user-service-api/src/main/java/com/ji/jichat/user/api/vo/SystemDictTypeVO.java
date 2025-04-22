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
 * 字典类型表VO
 * </p>
 *
 * @author jisl
 * @since 2025-04-22
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "字典类型表VO")
public class SystemDictTypeVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "字典主键")
    @NotNull
    private Long id;

    @Schema(description = "字典名称")
    @NotBlank
    private String name;

    @Schema(description = "字典类型")
    @NotBlank
    private String type;

    @Schema(description = "状态（0正常 1停用）")
    @NotNull
    private Integer status;

    @Schema(description = "备注")
    @NotBlank
    private String remark;

    @Schema(description = "创建者")
    @NotBlank
    private String creator;

    @Schema(description = "更新者")
    @NotBlank
    private String updater;

    @Schema(description = "是否删除")
    @NotNull
    private Boolean deleted;

    @Schema(description = "删除时间")
    @NotNull
    private Date deletedTime;


}
