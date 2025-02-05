package com.ji.jichat.excel.util;

import com.ji.jichat.excel.util.service.ExcelExportService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExcelExportAutoConfiguration {

    @Bean
    public ExcelExportService<?> excelExportService() {
        return new ExcelExportService<>();
    }
}
