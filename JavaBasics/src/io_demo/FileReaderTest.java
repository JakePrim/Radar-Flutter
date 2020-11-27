package io_demo;

import java.io.FileReader;
import java.io.IOException;

public class FileReaderTest {
    public static void main(String[] args) {
        FileReader fr = null;
        try {
            fr = new FileReader("/Users/prim/Desktop/a.txt");
            //读取文件的单个字符
//            int res = fr.read();
//            System.out.println("读取到的单个字符是:"+(char)res);//a
            //读取文件的所有内容
//            int r = 0;
//            while ((r=fr.read()) != -1){
//                System.out.println("读取到的单个字符是:"+(char)r);
//            }
            //准备一个字符数组保存读取到的数据内容
            char[] carr = new char[5];
            //读取三个字符 放入carr数组从下标为1的位置开始放3个
//            int res = fr.read(carr, 1, 3);
//            System.out.println("实际读取到的字符个数：" + res);//3
//            for (char c : carr) {
//                System.out.println("" + c);
//            }
            //读完整个数组
            int res = fr.read(carr);
            System.out.println("实际读取到的字符个数：" + res);//3
            for (char c : carr) {
                System.out.println("" + c);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fr) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
