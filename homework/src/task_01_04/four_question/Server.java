package task_01_04.four_question;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        ServerSocket ss = null;
        Socket s = null;
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ss = new ServerSocket(8888);
            System.out.println("服务器启动，等待客户连接...");
            s = ss.accept();
            //接受客户端发送过来的信息
            ois = new ObjectInputStream(s.getInputStream());
            UserManager tum = (UserManager) ois.readObject();
            System.out.println("服务器接受到信息:" + tum);
            User user = tum.getUser();
            System.out.println("服务器验证信息中...");
            if ("admin".equals(user.getName()) && "123456".equals(user.getPassword())) {
                tum.setType("success");
            } else {
                tum.setType("fail");
            }
            //获得信息，将结果返回给客户端
            oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(tum);
            oos.flush();
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
            if (null != oos) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != s) {
                try {
                    s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != ss) {
                try {
                    ss.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
