import com.sfl.mapper.CourseDao;
import com.sfl.mapper.UserDao;
import com.sfl.pojo.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @program: Edu
 * @Description: 测试用户相关
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-04 16:09
 * @PackageName: PACKAGE_NAME
 * @ClassName: UserTest.java
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-dao.xml"})
public class UserTest {
    @Autowired
    private UserDao userDao;

    @Autowired
    private CourseDao courseDao;

    @Test
    public void testLogin() {
//        User user = userDao.login("110", "123");
//        System.out.println(user);
        List<Course> courses = courseDao.queryAll();
        Course course = courses.get(0);
        System.out.println("课程:" + course.getCourseName() + " id:" + course.getId());
        System.out.println("teacherId:" + course.getTeacher().getId() + " name:" + course.getTeacher().getTeacherName());
        CourseSection section = course.getCourseSectionList().get(0);
        System.out.println("第一章信息" + section.getSectionName());
        for (CourseLesson lesson : section.getCourseLessonList()) {
            System.out.println("课时:" + lesson.getTheme());
            CourseMedia courseMedia = lesson.getCourseMedia();
            System.out.println(courseMedia);
            if (courseMedia != null) {
                System.out.println("媒体信息：" + courseMedia.getFileName());
            }
        }
    }

    @Test
    public void testRegister() {
//        Integer register = userDao.register("114", "123");
//        System.out.println(register);
//        Integer integer = userDao.checkPhone("110");
//        System.out.println("int:" + integer);
    }
}
