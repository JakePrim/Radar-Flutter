//
// Created by JakePrim on 2021/9/15.
//
#include <stdio.h>

/**
 * 二维数组遍历操作
 * @return
 */
int main() {
    int a[3][4] = {
            1, 2, 3, 4,
            5, 6, 7, 8,
            9, 10, 11, 12
    };

    //a 第一行数组的首地址   &a 整个二维数组首地址   *a 第一行第一个元素的首地址
    printf("a:%p\n", a);//000000000061FDE0
    printf("&a:%p\n", &a);//000000000061FDE0
    printf("*a:%p\n", *a);//000000000061FDE0

    printf("-----------------\n");
    //查看类型大小size
    printf("a-size:%d\n", sizeof(*a));//16  4*4 表示一行数组
    printf("a:%p\n", a);//000000000061FDE0
    printf("a+1:%p\n",a+1);//000000000061FDF0    +16 进1
    printf("-----------------\n");
    printf("&a-size:%d\n", sizeof(*&a));//48 12*4 表示整个数组
    printf("&a:%p\n", &a);//000000000061FDE0
    printf("&a+1:%p\n", &a+1);//000000000061FE10   +48
    printf("-----------------\n");
    printf("*a-size:%d\n", sizeof(**a));//4  表示数组中的一个元素
    printf("*a:%p\n", *a);//000000000061FDE0
    printf("*a+1:%p\n", *a+1);//000000000061FDE4   +4
    printf("-----------------\n");
    //数组取值法
    printf("value:%d\n",a[1][0]);//5
    //指针取值法
    printf("*a:%d\n", **a);//1  第一行第一个元素
    printf("*a:%d\n", **(a + 1));//5 第二行第一个元素
    printf("*a:%d\n", *(*(a + 1) + 1));//6 第二行第二个元素


    /**
     * 可以把二维数组表示成 数组指针
     */
    int (*p)[4] = a;//p == &a
    printf("p:%p\n", *p);//000000000061FDE0
    printf("p:%d\n", **p);//1
    printf("p:%p\n", *(p + 1));//000000000061FDF0
    printf("p:%d\n", **(p + 1));//5
    printf("p:%d\n", *(*(p + 1) + 1));//6

    //遍历二维数组
    for (int i = 0; i < 3; ++i) {
        for (int j = 0; j < 4; ++j) {
            printf("arr:%d\n",*(*(p+i)+j));
        }
    }

    return 0;
}
