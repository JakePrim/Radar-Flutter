package datagram_demo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * UDP协议接收方
 */
public class ReceiveTest {
    public static void main(String[] args) {
        //创建DatagramSocket对象提供端口号
        DatagramSocket ds = null;
        try {
            ds = new DatagramSocket(8888);
            //创建DatagramPacket对象提供缓冲区
            byte[] barr = new byte[20];
            DatagramPacket dp = new DatagramPacket(barr, barr.length);
            System.out.println("等待数据的到来");
            //通过Socket接收存放到缓冲汇区的数据 receive方法会进行阻塞
            ds.receive(dp);
            //dp.getLength() 获取接收数据的长度
            System.out.println("接收到的数据是:" + new String(barr, 0, dp.getLength()) + "!");

            // 实现回发给发送方
            byte[] b = "I receive".getBytes();
            //dp.getAddress(),dp.getPort() 获得发送方的地址和端口号
            DatagramPacket dp2 = new DatagramPacket(b, b.length, dp.getAddress(), dp.getPort());
            ds.send(dp2);
            System.out.println("回发数据成功");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != ds) {
                ds.close();
            }
        }


    }
}
