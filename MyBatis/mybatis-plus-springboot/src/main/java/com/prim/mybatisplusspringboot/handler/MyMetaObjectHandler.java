package com.prim.mybatisplusspringboot.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @program: MyBatis-PlusDemo
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-15 22:11
 * @PackageName: com.prim.mybatisplusspringboot.handler
 * @ClassName: MyMetaObjectHandler.java
 **/
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        //插入时填充的方法 在插入时自动填充默认值
        Object version = getFieldValByName("version", metaObject);
        if (null == version) {
            //该属性为空 可以进行填充
            setFieldValByName("version", 1, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //更新时插入的方法
    }
}
