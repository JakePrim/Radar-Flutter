package com.prim.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class FileuploadController {
    /**
     * @param username
     * @param filePic  MultipartFile 文件上传类型
     * @return
     */
    @RequestMapping(value = "/fileupload", method = RequestMethod.POST)
    public String fileupload(String username, MultipartFile filePic) {
        //获取表单的提交参数 完成文件上传
        System.out.println(username);

        //当前的文件 存到哪个目录下
        try {
            String originalFilename = filePic.getOriginalFilename();//获取原始的文件上传名
            filePic.transferTo(new File("/Users/prim/workspace/upload/" + originalFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "success";
    }

    /**
     * 多文件上传
     *
     * @param username
     * @param filePic
     * @return
     */
    @RequestMapping(value = "/fileuploads", method = RequestMethod.POST)
    public String fileuploads(String username, MultipartFile[] filePic) {
        //获取表单的提交参数 完成文件上传
        System.out.println(username);
        //当前的文件 存到哪个目录下
        try {
            for (MultipartFile file : filePic) {
                String originalFilename = file.getOriginalFilename();//获取原始的文件上传名
                file.transferTo(new File("/Users/prim/workspace/upload/" + originalFilename));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "success";
    }
}
