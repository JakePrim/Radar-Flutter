package string_demo;

import java.util.Date;

public class StringTest {
    public static void main(String[] args) {
        long start = new Date().getTime();
        String a = "";
        for (int i = 0; i < 10000; i++) {
            a += i;
        }
        System.out.println(new Date().getTime()-start);//1662
        long start1 = new Date().getTime();
        StringBuilder b = new StringBuilder("");
        for (int i = 0; i < 10000; i++) {
            b.append(i);
        }
        System.out.println(new Date().getTime()-start1);//1

        long start2 = new Date().getTime();
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < 10000; i++) {
            sb.append(i);
        }
        System.out.println(new Date().getTime()-start2);//7

    }
}
