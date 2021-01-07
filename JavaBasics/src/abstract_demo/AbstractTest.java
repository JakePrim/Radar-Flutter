package abstract_demo;

public abstract class AbstractTest {
    private int cnt;

    public AbstractTest() {
    }

    public AbstractTest(int cnt) {
        this.cnt = cnt;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    //抽象方法
    public abstract void show();

    public static void main(String[] args) {
        //抽象类：可以有成员变量 成员方法  构造方法 抽象方法
    }
}
