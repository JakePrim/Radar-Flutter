#include <iostream>

using namespace std;

/**
 * C 语言中没有引用的概念，当进行两个数的交换时就需要使用指针，不断的取址 取值
 */
void swap2(int *a, int *b) {
    int temp = *a;
    *a = *b;
    *b = temp;
}

/**
 * 使用C++ 提供的引用的特性，其实在Java中也存在引用的概念
 * 函数的参数作为引用
 */
void swap(int &a, int &b) {
    int temp = a;
    a = b;
    b = temp;
}

/**
 * 函数返回值是引用的问题
 */
float &fun1(float r) {
    float temp = (float) (r * r * 3.14);
    //和下面的代码一样
//    float &b = temp;
    return temp;
}

int vals[10];

int error = -1;

/**
 * 全局变量作为引用 返回
 * @param n
 * @return
 */
int &put(int n) {
    if (n >= 0 && n <= 9) {
        return vals[n];
    }
    return error;
}

/**
 * 引用：C++中引用是非常重要的，代表了某一个变量的别名，对引用操作就是对变量直接操作完全一样，引用不会在内存上分配任何空间，节省内存
 * @return
 */
int main() {
    //常见的引用应用方式
    put(0) = 0;//等价于 put(0) 相当于vals[0]的引用，那么put(0) = 0 就等价于 vals[0] = 0 因为vals是全局变量 引用还存在着
    put(1) = 1;//同样的原理 vals[1] = 1
    cout << vals[0] << vals[1] << endl;

//    float f = fun1(10.0);//fun1返回引用，但是返回的是局部的变量的别名/引用，但是函数执行完毕 局部变量temp不在了，
    // 这里要注意 如果函数返回值要返回引用，那么引用的变量不能是函数的局部变量
//    cout << "f=" << f <<endl;

//    float &f = swap();//不能对函数的返回值的局部变量做引用


    int a = 1;
    int &b = a;//&：注意在等号的左边就是表示引用，在等号的右边 int *b = &a;就表示取址 含义是不同的

    printf("a:%d,b:%d\n", a, b);//a:1,b:1
    b = 2;//操作别名就相当于操作a本省
    printf("a:%d,b:%d\n", a, b);//a:2,b:2
    //a 和 b的地址是完全一致的
    printf("a:%p,b:%p\n", &a, &b);//a:000000000061fe14,b:000000000061fe14

    int c = 20;
    int d = 40;
//    swap2(&c,&d);//使用指针的方式
    swap(c, d);//使用引用的方式  1. 避免了指针的繁琐操作  2. 引用不会分配内存，直接操作变量本身
    printf("c:%d,d:%d\n", c, d);//c:40,d:20

    //常量引用
    int p = 11;
    const int &pr = p;//常量引用,引用的变量不能通过别名去修改
    //pr = 22;//常量引用：不允许引用/别名去修改变量的值
    p = 22;//当p被修改，那么pr也会能到最新的值，因为引用就是变量的本身
    cout << p << pr << endl;//p=22 pr=22


    return 0;
}