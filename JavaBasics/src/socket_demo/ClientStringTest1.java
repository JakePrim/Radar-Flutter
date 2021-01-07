package socket_demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * 客户端
 */
public class ClientStringTest1 {
    public static void main(String[] args) {
        //1 创建socket对象并提供服务器的主机名和端口号
        Socket s = null;
        PrintStream ps = null;
        Scanner sc = null;
        BufferedReader br = null;
        try {
            s = new Socket("127.0.0.1", 8888);
            System.out.println("链接服务器成功");
            sc = new Scanner(System.in);
            ps = new PrintStream(s.getOutputStream());
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            while (true) {
                //1 实现客户端发送的内容由用户从键盘输入
                System.out.println("请输入要发送的数据内容:");
                String next = sc.next();
                //2 客户端向服务器发送通信 通过输出流进行通信
                ps.println(next);
                System.out.println("客户端发送数据内容成功");
                // 当发送的数据内容为bye则聊天结束
                if ("bye".equals(next)) {
                    System.out.println("聊天结束");
                    break;
                }
                //3 客户端接收服务器发来的内容
                String s1 = br.readLine();
                System.out.println("服务器回发的消息是:" + s1);
            }
        } catch (IOException /*| InterruptedException*/ e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != sc) {
                sc.close();
            }
            if (null != s) {
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != ps) {
                ps.close();
            }
        }
    }
}
