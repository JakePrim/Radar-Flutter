//
// Created by JakePrim on 2021/9/23.
//
#include <stdio.h>

int main(){
    //结构体 类似java的javabean
    struct stu{
        char *name;
        int num;
        int age;
        char group;
        float scope;
    } person3;//声明时设置结构体

    //类似匿名 内部类
    struct{
        char *name;//8

        int num;//4
        int age;//4

        //这两个也是按照8个字节来算的
        char group;//1
        float scope;//4
    } person4;//声明时设置结构体

    //定义结构体
    struct stu person,person2;

    printf("person:%d\n", sizeof(person));//总大小：24字节  8 + 4 + 4 + 1 + 4
    // 内存在64位 每次取8个字节

    //常量指针
    const int * p;

    int const * p1;
    //指针常量
    int * const p2;


    return 0;
}

