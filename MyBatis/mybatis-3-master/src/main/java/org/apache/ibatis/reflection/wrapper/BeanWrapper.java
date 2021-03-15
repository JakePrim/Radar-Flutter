/**
 * Copyright 2009-2017 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ibatis.reflection.wrapper;

import org.apache.ibatis.reflection.*;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.invoker.Invoker;
import org.apache.ibatis.reflection.property.PropertyTokenizer;

import java.util.List;

/**
 * 普通对象的 ObjectWrapper 实现类
 *
 * @author Clinton Begin
 */
public class BeanWrapper extends BaseWrapper {

    /**
     * 普通对象
     */
    private final Object object;
    private final MetaClass metaClass;

    public BeanWrapper(MetaObject metaObject, Object object) {
        super(metaObject);
        this.object = object;
        // 创建 MetaClass 对象
        this.metaClass = MetaClass.forClass(object.getClass(), metaObject.getReflectorFactory());
    }

    @Override
    public Object get(PropertyTokenizer prop) {
        // 获得集合类型的属性的指定位置的值
        if (prop.getIndex() != null) {
            // 获得集合类型的属性
            Object collection = resolveCollection(prop, object);
            // 获得指定位置的值
            return getCollectionValue(prop, collection);
        // 获得属性的值
        } else {
            return getBeanProperty(prop, object);
        }
    }

    @Override
    public void set(PropertyTokenizer prop, Object value) {
        // 设置集合类型的属性的指定位置的值
        if (prop.getIndex() != null) {
            // 获得集合类型的属性
            Object collection = resolveCollection(prop, object);
            // 设置指定位置的值
            setCollectionValue(prop, collection, value);
        // 设置属性的值
        } else {
            setBeanProperty(prop, object, value);
        }
    }

    @Override
    public String findProperty(String name, boolean useCamelCaseMapping) {
        return metaClass.findProperty(name, useCamelCaseMapping);
    }

    @Override
    public String[] getGetterNames() {
        return metaClass.getGetterNames();
    }

    @Override
    public String[] getSetterNames() {
        return metaClass.getSetterNames();
    }

    // 获得指定属性的 setting 方法的方法参数。
    @Override
    public Class<?> getSetterType(String name) {
        PropertyTokenizer prop = new PropertyTokenizer(name);
        if (prop.hasNext()) {
            MetaObject metaValue = metaObject.metaObjectForProperty(prop.getIndexedName());
            if (metaValue == SystemMetaObject.NULL_META_OBJECT) {
                return metaClass.getSetterType(name);
            } else {
                return metaValue.getSetterType(prop.getChildren());
            }
        } else {
            return metaClass.getSetterType(name);
        }
    }

    // 获得指定属性的 getting 方法的返回值。
    @Override
    public Class<?> getGetterType(String name) {
        // 创建 PropertyTokenizer 对象，对 name 进行分词
        PropertyTokenizer prop = new PropertyTokenizer(name);
        // 有子表达式
        if (prop.hasNext()) {
            // 创建 MetaObject 对象
            MetaObject metaValue = metaObject.metaObjectForProperty(prop.getIndexedName());
            // 如果 metaValue 为空，则基于 metaClass 获得返回类型
            if (metaValue == SystemMetaObject.NULL_META_OBJECT) {
                return metaClass.getGetterType(name);
            // 如果 metaValue 非空，则基于 metaValue 获得返回类型。
            // 例如：richType.richMap.nihao ，其中 richMap 是 Map 类型，而 nihao 的类型，需要获得到 nihao 的具体值，才能做真正的判断。
            } else {
                // 递归判断子表达式 children ，获得返回值的类型
                return metaValue.getGetterType(prop.getChildren());
            }
        // 有子表达式
        } else {
            // 直接获得返回值的类型
            return metaClass.getGetterType(name);
        }
    }

    @Override
    public boolean hasSetter(String name) {
        PropertyTokenizer prop = new PropertyTokenizer(name);
        if (prop.hasNext()) {
            if (metaClass.hasSetter(prop.getIndexedName())) {
                MetaObject metaValue = metaObject.metaObjectForProperty(prop.getIndexedName());
                if (metaValue == SystemMetaObject.NULL_META_OBJECT) {
                    return metaClass.hasSetter(name);
                } else {
                    return metaValue.hasSetter(prop.getChildren());
                }
            } else {
                return false;
            }
        } else {
            return metaClass.hasSetter(name);
        }
    }

    // 是否有指定属性的 getting 方法
    @Override
    public boolean hasGetter(String name) {
        // 创建 PropertyTokenizer 对象，对 name 进行分词
        PropertyTokenizer prop = new PropertyTokenizer(name);
        // 有子表达式
        if (prop.hasNext()) {
            // 判断是否有该属性的 getting 方法
            if (metaClass.hasGetter(prop.getIndexedName())) {
                // 创建 MetaObject 对象
                MetaObject metaValue = metaObject.metaObjectForProperty(prop.getIndexedName());
                // 如果 metaValue 为空，则基于 metaClass 判断是否有该属性的 getting 方法
                if (metaValue == SystemMetaObject.NULL_META_OBJECT) {
                    return metaClass.hasGetter(name);
                // 如果 metaValue 非空，则基于 metaValue 判断是否有 getting 方法。
                } else {
                    // 递归判断子表达式 children ，判断是否有 getting 方法
                    return metaValue.hasGetter(prop.getChildren());
                }
            } else {
                return false;
            }
        // 有子表达式
        } else {
            // 判断是否有该属性的 getting 方法
            return metaClass.hasGetter(name);
        }
    }

    @Override
    public MetaObject instantiatePropertyValue(String name, PropertyTokenizer prop, ObjectFactory objectFactory) {
        MetaObject metaValue;
        // 获得 setting 方法的方法参数类型
        Class<?> type = getSetterType(prop.getName());
        try {
            // 创建对象
            Object newObject = objectFactory.create(type);
            // 创建 MetaObject 对象
            metaValue = MetaObject.forObject(newObject, metaObject.getObjectFactory(), metaObject.getObjectWrapperFactory(), metaObject.getReflectorFactory());
            // 设置当前对象的值
            set(prop, newObject);
        } catch (Exception e) {
            throw new ReflectionException("Cannot set value of property '" + name + "' because '" + name + "' is null and cannot be instantiated on instance of " + type.getName() + ". Cause:" + e.toString(), e);
        }
        return metaValue;
    }

    private Object getBeanProperty(PropertyTokenizer prop, Object object) {
        try {
            Invoker method = metaClass.getGetInvoker(prop.getName());
            try {
                return method.invoke(object, NO_ARGUMENTS);
            } catch (Throwable t) {
                throw ExceptionUtil.unwrapThrowable(t);
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Throwable t) {
            throw new ReflectionException("Could not get property '" + prop.getName() + "' from " + object.getClass() + ".  Cause: " + t.toString(), t);
        }
    }

    private void setBeanProperty(PropertyTokenizer prop, Object object, Object value) {
        try {
            Invoker method = metaClass.getSetInvoker(prop.getName());
            Object[] params = {value};
            try {
                method.invoke(object, params);
            } catch (Throwable t) {
                throw ExceptionUtil.unwrapThrowable(t);
            }
        } catch (Throwable t) {
            throw new ReflectionException("Could not set property '" + prop.getName() + "' of '" + object.getClass() + "' with value '" + value + "' Cause: " + t.toString(), t);
        }
    }

    @Override
    public boolean isCollection() {
        return false;
    }

    @Override
    public void add(Object element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <E> void addAll(List<E> list) {
        throw new UnsupportedOperationException();
    }

}
