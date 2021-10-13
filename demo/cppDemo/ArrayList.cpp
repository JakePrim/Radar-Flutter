//
// Created by JakePrim on 2021/10/9.
//
#include <iostream>
#include <cstdio>
#include "ArrayList.h"

using namespace std;

template<typename T>
ArrayList<T>::ArrayList(int capacity) {
    this->size = capacity;
    this->realSize = 0;
    //在堆区申请数组
    this->arr = new T[this->size];//在堆中开辟的一块空间 存储的是一个int[size] 数组，arr指向数组的首地址
}

template<typename T>
ArrayList<T>::ArrayList() {

}

template<typename T>
ArrayList<T>::ArrayList(const ArrayList &arrayList) {

}

template<typename T>
ArrayList<T>::~ArrayList() {

}

template<typename T>
void ArrayList<T>::add(T val) {
    //添加到末尾

}
template<typename T>
void ArrayList<T>::add(T val, int index) {

}

template<typename T>
void ArrayList<T>::resize() {

}

template<typename T>
T ArrayList<T>::get(int index) {

}

template<typename T>
T ArrayList<T>::remove(int index) {

}

template<typename T>
void ArrayList<T>::toString() {

}

template<typename T>
bool ArrayList<T>::isEmpty() const {

}

/**
 * 定义为常函数 不能够修改内部的变量
 * @return
 */
template<typename T>
int ArrayList<T>::getLength() const {

}

