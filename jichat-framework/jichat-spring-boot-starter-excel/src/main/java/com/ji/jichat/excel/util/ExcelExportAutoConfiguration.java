package com.ji.jichat.excel.util;

import com.ji.jichat.excel.util.core.DictFrameworkUtils;
import com.ji.jichat.excel.util.service.ExcelExportService;
import com.ji.jichat.user.api.DictDataRpc;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

@AutoConfiguration
public class ExcelExportAutoConfiguration {

    private final DictDataRpc dictDataRpc;

    public ExcelExportAutoConfiguration(DictDataRpc dictDataRpc) {
        this.dictDataRpc = dictDataRpc;
    }

    /**
     * 注册导出服务，用户未定义时才注入
     */
    @Bean
    @ConditionalOnMissingBean
    public ExcelExportService<?> excelExportService() {
        return new ExcelExportService<>();
    }

    /**
     * 初始化工具类（静态注入依赖），不注册为 Bean
     */
    @Bean
    public void initDictUtils() {
        DictFrameworkUtils.init(dictDataRpc);
    }
}
