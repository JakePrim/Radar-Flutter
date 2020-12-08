package project.client.model;

import java.io.Serializable;
import java.util.List;

public class Student implements Serializable {
    private static final long serialVersionUID = 3422953473682278660L;
    private Integer sId;//学员id
    private String name;//学员姓名
    private String password;//密码
    //忽略
    private List<Grade> gradeList;//成绩列表 不需要直接根据学员ID查询成绩表即可 忽略该字段

    public Student() {
    }

    public Student(Integer sId, String name, String password, List<Grade> gradeList) {
        this.sId = sId;
        this.name = name;
        this.password = password;
        this.gradeList = gradeList;
    }

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Grade> getGradeList() {
        return gradeList;
    }

    public void setGradeList(List<Grade> gradeList) {
        this.gradeList = gradeList;
    }

    @Override
    public String toString() {
        return "Student{" +
                "sId=" + sId +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", gradeList=" + gradeList +
                '}';
    }
}
