package com.edu.controller;

import com.edu.common.StateCode;
import com.edu.pojo.ResponseResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BaseController {
    /**
     * 通用的文件上传方法
     * @param uPath   上传路径
     * @param file    上传文件
     * @param request
     * @return
     * @throws IOException
     */
    protected Map<String, String> fileUpload(String uPath, MultipartFile file, HttpServletRequest request) throws IOException {
        //1. 判断文件是否为空
        if (file.isEmpty()) {
            throw new RuntimeException();
        }
        //2. 获取项目部署路径
        //tomcat/webapp/edu_home/
        String realPath = request.getServletContext().getRealPath("/");
        //截取文件路径：tomcat/webapp/
        String path = realPath.substring(0, realPath.indexOf("edu_api"));

        //3. 获取文件的原名 jake.jpg
        String originalFilename = file.getOriginalFilename();

        //4. 生成新的文件名
        long millis = System.currentTimeMillis();
        //1231321213_.jpg
        String fileName = millis + "_" + originalFilename.substring(originalFilename.lastIndexOf("."));

        //5. 文件上传
        //文件上传的目录
//        String uploadPath = path + "upload/ad";
        String uploadPath = path + uPath;
        File filePath = new File(uploadPath, fileName);
        //如果目录不存在 则进行创建
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
        }
        //图片就进行了真正的上传了
        file.transferTo(filePath);

        //6. 将文件名和文件路径返回进行响应
        HashMap<String, String> map = new HashMap<>();
        map.put("fileName", fileName);
        //如何将地址动态写活呢？
        String finalPath = "http://localhost:8080/" + uPath + fileName;
        map.put("filePath", finalPath);
        return map;
    }

}
