//
// Created by JakePrim on 2021/9/16.
//
#include <stdio.h>

int jia(int a, int b) {
    return a + b;
}

int jian(int a, int b) {
    return a - b;
}

int cheng(int a, int b) {
    return a * b;
}

int chu(int a, int b) {
    return a / b;
}

//函数指针数组
int main() {
    int *p[10];//指针数组

    ////函数指针数组   [4] -> [int (*) (int,int),int (*) (int,int),int (*) (int,int),int (*) (int,int)]
    int (*pn[4])(int, int) = {jia, jian, cheng, chu};
    int a = 10;
    int b = 20;
    for (int i = 0; i < 4; ++i) {
        /**
         * result:30 result:-10 result:200 result:0
         */
        printf("result:%d\n", pn[i](a, b));
    }

    //函数指针数组：适合应用在观察者集合，回调给各个模块

    return 0;
}