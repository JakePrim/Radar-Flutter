public class Student {
    //私有化成员变量 该成员变量只能在当前类内部使用
    private int age;
    private String name;

    public static String coutry;

    public Student() {
    }

    public Student(String name,int age) {
        //复用age的赋值判断
        setAge(age);
        setName(name);
    }

    //提供公有的get和set方法来操作成员变量 public修饰符，表示该方法可有在任意位置使用
    public void setAge(int age) {
        if (age > 0) {//判断合理的年龄
            this.age = age;
        } else {
            System.out.println("年龄不合理");
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    //默认的访问权限，介于private和public之间 protected权限
    void show() {
        System.out.println(coutry);
        System.out.println("name:" + getName() + " age:" + getAge());
    }

    static void show2(){
//        System.out.println(this.name);//报错
        System.out.println(coutry);
    }

    public static void main(String[] args) {
        Student.coutry = "中国";
        Student student = new Student();
        System.out.println(student.coutry+"|"+Student.coutry);
    }
}
