package com.prim.mybatisplusspringboot.config;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.prim.mybatisplusspringboot.injector.MySqlInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.channels.MulticastChannel;

/**
 * @program: MyBatis-PlusDemo
 * @Description: mybatisplus的分页配置
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-15 16:45
 * @PackageName: com.prim.mybatisplusspringboot.config
 * @ClassName: MybatisPlusConfig.java
 **/
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件
     * @return
     */
    @Bean //放到IOC容器中
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }


    /**
     * 性能分析插件
     *
     * @return
     */
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        performanceInterceptor.setMaxTime(100);// SQL语句的最大执行时长ms
        performanceInterceptor.setFormat(true);//SQL 是否格式化显示
        return performanceInterceptor;
    }

    /**
     * 乐观锁插件
     *
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    /**
     * 自定义的SQL注入器
     */
    @Bean
    public MySqlInjector mySqlInjector() {
        return new MySqlInjector();
    }

}
