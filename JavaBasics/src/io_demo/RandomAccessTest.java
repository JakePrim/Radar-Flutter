package io_demo;

import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessTest {
    public static void main(String[] args) {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile("/Users/prim/Desktop/a.txt", "rw");
            //设置距离文件开头位置的偏移量，从文件开头位置向后偏移3个字节
            raf.seek(3);
            int read = raf.read();
            System.out.println("读取到的单个字符是:" + (char) read);
            read = raf.read();
            System.out.println("读取到的单个字符是:" + (char) read);
            raf.write('2');//执行该行代码后，覆盖了字符'e'
            System.out.println("写入数据成功");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != raf) {
                try {
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
