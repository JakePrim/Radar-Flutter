package com.prim.dependencyinversion;

public class DipTest {
    //v1
    public static void main(String[] args) {
        //应用层 高层
        //-----------------v1------------------
//        Tom tom = new Tom();
//        tom.studyJavaCourse();
//        tom.studyPythonCourse();
//        //要增加一个新的课程学习
//        tom.studyAICourse();

        //---------------v2-----------
        //传递相应的课程类
//        tom.study(new JavaCourse());
//        tom.study(new PythonCourse());
        //对于一个新的课程 我们只需要增加一个类，而不用去修改Tom类

        //---------------v3--------------
        Tom tom = new Tom(new JavaCourse());
        tom.study();

        //---------------v4---------------------
        //tom只new一次
        Tom tom1 = new Tom();
        tom1.setCourse(new JavaCourse());
        tom1.study();
    }
}
