package abstract_demo;

public class SubAbstractTest extends AbstractTest {

    //继承抽象类必须实现抽象方法
    @Override
    public void show() {
        System.out.println("SubAbstractTest");
    }
}
