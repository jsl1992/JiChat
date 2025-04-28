package com.ji.jichat.excel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * excel导入返回对象
 *
 * @author jishenglong on 2019/9/10 13:38
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExcelImportDTO {

    @Schema(description ="成功数")
    private Integer successNum;

    @Schema(description ="失败数")
    private Integer failNum;

    @Schema(description ="异常详情")
    private List<String> errors;

    @Schema(description ="用时（s）")
    private Long timeConsuming;


}
