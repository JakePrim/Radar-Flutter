import com.homework.homeword01.pojo.Admin;
import com.homework.homeword01.pojo.Student;
import com.homework.homeword01.service.AdminService;
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
}
