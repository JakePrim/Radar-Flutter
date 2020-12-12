package com.prim.dependencyinversion;

public class JavaCourse implements ICourse{
    @Override
    public void study() {
        System.out.println("tom 正在学习Java！");
    }
}
