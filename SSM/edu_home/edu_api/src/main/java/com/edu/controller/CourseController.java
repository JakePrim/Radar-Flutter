package com.edu.controller;

import com.edu.common.StateCode;
import com.edu.pojo.Course;
import com.edu.pojo.ResponseResult;
import com.edu.pojo.bo.CourseBo;
import com.edu.pojo.vo.CourseVo;
import com.edu.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * @param courseVo
     * @return
     * @RequestBody 将json的参数 封装成实体
     */
    @PostMapping("/findAllCourse")
    public ResponseResult findAllCourse(@RequestBody CourseVo courseVo) {
        List<Course> courses = courseService.findCourseByCondition(courseVo);
        ResponseResult result = new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), courses);
        return result;
    }

    @PostMapping("/courseUpload")
    public ResponseResult fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
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
        String uploadPath = path + "upload";
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
        map.put("filePath", "http://localhost:8080/upload/" + fileName);
        ResponseResult result = new ResponseResult(true, StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), map);
        return result;
    }

    /**
     * 保存和更新课程api
     *
     * @param courseVo
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @PostMapping("/saveOrUpdateCourse")
    public ResponseResult saveOrUpdateCourse(@RequestBody CourseVo courseVo) {
        try {
            if (courseVo.getId() != null) {
                courseService.updateCourseOrTeacher(courseVo);
            } else {
                courseService.saveCourseOrTeacher(courseVo);
            }
            ResponseResult result = new ResponseResult(true,
                    StateCode.SUCCESS.getCode(), StateCode.SUCCESS.getMsg(), null);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/findByCourseId")
    public ResponseResult findByCourseId(Integer id) {
        CourseBo courseBo = courseService.findByCourseId(id);
        ResponseResult result =
                new ResponseResult(true, StateCode.SUCCESS.getCode(),
                        StateCode.SUCCESS.getMsg(), courseBo);
        return result;
    }

    @RequestMapping("/updateCourseStatus")
    public ResponseResult updateCourseStatus(Integer id, Integer status) {
        courseService.updateCourseStatus(id, status);
        HashMap<String, Integer> map = new HashMap<>();
        map.put("status", status);
        ResponseResult result =
                new ResponseResult(true, StateCode.SUCCESS.getCode(),
                        StateCode.SUCCESS.getMsg(), map);
        return result;
    }
}
