/**
 * Copyright 2009-2018 the original author or authors.
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
package org.apache.ibatis.reflection;

import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 对象元数据，提供了对象的属性值的获得和设置
 *
 * @author Clinton Begin
 */
public class MetaObject {

    /**
     * 原始 Object 对象
     */
    private final Object originalObject;
    /**
     * 封装过的 Object 对象
     */
    private final ObjectWrapper objectWrapper;
    private final ObjectFactory objectFactory;
    private final ObjectWrapperFactory objectWrapperFactory;
    private final ReflectorFactory reflectorFactory;

    private MetaObject(Object object, ObjectFactory objectFactory, ObjectWrapperFactory objectWrapperFactory, ReflectorFactory reflectorFactory) {
        this.originalObject = object;
        this.objectFactory = objectFactory;
        this.objectWrapperFactory = objectWrapperFactory;
        this.reflectorFactory = reflectorFactory;

        if (object instanceof ObjectWrapper) {
            this.objectWrapper = (ObjectWrapper) object;
        } else if (objectWrapperFactory.hasWrapperFor(object)) {
            // 创建 ObjectWrapper 对象
            this.objectWrapper = objectWrapperFactory.getWrapperFor(this, object);
        } else if (object instanceof Map) {
            // 创建 MapWrapper 对象
            this.objectWrapper = new MapWrapper(this, (Map) object);
        } else if (object instanceof Collection) {
            // 创建 CollectionWrapper 对象
            this.objectWrapper = new CollectionWrapper(this, (Collection) object);
        } else {
            // 创建 BeanWrapper 对象
            this.objectWrapper = new BeanWrapper(this, object);
        }
    }

    /**
     * 创建 MetaObject 对象
     *
     * @param object 原始 Object 对象
     * @param objectFactory
     * @param objectWrapperFactory
     * @param reflectorFactory
     * @return MetaObject 对象
     */
    public static MetaObject forObject(Object object, ObjectFactory objectFactory, ObjectWrapperFactory objectWrapperFactory, ReflectorFactory reflectorFactory) {
        if (object == null) {
            return SystemMetaObject.NULL_META_OBJECT;
        } else {
            return new MetaObject(object, objectFactory, objectWrapperFactory, reflectorFactory);
        }
    }

    public ObjectFactory getObjectFactory() {
        return objectFactory;
    }

    public ObjectWrapperFactory getObjectWrapperFactory() {
        return objectWrapperFactory;
    }

    public ReflectorFactory getReflectorFactory() {
        return reflectorFactory;
    }

    public Object getOriginalObject() {
        return originalObject;
    }

    public String findProperty(String propName, boolean useCamelCaseMapping) {
        return objectWrapper.findProperty(propName, useCamelCaseMapping);
    }

    public String[] getGetterNames() {
        return objectWrapper.getGetterNames();
    }

    public String[] getSetterNames() {
        return objectWrapper.getSetterNames();
    }

    public Class<?> getSetterType(String name) {
        return objectWrapper.getSetterType(name);
    }

    public Class<?> getGetterType(String name) {
        return objectWrapper.getGetterType(name);
    }

    public boolean hasSetter(String name) {
        return objectWrapper.hasSetter(name);
    }

    public boolean hasGetter(String name) {
        return objectWrapper.hasGetter(name);
    }

    public Object getValue(String name) {
        // 创建 PropertyTokenizer 对象，对 name 分词
        PropertyTokenizer prop = new PropertyTokenizer(name);
        // 有子表达式
        if (prop.hasNext()) {
            // 创建 MetaObject 对象
            MetaObject metaValue = metaObjectForProperty(prop.getIndexedName());
            // 递归判断子表达式 children ，获取值
            if (metaValue == SystemMetaObject.NULL_META_OBJECT) {
                return null;
            } else {
                return metaValue.getValue(prop.getChildren());
            }
        // 无子表达式
        } else {
            // 获取值
            return objectWrapper.get(prop);
        }
    }

    public void setValue(String name, Object value) {
        // 创建 PropertyTokenizer 对象，对 name 分词
        PropertyTokenizer prop = new PropertyTokenizer(name);
        // 有子表达式
        if (prop.hasNext()) {
            // 创建 MetaObject 对象
            MetaObject metaValue = metaObjectForProperty(prop.getIndexedName());
            // 递归判断子表达式 children ，设置值
            if (metaValue == SystemMetaObject.NULL_META_OBJECT) {
                if (value == null) {
                    // don't instantiate child path if value is null
                    return;
                } else {
                    // 创建值
                    metaValue = objectWrapper.instantiatePropertyValue(name, prop, objectFactory);
                }
            }
            // 设置值
            metaValue.setValue(prop.getChildren(), value);
        // 无子表达式
        } else {
            // 设置值
            objectWrapper.set(prop, value);
        }
    }

    public MetaObject metaObjectForProperty(String name) {
        // 获得属性值
        Object value = getValue(name);
        // 创建 MetaObject 对象
        return MetaObject.forObject(value, objectFactory, objectWrapperFactory, reflectorFactory);
    }

    public ObjectWrapper getObjectWrapper() {
        return objectWrapper;
    }

    public boolean isCollection() {
        return objectWrapper.isCollection();
    }

    public void add(Object element) {
        objectWrapper.add(element);
    }

    public <E> void addAll(List<E> list) {
        objectWrapper.addAll(list);
    }

}
