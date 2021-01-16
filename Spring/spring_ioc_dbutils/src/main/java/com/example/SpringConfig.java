package com.example;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

/**
 * spring核心配置类
 */
@Configuration //标记该类为核心配置类
@ComponentScan("com.example") // 开启扫描包及子包注解
@Import({DataSourceConfig.class}) // 引入子配置类
public class SpringConfig {
    @Bean(name = "queryRunner")
    public QueryRunner getQueryRunner(@Autowired DataSource dataSource) {
        return new QueryRunner(dataSource);
    }
}
