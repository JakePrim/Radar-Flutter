#include <iostream>

//引用命名空间
using namespace std;

/**
 * 输入输出
 * @return
 */
int main() {
    int a = 11;

    //输出 cout << 插入到屏幕,输入 cin >> 输入到程序
    cout << "hello" << a << endl;//endl 会进行换行
    cout << "hello 换行" << endl;

    cout << "sds"
         << "sdsd"
         << endl;

    //cin 输入
    cout << "pleases input " << endl;
    int b = 0, c = 0, d = 0;
    cin >> b >> c >> d;
    cout << b << c << d << endl;

    cout << b + c << endl;//也可以输出表达式


    return 0;
}

