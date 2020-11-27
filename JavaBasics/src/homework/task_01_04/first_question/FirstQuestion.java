package homework.task_01_04.first_question;

import java.util.LinkedList;
import java.util.List;

/**
 * 使用 List 集合实现简易的学生信息管理系统，要求打印字符界面提示用户选择相应的功 能，
 * 根据用户输入的选择去实现增加、删除、修改、查找以及遍历所有学生信息的功能。
 * <p>
 * 其中学生的信息有：学号、姓名、年龄。 要求： 尽量将功能拆分为多个.java 文件。
 */
public class FirstQuestion {
    public static void main(String[] args) {
        //定义学生信息集合
        List<Student> students = new LinkedList<>();
        IStudentService<Student> studentService = new StudentService(students);
        Console console = new Console(studentService);
        console.start();
    }
}
