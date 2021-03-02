package com.prim.springbootdata.pojo;

/**
 * @program: springboot-demo
 * @Description:宠物类
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-28 20:13
 * @PackageName: com.prim.springbootdata.pojo
 * @ClassName: Pet.java
 **/
public class Pet {
    //品种
    private String type;
    //名称
    private String name;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
