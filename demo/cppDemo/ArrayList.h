#include <iostream>

using namespace std;

#ifndef CPPDEMO_ARRAYLIST_H
#define CPPDEMO_ARRAYLIST_H

template<typename T>
class ArrayList {
public:
    int d = 11;

    ArrayList() {
        this->size = 16;
        this->realSize = 0;
        this->arr = new T[this->size];
    }

    //explicit 不能通过隐式调用
    explicit ArrayList(int capacity) {
        this->size = capacity;
        this->realSize = 0;
        //在堆区申请数组
        this->arr = new T[this->size];//在堆中开辟的一块空间 存储的是一个int[size] 数组，arr指向数组的首地址
    }

    //拷贝函数
    ArrayList(const ArrayList &arrayList) {
        this->size = arrayList.size;
        this->realSize = arrayList.realSize;
        this->arr = new T[arrayList.size];
        //将数组的值赋值到arr中
        for (int i = 0; i < this->size; ++i) {
            this->arr[i] = arrayList.arr[i];//arrayList.arr[i]他也是指针  this->arr[i] 是指针
        }
    }

    //析构函数
    ~ArrayList() {
        if (this->arr != nullptr) {
            delete[] this->arr;
            this->arr = nullptr;
        }
    }

    void add(T val) {
        add(val, this->realSize);
    }

    void add(T val, int index) {
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

    T get(int index) {
        if (index < 0 || index >= realSize) {
            return -1;
        }
        return this->arr[index];
    }

    T remove(int index) {
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

    //const 定义为常函数
    int getLength() const {
        //    realSize = realSize - 1; 这样会报错 不能修改函数内部的所有变量
        c = 11;//mutable 修饰的变量可以在常函数中修改
        return realSize;
    }

    bool isEmpty() const {
        return realSize == 0;
    }

    void resize() {
        int netLength = size * 2;
        T *p = new T[netLength];
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

    void toString() {
        cout << "[ ";
        for (int i = 0; i < realSize; ++i) {
            cout << arr[i] << ", ";
        }
        cout << " ] " << endl;
    }

private:
    int size{};//容器的大小
    int realSize{};//真实的数组长度
    T *arr;//这里不能使用数组，因为数组名是arr指针常量，不能对arr重新赋值， 指针是指针变量，而数组名只是一个指针常量
    mutable int c = 10;//可以在常函数中修改的变量 需要使用mutable进行修饰
};


#endif //CPPDEMO_ARRAYLIST_H
