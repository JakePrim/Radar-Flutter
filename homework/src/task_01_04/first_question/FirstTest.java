package task_01_04.first_question;

import java.util.LinkedList;
import java.util.List;

/**
 * 使用 List 集合实现简易的学生信息管理系统，要求打印字符界面提示用户选择相应的功 能，
 * 根据用户输入的选择去实现增加、删除、修改、查找以及遍历所有学生信息的功能。
 * <p>
 * 其中学生的信息有：学号、姓名、年龄。 要求： 尽量将功能拆分为多个.java 文件。
 * <p>
 * 基于学生信息管理系统增加以下两个功能：
 * <p>
 * a.自定义学号异常类和年龄异常类，并在该成员变量不合理时产生异常对象并抛出。
 * <p>
 * b.当系统退出时将 List 集合中所有学生信息写入到文件中，当系统启动时读取文件中所 有学生信息到 List 集合中。
 */
public class FirstTest {
    public static void main(String[] args) {
        //定义学生信息集合
        List<Student> students = new LinkedList<>();
        IStudentService<Student> studentService = new StudentService(students);
        Console console = new Console(studentService);
        console.start();
    }
}
