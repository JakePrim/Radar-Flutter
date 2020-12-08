package project.client;

import com.sun.security.ntlm.Server;
import project.client.model.*;
import project.server.dao.ServerDao;

import java.io.*;
import java.util.List;

/**
 * 客户端主界面
 */
public class ClientView {
    private ClientInitClose cic;

    //合成复用原则
    public ClientView(ClientInitClose cic) {
        this.cic = cic;
    }

    public void clientMainPage() throws IOException, ClassNotFoundException {
        //导入学员信息 考题信息
        importData();
        while (true) {
            System.out.println("  \n\n\t在线考试系统");
            System.out.println("----------------------------------------------");
            System.out.print("   [1] 学员系统");
            System.out.println("    [2] 管理员系统");
            System.out.println("   [0] 退出系统");
            System.out.println("请选择要进行的业务编号:");
            int choose = ClientScanner.getScanner().nextInt();
            switch (choose) {
                case 1:
                    System.out.println("正在进入学员系统");
                    clientStudentLogin();
                    break;
                case 2:
                    System.out.println("正在进入管理员员系统");
                    clientManagerLogin();
                    break;
                case 0:
                    System.out.println("正在退出系统");
                    UserMessage tum = new UserMessage("exit", null);
                    //2. 将tum对象发送给服务器
                    cic.getOos().writeObject(tum);
                    return;
                default:
                    System.out.println("输入错误，请重新选择");
                    break;
            }
        }
    }

    /**
     * 导入
     */
    private void importData() {
        ObjectInputStream ois = null;
        ObjectInputStream ois2 = null;
        ObjectInputStream ois3 = null;
        try {
            File studentFile = new File(ServerDao.studentPath);
            if (studentFile.exists()) {
                ois = new ObjectInputStream(new FileInputStream(ServerDao.studentPath));
                List<Student> studentList = (List<Student>) ois.readObject();
                //
                UserMessage<List<Student>> message = new UserMessage<>("import-student", studentList);
                cic.getOos().writeObject(message);

                message = (UserMessage<List<Student>>) cic.getOis().readObject();
                if ("success".equals(message.getType())) {
                    System.out.println("导入学生数据成功");
                }
            }
            File examFile = new File(ServerDao.examPath);
            if (examFile.exists()) {
                ois2 = new ObjectInputStream(new FileInputStream(ServerDao.examPath));
                List<Exam> exams = (List<Exam>) ois2.readObject();
                UserMessage<List<Exam>> message1 = new UserMessage<>("import-exam", exams);
                cic.getOos().writeObject(message1);

                message1 = (UserMessage<List<Exam>>) cic.getOis().readObject();
                System.out.println(message1);
                if ("success".equals(message1.getType())) {
                    System.out.println("导入考题数据成功");
                }
            }

            File gradeFile = new File(ServerDao.gradePath);
            if (gradeFile.exists()) {
                ois3 = new ObjectInputStream(new FileInputStream(ServerDao.gradePath));
                List<Grade> grades = (List<Grade>) ois3.readObject();
                UserMessage<List<Grade>> message2 = new UserMessage<>("import-grade", grades);
                cic.getOos().writeObject(message2);
                message2 = (UserMessage<List<Grade>>) cic.getOis().readObject();
                System.out.println(message2);
                if ("success".equals(message2.getType())) {
                    System.out.println("导入成绩数据成功");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (null != ois) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != ois2) {
                try {
                    ois2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 学员登录
     */
    private void clientStudentLogin() throws IOException, ClassNotFoundException {
        System.out.println("请输入学员账户信息:");
        String name = ClientScanner.getScanner().next();
        System.out.println("请输入学员密码信息:");
        String password = ClientScanner.getScanner().next();

        UserMessage<User> tum = new UserMessage<>("studentCheck", new User(name, password));
        cic.getOos().writeObject(tum);
        UserMessage<Student> loginStudent = (UserMessage<Student>) cic.getOis().readObject();
        if ("success".equals(loginStudent.getType())) {
            System.out.println("登录成功，欢迎使用");
            StudentView studentView = new StudentView(cic, loginStudent.getContent());
            studentView.accessStudentSystem();
        } else {
            System.out.println("用户名或密码错误");
        }
    }

    /**
     * 管理员登录
     */
    private void clientManagerLogin() throws IOException, ClassNotFoundException {
        //1. 准备管理员相关的数据
        System.out.println("请输入管理员的账户信息:");
        String userName = ClientScanner.getScanner().next();
        System.out.println("请输入管理员的密码信息：");
        String password = ClientScanner.getScanner().next();
        //创建消息体对象
        UserMessage tum = new UserMessage("managerCheck", new User(userName, password));
        //2. 将tum对象发送给服务器
        cic.getOos().writeObject(tum);
        System.out.println("客户端发送管理员账户信息成功!");
        //3. 接收服务器的结果
        tum = (UserMessage) cic.getOis().readObject();
        if ("success".equals(tum.getType())) {
            System.out.println("登录成功，欢迎使用!");
            ManagerView managerView = new ManagerView(cic);
            managerView.accessManagerSystem();
        } else {
            System.out.println("用户名或密码错误");
        }
    }
}
