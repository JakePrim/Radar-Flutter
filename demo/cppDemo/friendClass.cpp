#include <iostream>

//友元类
class CCar{
private:
    int price;
    friend class CDriver;//定义友元类 可以访问CCar的私有成员
};

class CDriver{
public:
    CCar mCar;
    void Most(){
        mCar.price+=100;
    }

private:
    void Host(){
        mCar.price+=1;
    }
};

