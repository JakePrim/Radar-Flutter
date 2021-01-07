/**
 * 方法重载
 */
public class OverloadTest {
    void show() {
        System.out.println("show()");
    }

    void show(int i) {//体现方法参数的个数不同
        System.out.println("show(i):" + i);
    }

    void show(int i,double d){//体现方法参数的个数不同
        System.out.println("show(int,double)");
    }

    void show(int i,int d){//体现方法参数的类型不同
        System.out.println("show(int,int)");
    }

    void show(double d,int i){//体现方法参数的顺序不同
        System.out.println("show(double,int)");
    }

//    void show(double a,int b){//错误：与参数的变量名无关 方法在调用的时候无法分清楚 上面的方法和这个方法调用哪个
//        System.out.println("show(double,int)");
//    }

//    int show(double d,int i){//错误：与方法的返回值类型无关 同样方法在调用的时候无法分清楚 上面的方法和这个方法调用哪个
//        System.out.println("show(double,int)");
//        return 1;
//    }

    public static void main(String[] args) {
        OverloadTest overloadTest = new OverloadTest();
        overloadTest.show();
        overloadTest.show(66);
        overloadTest.show(66,1.34);
        overloadTest.show(66,88);
        overloadTest.show(1.34,88);

    }

}
