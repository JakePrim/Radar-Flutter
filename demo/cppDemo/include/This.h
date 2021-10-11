//
// 头文件不会被编译到exe中，参与编译的过程，用来查找实现类
//
//#pragma once
//if not define
#ifndef CPPDEMO_THIS_H
//定义宏 CPPDEMO_THIS_H
#define CPPDEMO_THIS_H


class This {
public:
    int a;
private:
    int b;
protected:
    int c;

public:
    int getAge();

    void setAge(int a);

};

//防止多重导入 影响编译效率 其他文件导入头文件，判断宏是不是存在，如果存在 直接返回。不存在则定义宏，编译文件
#endif //CPPDEMO_THIS_H
