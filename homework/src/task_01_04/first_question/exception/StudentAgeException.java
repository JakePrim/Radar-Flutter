package task_01_04.first_question.exception;

/**
 * 学生年龄异常
 */
public class StudentAgeException extends Exception {
    public StudentAgeException() {
    }

    public StudentAgeException(String message) {
        super(message);
    }
}
