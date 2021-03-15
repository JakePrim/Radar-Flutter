package com.prim.mybatisplusspringboot.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.AbstractSqlInjector;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.injector.methods.*;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @program: MyBatis-PlusDemo
 * @Description: 自定义的SQL注入器 如果继承AbstractSqlInjector原有的默认的就会被改动了所以需要继承默认的
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-15 21:49
 * @PackageName: com.prim.mybatisplusspringboot.injector
 * @ClassName: MySqlInjector.java
 **/

public class MySqlInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList() {
        //拿到原有的method集合
        List<AbstractMethod> methodList = super.getMethodList();
        //在原有的基础上添加FindAll方法
        methodList.add(new FindAll());
        return methodList;
    }
}
