package abstract_demo;

public class FixedAccount extends Account {
    public FixedAccount(int money) {
        super(money);
    }

    public FixedAccount() {
    }

    @Override
    public double getLixi() {
        //利息=本金 * 利率 * 时间
        return getMoney() * 0.03 * 1;
    }

    public static void main(String[] args) {
        //形成多态
        Account acc = new FixedAccount(1000);
        System.out.println("定期利息:" + acc.getLixi());
    }
}
