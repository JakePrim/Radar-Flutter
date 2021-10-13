#include <iostream>
#include <stdio.h>
#include <string>
#include <thread>
#include <future>
#include <time.h>
#include <cstring>

using namespace std;

/**
 * 运算符重载相关知识，重载也是多态的一种
 */


/**
 * 算数运算符重载
 */
class complex {
public:
    complex();

    complex(double real, double imag);

private:
    double m_real;
    double m_imag;
public:
    //声明运算符重载 参数是一个常量引用，重载函数是常函数，不能修改函数内部的所有变量
    complex operator+(const complex &A) const;

    //打印函数也是一个常函数，不能修改变量
    void display() const;
};

complex::complex() : m_real(0), m_imag(0) {

}

complex::complex(double real, double imag) : m_real(real), m_imag(imag) {}

complex complex::operator+(const complex &A) const {
    complex B;
    B.m_real = this->m_real + A.m_real;
    B.m_imag = this->m_imag + A.m_imag;
    return B;
}

void complex::display() const {
    cout << m_imag << " + " << m_real << endl;
}

/**
 * 指针运算符重载
 */
class Person {
public:
    Person(string name) {
        this->name = name;
    }

    ~Person() {

    }

    const string &get_name() {
        return name;
    }

private:
    string name;
};

//模板
template<class classtype>
class SmartPoint {
private:
    classtype *pointer;
public:
    SmartPoint(classtype *pointer) {
        this->pointer = pointer;
    }

    ~SmartPoint() {
        if (this->pointer != NULL) {
            delete this->pointer;
        }
    }

    //重载运算符 ->
    classtype *operator->() {
        return this->pointer;
    }

    //重载解引用
    classtype &operator*() {
        return *(this->pointer);
    }
};

/**
 * new运算符重载
 */
class CTest1 {
    char m_buff[4096];
};

//重载new运算符，将对象创建到全局的buffer中去
char buf[4100];//

class CTest2 {
    char m_buff[4096];
public:
    void *operator new(size_t size) {
        return (void *) buf;
    }

    void operator delete(void *p) {

    }
};

/**
 * 赋值运算符重载
 */
class Customer {
private:
    int id;
    char *name;
public:
    Customer() : id(0), name(NULL) {}

    Customer(int _id, char *_name) {
        id = _id;
        //注意这里不能直接赋值的，需要重新申请一块内存给name，并且将值复制给name
        name = new char[strlen(_name) + 1];
        strcpy(name, _name);
    }

    //拷贝函数
    Customer(const Customer &str) {
        cout << "copy constructor" << endl;
        id = str.id;
        name = new char[strlen(str.name) + 1];
        strcpy(name, str.name);
    }

    //重载赋值运算符
    Customer &operator=(const Customer &str) {
        cout << "operator:" << &(*this) << " str:" << &str << endl;
        if (this != &str) {
            if (name != NULL) {
                delete name;
            }
            this->id = str.id;
            name = new char[strlen(str.name) + 1];
            strcpy(name, str.name);
        }
        //this表示了是指向当前对象的指针也就等价于 Customer *c = new Customer();   this == c    *this == *c 就表示了当前的对象
        return *this;
    }

    //析构函数 一定一定一定不要忘记写
    ~Customer() {
        delete[] name;
    }
};


/**
 * []运算符重载：a[9] = a + 9
 */


int main() {
    char *name = "lll";
    Customer cc1(1, name);
    Customer cc2;
    cc2 = cc1;//operator:0x63fc60 str:0x63fc70   这里才会调用重载的赋值运算符
    Customer cc3 = cc2;//copy constructor  注意直接给变量赋值，调用了拷贝函数


    //测试CTest1的性能
    clock_t count = clock();
    for (int i = 0; i < 0x5ffff; ++i) {
        CTest1 *c = new CTest1();
        delete c;
    }
    cout << "Interval = " << clock() - count << endl;//Interval = 52

    clock_t count1 = clock();
    for (int i = 0; i < 0x5ffff; ++i) {
        CTest2 *c = new CTest2();
        delete c;
    }
    cout << "Interval = " << clock() - count1 << endl;//Interval = 26 性能会更好



    complex c1(4.3, 5.9);
    complex c2(2.4, 3.7);
    complex c3;
    c3 = c1 + c2;//+操作会执行operator+()
    c3.display();//9.6 + 6.7

    //shared_ptr 智能指针采用引用计数的方式，避免指针缺陷，内存泄漏、野指针等问题
    std::shared_ptr<Person> p1(new Person("meimei"));//Person("meimei")的引用计数为1
    std::shared_ptr<Person> p2 = std::make_shared<Person>("lilei");//make_shared创建一个智能指针
    p1.reset(new Person("guoguo"));
    std::shared_ptr<Person> p3 = p1;//shared_ptr 重载了-> 运算符
    cout << "p3 is" << p3->get_name() << endl;//-> 获取的是p1.get_name();


    SmartPoint<Person> p = SmartPoint<Person>(new Person("lili"));
    cout << p->get_name() << endl;
    cout << (*p).get_name() << endl;

    return 0;
}