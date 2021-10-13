#include <iostream>
#include "ArrayList.h"
using namespace std;

class Person {
public:
    //增加了一个虚函数表的指针
    virtual void look() {//虚函数 子类可以覆写的函数
        cout << "virtual look" << endl;
    }

    virtual void speak() {

    };//纯虚函数 必须要让子类实现的
    //基类的析构函数必须声明为虚函数
    virtual ~Person() {
        cout << "~Person" << endl;
    }
};

class Child : public Person {
public:
    void speak() override {//子类实现纯虚函数
        cout << "child speak" << endl;
    }

    void look() override {
        cout << "child look" << endl;
        Person::look();//访问父类的方法
    }

    ~Child() {
        cout << "~Child" << endl;
    }
};

int main() {
    ArrayList<int> arr(2);
    ArrayList<char> arr2(3);

    arr.add(1);
    arr.add(2);
    arr.add(3);
    arr.add(4);
    arr.toString();

    arr2.add('a');
    arr2.add('b');
    arr2.add('c');
    arr2.add('d');
    arr2.toString();


    /**
     * child speak
        child look
        virtual look
        ~Child
        ~Person
     */
//    Person *person = new Child();//必须通过指针的方式，不同通过栈的方式去派生抽象
//    person->speak();//child speak
//    person->look();//child look
//    delete person;
//
//    Person p;
//    cout << sizeof(p) << endl;//8 这就表明了虚函数是有一个虚函数表，增加一个指针*vtable，指向了虚函数表
//    //下面代码来证明
//    typedef void (*func)(void);
//    func fun = NULL;
//    cout << (int *) &p << endl;//指向函数的首地址 0x61fdf8
//    cout << (int *) *(int *) &p << endl;//函数的地址 0x404560
//    fun = (func) *((int *) *(int *) &p);
//    fun();//virtual look
    return 0;
}

