package com.prim.dependencyinversion;

/**
 * 底层
 */
public class Tom {
//    public void studyJavaCourse(){
//        System.out.println("tom 正在学习Java！");
//    }
//    public void studyPythonCourse(){
//        System.out.println("tom 正在学习Python！");
//    }
//    public void studyAICourse(){
//        System.out.println("tom 正在学习AI！");
//    }

    /**
     * 通用的学习方法
     * @param course
     */
//    public void study(ICourse course){
//        course.study();
//    }

    private ICourse course;

    public Tom(ICourse course) {
        this.course = course;
    }


    public Tom() {
    }

    public void setCourse(ICourse course) {
        this.course = course;
    }

    public void study(){
        course.study();
    }


}
