#include <stdio.h>

/**
 * 复杂函数
 * @return
 */
//函数的定义
typedef void (*p)(int, int);

//int[] getArray(){
//
//}

int main() {
//    struct node{
//        char a;// 1
//        int b;// 4
//    }node;
//    //字节对齐 最大类型的整数倍
//    printf("node:%d\n", sizeof(node));//8
//
//    struct pa{
//        char a;//1
//        struct node b;//8
//    };
//    struct pa p1;
//    printf("p1:%d\n", sizeof(p1));//12
//
//    struct node2{
//        double a;//8
//        char c;//1
//    };
//    struct node2 n2;
//    //最大类型的整数倍
//    printf("n2:%d\n", sizeof(n2));//16

    int (*func)(int);

    int (f)(int,int);

    //复杂函数的判断从右往左看，遇到括号转向
    //f传递的也是一个函数，返回值int 参数是int *，
    //fun1 也是一个函数,返回值int，参数是int *,函数f
    int (*fun1)(int *p,int (*f)(int *));

    //fun2 是一个数组，数组的元素存放指针,元素指向的是一个函数 int 函数(int *)
    int (*fun2[5])(int *p);

    //func3 是一个指针指向了一个数组是一个数组指针，数组里面的元素类型为指针，元素的指针指向的是一个函数类型 int 函数(int *p)
    int (*(*func3)[5])(int *p);

    //func4 是一个指针，指向了一个函数(int *),返回值是一个指针，指针类型指向了一个数组[5] 返回值是一个数组指针，数组的元素存放int类型
    int (*(*func4)(int *p))[5];

    //pfn 是一个指针，指向了一个函数(int *),返回值是一个指针，指针的类型指向了一个数组[10],返回值是一个数组指针，数组的元素存放的int *
    int *(*(*pfn)(int *))[10];

    //C 语言不能直接返回一个数组，需要通过指针指向数组,返回指针即可
//    int (*func6)(int)[5];
    int (*(*func6)(int))[5];

    //func5 是一个具有5个元素的数组，这个数组的元素都是函数，这是非法的，因为数组的元素除了类型必须一致，每个元素的所占空间也必须相同
    //函数通常所占用的空间是不相同的，一般都是通过指针来指向函数。
//    int func5[5](void);
    int (*func5[5])(void);//指针数组 元素指向的是一个函数

    return 0;
}

