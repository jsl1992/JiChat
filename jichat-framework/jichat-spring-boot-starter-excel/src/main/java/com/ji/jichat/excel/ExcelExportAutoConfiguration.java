package com.ji.jichat.excel;

import com.ji.jichat.excel.core.DictFrameworkUtils;
import com.ji.jichat.user.api.DictDataRpc;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class ExcelExportAutoConfiguration {

    private final DictDataRpc dictDataRpc;

    public ExcelExportAutoConfiguration(DictDataRpc dictDataRpc) {
        this.dictDataRpc = dictDataRpc;
    }


    /**
     * 初始化工具类（静态注入依赖），不注册为 Bean
     */
    @Bean
    public void initDictUtils() {
        DictFrameworkUtils.init(dictDataRpc);
    }
}
