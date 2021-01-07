package com.prim.datasource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;

/**
 * c3p0 与 mybatis 兼容的数据源工厂类
 * 在mybatis-config.xml中配置datasource type属性添加该类 就会自动使用C3P0DataSourceFactory 以c3p0作为连接池数据源
 */
public class C3P0DataSourceFactory extends UnpooledDataSourceFactory {
    public C3P0DataSourceFactory() {
        this.dataSource = new ComboPooledDataSource();
    }
}
