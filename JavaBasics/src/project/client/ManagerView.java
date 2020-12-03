package project.client;

import project.client.model.Exam;
import project.client.model.Student;
import project.client.model.UserMessage;
import project.server.dao.ServerDao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 管理员系统主界面
 */
public class ManagerView {
    private ClientInitClose cic;

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    /**
     * 和服务器对应的信息码
     */
    public static final String QUERY_STUDENT = "manager-query";
    public static final String ADD_STUDENT = "manager-add";
    public static final String UPDATE_STUDENT = "manager-update";
    public static final String DELETE_STUDENT = "manager-delete";

    public static final String ADD_EXAM = "exam-add";
    public static final String QUERY_EXAM = "exam-query";
    public static final String UPDATE_EXAM = "exam-update";
    public static final String DELETE_EXAM = "exam-delete";

    //合成复用原则
    public ManagerView(ClientInitClose cic) {
        this.cic = cic;
    }

    /**
     * 进入管理员系统
     * //学员管理模块
     * //考题管理模块
     */
    public void accessManagerSystem() throws IOException, ClassNotFoundException {
        while (true) {
            System.out.println("  \n\n\t管理员系统");
            System.out.println("----------------------------------------------");
            System.out.print("   [1] 学员管理");
            System.out.println("    [2] 考题管理");
            System.out.println("   [0] 返回");
            System.out.println("请选择要进行的业务编号:");
            int choose = ClientScanner.getScanner().nextInt();
            switch (choose) {
                case 1:
                    studentManager();
                    break;
                case 2:
                    examinationQuestionsManager();
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
     * 考题管理
     */
    private void examinationQuestionsManager() throws IOException, ClassNotFoundException {
        while (true) {
            System.out.println("  \n\n\t考题管理");
            System.out.println("----------------------------------------------");
            System.out.print("    [1] 增加考题");
            System.out.print("    [2] 修改考题");
            System.out.print("    [3] 删除考题");
            System.out.print("    [4] 查询考题");
            System.out.print("    [5] 导入考题");
            System.out.println("    [0] 返回");
            System.out.println("请选择要进行的业务编号:");
            int choose = ClientScanner.getScanner().nextInt();
            switch (choose) {
                case 1:
                    addExam();
                    break;
                case 2:
                    updateExam();
                    break;
                case 3:
                    deleteExam();
                    break;
                case 4:
                    queryExam();
                    break;
                case 5:
                    importExam();
                    break;
                case 0:
                    System.out.println("返回上一页");
                    return;
                default:
                    System.out.println("编号输入错误，请重新输入");
                    break;
            }
        }
    }

    private void importExam() {
        System.out.println("请输入考题文件的绝对路径：");
        String path = ClientScanner.getScanner().next();
        ObjectInputStream ois2 = null;
        try {
            ois2 = new ObjectInputStream(new FileInputStream(path));
            List<Exam> exams = (List<Exam>) ois2.readObject();
            UserMessage<List<Exam>> message1 = new UserMessage<>("import-exam", exams);
            cic.getOos().writeObject(message1);

            message1 = (UserMessage<List<Exam>>) cic.getOis().readObject();
            System.out.println(message1);
            if ("success".equals(message1.getType())) {
                System.out.println("导入考题数据成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (null != ois2) {
                try {
                    ois2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void queryExam() throws IOException, ClassNotFoundException {
        System.out.println("请输入考题ID：");
        int id = ClientScanner.getScanner().nextInt();
        UserMessage<Integer> tum = new UserMessage<>(QUERY_EXAM, id);
        cic.getOos().writeObject(tum);
        UserMessage<Exam> result = (UserMessage<Exam>) cic.getOis().readObject();
        if (SUCCESS.equals(result.getType())) {
            System.out.println("查询成功:" + result.getContent());
        } else {
            System.out.println("没有该考题信息");
        }
    }

    private void deleteExam() throws IOException, ClassNotFoundException {
        System.out.println("请输入考题ID：");
        int id = ClientScanner.getScanner().nextInt();
        UserMessage<Integer> tum = new UserMessage<>(QUERY_EXAM, id);
        cic.getOos().writeObject(tum);
        tum = (UserMessage<Integer>) cic.getOis().readObject();
        if (SUCCESS.equals(tum.getType())) {
            System.out.println("删除考题成功");
        } else {
            System.out.println("删除考题失败");
        }
    }

    private void updateExam() throws IOException, ClassNotFoundException {
        System.out.println("请输入考题ID：");
        int id = ClientScanner.getScanner().nextInt();
        UserMessage<Integer> tum = new UserMessage<>(QUERY_EXAM, id);
        cic.getOos().writeObject(tum);
        UserMessage<Exam> result = (UserMessage<Exam>) cic.getOis().readObject();
        if (SUCCESS.equals(result.getType())) {
            Exam content = result.getContent();
            while (true) {
                System.out.println("修改考题");
                System.out.println("----------------------------------------------");
                System.out.print("    [1] 修改选项");
                System.out.print("    [2] 修改答案");
                System.out.print("    [3] 修改题目");
                System.out.println("    [4] 修改完成");
                System.out.println("请选择要进行的业务编号:");
                int choose = ClientScanner.getScanner().nextInt();
                switch (choose) {
                    case 1:
                        System.out.println("请输入选项1~4:");
                        int index = ClientScanner.getScanner().nextInt();
                        if (index <= 0 || index > 4) {
                            System.out.println("只能输入1~4请重新输入");
                        } else {
//                            String cur = content.getSelect().get(index - 1);
                            System.out.println("请输入该选项的信息:");
                            String newSelect = ClientScanner.getScanner().next();
                            if (null != newSelect && !"".equals(newSelect)) {
                                content.getSelect().set((index - 1), newSelect);
                            } else {
                                System.out.println("不能输入空字符串，请重新输入");
                            }
                        }
                        break;
                    case 2:
                        System.out.println("请输入答案1~4:");
                        int i = ClientScanner.getScanner().nextInt();
                        if (i <= 0 || i > 4) {
                            System.out.println("只能输入1~4请重新输入");
                        } else {
                            content.setCorrectResponse(i);
                        }
                        break;
                    case 3:
                        System.out.println("请输入题目:");
                        String name = ClientScanner.getScanner().next();
                        content.setSubject(name);
                        break;
                    case 4:
                        UserMessage<Exam> newTum = new UserMessage<>(UPDATE_EXAM, content);
                        cic.getOos().writeObject(newTum);
                        newTum = (UserMessage<Exam>) cic.getOis().readObject();
                        if (SUCCESS.equals(newTum.getType())) {
                            System.out.println("考题信息修改成功");
                        } else {
                            System.out.println("考题信息修改失败");
                        }
                        return;
                }
            }
        } else {
            System.out.println("没有该考题信息,无法修改");
        }
    }

    private void addExam() throws IOException, ClassNotFoundException {
        List<String> selectList = new ArrayList<>();
        System.out.println("请输入考题ID：");
        int id = ClientScanner.getScanner().nextInt();
        System.out.println("请输入考题题目:");
        String name = ClientScanner.getScanner().next();
        System.out.println("请输入考题选项1:");
        String select1 = ClientScanner.getScanner().next();
        selectList.add(select1);
        System.out.println("请输入考题选项2:");
        String select2 = ClientScanner.getScanner().next();
        selectList.add(select2);
        System.out.println("请输入考题选项3:");
        String select3 = ClientScanner.getScanner().next();
        selectList.add(select3);
        System.out.println("请输入考题选项4:");
        String select4 = ClientScanner.getScanner().next();
        selectList.add(select4);
        System.out.println("请输入正确的选项1~4:");
        int enterSelect = ClientScanner.getScanner().nextInt();
        if (enterSelect <= 0 || enterSelect > 4) {
            System.out.println("只能输入1~4请重新输入");
        } else {
            Exam exam = new Exam(id, selectList, name, enterSelect);
            UserMessage<Exam> tum = new UserMessage<>(ADD_EXAM, exam);
            cic.getOos().writeObject(tum);
            //接收服务端返回来的信息
            tum = (UserMessage<Exam>) cic.getOis().readObject();
            if (SUCCESS.equals(tum.getType())) {
                System.out.println("添加考题成功");
            } else {
                System.out.println("添加考题失败");
            }
        }
    }

    /**
     * 学员管理
     */
    private void studentManager() throws IOException, ClassNotFoundException {
        while (true) {
            System.out.println("  \n\n\t学员管理");
            System.out.println("----------------------------------------------");
            System.out.print("    [1] 添加学员");
            System.out.print("    [2] 修改学员");
            System.out.print("    [3] 删除学员");
            System.out.print("    [4] 查询学员");
            System.out.println("    [0] 返回");
            System.out.println("请选择要进行的业务编号:");
            int choose = ClientScanner.getScanner().nextInt();
            switch (choose) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    updateStudent();
                    break;
                case 3:
                    deleteStudent();
                    break;
                case 4:
                    queryStudent();
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
     * 删除学员
     */
    private void deleteStudent() throws IOException, ClassNotFoundException {
        System.out.println("请输入要删除学员的学号:");
        int id = ClientScanner.getScanner().nextInt();
        UserMessage<Integer> tum = new UserMessage<>(DELETE_STUDENT, id);
        cic.getOos().writeObject(tum);
        tum = (UserMessage<Integer>) cic.getOis().readObject();
        if (SUCCESS.equals(tum.getType())) {
            System.out.println("删除成功");
        } else if (FAIL.equals(tum.getType())) {
            System.out.println("删除失败");
        }
    }

    /**
     * 查询学员
     */
    private void queryStudent() throws IOException, ClassNotFoundException {
        System.out.println("请输入要查询学员的学号:");
        int id = ClientScanner.getScanner().nextInt();
        UserMessage<Integer> tum = new UserMessage<>(QUERY_STUDENT, id);
        cic.getOos().writeObject(tum);
        //接收服务器返回的信息
        UserMessage<Student> result = (UserMessage<Student>) cic.getOis().readObject();
        if (SUCCESS.equals(result.getType())) {
            System.out.println("查询成功:" + result.getContent());
        } else if (FAIL.equals(result.getType())) {
            System.out.println("查询失败");
        }
    }

    /**
     * 修改学员信息
     */
    private void updateStudent() throws IOException, ClassNotFoundException {
        System.out.println("请输入要修改学员的学号:");
        int id = ClientScanner.getScanner().nextInt();
        //首先查询是否存在该学员
        UserMessage<Integer> tum = new UserMessage<>(QUERY_STUDENT, id);
        cic.getOos().writeObject(tum);
        //接收服务器返回的信息
        UserMessage<Student> result = (UserMessage<Student>) cic.getOis().readObject();
        if (SUCCESS.equals(result.getType())) {
            Student content = result.getContent();
            System.out.println("查询成功:" + content);
            while (true) {
                System.out.println("请输入要修改学生信息编号:");
                System.out.println("----------------------------------------------");
                System.out.print("    [1] 修改学号");
                System.out.print("    [2] 修改姓名");
                System.out.print("    [3] 修改密码");
                System.out.println("    [4] 修改完成");
                System.out.println("请选择要进行的业务编号:");
                int choose = ClientScanner.getScanner().nextInt();
                switch (choose) {
                    case 1:
                        System.out.println("请输入要修改的学号:");
                        int sid = ClientScanner.getScanner().nextInt();
                        content.setsId(sid);
                        break;
                    case 2:
                        System.out.println("请输入要修改的姓名:");
                        String name = ClientScanner.getScanner().next();
                        content.setName(name);
                        break;
                    case 3:
                        System.out.println("请输入要修改的密码:");
                        String password = ClientScanner.getScanner().next();
                        content.setPassword(password);
                        break;
                    case 4:
                        UserMessage<Student> newStudent = new UserMessage<>(UPDATE_STUDENT, content);
                        cic.getOos().writeObject(newStudent);
                        //接收服务器返回的信息
                        UserMessage<Student> res = (UserMessage<Student>) cic.getOis().readObject();
                        if (SUCCESS.equals(res.getType())) {
                            System.out.println("修改信息成功");
                        } else if (FAIL.equals(res.getType())) {
                            System.out.println("修改信息失败");
                        }
                        return;
                }
            }
        } else if (FAIL.equals(result.getType())) {
            System.out.println("查询失败，没有此学员无法进行修改操作，请重新输入");
        }
    }

    /**
     * 添加学员信息
     */
    private void addStudent() throws IOException, ClassNotFoundException {
        System.out.println("请输入学员学号:");
        int id = ClientScanner.getScanner().nextInt();
        System.out.println("请输入学员姓名:");
        String name = ClientScanner.getScanner().next();
        System.out.println("请输入学员密码:");
        String password = ClientScanner.getScanner().next();
        UserMessage<Student> tum = new UserMessage<>(ADD_STUDENT,
                new Student(id, name, password, null));
        //发送给服务器
        cic.getOos().writeObject(tum);
        //发送服务器成功
        //接收服务器的结果
        tum = (UserMessage<Student>) cic.getOis().readObject();
        if (SUCCESS.equals(tum.getType())) {
            System.out.println("添加学员信息成功!");
        } else {
            System.out.println("添加学员信息失败");
        }
    }
}
