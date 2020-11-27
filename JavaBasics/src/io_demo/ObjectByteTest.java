package io_demo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectByteTest {
    public static void main(String[] args) {
        //ObjectOutputStream 对象将一个序列化对象转换为字节信息
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream("/Users/prim/Desktop/a.txt"));
            User user = new User("sd", "123321", "1389232323");
            oos.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != oos) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
