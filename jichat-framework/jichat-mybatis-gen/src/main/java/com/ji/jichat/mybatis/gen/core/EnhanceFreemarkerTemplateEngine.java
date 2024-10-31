package com.ji.jichat.mybatis.gen.core;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.util.Map;
import java.util.Objects;

/**
 * 代码生成器支持自定义[DTO\VO等]模版
 */
public final class EnhanceFreemarkerTemplateEngine extends FreemarkerTemplateEngine {


    @Override
    protected void outputCustomFile(Map<String, String> customFile, TableInfo tableInfo, Map<String, Object> objectMap) {
        final Map aPackage = (Map) objectMap.get("package");
        String parentPackage = (String) aPackage.get("Parent");
        String entityName = tableInfo.getEntityName();
        for (Map.Entry<String, String> entry : customFile.entrySet()) {
            String otherPath = getPath(parentPackage, entry.getKey());
            String fileName = String.format(otherPath + File.separator + entityName + "%s", entry.getKey());
            this.outputFile(new File(fileName), objectMap, entry.getValue(), false);
        }

    }

    private String getPath(String parentPackage, String classType) {
        if (!Objects.equals(classType, MyBatisConstants.CONVERT)) {
//            Convert 目录在app，其他都在api里
            parentPackage = parentPackage + ".api";
        }
        String otherPath = joinPath(this.getPathInfo(OutputFile.other), parentPackage);
        switch (classType) {
            case MyBatisConstants.DTO:
                otherPath = otherPath + File.separator + "dto";
                break;
            case MyBatisConstants.VO:
                otherPath = otherPath + File.separator + "vo";
                break;
            case MyBatisConstants.RPC:
                otherPath = otherPath + File.separator;
                break;
            case MyBatisConstants.CONVERT:
                otherPath = Objects.requireNonNull(this.getPathInfo(OutputFile.entity)).replaceAll("entity", "") + File.separator + "convert";
                break;
            default:
                throw new RuntimeException("当前类型不支持");
        }
        return otherPath;
    }


    private String joinPath(String parentDir, String packageName) {
        if (StringUtils.isBlank(parentDir)) {
            parentDir = System.getProperty(ConstVal.JAVA_TMPDIR);
        }
        if (!StringUtils.endsWith(parentDir, File.separator)) {
            parentDir += File.separator;
        }
        packageName = packageName.replaceAll("\\.", StringPool.BACK_SLASH + File.separator);
        return parentDir + packageName;
    }
}