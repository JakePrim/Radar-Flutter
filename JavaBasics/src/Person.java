/**
 * 类的定义
 */
public class Person {
    //数据类型 成员变量名 = 初始值；(初始值可以省略不写)
    String name;//用于描述姓名的成员变量
    int age;//用于描述年龄的成员变量

    /**
     * 无参构造方法 如果没有无参构造方法 那么new Person对象的时候，必须使用下面的自定义构造方法必须传值
     */
    public Person() {
//        this("张三",23);
        System.out.println("默认构造方法");

    }

    /**
     * 自定义构造方法
     */
    public Person(String name, int age) {
        this();
        this.name = name;
        this.age = age;
    }

    //成员方法实现成员变量的打印
    //返回值类型 方法名称(形参列表) {方法体}
    void show() {
        this.grow();
        //成员变量和成员方法都属于类内部的成员。可以直接访问成员变量
        System.out.println("我是" + this.name + ",今年:" + this.age);
    }

    //成员方法实现将姓名修改为参数指定数值的行为
    void setName(String s) {
        name = s;
    }

    //成员方法实现将姓名修改为参数指定数值的行为
    void setAge(int a) {
        age = a;
    }

    void setNameAge(String s, int i) {
        name = s;
        age = i;
    }

    //成员方法实现可变长参数
    void showArgument(String... args) {
        for (int i = 0; i < args.length; i++) {
            System.out.println("第" + (i + 1) + "个参数为：" + args[i]);
        }
    }

    //返回值
    String getName() {
        return name;
    }

    int getAge() {
        return age;
    }

    void setArgumentsTest(int a) {
        a = 100;
        System.out.println("a:" + a);//100
    }

    void setArgumentsTest(int[] args) {
        args[0] = 200;
        System.out.println("args[0]:" + args[0]);//200
    }

    void setArgumentsTest2(int[] args) {
        args = new int[]{10};
        System.out.println("args[0]:" + args[0]);//10
    }

    void grow() {
        age++;
    }

    void grow(int i) {
        age += i;
    }

    Person getPerson(){
        return this;
    }


    public static void main(String[] args) {
        //数据类型(类名) 引用变量名 = new 类名();
        Person person = new Person();//创建对象
        //打印对象中的成员变量值:引用变量名.成员变量名
        person.setName("zhangfei");
        person.setAge(30);
        //引用变量名.成员方法名(实参列表)
        person.show();
        person.setNameAge("liubei", 40);
        person.show();
        person.showArgument("1", "2", "3");
        //参数的注意事项
        int age = 18;
        person.setArgumentsTest(age);
        //基本类型的实参不会因为形参的变化而改变 基本类型都有自己的存储空间
        System.out.println("age:" + age);//18
        //注意：引用类型的实参会因为形参的变化而改变 因为他们都指向同一个内存地址
        int[] ages = new int[]{18};
        person.setArgumentsTest(ages);
        System.out.println("ages[0]:" + ages[0]);//200
        //注意：如果形参指向了其他的内存地址，不会影响实参的变化
        int[] ages2 = new int[]{18};
        person.setArgumentsTest2(ages2);
        System.out.println("ages[0]:" + ages2[0]);//18

        //调用自定义构造方法 实现成员变量的初始化
        Person person1 = new Person("张飞", 30);
        Person person2 = new Person("关羽", 30);
        person1.show();
        person2.show();

        //引用变量的数值为空
        Person person3 = null;
//        person3.show();//报错
    }
}
