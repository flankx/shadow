package com.github.shadow.generate;

import java.sql.Types;
import java.util.Collections;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * @author xujianrong@leateckgroup.com
 * @date 2023/7/12
 */
public class TestDemo {
    public static void main(String[] args) {
        FastAutoGenerator.create("", "", "").globalConfig(builder -> {
            builder.author("xuwen") // 设置作者
                .enableSwagger() // 开启 swagger 模式
                .fileOverride() // 覆盖已生成文件
                .outputDir("C:\\Users\\admin\\IdeaProjects\\shadow\\src\\main\\java"); // 指定输出目录
        }).dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
            int typeCode = metaInfo.getJdbcType().TYPE_CODE;
            if (typeCode == Types.SMALLINT) {
                // 自定义类型转换
                return DbColumnType.INTEGER;
            }
            return typeRegistry.getColumnType(metaInfo);
        })).packageConfig(builder -> {
            builder.parent("com.github") // 设置父包名
                .moduleName("shadow") // 设置父包模块名
                .pathInfo(Collections.singletonMap(OutputFile.xml,
                    "C:\\Users\\admin\\IdeaProjects\\shadow\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
        }).strategyConfig(builder -> {
            builder.addInclude("sys_user", "sys_role", "sys_role_permission") // 设置需要生成的表名
                .addTablePrefix(""); // 设置过滤表前缀
        }).templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
            .execute();
    }
}
