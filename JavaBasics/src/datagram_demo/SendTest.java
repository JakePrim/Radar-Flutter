package datagram_demo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * 发送方
 */
public class SendTest {
    public static void main(String[] args) {
        DatagramSocket ds = null;
        try {
            //1.创建DatagramSocket类型的对象
            ds = new DatagramSocket();
            //2.创建DatagramPacket类型的对象，并提供接收方的通信地址和端口号
            byte[] barr = "hello".getBytes();
            DatagramPacket dp = new DatagramPacket(barr, barr.length, InetAddress.getLocalHost(), 8888);
            //3.通过Socket发送Packet，调用send方法
            ds.send(dp);
            System.out.println("发送数据成功");
            //接收回发的数据内容
            byte[] b = new byte[20];
            DatagramPacket dp2 = new DatagramPacket(b, b.length);
            ds.receive(dp2);
            System.out.println("接收到的回发消息是:" + new String(b, 0, dp2.getLength()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != ds) {
                ds.close();
            }
        }


    }
}
