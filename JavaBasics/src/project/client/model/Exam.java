package project.client.model;

import java.io.Serializable;
import java.util.List;

/**
 * 考题
 */
public class Exam implements Serializable {
    private Integer eId;//考题ID
    private List<String> select;//选项
    private String subject;//考题 题目
    private Integer correctResponse;//正确答案
    private Integer choose;//学员选择的选项

    public Exam() {
    }

    public Exam(Integer eId, List<String> select, String subject, Integer correctResponse) {
        this.eId = eId;
        this.select = select;
        this.subject = subject;
        this.correctResponse = correctResponse;
    }

    public Integer geteId() {
        return eId;
    }

    public void seteId(Integer eId) {
        this.eId = eId;
    }

    public List<String> getSelect() {
        return select;
    }

    public void setSelect(List<String> select) {
        this.select = select;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getCorrectResponse() {
        return correctResponse;
    }

    public void setCorrectResponse(Integer correctResponse) {
        this.correctResponse = correctResponse;
    }

    public Integer getChoose() {
        return choose;
    }

    public void setChoose(Integer choose) {
        this.choose = choose;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "eId=" + eId +
                ", 选项=" + select +
                ", 题目='" + subject + '\'' +
                ", 正确答案=" + correctResponse +
                ", 学员选择的选项=" + choose +
                '}';
    }
}
