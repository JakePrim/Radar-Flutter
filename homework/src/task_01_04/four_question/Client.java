package task_01_04.four_question;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 客户端类
 */
public class Client {
    public static void main(String[] args) {
        Socket s = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            s = new Socket("127.0.0.1", 8888);//socket去连接服务端
            System.out.println("连接服务器成功!");
            //然后通过ObjectOutputStream 向服务器发送信息
            oos = new ObjectOutputStream(s.getOutputStream());
            //创建UserManager对象 类型 用户信息
            UserManager tum = new UserManager("check", new User("admin1", "123456"));
            //进行发送给服务端
            oos.writeObject(tum);
            oos.flush();
            System.out.println("登录中...");
            //等待服务端的反馈 判断是否登录成功 接受服务发送过来的信息
            ois = new ObjectInputStream(s.getInputStream());
            UserManager result = (UserManager) ois.readObject();
            System.out.println("接受到服务器发送过来的消息:" + result);
            if ("fail".equals(result.getType())) {
                System.out.println("登录失败");
            } else if ("success".equals(result.getType())) {
                System.out.println("登录成功");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
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
        }
    }
}
