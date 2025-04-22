package com.ji.jichat.user.api.dto;

import java.io.Serializable;
import java.io.Serial;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 字典数据表PageDTO
 * </p>
 *
 * @author jisl
 * @since 2025-04-22
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "字典数据表PageDTO")
public class SystemDictDataPageDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Schema(description = "字典编码")
    private Long id;

    @Schema(description = "字典排序")
    private Integer sort;

    @Schema(description = "字典标签")
    private String label;

    @Schema(description = "字典键值")
    private String value;

    @Schema(description = "字典类型")
    private String dictType;

    @Schema(description = "状态（0正常 1停用）")
    private Integer status;

    @Schema(description = "颜色类型")
    private String colorType;

    @Schema(description = "css 样式")
    private String cssClass;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建者")
    private String creator;

    @Schema(description = "更新者")
    private String updater;

    @Schema(description = "是否删除")
    private Boolean deleted;



}
