package homework.task_01_04.first_question;

import java.util.List;

public class StudentService implements IStudentService<Student> {
    private List<Student> students;

    public StudentService(List<Student> students) {
        this.students = students;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public void add(Student student) {
        for (Student s : students) {
            if (s.equals(student)) {
                System.out.println("已有该学生，不要重复添加");
                return;
            }
            if (s.getNumber() == student.getNumber()) {
                System.out.println("已存在此学号，不能添加。可以修改信息");
                return;
            }
        }
        students.add(student);
    }

    @Override
    public void remove(Student student) {
        students.remove(student);
    }

    @Override
    public boolean remove(int number) {
        return students.removeIf(next -> next.getNumber() == number);
    }

    @Override
    public void update(Student student) {
        int indexOf = students.indexOf(student);
        if (indexOf >= 0) {
            students.set(indexOf, student);
        }
    }

    /**
     * 根据学号查找学生
     *
     * @param number
     * @return
     */
    @Override
    public Student find(int number) {
        for (Student student : students) {
            if (student.getNumber() == number) {
                return student;
            }
        }
        return null;
    }

    /**
     * 根据姓名查找学生
     *
     * @param name
     * @return
     */
    @Override
    public Student find(String name) {
        for (Student student : students) {
            if (student.getName().equals(name)) {
                return student;
            }
        }
        return null;
    }

    @Override
    public List<Student> findAll() {
        return students;
    }

    @Override
    public void add(List<Student> t) {
        this.students = t;
    }
}
