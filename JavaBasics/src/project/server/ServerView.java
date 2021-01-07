package project.server;

import project.client.ManagerView;
import project.client.model.*;
import project.server.dao.ServerDao;
import project.server.service.IGradeService;
import project.server.service.impl.ExamService;
import project.server.service.IExamService;
import project.server.service.IStudentService;
import project.server.service.impl.GradeService;
import project.server.service.impl.StudentService;

import java.io.EOFException;
import java.io.IOException;
import java.util.List;

/**
 * 服务器的主功能
 */
public class ServerView {
    private ServerInitClose sic;

    private ServerDao serverDao;

    private IStudentService studentService;
    private IExamService examService;
    private IGradeService gradeService;

    public ServerView(ServerInitClose sic, ServerDao serverDao) {
        this.sic = sic;
        this.serverDao = serverDao;
        studentService = new StudentService(serverDao);
        examService = new ExamService(serverDao);
        gradeService = new GradeService(serverDao);
    }

    /**
     * 接收客户端发来的消息并进行处理
     * 服务器需要处理多个客户端发来的消息
     */
    public void serverReceive() throws IOException, ClassNotFoundException {
        while (true) {//循环接受客户端发送过来的消息
            try {
                UserMessage tum = (UserMessage) sic.getOis().readObject();
                String type = tum.getType();
                //
                StringBuilder sb = new StringBuilder("服务端接收到客户端" + sic.getSocket().getInetAddress() + "的");
                sb.append("\"");
                sb.append(type).append("\"请求,");
                sb.append("请求数据为\"");
                sb.append(tum).append("\"");
                switch (type) {
                    case "exit":
                        serverDao.save();
                        System.out.println("客户端关闭，服务端进行保存...");
                        return;
                    case "import-student":
                        importStudent(tum, sb);
                        break;
                    case "import-exam":
                        importExam(tum, sb);
                        break;
                    case "import-grade":
                        importGrade(tum, sb);
                        break;
                    case "managerCheck":
                        checkAdminInfo(tum, sb);
                        break;
                    case "studentCheck":
                        checkStudentInfo(tum, sb);
                        break;
                    case ManagerView.ADD_STUDENT:
                        addStudent(tum, sb);
                        break;
                    case ManagerView.QUERY_STUDENT:
                        queryStudent(tum, sb);
                        break;
                    case ManagerView.UPDATE_STUDENT:
                        updateStudent(tum, sb);
                        break;
                    case ManagerView.DELETE_STUDENT:
                        deleteStudent(tum, sb);
                        break;
                    case ManagerView.ADD_EXAM:
                        addExam(tum, sb);
                        break;
                    case ManagerView.QUERY_EXAM:
                        queryExam(tum, sb);
                        break;
                    case ManagerView.UPDATE_EXAM:
                        updateExam(tum, sb);
                        break;
                    case ManagerView.DELETE_EXAM:
                        deleteExam(tum, sb);
                        break;
                    case "loadExam":
                        loadExam(tum, sb);
                        break;
                    case "saveGrade":
                        saveGrade(tum, sb);
                        break;
                    case "importGrade":
                        exportGrade(tum, sb);
                        break;
                    case "queryGrade":
                        queryGrade(tum, sb);
                        break;
                }
            } catch (EOFException e) {
                System.out.println("客户端" + sic.getSocket().getInetAddress() + "退出系统");
            }
        }
    }

    /**
     * 查询学员成绩
     *
     * @param tum
     * @param sb
     */
    private void queryGrade(UserMessage tum, StringBuilder sb) throws IOException {
        sb.append("调用\"queryGrade\"方法,");
        Integer sId = (Integer) tum.getContent();
        List<Grade> grade = gradeService.query(sId);
        UserMessage<List<Grade>> userMessage = new UserMessage<>("", grade);
        if (grade != null && grade.size() > 0) {
            sb.append("响应结果为:{\"result\",\"success\"}");
            userMessage.setType("success");
        } else {
            sb.append("响应结果为:{\"result\",\"fail\"}");
            userMessage.setType("fail");
        }
        System.out.println(sb.toString());
        sic.getOos().writeObject(userMessage);
    }

    /**
     * 导出所有成绩信息
     *
     * @param tum
     * @param sb
     */
    private void exportGrade(UserMessage tum, StringBuilder sb) throws IOException {
        sb.append("调用\"importGrade\"方法,");
        gradeService.importGrade();
        tum.setType("success");
        tum.setContent("/Users/prim/Desktop/gradeTxt.txt");
        sb.append("响应结果为:{\"result\",\"success\"}");
        System.out.println(sb.toString());
        sic.getOos().writeObject(tum);
    }

    /**
     * 保存成绩信息
     *
     * @param tum
     * @param sb
     */
    private void saveGrade(UserMessage tum, StringBuilder sb) throws IOException {
        sb.append("调用\"saveGrade\"方法,");
        Grade grade = (Grade) tum.getContent();
        gradeService.add(grade);
        tum.setType("success");
        sb.append("响应结果为:{\"result\",\"success\"}");
        System.out.println(sb.toString());
        sic.getOos().writeObject(tum);
    }

    /**
     * 加载考题
     *
     * @param tum
     * @param sb
     */
    private void loadExam(UserMessage tum, StringBuilder sb) throws IOException {
        sb.append("调用\"loadExam\"方法,");
        List<Exam> examList = serverDao.getExamList();
        tum.setContent(examList);
        tum.setType("success");
        sb.append("响应结果为:{\"result\",\"success\"}");
        System.out.println(sb.toString());
        sic.getOos().writeObject(tum);
    }

    /**
     * 检查用户信息
     *
     * @param tum
     * @param sb
     */
    private void checkStudentInfo(UserMessage tum, StringBuilder sb) throws IOException {
        sb.append("调用\"checkStudentInfo\"方法,");
        User user = (User) tum.getContent();
        Student checkStudent = studentService.checkStudent(user);
        UserMessage<Student> userMessage = new UserMessage<>("", checkStudent);
        if (checkStudent != null) {
            //将登陆的学员信息返回
            userMessage.setType("success");
            sb.append("响应结果为:{\"result\",\"success\"}");
        } else {
            userMessage.setType("fail");
            sb.append("响应结果为:{\"result\",\"fail\"}");
        }
        System.out.println(sb.toString());
        sic.getOos().writeObject(userMessage);
    }

    private void importGrade(UserMessage tum, StringBuilder sb) throws IOException {
        sb.append("调用\"importGrade\"方法,");
        List<Grade> grades = (List<Grade>) tum.getContent();
        serverDao.setGradeList(grades);
        tum.setType("success");
        sb.append("响应结果为:{\"result\",\"success\"}");
        System.out.println(sb.toString());
        sic.getOos().writeObject(tum);
    }

    private void importExam(UserMessage tum, StringBuilder sb) throws IOException {
        sb.append("调用\"importExam\"方法,");
        List<Exam> exams = (List<Exam>) tum.getContent();
        serverDao.setExamList(exams);
        tum.setType("success");
        sb.append("响应结果为:{\"result\",\"success\"}");
        System.out.println(sb.toString());
        sic.getOos().writeObject(tum);
    }

    private void importStudent(UserMessage tum, StringBuilder sb) throws IOException {
        sb.append("调用\"importStudent\"方法,");
        List<Student> studentList = (List<Student>) tum.getContent();
        serverDao.setStudentList(studentList);
        tum.setType("success");
        sb.append("响应结果为:{\"result\",\"success\"}");
        System.out.println(sb.toString());
        sic.getOos().writeObject(tum);
    }

    private void deleteExam(UserMessage tum, StringBuilder sb) throws IOException {
        sb.append("调用\"deleteExam\"方法,");
        Integer id = (Integer) tum.getContent();
        boolean delete = examService.delete(id);
        if (delete) {
            tum.setType("success");
        } else {
            tum.setType("fail");
        }
        sb.append("响应结果为:{\"result\",\"success\"}");
        System.out.println(sb.toString());
        sic.getOos().writeObject(tum);
    }

    private void updateExam(UserMessage tum, StringBuilder sb) throws IOException {
        sb.append("调用\"updateExam\"方法,");
        Exam exam = (Exam) tum.getContent();
        examService.update(exam);
        tum.setType("success");
        sb.append("响应结果为:{\"result\",\"success\"}");
        System.out.println(sb.toString());
        sic.getOos().writeObject(tum);
    }

    private void queryExam(UserMessage tum, StringBuilder sb) throws IOException {
        sb.append("调用\"queryExam\"方法,");
        Integer id = (Integer) tum.getContent();
        Exam exam = examService.query(id);
        UserMessage<Exam> userMessage = new UserMessage<>("success", exam);
        if (null != exam) {
            sb.append("响应结果为:{\"result\",\"success\"}");
        } else {
            userMessage.setType("fail");
            sb.append("响应结果为:{\"result\",\"fail\"}");
        }
        System.out.println(sb.toString());
        sic.getOos().writeObject(userMessage);
    }

    private void addExam(UserMessage tum, StringBuilder sb) throws IOException {
        sb.append("调用\"addExam\"方法,");
        //添加学员信息
        Exam exam = (Exam) tum.getContent();
        examService.add(exam);
        tum.setType("success");
        sb.append("响应结果为:{\"result\",\"success\"}");
        System.out.println(sb.toString());
        sic.getOos().writeObject(tum);
    }

    private void deleteStudent(UserMessage tum, StringBuilder sb) throws IOException {
        sb.append("调用\"deleteStudent\"方法,");
        Integer id = (Integer) tum.getContent();
        boolean delete = studentService.delete(id);
        if (delete) {
            tum.setType("success");
        } else {
            tum.setType("fail");
        }
        sb.append("响应结果为:{\"result\",\"success\"}");
        System.out.println(sb.toString());
        sic.getOos().writeObject(tum);
    }

    private void updateStudent(UserMessage tum, StringBuilder sb) throws IOException {
        sb.append("调用\"updateStudent\"方法,");
        Student student = (Student) tum.getContent();
        studentService.update(student);
        tum.setType("success");
        sb.append("响应结果为:{\"result\",\"success\"}");
        System.out.println(sb.toString());
        sic.getOos().writeObject(tum);
    }

    private void queryStudent(UserMessage tum, StringBuilder sb) throws IOException {
        sb.append("调用\"queryStudent\"方法,");
        Integer id = (Integer) tum.getContent();
        Student student = studentService.query(id);
        UserMessage<Student> userMessage = new UserMessage<>("success", student);
        if (null != student) {
            sb.append("响应结果为:{\"result\",\"success\"}");
        } else {
            userMessage.setType("fail");
            sb.append("响应结果为:{\"result\",\"fail\"}");
        }
        System.out.println(sb.toString());
        sic.getOos().writeObject(userMessage);
    }

    private void addStudent(UserMessage tum, StringBuilder sb) throws IOException {
        sb.append("调用\"addStudent\"方法,");
        //添加学员信息
        Student student = (Student) tum.getContent();
        studentService.add(student);
        tum.setType("success");
        sb.append("响应结果为:{\"result\",\"success\"}");
        System.out.println(sb.toString());
        sic.getOos().writeObject(tum);
    }

    /**
     * 检查管理员信息
     *
     * @param tum
     * @throws IOException
     */
    private void checkAdminInfo(UserMessage tum, StringBuilder sb) throws IOException {
        //登录管理员系统
        sb.append("调用\"checkAdminInfo\"方法,");
        //校验管理员信息
        if (serverDao.serverManagerCheck((User) tum.getContent())) {
            tum.setType("success");
            sb.append("响应结果为:{\"result\",\"success\"}");
        } else {
            tum.setType("fail");
            sb.append("响应结果为:{\"result\",\"fail\"}");
        }
        System.out.println(sb.toString());
        sic.getOos().writeObject(tum);
        System.out.println("服务器发送消息成功");
    }
}
