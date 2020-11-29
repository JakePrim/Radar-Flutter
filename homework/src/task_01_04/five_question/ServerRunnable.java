package task_01_04.five_question;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ServerRunnable implements Runnable {
    private Socket s;
    //接收对象
    private ObjectInputStream ois = null;

    private List<Socket> socketList;

    public ServerRunnable(Socket s, List<Socket> socketList) {
        this.s = s;
        this.socketList = socketList;
    }

    @Override
    public void run() {
        //读取客户端发送过来的聊天信息 转发给所有的客户端
        try {
            ois = new ObjectInputStream(s.getInputStream());
            while (true) {
                try {
                    //接收数据，当没有数据时此方法阻塞
                    User user = (User) ois.readObject();
                    System.out.println("服务接收到用户:" + user.getName() + "的信息->" + user);
                    //2. 将信息发送给所有客户端 socketList所有客户端的输出流
                    for (Socket ts : socketList) {
                        if (ts != s) {
                            ObjectOutputStream output = new ObjectOutputStream(ts.getOutputStream());
                            //将当前客户端发送过来的信息转发出去
                            output.writeObject(user);
                        }
                    }
                } catch (EOFException e) {
                    //表示连接断开
                    socketList.remove(s.getOutputStream());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (null != ois) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != s) {
                try {
                    System.out.println("socket 关闭");
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
