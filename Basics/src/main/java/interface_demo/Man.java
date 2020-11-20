package interface_demo;

public class Man implements Hunter {
    @Override
    public void hunt() {
        System.out.println("正在追赶猎物");
    }

    @Override
    public void run() {
        System.out.println("正在奔跑");
    }

    public static void main(String[] args) {
        //声明接口类型的引用指向实现类的对象，形成了多态
        Runner runner = new Man();
        runner.run();

        Hunter hunter = new Man();
        hunter.hunt();
    }

    @Override
    public void buy() {

    }
}
