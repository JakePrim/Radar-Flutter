package project.client;

import project.client.model.*;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static jdk.nashorn.tools.Shell.SUCCESS;
import static project.client.ManagerView.UPDATE_STUDENT;

/**
 * 学员系统界面
 */
public class StudentView {
    private ClientInitClose cic;

    /**
     * 当前登录的用户
     */
    private Student loginStudent;

    public StudentView(ClientInitClose cic, Student loginStudent) {
        this.cic = cic;
        this.loginStudent = loginStudent;
    }

    public void accessStudentSystem() throws IOException, ClassNotFoundException {
        while (true) {
            System.out.println("  \n\n\t学员系统");
            System.out.println("----------------------------------------------");
            System.out.print("   [1] 用户模块");
            System.out.println("    [2] 考试模块");
            System.out.println("   [0] 返回");
            System.out.println("请选择要进行的业务编号:");
            int choose = ClientScanner.getScanner().nextInt();
            switch (choose) {
                case 1:
                    userModule();
                    break;
                case 2:
                    examModule();
                    break;
                case 0:
                    System.out.println("返回上一页");
                    return;
                default:
                    System.out.println("输入编号错误请重新输入");
                    break;
            }
        }
    }

    /**
     * 考试模块
     */
    private void examModule() throws IOException, ClassNotFoundException {
        if (loginStudent == null) {
            System.out.println("当前学员未登录，请登录");
            login();
            return;
        }
        while (true) {
            System.out.println("  \n\n\t考试模块");
            System.out.println("----------------------------------------------");
            System.out.print("    [1] 开始考试");
            System.out.print("    [2] 导出成绩");
            System.out.print("    [3] 查询成绩");
            System.out.println("    [0] 返回");
            System.out.println("请选择要进行的业务编号:");
            int choose = ClientScanner.getScanner().nextInt();
            switch (choose) {
                case 1:
                    startExam();
                    break;
                case 2:
                    importGrade();
                    break;
                case 3:
                    queryGrade();
                    break;
                case 0:
                    System.out.println("返回上一页");
                    return;
            }
        }
    }

    private void queryGrade() throws IOException, ClassNotFoundException {
        System.out.println("请输入学员编号:");
        int id = ClientScanner.getScanner().nextInt();
        UserMessage<Integer> tum = new UserMessage<>("queryGrade", id);
        cic.getOos().writeObject(tum);
        UserMessage<List<Grade>> grades = (UserMessage<List<Grade>>) cic.getOis().readObject();
        if ("success".equals(grades.getType())) {
            System.out.println("查询成功");
            System.out.println(grades.getContent());
        } else {
            System.out.println("查询失败");
        }
    }

    /**
     * 导出成绩
     */
    private void importGrade() throws IOException, ClassNotFoundException {
        System.out.println("正在导出成绩....");
        UserMessage<String> tum = new UserMessage<>("importGrade", "");
        cic.getOos().writeObject(tum);
        tum = (UserMessage<String>) cic.getOis().readObject();
        if ("success".equals(tum.getType())) {
            System.out.println("导出成绩表成功:" + tum.getContent());
        } else {
            System.out.println("导出成绩表失败");
        }
    }

    private Random random = new Random();

    /**
     * 开始考试
     */
    private void startExam() throws IOException, ClassNotFoundException {
        System.out.println("正在加载考题....");
        UserMessage<List<Exam>> tum = new UserMessage<>("loadExam", null);
        cic.getOos().writeObject(tum);
        //接收服务器返回的考题列表并显示
        tum = (UserMessage<List<Exam>>) cic.getOis().readObject();
        List<Exam> examList = tum.getContent();
        //每道题的分数计算
        float score = 100f / examList.size();
        float sumScore = 0f;
        for (Exam exam : examList) {
            System.out.println("题目 " + exam.getSubject() + "[每道题分数:" + score + "分]：");
            for (int i = 0; i < exam.getSelect().size(); i++) {
                System.out.println("选项" + (i+1) + " : " + exam.getSelect().get(i));
            }
            System.out.print("请输入选项:");
            int choose = ClientScanner.getScanner().nextInt();
            exam.setChoose(choose);
            //记录分数
            if (choose == exam.getCorrectResponse()) {
                sumScore += score;
            }
            System.out.println();
        }
        System.out.println("考试完毕");
        //存储成绩
        Grade grade = new Grade(random.nextInt(10000), loginStudent.getsId(), sumScore);
        grade.setExamList(examList);
        UserMessage<Grade> gradeUserMessage = new UserMessage<>("saveGrade", grade);
        cic.getOos().writeObject(gradeUserMessage);
        gradeUserMessage = (UserMessage<Grade>) cic.getOis().readObject();
        if ("success".equals(gradeUserMessage.getType())) {
            System.out.println("成绩保存成功");
        } else {
            System.out.println("成绩保存失败，请重新考试");
        }
    }

    /**
     * 学员用户模块
     */
    private void userModule() throws IOException, ClassNotFoundException {
        while (true) {
            System.out.println("  \n\n\t用户模块");
            System.out.println("----------------------------------------------");
            System.out.print("    [1] 登录");
            System.out.print("    [2] 退出");
            System.out.print("    [3] 修改密码");
            System.out.println("    [0] 返回");
            System.out.println("请选择要进行的业务编号:");
            int choose = ClientScanner.getScanner().nextInt();
            switch (choose) {
                case 1:
                    login();
                    break;
                case 2:
                    exit();
                    break;
                case 3:
                    updatePassword();
                    break;
                case 0:
                    System.out.println("返回上一页");
                    return;
            }
        }
    }

    private void updatePassword() throws IOException, ClassNotFoundException {
        if (loginStudent == null) {
            System.out.println("当前没有登录，请登录");
            return;
        }
        System.out.println("请输入要修改的密码:");
        String password = ClientScanner.getScanner().next();
        loginStudent.setPassword(password);
        UserMessage<Student> newStudent = new UserMessage<>(UPDATE_STUDENT, loginStudent);
        cic.getOos().writeObject(newStudent);
        //接收服务器返回的信息
        UserMessage<Student> res = (UserMessage<Student>) cic.getOis().readObject();
        if ("success".equals(res.getType())) {
            System.out.println("修改信息成功");
        } else if ("fail".equals(res.getType())) {
            System.out.println("修改信息失败");
        }
    }

    private void exit() {
        if (loginStudent != null) {
            loginStudent = null;
            System.out.println("退出成功");
        } else {
            System.out.println("当前学员已经退出");
        }
    }

    private void login() throws IOException, ClassNotFoundException {
        if (loginStudent != null) {
            System.out.println("当前已经登录：" + loginStudent);
            return;
        }
        System.out.println("请输入学员名信息:");
        String name = ClientScanner.getScanner().next();
        System.out.println("请输入学员密码信息:");
        String password = ClientScanner.getScanner().next();
        UserMessage<User> tum = new UserMessage<>("studentCheck", new User(name, password));
        cic.getOos().writeObject(tum);
        UserMessage<Student> loginStudent = (UserMessage<Student>) cic.getOis().readObject();
        if ("success".equals(loginStudent.getType())) {
            System.out.println("登录成功，欢迎使用");
            this.loginStudent = loginStudent.getContent();
        } else {
            System.out.println("用户名或密码错误");
        }
    }
}
