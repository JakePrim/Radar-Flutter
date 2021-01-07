package com.prim.test.dao;

import com.jakeprim.dao.CourseDao;
import com.jakeprim.dao.impl.CourseDaoImpl;
import com.jakeprim.pojo.Course;
import com.jakeprim.utils.DateUtils;
import org.junit.Test;

import java.util.List;

public class TestCourse {
    CourseDao courseDao = new CourseDaoImpl();

    @Test
    public void findCourseList() {
        List<Course> courseList = courseDao.findCourseList();
        for (Course course : courseList) {
            System.out.println(course);
        }
    }

    //测试课程条件查询
    @Test
    public void findByCourseNameAndStatus() {
        List<Course> courseList = courseDao.findByCourseNameAndStatus("", "");
        for (Course course : courseList) {
            System.out.println(course.getId() + ":" + course.getCourse_name() + ":" + course.getStatus());
        }
    }

    //保存课程营销信息
    @Test
    public void saveCourseSalesInfo() {
        Course course = new Course();
        course.setCourse_name("爱情三十六计");
        course.setBrief("学会去找对象");
        course.setTeacher_name("药水哥");
        course.setTeacher_info("人人都是药水哥");
        course.setPreview_first_field("共10讲");
        course.setPreview_second_field("每周日更新");
        course.setDiscounts(88.88);
        course.setPrice(188.0);
        course.setPrice_tag("原价");
        course.setShare_image_title("哈哈");
        course.setShare_title("嘻嘻");
        course.setShare_description("分享的描述");
        course.setCourse_description("课程的详情");
        course.setCourse_img_url("https://www.baidu.com/xxx.jpg");
        course.setStatus(1);
        String dateFormart = DateUtils.getDateFormart();
        course.setCreate_time(dateFormart);
        course.setUpdate_time(dateFormart);
        int row = courseDao.saveCourseSalesInfo(course);
        System.out.println("row:" + row);
    }
}
