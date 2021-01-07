package io_demo;

import java.io.*;

public class BufferedCharCopyTest {
    public static void main(String[] args) {
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new FileReader("/Users/prim/Desktop/a.txt"));
            bw = new BufferedWriter(new FileWriter("/Users/prim/Desktop/b.txt"));
            String str = null;
            while ((str = br.readLine()) != null) {
                bw.write(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != bw) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
