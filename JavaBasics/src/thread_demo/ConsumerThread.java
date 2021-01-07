package thread_demo;

public class ConsumerThread extends Thread {
    private StoreHouse storeHouse;//声明一个仓库

    //保证两个线程 共用同一个仓库
    public ConsumerThread(StoreHouse storeHouse) {
        this.storeHouse = storeHouse;
    }

    @Override
    public void run() {
        while (true) {
            storeHouse.consumerProduct();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
