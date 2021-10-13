#include <iostream>
#include <cstring>

using namespace std;

//基类
class People {
public:
    void set_name(char *name);

    void set_age(int age);

    char *get_name() const;

    int get_age() const;

private:
    char *m_name;
    int m_age;
};

void People::set_name(char *name) {
    //参数传递过来的是常量指针，在设置时 对其进行复制重新创建
    if (name != NULL) {
        m_name = new char[strlen(name) + 1];
        strcpy(m_name, name);
    }
}

void People::set_age(int age) {
    m_age = age;
}

char *People::get_name() const {
    return m_name;
}

int People::get_age() const {
    return m_age;
}

//派生类Student
class Student : public People {
public:
    void setscore(float score) {
        m_score = score;
    }

    const float &getscore() const {
        return m_score;
    }

private:
    float m_score;
};

int main() {
    Student student;
    student.setscore(1.0);
    //通过public派生的子类才可以访问基类的变量和函数
    student.set_name("jake");
    student.set_age(11);
    cout << student.get_name() << " age: " << student.get_age() << " score:" << student.getscore() << endl;
    //jake age: 11 score:1
    return 0;
}

