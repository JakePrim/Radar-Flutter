package project.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 客户端初始化和关闭操作
 */
public class ClientInitClose {
    private Socket s;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public void clientInit() throws IOException {
        s = new Socket(InetAddress.getLocalHost(), 8888);
        System.out.println("连接服务器成功");
        oos = new ObjectOutputStream(s.getOutputStream());
        ois = new ObjectInputStream(s.getInputStream());
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public void clientClose() throws IOException {
        System.out.println("客户端关闭成功");
        if (null != oos) {
            oos.close();
        }
        if (null != ois) {
            ois.close();
        }
        if (null != s) {
            s.close();
        }
    }
}
