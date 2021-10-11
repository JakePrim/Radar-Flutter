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

typedef int PN (int,int);

void change(PN **p){
    *p = jia;
}

void changeError(PN *p){
    p = jia;
}

int main(){
    PN *p = jia;
    printf("p:%d\n",p(1,2));//3
    p = jian;//修改函数指针的指向
    printf("p:%d\n",p(1,2));//-1

    //变成p为jia函数
    changeError(p);//p->jian     jian -> jia 错误的，并不会改变p的指向

    printf("p:%d\n",p(1,2));//-1  p还是减法没有变成加法

    //如果要通过函数改变外部的变量，一定要传递外部变量的地址,如果是指针那么函数要用二级指针作为函数接收
    change(&p);//要改变p的指向，必须要传递&p

    printf("p:%d\n",p(1,2));//3  p还是减法没有变成加法

    return 0;
}

