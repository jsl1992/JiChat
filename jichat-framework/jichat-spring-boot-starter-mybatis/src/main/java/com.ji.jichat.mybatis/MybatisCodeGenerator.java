package com.ji.jichat.mybatis;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;

import java.util.Collections;

/**
 * @author jishenglong on 2023/2/16 9:05
 */
public class MybatisCodeGenerator {
    /**
     * 代码生成
     *
     * @param url          数据库地址
     * @param username     用户名
     * @param password     密码
     * @param author       作者
     * @param module_path  模块地址
     * @param package_name 包名
     * @param tableNames   需要生成的表(多张表用","分隔,例如:"t_user,t_role")
     * @author jisl on 2024/1/21 14:19
     **/
    public static void generator(String url, String username, String password, String author, String module_path, String package_name, String tableNames) {
        final String projectPath = System.getProperty("user.dir");
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author(author) // 设置作者
                            .enableSwagger() // 开启 swagger 模式
//                            .fileOverride() // 覆盖已生成文件
                            .outputDir(projectPath + module_path + "\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder
                            .parent(package_name) // 设置父包名
//                            .moduleName("system") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath + module_path + "\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
//t_cnfeenode,t_etcflag,t_ff_node,t_ff_noderelation,t_flagfee,t_station,
                    builder.addInclude(tableNames) // 设置需要生成的表名
                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }


}
