package object_demo;

import java.util.Objects;

/**
 * 默认继承Object类
 */
public class Student extends Object{
    private int id;
    private String name;

    public Student() {
    }

    public Student(int id, String name) {
        setId(id);
        setName(name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id > 0) {
            this.id = id;
        } else {
            System.out.println("学号不合理");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 重写equals方法 两个学号相等 则返回true
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        //两个对象内存地址相同则返回true
        if (this == o) return true;
        //类型不一致没有可比性 返回false
        if (o == null || getClass() != o.getClass()) return false;
        //添加自己的判断
        Student student = (Student) o;
        return id == student.id;
    }

    /**
     * 为了使得该方法的结果与equals方法的结果保持一致 需要重新
     * equals 相等取决于id,那么hashCode也依赖于学号
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
