package com.persistence.pojo;

import java.io.Serializable;

/**
 * @program: PBatisTest
 * @Description: 对Mapper映射文件的信息
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-25 19:09
 * @PackageName: com.persistence.pojo
 * @ClassName: MappedStatement.java
 **/
public class MappedStatement implements Serializable {
    //id标识
    private String id;
    //返回值类型
    private String resultType;
    //参数值类型
    private String parameterType;
    //SQL语句
    private String sql;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public String toString() {
        return "MappedStatement{" +
                "id='" + id + '\'' +
                ", resultType='" + resultType + '\'' +
                ", paramterType='" + parameterType + '\'' +
                ", sql='" + sql + '\'' +
                '}';
    }
}
