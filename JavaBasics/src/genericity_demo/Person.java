package genericity_demo;

public class Person<T> {
    private String name;
    private int age;
    private T sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public T getSex() {
        return sex;
    }

    public void setSex(T sex) {
        this.sex = sex;
    }

    /**
     * 泛型方法
     */
    public static <E> void printArray(E[] e){

    }

    public static void main(String[] args) {
        Person.<String>printArray(args);
    }
}
