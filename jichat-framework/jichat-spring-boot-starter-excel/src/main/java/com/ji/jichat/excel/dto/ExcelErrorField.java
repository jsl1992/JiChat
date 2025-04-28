package com.ji.jichat.excel.dto;

import lombok.*;


@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExcelErrorField {

    private Integer rowIndex;
    private String errorMessage;
}
