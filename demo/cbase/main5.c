//
// Created by JakePrim on 2021/9/14.
//
#include <stdio.h>
#include <malloc.h>

void print_array(int **arr,int n){
    for (int i = 0; i < n; i++) {
        printf("arr:%d\n",*arr[i]);//arr[i] === **(arr+i)
        printf("arr:%d\n",**(arr+i));//arr[i] === **(arr+i)
    }
}

void test() {
    int a1 = 10;
    int a2 = 20;
    int a3 = 30;
    int a4 = 40;
    int a5 = 50;
    int n = 5;
    //二级指针更加复杂的使用
    int **arr = malloc(sizeof(int *) * n);
//    arr[0] = &a1;//arr[0] === *arr
//    arr[1] = &a2;//arr[0] === *(arr+1)
//    arr[2] = &a3;//arr[0] === *(arr+2)
//    arr[3] = &a4;//arr[0] === *(arr+3)
//    arr[4] = &a5;//arr[0] === *(arr+4)

    *arr = &a1;//arr[0] === *arr
    *(arr+1) = &a2;//arr[0] === *(arr+1)
    *(arr+2) = &a3;//arr[0] === *(arr+2)
    *(arr+3) = &a4;//arr[0] === *(arr+3)
    *(arr+4) = &a5;//arr[0] === *(arr+4)
    print_array(arr,n);
    //释放空间
    free(arr);
}

int main() {
    //二级指针
    int b = 10;
    int *p = &b;
    int **p2 = &p;
    test();
    return 0;
}
