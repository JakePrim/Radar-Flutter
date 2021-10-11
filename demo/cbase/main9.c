#include <stdio.h>
/**
 * 函数指针
 * @return
 */
void func(int a,int b){
    printf("a:%d,b:%d\n",a,b);
}

//函数指针作为参数传递
/**
 * @param a
 * @param b
 * @param callback 类似java中的回调函数
 * @return
 */
void test(int a,int b,int (*callback) (int)){
    int result = a+b;
    callback(result);
}

int call(int result){
    printf("result:%d\n",result);
    return result;
}

//给函数定义别名
typedef void FUNC(int,int);

int main(){
    func(1,2);
    //1. 函数存在地址的 函数的指向类型没有意义的，因为函数指向的一块代码区域
    printf("func = %p\n",func);//0000000000401550
    printf("&func = %p\n",&func);//0000000000401550
    printf("*func = %p\n",*func);//0000000000401550

    //2. 函数指针：返回值 (*指针名) (参数列表)
    void (*p) (int,int);
    p = func;//给指针赋值
    p(3,4);//a:3,b:4

    //3. 函数指针作为参数传递
    test(30,40,call);//result:70

    //4. 定义函数别名简化函数指针
    FUNC *p2 = func;
    p2(1,2);//a:1,b:2
    (*p2)(1,2);//p2 == *p2
    (&func)(1,2);////a:1,b:2
    (*func)(1,2);////a:1,b:2
    return 0;
}