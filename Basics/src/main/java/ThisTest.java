/**
 * this关键字的使用
 */
public class ThisTest {
    public ThisTest() {
        //this代表当前正在狗仔的对象
        System.out.println("构造方法：this="+this);//this=ThisTest@7852e922
    }

    public void show(){
        //this代表当前正在调用的对象
        System.out.println("成员方法:this="+this);//this=ThisTest@7852e922
    }

    public static void main(String[] args) {
        ThisTest thisTest = new ThisTest();
        System.out.println(thisTest);//this=ThisTest@7852e922
        thisTest.show();
    }
}
