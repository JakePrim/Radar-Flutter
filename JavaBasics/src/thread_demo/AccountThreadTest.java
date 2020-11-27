package thread_demo;

public class AccountThreadTest extends Thread {

    //描述账户的余额
    private int balance;

    private static Demo dm = new Demo();//隶属于类层级 所有对象共享一个

    public AccountThreadTest() {
    }

    public AccountThreadTest(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public void run() {
        test();
//        System.out.println(Thread.currentThread().getName() + " 已启动");
//        //synchronized的锁注意：要保证锁的是同一个引用，只有一把锁
//        synchronized (dm) {
//            //模拟从后台查询账户余额的过程
//            int temp = getBalance();
//            //模拟取款200元的过程
//            if (temp >= 200) {
//                System.out.println("正在出钞。。。");
//                temp -= 200;
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("请取走钞票");
//            } else {
//                System.out.println("余额不足，请核对账户的余额");
//            }
//            //模拟将最新的账户余额写入到后台
//            setBalance(temp);
//        }
    }

    public static /*synchronized*/ void test() {
        //等价于 synchronized(AccountThreadTest.class)
        synchronized (AccountThreadTest.class) {
            System.out.println(Thread.currentThread().getName() + " 已启动");
            //synchronized的锁注意：要保证锁的是同一个引用，只有一把锁
            //模拟从后台查询账户余额的过程
            int temp = 1000;
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
        }
    }

    public static void main(String[] args) {
        AccountThreadTest att1 = new AccountThreadTest(1000);
        att1.start();
        AccountThreadTest att2 = new AccountThreadTest(1000);
        att2.start();
        System.out.println("主线程等待");
        try {
            att1.join();
            att2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("最终账户余额:" + att1.getBalance());
    }
}
