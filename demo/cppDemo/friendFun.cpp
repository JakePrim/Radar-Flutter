#include <iostream>

class Car;//提前声明Car
class Driver {
private:
    void pfun();
public:
    void ModifyCar(Car *car);
    void Not(Car *car);
};

class Car {
public:
    int a;
private:
    int price;

    friend int Most(Car cars[], int total);//声明友元函数
    friend void Driver::ModifyCar(Car *car);//声明友元函数
//    friend void Driver::pfun();//其他类的私有成员不能设置为友元函数
};

/**
 * 类定义的友元函数可以访问私有变量
 * @param car
 */
void Driver::ModifyCar(Car *car) {
    car->price += 1000;//ModifyCar作为友元函数 可以访问car的私有变量
}

/**
 * 外部定义的友元函数可以访问私有变量
 * @param cars
 * @param total
 * @return
 */
int Most(Car cars[], int total)  //求最贵气车的价格
{
    int tmpMax = -1;
    for (int i = 0; i<total; ++i)
        if (cars[i].price > tmpMax)
            tmpMax = cars[i].price;
    return tmpMax;
}

void Driver::Not(Car *car) {
    //无法访问私有变量
    car->a;
}
