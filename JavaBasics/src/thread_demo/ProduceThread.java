package thread_demo;

/**
 * 生产者线程，不断地生产产品
 */
public class ProduceThread extends Thread{
    private StoreHouse storeHouse;//声明一个仓库

    //保证两个线程 共用同一个仓库
    public ProduceThread(StoreHouse storeHouse) {
        this.storeHouse = storeHouse;
    }

    @Override
    public void run() {
        while (true){
            storeHouse.produceProduct();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
