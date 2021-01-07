package thread_demo;

import java.util.concurrent.locks.ReentrantLock;

public class AccountRunnableTest implements Runnable {
    //描述账户的余额
    private int balance;

    private Demo dm = new Demo();

    private ReentrantLock lock = new ReentrantLock();

    public AccountRunnableTest() {
    }

    public AccountRunnableTest(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public /*synchronized*/ void run() {
        //开始加锁
        lock.lock();
//        synchronized (this){} 等价于同步方法 synchronized void run()
        System.out.println(Thread.currentThread().getName() + " 已启动");
        //synchronized的锁注意：要保证锁的是同一个引用，只有一把锁
//        synchronized (dm) {
        //模拟从后台查询账户余额的过程
        int temp = getBalance();
        //模拟取款200元的过程
        if (temp >= 200) {
            System.out.println("正在出钞。。。");
            temp -= 200;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("请取走钞票");
        } else {
            System.out.println("余额不足，请核对账户的余额");
        }
        //模拟将最新的账户余额写入到后台
        setBalance(temp);
//        }
        //释放锁
        lock.unlock();
    }

    public static void main(String[] args) {
        AccountRunnableTest account = new AccountRunnableTest(1000);
        Thread thread1 = new Thread(account);
        Thread thread2 = new Thread(account);
        thread1.start();
        thread2.start();

        System.out.println("主线程开始等待");
        try {
            thread1.join();
            //等待线程1结束之后，启动线程2
//            thread2.start();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("最终的账户余额为:" + account.getBalance());//600
    }
}

class Demo {
}