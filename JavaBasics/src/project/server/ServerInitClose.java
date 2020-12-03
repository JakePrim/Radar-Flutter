package project.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 实现服务器的初始化和关闭
 */
public class ServerInitClose {
    private ServerSocket ss;
    private Socket s;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public ObjectInputStream getOis() {
        return ois;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public Socket getSocket() {
        return s;
    }

    /**
     * 服务器的初始化操作
     */
    public void serverInit() throws IOException {
        //创建ServerSocket类型的对象并提供端口号
        ss = new ServerSocket(8888);
        System.out.println("等待客户端的连接请求");
        //等待客户端连接
        s = ss.accept();
        System.out.println("客户端连接成功");
        System.out.println("服务器初始化成功");
        //创建输入流和输出流
        ois = new ObjectInputStream(s.getInputStream());
        oos = new ObjectOutputStream(s.getOutputStream());
    }

    /**
     * 实现服务器的关闭操作哦
     */
    public void serverClose() throws IOException {
        //关闭socket资源并释放
        if (null != oos) {
            oos.close();
        }
        if (null != ois) {
            ois.close();
        }
        if (null != s) {
            s.close();
        }
        if (null != ss) {
            ss.close();
        }
        System.out.println("服务器关闭成功");
    }
}
