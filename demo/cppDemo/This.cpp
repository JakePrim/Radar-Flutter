//
// Created by JakePrim on 2021/9/29.
//

#include "include/This.h"

//实现头文件的方法
int This::getAge() {
    return a;
}

void This::setAge(int a) {
    this->a = a;//C++ this->a   java : this.a
}
