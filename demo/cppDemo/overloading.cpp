#include <iostream>
#include "include/This.h"
using namespace std;

/**
 * 重载函数，和java类似区别不大，C++支持参数的默认值,Java是不支持的
 * @return
 */

void test() {

}

void test(int x) {

}

//void test(int x, int y = 10,int d) 错误的  实参一定要在默认参数前面
void test(int x, int y = 10) {
    cout << x << y << endl;
}

/**
 * 占位参数 int = 0没有变量名 函数体是用不到的
 * 意义：可以传值也可以不传
 * 为以后程序的扩展留下线索
 * 兼容C语言中可能出现的不规则写法,兼容C
 */
void test2(int a, int b, int = 0) {

}

//void t(int a) {
//    cout << "a:" << a << endl;
//}

/**
 * 指针算重载函数
 */
void t(int *a) {
    cout << "int *a:" << *a << endl;
}

/**
 * 和 int *a 产生了二义性不算做重载了
 */
//void t(int *a,int b = 10){
//
//}

/**
 * 常量指针也算重载，因为const int *a 的指向的内容不能改变是一个常量，int * a是变量，可以重载
 * @param a
 */
void t(const int *a) {
    cout << "const int *a:" << *a << endl;
}

/**
 * 指针常量 指针指向的地址不变，内容可以变，和变量 int * a 看成一致了 会冲突，int * const a 和 int * a 都是变量
 * @param a
 */
//void t( int * const a) {
//    cout << "a：" << *a << endl;
//}

/**
 * 引用算重载函数会和int a冲突 重载的前提具体调用的哪个类型
 */
void t(int &a) {
    cout << "int &a:" << a << endl;
}

/**
 * 常量引用，算重载但是和int a冲突
 * @param a
 */
void t(const int &a) {
    cout << "const int &a:" << a << endl;
}

//函数的别名 解决二义性
typedef void (*func)(int a, int b);

typedef void (*func2)(int a);
//typedef void func(int a, int b); 定义时需要添加*

int main() {
    This* tt = new This();//new的实例化在堆区
    tt->setAge(1);
    cout << tt->getAge() << endl;//1

    func f1 = test;//上面加了* 这里了就不用再加了
//    func *f2 = test;
    f1(10, 11);

    func2 f2 = test;
    f2(1);

    test();
//    test(1);
    test(1, 1);

    test2(1, 1, 1);

    int a = 11;
    int &b = a;
//    t(10);//引用、常量引用和普通变量 不算做重载
    t(b);//int &a:11 普通引用和常量引用算作重载
    const int &d = a;
    t(d);//const int &a:11

    int *p = &a;
    const int *p1 = &a;
//    int * const p;
    t(p);//int *a:11
    t(p1);//const int *a:11

    return 0;
}
