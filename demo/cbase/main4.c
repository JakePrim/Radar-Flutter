//
// Created by JakePrim on 2021/9/1.
//
#include <stdio.h>
#include <malloc.h>

#define Test 99;//宏变量 类似java中的static final
#define ADD(x, y) (x+y) //宏定义 加法操作   编译时替换 预处理

/**
 * 内存模型
 * @return
 */
int test() {
    int a = 1;
    int b = 2;
    return a + b;
}

/**
 * 注意返回指针的情况
 * @return
 */
 int * getNum(){
     int a = 10;//当getNum执行完毕后，会被移除a不存在了，但是该内存区域的值还是10并没有被清零
     int * p = &a;
    return p;
 }

 /**
  * getArr 执行完毕栈就被移除了，当去查找这个内存区域是查不到值
  * @return
  */
 int * getArr(){
     int arr[4] = {1,2,3,4};
     return arr;
 }

 /**
  * arr 指向了堆区的一块内存区域，当getArr2执行完毕，移除栈但是堆区的并没有移除
  * @return
  */
int * getArr2(){
    int *arr = malloc(16);
    return arr;
}


int main() {
    int a = 1;
    int b = 2;
    int c = 3;
    //栈区：高地址位于栈顶  低地址位于栈底，也就是说先入栈的地址要大于后入栈的
    //a的地址 > b > c 高地址 -> 低地址
    /*
    a:000000000061FE0C
    b:000000000061FE08
    c:000000000061FE04
    */
    printf("a:%p\n", &a);
    printf("b:%p\n", &b);
    printf("c:%p\n", &c);

    //堆区：地址是从低到高和栈区是相反的
    int *mp = malloc(16);
    for (size_t i = 0; i < 4; i++) {
        printf("mp %p\n", (mp + i));
    }
    /*
    mp 0000023891D455F0
    mp 0000023891D455F4
    mp 0000023891D455F8
    mp 0000023891D455FC
    */

    int d = test();//test执行：先入栈main得到结果 test执行完出栈

    printf("add:%d\n", ADD(1,2));//ADD() 直接替换成对应的源码 1+2 不用频繁的入栈和出栈
    //宏定义的问题
    printf("add:%d\n", ADD(1,2)*10);//21 ADD(1,2) -> 1+2；1+2*10  只会帮你替换为对应的源码，不考虑优先级
    //所以需要在宏定义时加上括号:ADD(x,y) (x+y)

    //指针越界的问题
    char buf[3] = "abc";//字符串遇到 \0，才会结束
    printf("buf:%s\n",buf);//abc??
    char buf2[3] = "ab\0";//字符串遇到 \0，才会结束
    printf("buf2:%s\n",buf2);//ab

    //函数返回指针
    int * s = getNum();
    printf("s:%d\n", *s);//10

    int * s1 = getArr();
    printf("s1:%p\n", s1);//s1:0000000000000000

    int * s2 = getArr2();
    printf("s2:%p\n", s2);//s2:00000000007B1460
    return 0;
}
