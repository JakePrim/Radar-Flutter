#include <stdio.h>
#include <string.h>
/**
 * 常量指针和指针常量
 * 常量的关键字：const
 * 常量指针：指向常量的指针，不能指向变量，它指向的内容不能被改变，不能通过指针来修改它指向的内容，但是指针自身不是常量，它自身的值可以改变，指向另一个常量
 * 指针常量：指针本身是常量，它指向的地址不可改变，但是地址的内容可以通过指针来改变，那么指针常量在定义时必须赋值，并且指向的地址伴随它一生
 * @return
 */

//字符串拼接
void mystrcat(char *s1, char *s2) {
    //s1和s2指向的是存储在栈区的字符串 所以可以通过指针进行操作
    while (*s1) s1++;//while(\0) 循环结束   s1 -> \0
    //修改指针 指向的内容   s1 -> \0 -> *s2
    while (*s1++ = *s2++);//s1 = \0 结束循环
}

int main() {
    //* 和 const 谁在前面，谁先读：* 指针、const 常量

    //常量指针:指向了常量，可以改变指向，但是不能改变地址的内容
    char *s = "abc";
    char data[] = "abc";//默认加了一个 \0
    printf("string size:%d\n", strlen(s));//string size:3
    printf("s sizeof:%d\n", sizeof(s));//8
    printf("data sizeof:%d\n", sizeof(data));//4 多出来的一个就是 \0
    const int *ptr;//const 离 *ptr最近 不能修改
    int const *ptr2;//const 离 *ptr2最近 不能修改

    //指针常量:指针是一个常量，指向的地址不能修改，指向地址的内容可以修改
    int a = 2;
    int *const b = &a;//b就是指针常量，const 离谁最近 谁就不能修改  b指向的地址不能修改

    //字符串拷贝
    char s1[] = "hellotest";//存储在栈区
    printf("len:%d\n", strlen(s1));//9  strlen 同理遇到\0就计算结束
    printf("sizeof:%d\n", sizeof(s1));//10 数组的大小多了一个\0

    //将jake复制到s1
    strcpy(s1, "jake");//此处操作实际是：jake\0test  前面的hello被替换掉了

    printf("%s\n", s1);//jake  输出字符串 遇到了\0就会自动结束
    printf("len:%d\n", strlen(s1));//4  strlen 同理遇到\0就计算结束
    printf("%d\n", s1[4]);//第五个元素为0  在C语言中字符串遇到\0就会结束
    printf("%c\n", s1[5]);//第六个元素 t
    printf("sizeof:%d\n", sizeof(s1));//10 数组的大小没有改变

    char s2[] = "test\0abc";
    printf("s2:%s\n", s2);//test

    char *s3 = "abc";
//    strcpy(s3,"d"); //出现错误 strcpy无法传递
//    printf("s3:%s\n",s3);

    char s4[] = "abc";//栈
    char s5[] = "123";//栈

    //字符串拼接
    //如果是 char * s4="abc"  char * s5 = "123" 这样是不行的 因为常量区的内容不能进行修改
    mystrcat(s4, s5);
    printf("s4:%s\n", s4);//s4:abc123
    printf("s4:%d\n", s4[6]);//0


    //字符串本身是一个数组
    //字符串数组是一个二维数组

    //指针常量
    char arr[4][6] = {"abc", "def", "123", "wer"};
    //指针常量  arr -> * const
//    arr[0] = "sds";//不行的 不能修改指向  arr[0] = "abc" 是一个指针常量
    printf("arr[0]:%s\n", arr[0]);//arr[0]:abc
    //可以修改指向的内容
    strcpy(arr[0], "aabbcc");//arr[0]:aabbcc  可以修改内容  不可以修改指向的地址：指针常量
    printf("arr[0]:%s\n", arr[0]);

    //常量指针  指针数组 arr1[0]:常量指针 { char *,char *,char *,char * }
    char *arr1[4] = {"abc", "123", "sd", "se"};
    arr1[0] = "abd";//可以修改指向，不可以修改指向的内容  定价于 char * arr1 = "abc" 正是一个常量指针
    printf("value:%s\n", arr1[0]);//value:abd
//    *(arr1[0]) = "12";
//    strcpy(arr1[0], "aabbcc");//修改内容 报错
//    printf("value:%s\n", arr1[0]);
    return 0;
}
