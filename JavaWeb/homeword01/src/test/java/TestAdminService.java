import com.homework.homeword01.dao.impl.StudentDaoImpl;
import com.homework.homeword01.pojo.Admin;
import com.homework.homeword01.pojo.PageHelp;
import com.homework.homeword01.pojo.SClass;
import com.homework.homeword01.pojo.Student;
import com.homework.homeword01.service.AdminService;
import com.homework.homeword01.service.SClassService;
import com.homework.homeword01.service.StudentService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class TestAdminService {
    @Test
    public void login() {
        AdminService adminService = new AdminService();
        Admin admin = adminService.adminLogin(new Admin("admin", "123456"));
        System.out.println(admin);
    }

    @Test
    public void student() {
        StudentService service = new StudentService();
//        List<Student> all = service.findById(1);
//        for (Student student : all) {
//            System.out.println(student);
//        }
    }

    @Test
    public void findAllClass() {
        SClassService sClassService = new SClassService();
        List<SClass> all = sClassService.findAll();
        for (SClass sClass : all) {
            System.out.println(sClass);
        }
    }

    @Test
    public void findPage() {
        StudentDaoImpl studentDao = new StudentDaoImpl();
        PageHelp<Student> page = studentDao.findPage(1, 2);
        System.out.println(page);
        List items = page.getItems();
        for (Object item : items) {
            System.out.println(item);
        }
        page = studentDao.findPage(2, 2);
        System.out.println(page);
        items = page.getItems();
        for (Object item : items) {
            System.out.println(item);
        }
    }
}
