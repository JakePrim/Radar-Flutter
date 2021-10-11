//
// Created by JakePrim on 2021/10/9.
//

#ifndef CPPDEMO_ARRAYLIST_H
#define CPPDEMO_ARRAYLIST_H


class ArrayList {
public:
    int d = 11;
    ArrayList();

    //explicit 不能通过隐式调用
    explicit ArrayList(int capacity);

    //拷贝函数
    ArrayList(const ArrayList &arrayList);

    //析构函数
    ~ArrayList();

    void add(int val);

    void add(int val, int index);

    int get(int index);

    int remove(int index);

    //const 定义为常函数
    int getLength() const;

    bool isEmpty() const;

    void resize();

    void toString();

private:
    int size;//容器的大小
    int realSize;//真实的数组长度
    int *arr;//这里不能使用数组，因为数组名是arr指针常量，不能对arr重新赋值， 指针是指针变量，而数组名只是一个指针常量
    mutable int c = 10;//可以在常函数中修改的变量 需要使用mutable进行修饰
};


#endif //CPPDEMO_ARRAYLIST_H
