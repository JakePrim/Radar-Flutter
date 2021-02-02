import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

import java.io.IOException;

/**
 * 文件上传
 */
public class TestUpload {

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
            //定义文件元信息
            NameValuePair[] list = new NameValuePair[1];
            list[0] = new NameValuePair("fileName", "avatar.png");
            //上传文件
            String fileId = storageClient1.upload_file1("/Users/prim/Downloads/avatar.png", "png", list);
            System.out.println(fileId);//group1/M00/00/00/rBCWgmAX7DiAV1vDAAH6S0vtgAQ068.png
            //group1:一台服务器，就是一个组
            //M00:对应的目录/home/fastdfs/fdfs_storage/data 在之前配置时进行设置的
            //00/00:两级数据目录
            //关闭Tracker
            trackerServer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
}
