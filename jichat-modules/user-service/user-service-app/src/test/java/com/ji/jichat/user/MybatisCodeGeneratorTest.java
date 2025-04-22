package com.ji.jichat.user;


import com.ji.jichat.mybatis.gen.MybatisCodeGenerator;

/**
 * @author jisl on 2024/1/21 14:15
 */
public class MybatisCodeGeneratorTest {

    //示例值
    //作者
    private static final String author = "jisl";

    //数据库
    private static final String url = "jdbc:mysql://192.168.137.179:3306/ji_chat?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8";
    private static final String userName = "root";
    private static final String password = "aB37z.9";
    //需要生成的表(多张表用","分隔,例如:"t_user,t_role")
    private static final String tableNames = "system_dict_type,system_dict_data";
    //模块地址
    private static final String module_path = "/jichat-modules/user-service/user-service-app";
    //包名
    private static final String package_name = "com.ji.jichat.user";

    public static void main(String[] args) {
        MybatisCodeGenerator.generator(url,userName,password,author,module_path,package_name,tableNames);
    }
}
