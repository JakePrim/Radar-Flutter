package abstract_demo;

public abstract class Account {
    private int money;

    public Account(int money) {
        setMoney(money);
    }

    public Account() {
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        if (money >= 0) {
            this.money = money;
        } else {
            System.out.println("金额不合理");
        }
    }

    //    private abstract double getLixi();
//private和abstract 关键字不能共同修饰一个方法，而抽象方法就是让子类实现的一旦抽象方法设置为private不能被子类重写，就没有意义了
    public abstract double getLixi();
    //final的意义在于不能被重写，private在于不能被继承
//    public final abstract double getLixi();
    //static 不能和abstract共存，抽象类的意义不能new对象，而加上static可以直接类名. 访问 所以不能共存
//    public static abstract double getLixi();

}
