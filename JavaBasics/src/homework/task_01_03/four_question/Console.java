package homework.task_01_03.four_question;

import java.util.List;
import java.util.Scanner;

import static homework.task_01_03.four_question.COMMAND.*;

/**
 * 控制台打印
 */
public class Console {
    private IStudentService<Student> studentService;

    private boolean isPlay = true;

    public Console(IStudentService<Student> studentService) {
        this.studentService = studentService;
    }

    public void start() {
        System.out.println("学生信息管理系统:");
        while (isPlay) {
            System.out.println(EXIT.getCommand() + ":退出 "
                    + ADD.getCommand() + "：添加学生信息 "
                    + REMOVE.getCommand() + "：删除学生信息 "
                    + UPDATE.getCommand() + "：修改学生信息 "
                    + FIND.getCommand() + "：查找学生信息 "
                    + FIND_ALL.getCommand() + "：查看所有学生信息");
            Scanner sc = new Scanner(System.in);
            String tab = sc.nextLine();
            COMMAND command = fromCommand(tab);
            if (null == command) {
                System.out.println("没有找到该命令");
                return;
            }
            switch (command) {
                case ADD:
                    add();
                    break;
                case REMOVE:
                    remove();
                    break;
                case UPDATE:
                    update();
                    break;
                case FIND:
                    find();
                    break;
                case FIND_ALL:
                    findAll();
                    break;
                case EXIT:
                    System.out.println("已退出系统!");
                    isPlay = false;
                    break;
                default:
                    System.out.println("输入错误，没有该选项，请重新输入");
                    break;
            }
        }
    }

    private void update() {
        System.out.println("请输入要修改学生学号:");
        Scanner sc1 = new Scanner(System.in);
        String number = sc1.nextLine();
        Student student = studentService.find(Integer.parseInt(number));
        if (null == student) {
            System.out.println("没有该学生，请重新输入：");
            update();
        } else {
            modify(student);
        }
    }

    private void modify(Student student) {
        System.out.println("请输入命令 -> 1：修改学生年龄  2：修改学生姓名 3：修改学生学号 0: 修改完成");
        Scanner sc = new Scanner(System.in);
        String num = sc.nextLine();
        switch (num) {
            case "1":
                System.out.println("请输入要修改学生的年龄：");
                Scanner sc2 = new Scanner(System.in);
                String age = sc2.nextLine();
                student.setAge(Integer.parseInt(age));
                modify(student);
                break;
            case "2":
                System.out.println("请输入要修改学生的姓名：");
                Scanner sc3 = new Scanner(System.in);
                String name = sc3.nextLine();
                student.setName(name);
                modify(student);
                break;
            case "3":
                System.out.println("请输入要修改学生的学号：");
                Scanner sc4 = new Scanner(System.in);
                String sno = sc4.nextLine();
                student.setNumber(Integer.parseInt(sno));
                modify(student);
                break;
            case "0":
                studentService.update(student);
                System.out.println("修改信息完成!");
                break;
        }
    }

    private void find() {
        System.out.println("请输入要查询学生的学号:");
        Scanner sc1 = new Scanner(System.in);
        String number = sc1.nextLine();
        try {
            Student student = studentService.find(Integer.parseInt(number));
            System.out.println("查询成功:" + student);
        } catch (NumberFormatException e) {
            System.out.println("请输入整数类型");
        }
    }

    private void findAll() {
        System.out.println("查询成功：");
        List<Student> all = studentService.findAll();
        System.out.println(all);
    }

    private void remove() {
        System.out.println("请输入学生学号，删除:");
        Scanner sc1 = new Scanner(System.in);
        String number = sc1.nextLine();
        try {
            if (studentService.remove(Integer.parseInt(number))) {
                System.out.println("删除完成!");
            } else {
                System.out.println("删除失败或没有该学号");
            }
        } catch (NumberFormatException e) {
            System.out.println("请输入数字类型");
        }
    }

    private void add() {
        System.out.println("请输入学生学号：");
        Scanner sc1 = new Scanner(System.in);
        String number = sc1.nextLine();
        System.out.println("请输入学生年龄：");
        Scanner sc2 = new Scanner(System.in);
        String age = sc2.nextLine();
        System.out.println("请输入学生的姓名：");
        Scanner sc3 = new Scanner(System.in);
        String name = sc3.nextLine();
        try {
            Student student = new Student(name, Integer.parseInt(age), Integer.parseInt(number));
            studentService.add(student);
            System.out.println("添加完成!");
        } catch (NumberFormatException e) {
            System.out.println("年龄和学号请输入数字");
        }
    }
}
