package com.ji.jichat.excel.util;

import com.ji.jichat.excel.util.core.DictFrameworkUtils;
import com.ji.jichat.excel.util.service.ExcelExportService;
import com.ji.jichat.user.api.DictDataRpc;
import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AutoConfiguration
public class ExcelExportAutoConfiguration {


    @Resource
    private DictDataRpc dictDataRpc;

    @Bean
    public ExcelExportService<?> excelExportService() {
        return new ExcelExportService<>();
    }


    @Bean
    public DictFrameworkUtils dictUtils() {
        DictFrameworkUtils.init(dictDataRpc);
        return new DictFrameworkUtils();
    }
}
