package thread_demo;

public class StoreHouseTest {
    public static void main(String[] args) {
        StoreHouse storeHouse = new StoreHouse();
        ProduceThread produceThread = new ProduceThread(storeHouse);
        ConsumerThread consumerThread = new ConsumerThread(storeHouse);
        produceThread.start();
        consumerThread.start();
    }
}
