package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端
 */
public class ServerStringTest {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket s = null;
        try {
            //创建服务端
            serverSocket = new ServerSocket(8888);
            //服务器一直等待客户端连接
            while (true) {
                //等待客户端的链接请求
                System.out.println("等待客户端链接请求....");
                //当没有客户端链接时 就会阻塞在这里
                s = serverSocket.accept();
                System.out.println("客户端:"+s.getInetAddress()+" 链接成功");
                //每当有一个新的客户端连接成功，就需要启动一个新的线程为之服务
                ServerThread st = new ServerThread(s);
                st.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != serverSocket) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
