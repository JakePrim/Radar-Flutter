//
// Created by JakePrim on 2021/9/16.
//
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

/**
 * 字符串：
 * 在C语言中，不存在显示的字符串类型，C语言中的字符串都以字符串常量的形式出现或存储在字符串数组中
 * 同时C语言提供了一系列的库函数来操作字符串这些库函数都包含在头文件string.h中
 * @return
 */
int main2() {
    //数组char 在栈区
    char data[] = {'a', 'b', 'c', 'd', 'e', '\0'};//在数组中 需要手动加上\0
    printf("%s\n", data);//打印字符串 abcde

    //指针定义的char abcde在常量区，注意：常量区的内容是不能修改的
    char *str = "abcde";//C 语言中在字符串默认加上 \0 表示字符串结束
    //%s 直接输入str即可，不用加上*
    printf("%s\n", str);//打印字符串 abcde
    while (*str != '\0') {
        //%c 需要加上*
        printf("%c ", *str++);
    }
    printf("\n");

    //凡是常量区定义的数据是不能修改的
//    *str = "hello"; //报错

    //如果不定义\0
    char data2[] = {'a', 'b', 'c', 'd', 'e'};//data2:abcdeabcde 直到遇到内存中的\0才会停止
    printf("data2:%s\n", data2);
    char data3[5] = {'h', 'e', 'l', 'l', 'o'};
    printf("data3=%s\n", data3);//data3=helloabcdeabcde

    //定义数组 通过scanf进行输入字符串看结果如何
    char data4[10];
    printf("input:\n");
    //控制台输入字符串
    scanf("%s", data4); //注意scanf 遇到空格就会输出一行，遇到回车结束
    printf("data4=%s\n", data4);//data4=hello   scanf会自动添加\0

    gets(data4);//gets 会把空格当成字符串  遇到回车结束
    printf("gets:%s\n",data4);


    //获取字符串的长度
    printf("len:%zu\n", strlen(str));//len:
    return 0;
}


int main3() {
    //其他方式的输入
    printf("input:\n");
    //以固定的长度为一组 而不是根据数组大小
    while (1) {
        char str[10];
        fgets(str, 10, stdin);
        printf("str=%s\n", str);
    }
    /**
     * input:
hello world this is fgets out 10

输出方式如下：
str=hello wor
str=ld this i
str=s fgets o
str=ut 10
     */
    return 0;
}

int main(){
    //注意 输入不能使用char *   因为char *声明的字符串是在常量区，而且常量区是不能被修改的
    char *s1 = "abc";
    *s1 = "a";//这样是不行的，他是向改变常量的内容
    s1 = "bcd";//这样可以的，改变了指针的指向，并没有改变常量
    gets(s1);//发生错误，gets尝试的就是*s1 = xxxx 要改变常量的内容 所以报错了
    printf("s1:%s\n",s1);
}