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
 * 字典类型表PageDTO
 * </p>
 *
 * @author jisl
 * @since 2025-04-22
 */
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "字典类型表PageDTO")
public class SystemDictTypePageDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Schema(description = "字典主键")
    private Long id;

    @Schema(description = "字典名称")
    private String name;

    @Schema(description = "字典类型")
    private String type;

    @Schema(description = "状态（0正常 1停用）")
    private Integer status;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建者")
    private String creator;

    @Schema(description = "更新者")
    private String updater;

    @Schema(description = "是否删除")
    private Boolean deleted;

    @Schema(description = "删除时间-开始")
    private Date deletedTimeStart;


    @Schema(description = "删除时间-结束")
    private Date deletedTimeEnd;



}
