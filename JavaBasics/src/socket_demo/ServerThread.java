package socket_demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 服务器采用多线程机制实现
 */
public class ServerThread extends Thread {
    Socket s = null;
    BufferedReader reader = null;
    PrintStream ps = null;

    /**
     * 需要将连接的客户端socket传递引用过来
     *
     * @param s
     */
    public ServerThread(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            ps = new PrintStream(s.getOutputStream());
            while (true) {
                //使用输入输出流进行通信
                //实现对客户端发来的信息接收并打印
                //当没有数据发来时，下面的方法会形成阻塞
                String s1 = reader.readLine();
                InetAddress inetAddress = s.getInetAddress();
                System.out.println("客户端" + inetAddress + "发来的信息:" + s1);
                //当客户端发来的内容为bye时 则聊天结束
                if ("bye".equalsIgnoreCase(s1)) {
                    System.out.println(getName() + "客户端" + inetAddress + "已下线");
                    break;
                }
                //实现服务器向客户端发送信息
                ps.println("I received!");
                System.out.println(getName() + "-服务器发送数据成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //关闭Socket并释放有关的资源
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
