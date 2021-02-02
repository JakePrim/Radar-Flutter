import org.csource.common.MyException;
import org.csource.fastdfs.*;

import java.io.IOException;

/**
 * 文件查询
 */
public class TestQuery {
    public static void main(String[] args) {
        try {
            //加载配置文件
            ClientGlobal.initByProperties("config/fastdfs-client.properties");
            //创建Tracker客户端
            TrackerClient trackerClient = new TrackerClient();
            //通过Tracker客户端获取Tracker服务并返回
            TrackerServer trackerServer = trackerClient.getConnection();
            //声明Storage服务
            StorageServer storageServer = null;
            //定义Storage客户端
            StorageClient1 storageClient1 = new StorageClient1(trackerServer, storageServer);

            FileInfo fileInfo = storageClient1.query_file_info1("group1/M00/00/00/rBCWgmAX7DiAV1vDAAH6S0vtgAQ068.png");

            if (fileInfo != null) {
                System.out.println(fileInfo);
            } else {
                System.out.println("查无此文件");
            }
            // 返回信息如下：
            // source_ip_addr = 172.16.150.130,
            // file_size = 129611,
            // create_timestamp = 2021-02-01 19:55:36,
            // crc32 = 1273856004
            trackerServer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
}
