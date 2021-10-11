//
// Created by JakePrim on 2021/9/15.
//
#include <stdio.h>

/**
 * C 语言中的数组遍历，在C语言中数组是没有边界的
 * @return
 */
void forArray(int arr[], int size) {
    for (int i = 0; i < size; ++i) {
        //即便size大于数组的长度，也不会发生数组越界的问题
        printf("forArray:%d\n", arr[i]);
    }
}


int main() {
    int size = 10;
    int arr[6] = {1, 2, 3, 4, 5, 6,7};
    forArray(arr, size);
    //下标法遍历指针数组
    for (int i = 0; i < 3; ++i) {
        printf("arr:%d\n", arr[i]);
    }
    //地址法遍历数组
    for (int i = 0; i < 3; ++i) {
        printf("arr:%d\n", *(arr + i));
    }
    //指针法
    int *c = arr;
    for (int i = 0; i < 3; ++i) {
        printf("arr:%d\n",*(c+i));
    }
    printf("c:%p\n", c);//000000000061FDF0
    c++;//步长为int 4个字节
    printf("c++:%p\n", c);//000000000061FDF4

    //arr == &arr
    printf("arr:%p\n", arr);
    printf("&arr:%p\n", &arr);
    /**
     * arr:000000000061FDF0
        &arr:000000000061FDF0
     */
    //两个指针指向同步一个地址，指向类型不一定一样
    printf("*arr=%d\n", sizeof(*arr));//*arr=4       指向类型 int  很显然arr只是数组的首地址 第一个元素 int = 1
    printf("*&arr=%d\n", sizeof(*&arr));//*&arr=24   指向类型 int[6] &arr 整个数组   6 * 4 = 24






    int a1 = 10;
    int a2 = 20;
    int a3 = 30;
    int *a[3] = {&a1, &a2, &a3};
    /**
     *  a=000000000061FDC0
        &a=000000000061FDC0
     */
    printf("a=%p\n", a);
    printf("&a=%p\n", &a);
    printf("*a=%d\n", sizeof(*a));//8
    printf("*&a=%d\n", sizeof(*&a));//8*3 = 24



    return 0;
}

