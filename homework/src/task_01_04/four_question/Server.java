package task_01_04.four_question;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端的实现
 */
public class Server {
    public static void main(String[] args) {
        ServerSocket ss = null;//ServerSocket对象去连接客户端
        Socket s = null;//
        //输出输入流
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        try {
            ss = new ServerSocket(8888);
            System.out.println("服务器启动，等待客户连接...");
            s = ss.accept();//等待客户端的链接
            //然后通过ObjectInputStream 接受客户端发送过来的信息
            ois = new ObjectInputStream(s.getInputStream());
            //然后通过readObject去读取信息 并转换为UserManager对象
            UserManager tum = (UserManager) ois.readObject();
            System.out.println("服务器接受到信息:" + tum);
            User user = tum.getUser();//获取用户信息
            System.out.println("服务器验证信息中...");
            //用户的验证
            if ("admin".equals(user.getName()) && "123456".equals(user.getPassword())) {
                tum.setType("success");
            } else {
                tum.setType("fail");
            }
            //然后将修改后的UserManager对象信息，将结果返回给客户端
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
