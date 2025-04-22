package com.ji.jichat.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ji.jichat.mybatis.core.dataobject.BaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
* <p>
    * 字典数据表
    * </p>
*
* @author jisl
* @since 2025-04-22
*/
@EqualsAndHashCode(callSuper = true)
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(callSuper = true)
@TableName("system_dict_data")
@Schema( description = "字典数据表")
public class SystemDictData extends BaseDO {


    @Schema(description = "字典编码")
    @TableId(value = "id", type = IdType.AUTO)
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

    @Schema(description = "是否删除")
    private Boolean deleted;


}
