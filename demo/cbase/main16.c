#include <stdio.h>
#include <malloc.h>
#include <sys/stat.h>

//c文件编译：.c -> .o -> .exe
//int main1() {
//    FILE *p = fopen("./test/m.txt", "w");//如果文件不存在会创建文件
//    //r 只读，w 写入
//    if (p) {
//        fclose(p);//关闭文件
//        //这时候p就是悬空指针
//        p = NULL;
//        //文件存在
//        printf("文件存在");
//    } else {
//        //文件不存在
//        printf("文件不存在");
//    }
//    return 0;
//}
//
///**
// * 读文件
// */
//int main2() {
//    //读文件
//    FILE *p = fopen("./test/m.txt", "r");
//    if (p) {
//        while (1) {
//            char c = fgetc(p);
//            //如果读到的是EOF表示读取完毕
//            if (c == EOF) {
//                break;
//            }
//            printf("c:%c\n", c);//c:a
//        }
//    }
//    fclose(p);
//    p = NULL;
//    return 0;
//}
//
///**
// * 写文件
// */
//int main3() {
//    FILE *p = fopen("./test/m.txt", "w");//打开文件
//    if (p) {
//        fputc('b', p);//全部覆盖重写
//        fclose(p);
//        p = NULL;
//    }
//    return 0;
//}
//
///**
//* copy文件
//*/
//int main4() {
//    FILE *p = fopen("./test/write.txt", "w");//复制到新文件中
//    FILE *p1 = fopen("./test/m.txt", "r");//读取原文件
//    if (p == NULL || p1 == NULL) {
//        return -1;
//    }
//    while (1) {
//        char c = getc(p1);
//        if (c == EOF) {
//            break;
//        }
//        fputc(c, p);
//    }
//    fclose(p);
//    fclose(p1);
//    p = NULL;
//    p1 = NULL;
//    return 0;
//}
//
//int acc(int a, char b,int c){
//    int result = 0;
//    switch (b) {
//        case '+':
//            result = a + c;
//            break;
//        case '-':
//            result = a - c;
//            break;
//        case '*':
//            result = a * c;
//            break;
//        case '/':
//            result = a / c;
//            break;
//    }
//    return result;
//}
//
///**
// * 解析文件
// */
//int main5() {
//    FILE *p = fopen("./test/m1.txt", "r");
//    if (p) {
//        while (1) {
//            char buffer[100] = {0};//定义100个char,都设置为0，内存中是有脏数据的 推荐都设置为0
//            //读取一行 gets
//            fgets(buffer, sizeof(buffer), p);//将一行的数据 读取到buffer中
//            int a = 0;
//            char b = 0;
//            int c = 0;
//            //格式化读取
//            sscanf(buffer, "%d%c%d", &a, &b, &c);
//            printf("a:%d b:%c c:%d = %d\n",a,b,c, acc(a,b,c));
//
//            //判断文件结尾 通过feof判断文件是否读取到了末尾
//            if (feof(p)) {
//                break;
//            }
//        }
//    }
//    fclose(p);
//    p=NULL;
//    return 0;
//}
#define BLOCK_SIZE (1024 * 64)

int main(int argc,char** argv){
//    const int number = 11;
//    const int * acc = &number;//指向了一个常量 常量指针 不允许通过指针修改常量的值
//    *acc = 111;
//    printf("acc:%d\n",number);

    //argc 参数长度 size
    //argv[0] 被系统占用
    //rb 读取二进制  wb 写二进制
    printf("argc:%d agrv1:%s,agrv2:%s\n",argc,argv[1],argv[2]);//argc:1 agrv:(null)
    FILE *pr1 = fopen(argv[1],"rb");
    if(pr1 == NULL) return 0;
    FILE *pr2 = fopen(argv[2],"wb");
    if(pr2 == NULL) return 0;

    //先设置缓冲区
    struct stat st = {0};
    //获取文件的大小
    stat(argv[1],&st);
    int size = st.st_size;//这个大小如何设置内不能说 视频100M 分配100M的内存 一定要进行判断
    printf("size:%d\n",size);
    if(size > BLOCK_SIZE){//如果大于64KB 则分块读取文件
        size = BLOCK_SIZE;
    }
    char *buf = calloc(1,size);//calloc 声明一块内存空间 并且置为内存的初始值  malloc声明一块内存空间 不会置为0

    unsigned int index = 0;//auto 自动正负号 unsigned正数

    while (!feof(pr1)){
        //读取文件 返回最终读取了多少个
        int res = fread(buf,1,size,pr1);//读取pr1文件 读取1个size大小，读取到buffer中去
        //写入目标文件
        fwrite(buf,1,res,pr2);
        index ++;
    }
    free(buf);
    buf = NULL;
    fclose(pr1);
    fclose(pr2);
    pr1 = NULL;
    pr2 = NULL;
    return 0;
}