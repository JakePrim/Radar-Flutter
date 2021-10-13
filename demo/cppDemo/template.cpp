#include <iostream>
#include <string>
#include <cstring>

using namespace std;
/**
 * 函数模板和java中的泛型类似
 */

//方法泛型 这里只能声明在方法上
template<typename T, typename R=int>
//R的默认类型是int
//typename == class 两个等价的
void swap2(T t, R r) {
}


template<typename T>
void swapT(T &a, T &b) {
    cout << "swap: T a T b" << endl;
    T temp = a;
    a = b;
    b = temp;
}

//普通函数优先级比泛型函数高，只有类型重合的状态下
void swapT(int &a, int &b) {
    cout << "swap : int a int b" << endl;
    int temp = a;
    a = b;
    b = temp;
}

//模板修饰在类上
template<typename T, typename R>
class Person {
public:
    T a;
    R b;

    Person(T t) {

    }

    T &getA() {
        T t1;
//        return t1;//这里不可以返回，因为方法执行完毕后会销毁掉
        return a;//返回值是引用
    }
};

/**
 * 和java不同的部分，比java更加灵活
 */
class Pp {
public:
    void show() {
        cout << "Pp show" << endl;
    }
};

template<typename T>
class ObjTemp {
private:
    T obj;
public:
    void showPp() {
        //自动检查 但是会出现不可预期的错误
        obj.show();//假设模板是Pp，可以调用Pp的变量和方法，在java中需要<T extend Pp> T才能调用方法
    }
};


template<typename T, typename R>
class CTest {
public:
    T m_name;
    R m_age;

    CTest(T name, R age) {
        this->m_name = name;
        this->m_age = age;
    }

    void show() {
        cout << "show T:" << m_name << " R:" << m_age << endl;
    }
};

template<typename T, typename R>
void doWork(CTest<T, R> &cTest) {
    cTest.show();
}

template<typename T>
void doWork2(T &t) {
    t.show();//在java中必须是<T extend xxxx>
}


//继承模板问题和java是一样的
template<typename T>
class Base {
public:
    T t;
};

//确定的类型或者模板
template<typename T, typename R>
class Son : Base<R> {
public:
    T t1;
};

int main() {
    //字符串 string 是C++独有的string是一个对象，内部封装了和C一样的字符串的表现形式
    string s1();
    string s2("123");
    string s3 = "wew";//string字符串是声明在堆区的
    string s4(4, 'k');//4个K组成 kkkk
    string s5("123456", 1, 4);//从1开始，输出四个字符串：2345
    cout << s4 << " " << s5 << endl;
    s2.append(s3);//追加123wew
    s2.append(s3,1,2);//ew
    cout << s2 << endl;//123wewew
    string sub = s2.substr(2,3);//字符串裁剪
    cout << sub << endl;//3we

    s4.swap(s5);//字符串交换，只有引用和地址才会改变外部的值

    //c_str 支持C，转换为char *
    string s = "jakeprim";//存储在堆区 方法执行完毕 执行析构函数 从堆区移除
    //一般不会这样使用
    const char *s_c = s.c_str();//将C++ string转换为支持C的字符串，返回常量指针 指针指向了常量，不能通过指针来修改常量
    printf("%s\n", s_c);

    //一般开发会使用strcpy拷贝，防止被销毁掉等问题 在FFmpeg是使用的C，所以在使用C++开发时必须要对C的转换
    char ss[20];
    strcpy(ss, s.c_str());//拷贝到一个新的变量中



    //函数模板
    CTest<string, int> test("jake", 28);//show T:jake R:28
    doWork(test);
    doWork2<CTest<string, int>>(test);//显示调用
    doWork2(test);//自动推导

    ObjTemp<Pp> temp;
    temp.showPp();//Pp show 可以调用传递过来的模板的方法


    int a = 10;
    int b = 20;
    char c = 'a';
    swapT<int>(a, b);//显示调度
    swapT(a, b);//自动推导
//    swap(a,c);//报错 无法推导出具体的类型

//    swap2();//报错 无法推导出具体的类型

    //自动类型推导，在类模板上不可以使用，无法推导出具体的类型
    Person<int, string> p(100);
    cout << p.getA() << endl;
    return 0;
}

