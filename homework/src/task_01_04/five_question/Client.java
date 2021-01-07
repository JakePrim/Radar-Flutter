package task_01_04.five_question;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * 用户端
 */
public class Client {
    public static void launch(String host, int port, User user) {
        Socket s = null;
        Scanner sc = null;
        ObjectOutputStream oos = null;
        try {
            s = new Socket(host, port);
            System.out.println("连接服务器成功");
            sc = new Scanner(System.in);
            oos = new ObjectOutputStream(s.getOutputStream());
            //注意 当客户端要读取服务端的数据时，另起一个线程进行读取 防止客户端的写入和s.getInputStream阻塞
            ReadThread readThread = new ReadThread(s);
            readThread.start();
            //主线程中 用户写入的信息
            while (true) {
                System.out.println(user.getName() + ":");
                while (sc.hasNext()) {
                    String content = sc.nextLine();//获取用户输入的最后一行 因为
                    user.setContent(content);
                    //向服务端发送信息
                    oos.writeObject(user);
                    oos.flush();
                    /**
                     * 必须调用reset否则，服务器读取到的对象还是之前的对象。那么发送给其他的客户端就是之前的对象信息
                     * 方法将忽略已经写入流中的任何对象的状态。的状态被重置为相同的新对象输出。
                     * 当前点在流中被标记为复位，相应的ObjectInputStream将在相同的点被复位。
                     * 以前写入流对象将不会被方称为已在流中存在。它们将被再次写入流。
                     * http://gitbook.net/java/io/objectoutputstream_reset.html
                     */
                    oos.reset();
                }
            }
        } catch (IOException e) {
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
            if (null != sc) {
                sc.close();
            }
        }
    }
}
