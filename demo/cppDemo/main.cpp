#include <iostream>
#include "ArrayList.h"

using namespace std;

/**
 * 单例模式实现
 */
class Instance {
public:
    static Instance *getInstance() {
        return instance;
    }

private:
    static Instance *instance;
private:
    //保护构造方法
    Instance() {}

    //保护构造拷贝函数 不让外界去声明 这里和java是不一样的
    Instance(const Instance &instance) {

    }
};

//初始化静态属性 在类外初始化
Instance *Instance::instance = new Instance;

class Dog;

class Person {
public:
    int a = 1;//存在栈区
    static int e;//静态变量存在静态区

    //C++ 内存分布：栈区、堆区、代码段
    Person(){};//函数存在代码段
    void visit(){
    };

private:
    Dog *dog;
};


class Dog {
    //1. 将Person暴露出去
    friend class Person;

public:
    int age;
private:
    string name;
};


int main() {
    Person person;
    //sizeof 只会测量栈区和堆区
    cout << sizeof(person) << endl;//1 占用1个字节 空的对象没有任何变量 就是1个字节，如果有其他的变量就要进行计算和结构体类似

    Instance *i1 = Instance::getInstance();
    Instance *i2 = Instance::getInstance();
    if (i1 == i2) {
        cout << "i1==i2" << endl;
    }
//    Instance *i3 = new Instance(*i1);//调用构造拷贝函数
    Instance *i3 = Instance::getInstance();//调用构造拷贝函数
    if (i3 == i1) {
        cout << "i3 == i1" << endl;
    }


    ArrayList arrayList = ArrayList(2);
    arrayList.add(1);
    arrayList.add(2);
    arrayList.add(3);
    arrayList.add(4);
    arrayList.toString();
    arrayList.remove(1);
    arrayList.toString();
    arrayList.add(11);
    arrayList.toString();
    cout << arrayList.get(2) << endl;

    //常对象 对任何变量的修改都是违法的 一般用在不能改变的对象上，比如单例模式的一些配置信息等
    const ArrayList arrayList1 = ArrayList();
//    arrayList1.d = 11; //错误的
//    常对象只能调用常函数，其他函数无法调用.
    arrayList1.getLength();

    return 0;
}

