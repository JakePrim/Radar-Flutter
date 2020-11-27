/**
 * 封装
 */
public class Pers {
    private String name;
    private int age;

    public Pers() {
        System.out.println("pers()");
    }

    public Pers(String name, int age) {
        System.out.println("Pers(String,int)");
        setName(name);
        setAge(age);
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
        if (age > 0 && age < 150) {
            this.age = age;
        } else {
            System.out.println("年龄错误");
        }
    }

    Pers show() {
        System.out.println("我是:" + getName() + " 今年：" + age);
        return this;
    }

    //描述吃放的行为
    public void eat(String food) {
        System.out.println("我正在吃:" + food);
    }

    public void play(String play) {
        System.out.println("我正在玩:" + play);
    }


}
