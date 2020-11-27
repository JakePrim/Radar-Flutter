package io_demo;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileCharCopyTest {
    public static void main(String[] args) {
        //实现文本文件的拷贝
        FileReader fr = null;
        FileWriter fw = null;
        try {
            fr = new FileReader("/Users/prim/Desktop/images.jpeg");
            fw = new FileWriter("/Users/prim/Desktop/images1.jpeg");
            int res = 0;
            while ((res = fr.read()) != -1) {
                fw.write(res);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //后创建的先关闭 资源
            if (null != fw) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
