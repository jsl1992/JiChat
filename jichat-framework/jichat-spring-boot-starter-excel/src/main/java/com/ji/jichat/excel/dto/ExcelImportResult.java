package com.ji.jichat.excel.dto;

import com.google.common.collect.Lists;
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
public class ExcelImportResult<T> {

    private List<T> successList = Lists.newArrayList();
    private List<String> failList = Lists.newArrayList();


}
