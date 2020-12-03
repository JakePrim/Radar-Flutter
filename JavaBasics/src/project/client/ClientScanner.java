package project.client;

import java.util.Scanner;

/**
 * 实现扫描器工具类的封装 可以在任意位置使用
 */
public class ClientScanner {
    private static final Scanner sc = new Scanner(System.in);

    public static Scanner getScanner() {
        return sc;
    }

    public static void scannerClose() {
        sc.close();
    }
}
