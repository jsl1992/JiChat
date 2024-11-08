package com.ji.jichat.mybatis.gen;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.ji.jichat.mybatis.core.dataobject.BaseDO;
import com.ji.jichat.mybatis.gen.core.EnhanceFreemarkerTemplateEngine;
import com.ji.jichat.mybatis.gen.core.MyBatisConstants;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jisl on 2023/2/16 9:05
 */
public class MybatisCodeGenerator {

    //示例值
    //作者
    private static final String AUTHOR = "jisl";

    //数据库
    private static final String URL = "jdbc:mysql://192.168.137.179:3306/ji_chat?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "aB37z.9";
    //需要生成的表(多张表用","分隔,例如:"t_user,t_role")
    private static final String TABLE_NAMES = "system_sms_code";
    //模块地址
    private static final String MODULE_PATH = "/jichat-modules/user-service/user-service-app";
    //包名
    private static final String PACKAGE_NAME = "com.ji.jichat.user";

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
        String apiPath = projectPath + module_path.replace("-app", "-api");
        String appPath = projectPath + module_path;
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author(author) // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .outputDir(appPath + "\\src\\main\\java") // 指定输出目录
                            .dateType(DateType.ONLY_DATE)
                            .disableOpenDir();

                })
                .packageConfig(builder -> {
                    final Map<OutputFile, String> pathInfo = new HashMap<>();
                    pathInfo.put(OutputFile.xml, appPath + "\\src\\main\\resources\\mapper");  // 设置mapperXml生成路径
                    builder
                            .parent(package_name) // 设置父包名
//                            .moduleName("system") // 设置父包模块名
                            .pathInfo(pathInfo)
                    ;

                })

                .strategyConfig(builder -> {
                    //t_cnfeenode,t_etcflag,t_ff_node,t_ff_noderelation,t_flagfee,t_station,
                    builder.addInclude(tableNames) // 设置需要生成的表名
                            .addTablePrefix("t_", "c_")
                            .controllerBuilder().enableRestStyle().template("/templates/java/controller.java").enableFileOverride()
                            .serviceBuilder().serviceTemplate("/templates/java/service.java").serviceImplTemplate("/templates/java/serviceImpl.java").enableFileOverride()
                            .mapperBuilder().mapperTemplate("/templates/java/mapper.java").mapperXmlTemplate("/templates/java/mapper.xml").enableFileOverride()
                            .entityBuilder().superClass(BaseDO.class).enableLombok().enableFileOverride().javaTemplate("/templates/java/entity.java")
                            .addSuperEntityColumns("create_time", "create_user", "update_time", "update_user")
                            .enableRemoveIsPrefix();
                    // 设置过滤表前缀
                })
                .injectionConfig(consumer -> {
                    // DTO
                    consumer.customFile(new CustomFile.Builder().fileName(MyBatisConstants.DTO).filePath(getFilePath(apiPath, package_name + ".api.dto")).templatePath("/templates/java/dto.java.ftl").build());
                    consumer.customFile(new CustomFile.Builder().fileName(MyBatisConstants.PAGE_DTO).filePath(getFilePath(apiPath, package_name + ".api.dto")).templatePath("/templates/java/page-dto.java.ftl").build());
                    consumer.customFile(new CustomFile.Builder().fileName(MyBatisConstants.VO).filePath(getFilePath(apiPath, package_name + ".api.vo")).templatePath("/templates/java/vo.java.ftl").build());
//                    consumer.customFile(new  CustomFile.Builder().fileName(MyBatisConstants.RPC).filePath("/templates/java/dto.java.ftl").build());
                    consumer.customFile(new CustomFile.Builder().fileName(MyBatisConstants.CONVERT).filePath(getFilePath(appPath, package_name + ".convert")).templatePath("/templates/java/convert.java.ftl").build());
                })
                .dataSourceConfig(builder -> {
                    // 配置 tinyint 转换
                    builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                        // 包装类转基本数据类型
                        IColumnType columnType = typeRegistry.getColumnType(metaInfo);
                        if (DbColumnType.BYTE.equals(columnType)) {
                            return DbColumnType.INTEGER;
                        }
                        return columnType;
                    });
                })
                .templateEngine(new EnhanceFreemarkerTemplateEngine()) // 使用Freemarker引擎模板(ftl)，默认的是Velocity引擎模板(vm)
                .execute();
    }

    private static String getFilePath(String rootPath, String packageName) {
//        将项目文件目录，拼上包名（包名转成文件格式）
        return rootPath + "\\src\\main\\java\\" + packageName.replaceAll("\\.", StringPool.BACK_SLASH + File.separator);
    }


    public static void main(String[] args) {
        MybatisCodeGenerator.generator(URL, USER_NAME, PASSWORD, AUTHOR, MODULE_PATH, PACKAGE_NAME, TABLE_NAMES);
    }


}
