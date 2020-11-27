package task_01_04.first_question.exception;

/**
 * 学生学号异常类
 */
public class StudentNumberException extends Exception {
    public StudentNumberException() {
    }

    public StudentNumberException(String message) {
        super(message);
    }
}
