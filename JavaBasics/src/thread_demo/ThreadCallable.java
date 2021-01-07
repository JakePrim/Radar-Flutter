package thread_demo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadCallable implements Callable {
    @Override
    public Object call() throws Exception {
        int sum = 0;
        for (int i = 1; i <= 10000; i++) {
            sum += i;
        }
        System.out.println("sum:" + sum);
        return sum;
    }

    public static void main(String[] args) {
        ThreadCallable tc = new ThreadCallable();
        FutureTask ft = new FutureTask(tc);
        Thread thread = new Thread(ft);
        thread.start();
        try {
            Object o = ft.get();
            System.out.println("线程处理方法的返回值是:" + o);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
