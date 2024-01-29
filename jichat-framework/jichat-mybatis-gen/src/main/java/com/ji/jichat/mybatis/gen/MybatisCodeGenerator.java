package com.ji.jichat.mybatis.gen;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.ji.jichat.mybatis.core.dataobject.BaseDO;
import com.ji.jichat.mybatis.gen.core.EnhanceFreemarkerTemplateEngine;
import com.ji.jichat.mybatis.gen.core.MyBatisConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jishenglong on 2023/2/16 9:05
 */
public class MybatisCodeGenerator {

    //示例值
    //作者
    private static final String author = "jisl";

    //数据库
    private static final String url = "jdbc:mysql://192.168.137.130:3306/jichat_user?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8";
    private static final String userName = "root";
    private static final String password = "root";
    //需要生成的表(多张表用","分隔,例如:"t_user,t_role")
    private static final String tableNames = "t_user,t_device";
    //模块地址
    private static final String module_path = "/jichat-modules/user-service/user-service-app";
    //包名
    private static final String package_name = "com.ji.jichat.user";

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
    public static void generator(String url, String username, String password, String author, String module_path, String package_name, String tableNames, boolean isCustom) {
        final String projectPath = System.getProperty("user.dir");
        String apiPath = projectPath + module_path.replace("-app", "-api");
        String appPath = projectPath + module_path;
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author(author) // 设置作者
                            .enableSwagger() // 开启 swagger 模式
//                            .fileOverride() // 覆盖已生成文件
                            .outputDir(appPath + "\\src\\main\\java") // 指定输出目录
                            .dateType(DateType.ONLY_DATE)
                            .disableOpenDir();

                })
                .packageConfig(builder -> {
                    final Map<OutputFile, String> pathInfo = new HashMap<>();
                    pathInfo.put(OutputFile.xml, appPath + "\\src\\main\\resources\\mapper");  // 设置mapperXml生成路径
                    pathInfo.put(OutputFile.other, apiPath + "\\src\\main\\java");  //自定义路径
                    builder
                            .parent(package_name) // 设置父包名
//                            .moduleName("system") // 设置父包模块名
                            .pathInfo(pathInfo)
                    ;

                })
                .templateConfig(builder -> {
                            builder.entity("/templates/java/entity.java");
                        }
                )
                .strategyConfig(builder -> {
                    //t_cnfeenode,t_etcflag,t_ff_node,t_ff_noderelation,t_flagfee,t_station,
                    builder.addInclude(tableNames) // 设置需要生成的表名
                            .addTablePrefix("t_", "c_")
                            .controllerBuilder().enableRestStyle()
                            .entityBuilder().superClass(BaseDO.class).enableLombok().fileOverride()
                            .addSuperEntityColumns("create_time","create_user","update_time","update_user");
                    // 设置过滤表前缀
                })
                .injectionConfig(consumer -> {
                    if (isCustom) {
                        Map<String, String> customFile = new HashMap<>(16);
                        // DTO
                        customFile.put(MyBatisConstants.DTO, "/templates/java/dto.java.ftl");
                        customFile.put(MyBatisConstants.VO, "/templates/java/vo.java.ftl");
//                        customFile.put(MyBatisConstants.RPC, "/templates/java/vo.java.ftl");
                        customFile.put(MyBatisConstants.CONVERT, "/templates/java/convert.java.ftl");
                        consumer.customFile(customFile);
                    }
                })
                .templateEngine(new EnhanceFreemarkerTemplateEngine()) // 使用Freemarker引擎模板(ftl)，默认的是Velocity引擎模板(vm)
                .execute();
    }

    public static void main(String[] args) {
        MybatisCodeGenerator.generator(url, userName, password, author, module_path, package_name, tableNames, true);
    }


}
