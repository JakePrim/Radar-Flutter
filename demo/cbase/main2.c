//
// Created by JakePrim on 2021/7/26.
//
#include <stdio.h>
#include <malloc.h>

void test() {
    int *p;//指针存储的是内存地址,只能是地址,指向内存的某一个区域 计算机中有地址总线
    //如果只声明指针，不给指针赋值就是一个野指针
    int a = 10;//实体变量
    p = &a;//指针p赋值为a的内存地址
    //查看a的地址
    printf("&a = %p\n", &a);//&a = 000000000061FDE4
    //查看指针变量p存储的内容
    printf("p=%p\n", p);//p=000000000061FDE4
    //指针变量p也是有地址的,*p取出改地址的实体也就是存储的值
    printf("p=%p,*p=%d\n", &p, *p);//p=000000000061FDE8,*p=10

    //改了*p的值，原来的值也会改变，因为都是同一个地址.相当于改变了内存地址000000000061FDE4 存储的值
    *p = 100;
    printf("a=%d,*p=%d\n", a, *p);//a=100,*p=100

    printf("sizeof p = %llu\n", sizeof p);//查看指针的大小sizeof p = 8

    char b = 'c';
    char *c = &b;
    char d = *c;//取出指针变量c，存储地址的实际值
    printf("sizeof c = %llu\n", sizeof c);//sizeof c = 8
    printf("d=%c\n", d);//d=c
    //指针占用的字节就是固定的8个字节(64为8个字节，32位4个字节)，指针的大小是不变的和指针的类型无关，地址是没有长短之分的 地址是固定的长度
    //注意：指针是有类型的，不能char类型的指针，存储int类型的指针
    //变量a是int类型
    //char *e = &a;//Incompatible pointer types initializing 'char *' with an expression of type 'int *'

    //指针的步长：有指针的类型决定
    int aa = 0xaabbccdd;
    int *p1 = &aa;
    char *p2 = &aa;
    printf("p1=%x\n",*p1);//p1=aabbccdd
    printf("p2=%x\n",*p2);//p2=ffffffdd *p2 找到aa的地址存储的值，并且当作char类型去使用

    //p1和p2都是同一个地址
    printf("p1=%p\n",p1);//p1=000000000061FDBC

    printf("p2=%p\n",p2);//p2=000000000061FDBC

    printf("p1=%p\n",p1+1);//p1=000000000061FDC0   加4个字节

    printf("p2=%p\n",p2+1);//p2=000000000061FDBD   加1个字节
}

int main() {
    test();

    return 0;
}
