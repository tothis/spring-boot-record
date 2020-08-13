package com.example.generator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.BeetlTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李磊
 * @datetime 2020/1/11 17:56
 * @description
 */
public class Generator {

    /**
     * 生成代码
     */
    public static void main(String[] args) {
        // 项目路径
        String path = System.getProperty("user.dir");

        // 生成文件人
        String author = "李磊";

        // 数据库连接信息
        String url = "jdbc:mysql://192.168.92.134:3306/test?useUnicode=true" +
                "&characterEncoding=utf8&autoReconnect=true&failOverReadOnly=false" +
                "&useAffectedRows=true&serverTimezone=GMT&useSSL=false";
        String driverName = "com.mysql.cj.jdbc.Driver";
        String username = "root";
        String password = "123456";

        // 父包名
        String parentPackage = "com.example";

        // 实体类输出包
        String entityPackage = "model";

        // mapper输出包
        String mapperPackage = "mapper";

        // service输出包
        String servicePackage = "service";

        // serviceImpl输出包
        String serviceImplPackage = "service.impl";

        // controller输出包
        String controllerPackage = "controller";

        // entity输出模版
        String entityTemplate = "templates/entity.java";

        // service输出模版
        String serviceTemplate = "templates/service.java";

        // mapper输出模版
        String mapperTemplate = "templates/mapper.xml";

        // controller输出模版
        String controllerTemplate = "templates/controller.java";

        // 父类实体
        String baseEntity = "com.example.model.BaseEntity";

        // 父类控制器
        String baseController = "com.example.controller.BaseController";

        // 代码生成器
        AutoGenerator generator = new AutoGenerator();

        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(path + "/src/main/java");
        globalConfig.setAuthor(author);
        globalConfig.setOpen(false);
        // globalConfig.setSwagger2(true); 实体属性Swagger2注解
        generator.setGlobalConfig(globalConfig);

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(url);
        dataSourceConfig.setDriverName(driverName);
        dataSourceConfig.setUsername(username);
        dataSourceConfig.setPassword(password);
        dataSourceConfig.setDbQuery(new MySqlQuery() {
            /**
             * 重写父类预留查询自定义字段<br>
             * 这里查询的SQL对应父类tableFieldsSql的查询字段 默认不能满足你的需求请重写它<br>
             * 模板中调用 table.fields 获取所有字段信息
             * 然后循环字段获取field.customMap从MAP中获取注入字段如下 NULL或者PRIVILEGES
             */
            @Override
            public String[] fieldCustom() {
                return new String[]{"NULL", "PRIVILEGES"};
            }
        });
        generator.setDataSource(dataSourceConfig);

        // 包配置
        PackageConfig packageConfig = new PackageConfig();
        // 模块名
        packageConfig.setModuleName("");
        packageConfig.setParent(parentPackage);
        packageConfig.setEntity(entityPackage);
        packageConfig.setMapper(mapperPackage);
        packageConfig.setService(servicePackage);
        packageConfig.setServiceImpl(serviceImplPackage);
        packageConfig.setController(controllerPackage);
        generator.setPackageInfo(packageConfig);

        // 自定义配置
        InjectionConfig config = new InjectionConfig() {
            // 自定义属性注入 在.ftl或.vm模板中 通过${cfg.key}获取属性值
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("key", "value");
                this.setMap(map);
            }
        };

        // beetl模板引擎
        String templatePath = "/templates/mapper.xml.btl";
        // freemarker模板引擎
        // String templatePath = "/templates/mapper.xml.btl";
        // velocity模板引擎
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> fileOutConfigs = new ArrayList<>();
        // 自定义配置会被优先输出
        fileOutConfigs.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 如果Entity设置了前后缀 此处注意xml的名称会跟着发生变化
                return path + "/src/main/java/com/example/mapper/" + packageConfig.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        config.setFileOutConfigList(fileOutConfigs);
        generator.setCfg(config);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setEntity(entityTemplate);
        templateConfig.setService(serviceTemplate);
        templateConfig.setController(controllerTemplate);
        templateConfig.setXml(null); // 使用自定义xml目录
        generator.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setSuperEntityClass(baseEntity);
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setRestControllerStyle(true);
        strategyConfig.setSuperControllerClass(baseController);
        strategyConfig.setSuperEntityColumns("id");
        strategyConfig.setControllerMappingHyphenStyle(true);
        strategyConfig.setTablePrefix("sys_");
        generator.setStrategy(strategyConfig);
        generator.setTemplateEngine(new BeetlTemplateEngine());
        generator.execute();
    }
}