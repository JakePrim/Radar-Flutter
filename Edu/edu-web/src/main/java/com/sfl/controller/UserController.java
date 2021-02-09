package com.sfl.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.sfl.FileSystem;
import com.sfl.pojo.ResultDTO;
import com.sfl.pojo.User;
import com.sfl.service.UserService;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.util.UUID;

/**
 * @program: edu-web
 * @Description: 用户相关接口
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-05 14:44
 * @PackageName: com.sfl.controller
 * @ClassName: UserController.java
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference //远程引用
    private UserService userService;

    /**
     * 用户登录/注册接口：如果手机号没有注册自动注册。
     *
     * @param phone
     * @param password
     * @return
     */
    @GetMapping("/login")
    public ResultDTO<User> login(String phone, String password) {
        ResultDTO<User> resultDTO = userService.login(phone, password);
        return resultDTO;
    }

    @PostMapping("/uploadFile")
    public ResultDTO uploadFile(MultipartHttpServletRequest request) throws Exception {
        FileSystem fileSystem = new FileSystem();

        //1. 先把文件保存到web服务器上
        //从页面请求中获取，拿上传的文件对象
        MultipartFile file = request.getFile("file");
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

        return ResultDTO.createSuccess("上传成功", fileSystem);
    }

    @PostMapping("/updateUserInfo")
    public ResultDTO updateUserInfo(@RequestBody User user) {
        Integer row = userService.updateUserInfo(user);
        if (row == 0) {
            return ResultDTO.createError("更新信息失败，请稍后再试");
        }
        return ResultDTO.createSuccess("修改成功");
    }

    @PostMapping("/updatePassword")
    public ResultDTO updatePassword(@RequestBody User user) {
        Integer row = userService.updatePassword(user);
        if (row == 0) {
            return ResultDTO.createError("更新信息失败，请稍后再试");
        }
        return ResultDTO.createSuccess("修改成功");
    }
}
