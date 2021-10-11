//
// Created by JakePrim on 2021/9/14.
//
#include <stdio.h>
#include <malloc.h>

void allocateSpace(int **addr){
    //p是一个二级指针
    int *temp = malloc(sizeof(int)*16);
    for (int i = 0; i < 16; ++i) {
        temp[i] = 100+i;
    }
    //**p = &p
    //*p = temp   **p = *temp
    *addr = temp;
}
void print_array(int **arr,int n){
    for (int i = 0; i < n; i++) {
        //(*arr) -> *p -> temp[]
        printf("arr:%d\n",(*arr)[i]);
    }
}

void free_array(int **array,int n){
    for (int i = 0; i < n; ++i) {
        free(*(array+i));
    }
}



void freeAddr(int ** addr){
    free(*addr);
    *addr = NULL;
}

int main(){
    //一级指针
    int * p = NULL;
    //二级指针 要改变指针需要传递指针的地址
    allocateSpace(&p);
    print_array(&p,16);

    //另一种
//    int (*temp)[4] = p;//指针指向类型 int()[4]  ++ 步长16
//    printf("value:%d",*temp[0]);
//
//    temp++;
//
//    printf("value:%d",*temp[0]);

    //如何释放呢？
    //函数释放
    freeAddr(&p);

//    free(p);
//    p = NULL;

    return 0;
}
