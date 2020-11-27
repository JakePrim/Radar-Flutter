package homework.task_01_03.four_question;

import java.util.Objects;

/**
 * 学生类
 */
public class Student {
    private String name;
    private int age;
    private int number;

    public Student() {
    }

    public Student(String name, int age, int number) {
        setName(name);
        setAge(age);
        setNumber(number);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (null == name || "".equals(name)) {
            System.out.println("学生名字设置不合理");
        } else {
            this.name = name;
        }
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age > 0 && age < 150) {
            this.age = age;
        } else {
            System.out.println("年龄不合理");
        }
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return age == student.age &&
                number == student.number &&
                Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, number);
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", number=" + number +
                '}';
    }
}
