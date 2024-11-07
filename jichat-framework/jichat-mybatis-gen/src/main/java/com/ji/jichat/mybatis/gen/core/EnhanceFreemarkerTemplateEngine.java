package com.ji.jichat.mybatis.gen.core;

import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 代码生成器支持自定义[DTO\VO等]模版
 */
public final class EnhanceFreemarkerTemplateEngine extends FreemarkerTemplateEngine {


    @Override
    protected void outputCustomFile(@NotNull List<CustomFile> customFiles, @NotNull TableInfo tableInfo, @NotNull Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        for (CustomFile customFile : customFiles) {
            String rootPath = customFile.getFilePath();
            String fileName = String.format(rootPath + File.separator + entityName + "%s", customFile.getFileName());
            this.outputFile(new File(fileName), objectMap, customFile.getTemplatePath(), true);
        }
    }


}