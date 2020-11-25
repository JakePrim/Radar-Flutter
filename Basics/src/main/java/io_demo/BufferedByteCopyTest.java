package io_demo;

import java.io.*;

public class BufferedByteCopyTest {
    public static void main(String[] args) {
        // /Users/prim/Desktop/images.jpeg
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream("/Users/prim/Desktop/images.jpeg"));
            bos = new BufferedOutputStream(new FileOutputStream("/Users/prim/Desktop/images3.jpeg"));
            byte[] b = new byte[1024];
            int res = 0;
            while ((res = bis.read(b)) != -1) {
                bos.write(b, 0, res);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != bos) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != bis) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
