public class Worker extends Pers {

    public Worker(String name, int age, int salary) {
        super(name, age);//调用父类的有参构造方法
        setSalary(salary);
    }

    public Worker() {
        //若没有加则编译器自动添加
        super();//super 调用父类的构造方法
    }

    private int salary;

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    /**
     * 覆盖父类的方法
     */
    @Override
    Worker show() {
        //super表示父类 .show()表示调用父类的show方法
        super.show();
        System.out.println("薪水:"+getSalary());
        return this;
    }

    public static void main(String[] args) {
        Worker worker = new Worker("123",30,2300);
        worker.eat("豆芽");
        worker.play("王者荣耀");
        worker.show();
    }
}
