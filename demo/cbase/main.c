#include <stdio.h>
#include <malloc.h>

/**
 * C 语言基础 - 理解指针
 * @return
 */

//可以理解为javabean
typedef struct {
    int a;
    int b;
    unsigned int c;//定义无符号类型
} Person;

int main() {
    Person *p = malloc(sizeof(Person));//创建结构体对象 malloc申请内存 sizeof 申请内存的大小
    p->a = 1;
    p->b = 2;
    p->c = -100;//无符号的int类型是一个正数

    printf("a=%d,b=%d,c=%d\n", p->a, p->b,p->c);//a=1,b=2

    //释放指针
    p = NULL;
    free(p);

    return 0;
}
