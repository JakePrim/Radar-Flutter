package controller;

import entity.FileSystem;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.naming.Name;
import java.io.File;
import java.util.UUID;

/**
 * @program: ImageServer
 * @Description: 处理上传文件控制器
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-01 21:40
 * @PackageName: controller
 * @ClassName: FilController.java
 **/
@Controller
public class FilController {

    /**
     * @param request MultipartHttpServletRequest是HttpServletRequest的强化版本 不仅可以装文本信息还可以装图片文件信息
     * @return
     * @throws Exception 上传流程：
     *                   1. 先把文件保存到web服务器上
     *                   2. 再从web服务上将文件上传到fastDFS上
     */
    @RequestMapping("upload")
    @ResponseBody
    public FileSystem upload(MultipartHttpServletRequest request) throws Exception {
        FileSystem fileSystem = new FileSystem();

        //1. 先把文件保存到web服务器上
        //从页面请求中获取，拿上传的文件对象
        MultipartFile file = request.getFile("fname");
        //从文件对象中获取文件的原始名称
        String oldFilename = file.getOriginalFilename();
        //获取后缀名 通过字符串截取获得
        String suffixName = oldFilename.substring(oldFilename.lastIndexOf(".") + 1);
        //重新生成文件名，避免文件同名而覆盖
        String newFileName = UUID.randomUUID().toString() + "." + suffixName;
        //创建web服务器保存文件的目录
        File webSaveFile = new File("/Users/prim/Downloads/" + newFileName);
        //将路径转换成文件
        file.transferTo(webSaveFile);
        //获取服务器的绝对路径
        String webPath = webSaveFile.getAbsolutePath();

        //2. 再从web服务上将文件上传到fastDFS上
        ClientGlobal.initByProperties("config/fastdfs-client.properties");
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer trackerServer = trackerClient.getConnection();
        StorageServer storageServer = null;
        StorageClient1 client1 = new StorageClient1(trackerServer, storageServer);
        NameValuePair[] nameValuePairs = new NameValuePair[1];
        nameValuePairs[0] = new NameValuePair("fileName", oldFilename);
        String fileId = client1.upload_file1(webPath, suffixName, nameValuePairs);
        trackerServer.close();

        //3. 封装返回的数据对象
        fileSystem.setFileId(fileId);
        fileSystem.setFileName(oldFilename);
        fileSystem.setFilePath(fileId);//已经上传到fastDFS上通过fileId来访问图片
        return fileSystem;
    }
}
