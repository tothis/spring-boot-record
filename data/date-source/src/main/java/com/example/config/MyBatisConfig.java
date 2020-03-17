package com.example.config;

import com.example.type.DataSourceEnum;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MyBatisConfig {

    /**
     * @Qualifier 根据名称注入 具有相同类型的多个实例时 使用名称注入
     */
    @Bean
    public DynamicDataSource dynamicDataSource(@Qualifier("db1") DataSource db1, @Qualifier("db2") DataSource db2) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceEnum.db1, db1);
        targetDataSources.put(DataSourceEnum.db2, db2);
        DynamicDataSource dataSource = new DynamicDataSource();
        // 该方法是AbstractRoutingDataSource的方法
        dataSource.setTargetDataSources(targetDataSources);
        // 设置默认的datasource
        dataSource.setDefaultTargetDataSource(db1);
        return dataSource;
    }

    /**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DynamicDataSource dynamicDataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dynamicDataSource); // 指定数据源(这个必须有 否则报错)
        // 下边两句仅仅用于*.xml文件 如果整个持久层操作不需要使用到xml文件的话 只用注解就可以搞定 则不加
        // factoryBean.setTypeAliasesPackage(typeAliasesPackage); // 指定基本包
        // factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        return factoryBean.getObject();
    }
}