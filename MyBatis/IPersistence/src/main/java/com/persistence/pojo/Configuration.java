package com.persistence.pojo;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: PBatisTest
 * @Description: 核心配置信息
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-26 14:45
 * @PackageName: com.persistence.pojo
 * @ClassName: Configuration.java
 **/
public class Configuration {
    /**
     * 数据源对象
     */
    private DataSource dataSource;

    /**
     * 存储mapper的映射
     * key: statementid = (namespace + id)
     * value: MappedStatement - 参数类型、返回值类型、SQL语句
     */
    private Map<String, MappedStatement> mapper = new HashMap<>();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MappedStatement> getMapper() {
        return mapper;
    }

    public void setMapper(Map<String, MappedStatement> mapper) {
        this.mapper = mapper;
    }
}
