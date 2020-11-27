package thread_demo;

public class SunRunnableRunTest {
    public static void main(String[] args) {
        //自定义类型实现Runnable接口的对象 作为实参构造Thread类型的对象
        Thread thread = new Thread(new SunRunnableRun());
        //如果使用Runnable引用构造的线程对象，调用该方法run是最终调用接口的版本
        thread.start();
    }
}
