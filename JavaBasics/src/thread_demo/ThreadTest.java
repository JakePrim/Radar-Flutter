package thread_demo;

public class ThreadTest {
    public static void main(String[] args) {
        //使用无参构造Thread类型的对象
        Thread t1 = new Thread();
        t1.run();//无参构造方法 导致了Runnable-target为null，run方法没有执行target的run方法
        System.out.println("干了啥");
    }
}
