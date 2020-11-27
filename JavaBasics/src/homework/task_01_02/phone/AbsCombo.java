package homework.task_01_02.phone;

/**
 * 套餐的抽象类
 */
public abstract class AbsCombo {

    /**
     * 每月消费
     */
    protected double monthConsume;


    public AbsCombo() {
    }

    public AbsCombo(double monthConsume) {
        setMonthConsume(monthConsume);
    }

    //抽象相同的特征和行为
    public double getMonthConsume() {
        return monthConsume;
    }

    public void setMonthConsume(double monthConsume) {
        if (monthConsume > 0) {
            this.monthConsume = monthConsume;
        } else {
            System.out.println("每月的套餐必须在0元以上");
        }
    }

    public void show() {
        System.out.println("每月资费:" + getMonthConsume() + "元");
    }
}
