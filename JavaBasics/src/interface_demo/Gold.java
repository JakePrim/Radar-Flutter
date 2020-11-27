package interface_demo;

public class Gold implements Money, Metal {
    @Override
    public void buy() {
        System.out.println("买了好多好吃的");
    }

    @Override
    public void shine() {
        System.out.println("发出金黄色的光芒");
    }

    public static void main(String[] args) {
        Metal mt = new Gold();
        mt.shine();

        Money m = new Gold();
        m.buy();
    }
}
