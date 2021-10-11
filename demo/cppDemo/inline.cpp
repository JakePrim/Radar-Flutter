#include <iostream>

using namespace std;

/**
 * 内联函数：解决C语言中的宏定义的问题，内联函数会将函数体替换到调用位置
 * @return
 */


//在C语言中使用#define 非常的繁琐要加入很多括号
#define ADD(a,b)(a+b)
/**
 * inline 内联的关键字，实现更加简单和函数的方式一致，代替了#define的繁琐操作
 * 保持了预处理宏的功能，更加安全可靠，可以定义复杂的函数。将函数体的代码插入到调用的地方
 * 适用于功能简单，功能较小 。不能有循环体 Switch 递归等不能使用内联函数，否则会自动将inline去掉 变成普通函数
 * @param x
 * @param y
 * @return
 */
inline int add(int x,int y){
    return x + y;
}

int main(){
    int res = add(1,2);
    cout << res << endl;
    return 0;
}

