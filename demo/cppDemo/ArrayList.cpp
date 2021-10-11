//
// Created by JakePrim on 2021/10/9.
//
#include <iostream>
#include <cstdio>
#include "ArrayList.h"

using namespace std;


ArrayList::ArrayList(int capacity) {
    this->size = capacity;
    this->realSize = 0;
    //在堆区申请数组
    this->arr = new int[this->size];//在堆中开辟的一块空间 存储的是一个int[size] 数组，arr指向数组的首地址
}

ArrayList::ArrayList() {
    this->size = 16;
    this->realSize = 0;
    this->arr = new int[this->size];
}

ArrayList::ArrayList(const ArrayList &arrayList) {
    this->size = arrayList.size;
    this->realSize = arrayList.realSize;
    this->arr = new int[arrayList.size];
    //将数组的值赋值到arr中
    for (int i = 0; i < this->size; ++i) {
        this->arr[i] = arrayList.arr[i];//arrayList.arr[i]他也是指针  this->arr[i] 是指针
    }
}

ArrayList::~ArrayList() {
    if (this->arr != nullptr) {
        delete[] this->arr;
        this->arr = nullptr;
    }
}

void ArrayList::add(int val) {
    //添加到末尾
    add(val, this->realSize);
}

void ArrayList::add(int val, int index) {
    if (index < 0 || index > size) {
        return;
    }
    //判断容量是否够大 不够进行扩容
    if (this->realSize >= size * 0.75) {
        resize();
    }
    this->arr[index] = val;// 等价于   *((this->arr)+index) = val
    this->realSize++;//数据量大小+1
}

void ArrayList::resize() {
    cout << "----resize-----" << endl;
    int netLength = size * 2;
    int *p = new int[netLength];
    //拷贝数据
    for (int i = 0; i < size; ++i) {
        *(p + i) = this->arr[i];
    }
    //释放之前的数组
    delete[] this->arr;
    //重新赋值
    this->arr = p;
    this->size = netLength;
}

int ArrayList::get(int index) {
    if (index < 0 || index >= realSize) {
        return -1;
    }
    return this->arr[index];
}

int ArrayList::remove(int index) {
    if (index < 0 || index >= realSize) {
        return -1;
    }
    //如何移除呢？循环往前移动
    int result = this->arr[index];
    for (int i = index; i < size - 1; ++i) {
        this->arr[i] = this->arr[i + 1];
    }
    this->realSize--;
    //判断缩减容量
    return result;
}

void ArrayList::toString() {
    cout << "[ ";
    for (int i = 0; i < realSize; ++i) {
        cout << arr[i] << ", ";
    }
    cout << " ] " << endl;
}

bool ArrayList::isEmpty() const {
    return realSize == 0;
}

/**
 * 定义为常函数 不能够修改内部的变量
 * @return
 */
int ArrayList::getLength() const {
//    realSize = realSize - 1; 这样会报错 不能修改函数内部的所有变量
    c = 11;//mutable 修饰的变量可以在常函数中修改
    return realSize;
}

