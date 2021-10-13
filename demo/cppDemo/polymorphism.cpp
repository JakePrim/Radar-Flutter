#include <iostream>
#include <string.h>

using namespace std;

/**
 * 多态和继承
 */
class Parent {
public:
    int name;
protected:
    int code;
private:
    int num;
};

class Parent1 {

};

//C++中，:表示继承，可以多继承逗号分隔
//public/protected/private继承,对于基类起到一些保护机制 默认是private继承
class Child : public Parent, Parent1 {
    void test() {
        //派生类可以访问到public属性和protected属性
        this->name;
        this->code;
    }
};

//private私有继承
class Child1 : private Parent {
    void test() {
        this->name;
        this->code;
    }
};

//protected继承
class Child2 : protected Parent {
    void test() {
        this->name;
        this->code;
    }
};

//父类
class Person {
protected:
    char *str;
private:
    Person(int a){

    }
public:
    Person(char *str) {
        if (str != NULL) {
            this->str = new char[strlen(str) + 1];
            strcpy(this->str, str);
        } else {
            this->str = NULL;
        }
        cout << "parent" << endl;
    }

    Person(const Person &p) {
        cout << "copy parent" << endl;
    }

    void printC() {
        cout << "parent printC" << endl;
    }

    ~Person() {
//        if (str != NULL) {
//            delete[] str;//如果调用了这个方法只会调用一次析构函数
//        }
//        cout << "parent destroy" << endl;
    }
};

//子类
class CTest : public Person {
public:
    //调用父类的构造方法
    CTest(char *str) : Person(str) {
        cout << "child" << endl;
    }

    void printC() {
        cout << "child printC" << endl;
    }

    ~CTest() {
        cout << "child destroy " << endl;
    }
};

//通过指针传递只会调用父类的方法，不会调用子类的方法
void howToPaint(Person *p) {
    p->printC();
}

//通过引用类型，只会调用父类的方法，不会调用子类的方法
void howToPaint1(Person &p) {
    p.printC();
}

int main() {
    // parent
    // child
    // child destroy
    // parent destroy
    // parent destroy
    Person person = CTest("jake");

    person.printC();//parent printC

    cout << "-----------" << endl;
    Person *p = NULL;
    CTest c1("123");
    p = &c1;
    c1.printC();//child printC
    p->printC();//parent printC 为什么会调用的是parent的方法呢？

    cout << "---------" << endl;
    howToPaint(p);//parent printC
    howToPaint(&c1);//parent printC

    cout << "-------" << endl;
    Person p1("123");
    //都是父类的方法
    howToPaint1(p1);//parent printC
    howToPaint1(c1);//parent printC

    cout << "--------" << endl;
    CTest c2("123");
    Person p2 = c2;//会不会调用父类的拷贝函数呢？ copy parent 会进行调用

    //那么如何真正调用子类的函数呢？ C++中提供了虚函数的概念


    Child child;
    child.name;//public继承。调用者可以访问到父类公有属性 私有属性访问不到的

    Child1 child1;
//    child1.name;//private继承.调用者访问不到父类公有属性和私有属性

    Child2 child2;
//    child2.name;//protected继承，调用者访问不到父类公有属性和私有属性

    return 0;
}
