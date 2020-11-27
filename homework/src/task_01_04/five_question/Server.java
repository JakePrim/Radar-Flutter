package task_01_04.five_question;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 服务器端
 */
public class Server {
    public static void main(String[] args) {
        ServerSocket ss = null;
        //存储链接客户端的一个集合
        List<OutputStream> socketList = new ArrayList<>();
        ExecutorService es = null;
        try {
            es = Executors.newCachedThreadPool();
            ss = new ServerSocket(9898);
            //处于一直等待用户连接
            while (true) {
                System.out.println("等待客户端连接中...");
                Socket s = ss.accept();
                //每次进来一个客户端 存储它的输出流
                socketList.add(s.getOutputStream());
                System.out.println("用户在线数:" + socketList.size());
                //每个用户交给一个线程去管理
                es.submit(new ServerRunnable(s, socketList));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != ss) {
                try {
                    ss.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != es) {
                es.shutdown();//关闭线程池
            }
        }
    }
}
