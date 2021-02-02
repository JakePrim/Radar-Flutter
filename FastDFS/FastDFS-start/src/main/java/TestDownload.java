import org.csource.common.MyException;
import org.csource.fastdfs.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestDownload {
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

            byte[] bytes = storageClient1.download_file1("group1/M00/00/00/rBCWgmAX7DiAV1vDAAH6S0vtgAQ068.png");
            //文件的id
            //通过IO流将字节数组转换成一个文件
            FileOutputStream outputStream = new FileOutputStream(new File("/Users/prim/xxx.png"));
            outputStream.write(bytes);
            outputStream.close();
            trackerServer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
}
