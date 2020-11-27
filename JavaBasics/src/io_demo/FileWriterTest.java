package io_demo;

import java.io.FileWriter;
import java.io.IOException;

public class FileWriterTest {
    public static void main(String[] args) {
        //1 构造对象 指定输出文件
        FileWriter fw = null;
        try {
            //若文件不存在FileWriter 会自动创建空文件
            fw = new FileWriter("/Users/prim/Desktop/a.txt");
            //保留原有的数据内容 以追加的方式添加数据
//            fw = new FileWriter("/Users/prim/Desktop/a.txt",true);
            //每当写入一个字符后则文件中的读写位置向后移动一位
            fw.write('a');
            //准备一个字符数组
            char[] carr = new char[]{'h', 'e', 'l', 'l', 'o'};
            //将数组下标从1开始三个字符写入
            fw.write(carr,1,3);
            //将整个字符数组写入
            fw.write(carr);
            System.out.println("写入数据成功");
            fw.flush();//刷新流
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
