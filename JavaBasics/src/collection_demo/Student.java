package collection_demo;

public class Student implements Comparable<Student> {
    private String name;
    private int age;

    public Student() {
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

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

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public int compareTo(Student o) {
        //0 调用对象与参数对象相等 则TreeSet只会添加一次
//        return 0;
//        return -1;//表示调用对象小于参数对象 则放到前面
        // [Student{name='张飞', age=30}, Student{name='刘备', age=40}, Student{name='关羽', age=35}]
//        return 1;//表示调用对象大于参数对象
        // [Student{name='关羽', age=35}, Student{name='刘备', age=40}, Student{name='张飞', age=30}]
//        return this.getName().compareTo(o.getName());//比较姓名的大小
        //[Student{name='关羽', age=35}, Student{name='刘备', age=40}, Student{name='张飞', age=30}]
        int compare = this.getName().compareTo(o.getName());
        //姓名相同比较年龄
        if (0 == compare) {
            return this.getAge() - o.getAge();
        }
        return compare;
    }
}
