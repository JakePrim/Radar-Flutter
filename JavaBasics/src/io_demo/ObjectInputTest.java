package io_demo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectInputTest {
    public static void main(String[] args) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("/Users/prim/Desktop/a.txt"));
            //如果多个对象写入文件时，建议将多个对象放入一个集合中，然后将集合这个整体看做一个对象写入输出流中即可
            Object object = ois.readObject();
            System.out.println("读取到的对象:"+object);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (null != ois){
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
