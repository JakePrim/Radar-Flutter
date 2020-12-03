package project.client.model;

import java.io.Serializable;
import java.util.List;

/**
 * 学员的成绩表
 */
public class Grade implements Serializable {
    private static final long serialVersionUID = -4540138229555645433L;
    private Integer gId;//成绩的ID
    private Integer sId;//学员的编号
    private float grade;//考试成绩
    private List<Exam> examList;//记录学员做的每一道考题 以及选项

    public Grade() {
    }

    public Grade(Integer gId, Integer sId, float grade) {
        this.gId = gId;
        this.sId = sId;
        this.grade = grade;
    }

    public Integer getgId() {
        return gId;
    }

    public void setgId(Integer gId) {
        this.gId = gId;
    }

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public List<Exam> getExamList() {
        return examList;
    }

    public void setExamList(List<Exam> examList) {
        this.examList = examList;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "gId=" + gId +
                ", sId='" + sId + '\'' +
                ", 考试成绩=" + grade +
                ", 考试记录=" + examList +
                '}';
    }
}
