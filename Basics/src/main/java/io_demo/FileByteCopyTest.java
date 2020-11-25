package io_demo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileByteCopyTest {
    public static void main(String[] args) {
        FileInputStream is = null;
        FileOutputStream os = null;
        try {
            is = new FileInputStream("/Users/prim/Desktop/images.jpeg");
            os = new FileOutputStream("/Users/prim/Desktop/images2.jpeg",true);
//            int res = 0;
//            while ((res = is.read()) != -1) {
//                os.write(res);
//            }
            //方式二：准备一个和文件大小一样的缓冲区，一次性将文件中的所有内容读取到缓冲区一次性写入
//            int available = is.available();//文件大小 10738
//            System.out.println(available);
//            byte[] b = new byte[available];
//            int res = is.read(b);
//            System.out.println("实际读取的文件大小:" + res);
//            os.write(b);
            //方式三：准备一个相对适当的缓冲区，分多次将文件拷贝完成
            byte[] b = new byte[1024];
            int res = 0;
            while ((res = is.read(b)) != -1) {
                os.write(b, 0, res);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
