package project.server.dao;


import project.client.model.Exam;
import project.client.model.Grade;
import project.client.model.Student;
import project.client.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据的存取
 */
public class ServerDao {
    /**
     * 学员数据的存储表
     */
    private List<Student> studentList = new ArrayList<>();

    /**
     * 考题存储表
     */
    private List<Exam> examList = new ArrayList<>();

    /**
     * 成绩存储表
     */
    private List<Grade> gradeList = new ArrayList<>();

    /**
     * 管理员账号的校验
     *
     * @param user
     * @return
     */
    public boolean serverManagerCheck(User user) {
        return "admin".equals(user.getUserName()) && "123456".equals(user.getPassword());
    }

    public void addStudent(Student student) {
        List<Student> studentList = getStudentList();
        for (Student s : studentList) {
            if (s.getsId().equals(student.getsId())) {
                System.out.println("学号重复，添加失败");
                return;
            }
        }
        studentList.add(student);
    }

    public void updateStudent(Student student) {
        for (Student stu : studentList) {
            if (stu.getsId().equals(student.getsId())) {
                int index = studentList.indexOf(stu);
                studentList.set(index, student);
            }
        }
    }

    public boolean deleteStudent(Integer sid) {
        Student student = queryStudent(sid);
        if (student != null) {
            getStudentList().remove(student);
            return true;
        }
        return false;
    }

    public Student queryStudent(Integer sid) {
        for (Student student : getStudentList()) {
            if (student.getsId().equals(sid)) {
                return student;
            }
        }
        return null;
    }

    public void addExam(Exam exam) {
        List<Exam> exams = getExamList();
        for (Exam e : exams) {
            if (e.geteId().equals(exam.geteId())) {
                System.out.println("考题重复，添加失败");
                return;
            }
        }
        examList.add(exam);
    }

    public void updateExam(Exam exam) {
        int indexOf = getExamList().indexOf(exam);
        if (indexOf >= 0) {
            getExamList().set(indexOf, exam);
        }
    }

    public Exam queryExam(Integer id) {
        for (Exam exam : getExamList()) {
            if (exam.geteId().equals(id)) {
                return exam;
            }
        }
        return null;
    }

    public boolean deleteExam(Integer id) {
        Exam exam = queryExam(id);
        if (exam != null) {
            getExamList().remove(exam);
            return true;
        }
        return false;
    }

    public void addGrade(Grade grade) {
        gradeList.add(grade);
    }

    public List<Grade> queryGrade(int sId) {
        List<Grade> grades = new ArrayList<>();
        for (Grade grade : gradeList) {
            if (sId == grade.getsId()) {
                grades.add(grade);
            }
        }
        return grades;
    }

    /**
     * 将成绩导出到文本文件
     */
    public void importGrade() {
        String gradePath = "/Users/prim/Desktop/gradeTxt.txt";
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(gradePath));
            for (Grade grade : gradeList) {
                bufferedWriter.write(grade.toString());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != bufferedWriter) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public List<Exam> getExamList() {
        return examList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public void setExamList(List<Exam> examList) {
        this.examList = examList;
    }

    public List<Grade> getGradeList() {
        return gradeList;
    }

    public void setGradeList(List<Grade> gradeList) {
        this.gradeList = gradeList;
    }

    public static String studentPath = "/Users/prim/Desktop/student.txt";
    public static String examPath = "/Users/prim/Desktop/exam.txt";
    public static String gradePath = "/Users/prim/Desktop/grade.txt";

    /**
     * 当系统退出后保存信息到文件中
     */
    public void save() {
        //学员信息单独的文件
        ObjectOutputStream oos = null;
        ObjectOutputStream oos2 = null;
        ObjectOutputStream oos3 = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(studentPath));
            oos.writeObject(studentList);

            oos2 = new ObjectOutputStream(new FileOutputStream(examPath));
            oos2.writeObject(examList);

            oos3 = new ObjectOutputStream(new FileOutputStream(gradePath));
            oos3.writeObject(gradeList);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != oos3) {
                try {
                    oos3.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != oos2) {
                try {
                    oos2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != oos) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Student checkStudent(User user) {
        for (Student student : studentList) {
            if (user.getUserName().equals(student.getName()) && user.getPassword().equals(student.getPassword())) {
                return student;
            }
        }
        return null;
    }
}
