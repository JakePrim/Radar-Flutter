//
// Created by JakePrim on 2021/9/28.
//
#include <iostream>


class Student{
    //修饰
public:
    char *name;
    int age;
    float score;
    void say(){
        printf("%s,%d,%f\n",name,age,score);
    }
private:
};