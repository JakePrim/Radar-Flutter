#include <iostream>

/**
 * cpp 是c++的文件名
 * C++ 命名空间
 * C 在不同的文件 声明相同的函数名会报错这里要注意
 */
//C++没有包名，但是有命名空间
namespace namespaceA {
    int a = 0;
}

namespace namespaceB {
    int a = 1;
    namespace C {
        struct T {
            int age = 30;
        };
    }
}

//int a = 1;//和全局变量重名了

int main() {

    int a1 = namespaceA::a;
    int b = namespaceB::a;

    //当变量多的时候如何处理呢？
    using namespace namespaceA;
//    using namespace namespaceB;
    int c = a;

    //多重命名空间
//    namespaceB::C::T t;
    using namespace namespaceB::C;
    T t;

    std::cout << "Hello, World!" << std::endl;


    return 0;
}
