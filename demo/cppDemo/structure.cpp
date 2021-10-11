#include <iostream>
#include <string.h>

using namespace std;

class Student {
public:
    int a;
    char *name;
    int b;
    int c;

    //无参构造函数 参数列表 给参数默认值
    Student() : a(10), b(20), c(30) {

    }

    //有参构造函数 参数列表
    Student(int a, int b, int c) : a(a), b(b), c(c) {

    }

    //无参构造函数
//    Student() {
//        cout << "create" << endl;
//    }

    //带参的构造函数
    Student(int a) {
        cout << "create int:" << a << endl;
        this->a = a;
    }

    Student(char *name, int age) {
        this->a = age;
        //由于外部传递过来的字符串存储在常量区域，name是一个常量指针，无法通过指针修改字符串的内容
        this->name = (char *) (malloc(strlen(name) + 1));//在堆区申请了一块内存
        strcpy(this->name, name);//复制到this.name中去
    }

    //explicit 不能通过隐式调用
    explicit Student(char *a) {
        cout << "create double:" << a << endl;
    }

    /**
     * 也是拷贝函数 且优先级比const高，但是在程序上必须使用安全的const作为拷贝函数
     * @param student
     */
//    Student(Student &student){
//
//    }

    //默认拷贝函数 常量引用 不可以改变拷贝对象的值  对参数赋值时会调用该拷贝函数，参数一定是常量引用
    Student(Student const &student) {
        cout << "const &student" << endl;
        //会进行浅拷贝 将值赋值给   C++ 内部会默认实现拷贝函数
        this->a = student.a;
        //新对象 也会在堆申请一块内存 各指自己的
        this->name = (char *) (malloc(strlen(student.name) + 1));//在堆区申请了一块内存
        strcpy(this->name, student.name);//复制到this.name中去
    }

    //析构函数 当对象被销毁时调用，在该方法中释放class中的成员及对象
    ~Student() {
        cout << "destroy" << this->a << endl;
        //第二个对象 name不为空，释放了name的堆空间，而到了第一个对象销毁时，也会调用free这时候就会发现问题
        //必须重写拷贝函数
        if (this->name != nullptr) {
            free(this->name);
            this->name = nullptr;
        }
    }
};

void test() {
    //声明在栈区的对象
    Student student;//等价于Student() 声明的对象存储在栈中，
    Student student1(10);//括号法 有参的构造函数
    Student student2 = Student();//显示法
    Student student21 = Student(11);//显示法
    Student student3 = 10;//隐式法：等价于Student(10); 隐式调用默认构造  不推荐使用 引起歧义
    Student student4[4];//声明数组 有实例化对象调用了默认的构造函数  在栈区实例化

    //声明在堆区的对象
    Student *stu = new Student();//使用new声明的对象存储在堆中，需要手动释放堆中的对象
}


void test2(Student student) {//形参 是实参拷贝 const &student 先声明Student对象
    cout << "a:" << student.a << endl;//拿到
}

void test3() {
    Student *s = new Student;
    delete s;//释放对象 new delete / malloc free

    //数组 实例化10个对象都是在堆区
    Student *sArr = new Student[10];
    //释放
    delete[]sArr;
}

int main() {
    //test();//create create destroy 函数执行完毕在栈中生成的对象会自动释放，但是堆中的对象需要手动释放

//    Student student = "a";explicit 不能隐式调用 防止产生歧义，一般开发中都不会使用隐式调用

//    Student student;//create
//    student.a = 10;
//    test2(student);//实例化了两次  析构函数调用了两次
//    Student student1(student);//会调用默认的拷贝函数
//    Student student2 = student;//赋值也会调用默认的考本函数

//    Student s1 = Student(10);
//    Student s2 = Student(s1);
//    s2.a = 101;
    //s1 和 s2 谁先销毁呢？ s2先销毁，栈先进后出
    /**
     * create int:10
        const &student
        destroy101
        destroy10
        拷贝函数
     */
    Student s1("jake23", 1);
//    Student &s3 = s1;
    Student s2(s1);//通过拷贝函数 构造新的对象
    s2.a = 11;
    return 0;
}

