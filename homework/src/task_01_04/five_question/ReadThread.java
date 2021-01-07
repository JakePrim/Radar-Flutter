package task_01_04.five_question;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * 设置读线程 socket.getInputStream会阻塞
 */
public class ReadThread extends Thread {
    private Socket s = null;
    private ObjectInputStream ois = null;


    public ReadThread(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        try {
//            ois = new ObjectInputStream(new BufferedInputStream(s.getInputStream()));
            while (true) {
                //这里为什么每次都要创建一个输入流呢？ 因为从服务端发送过来的对象不是同一个对象，会导致错误发生
                //StreamCorruptedException 都需要进行一一对应   不是很理解？
                //https://blog.csdn.net/sunhengzhe/article/details/37773431
                ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
                User result = (User) ois.readObject();
                System.out.println(result.getName() + "说:" + result.getContent() + " 上传文件:" + result.getFile().getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            System.out.println("读取执行结束");
            if (null != ois) {
                try {
                    ois.close();
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
