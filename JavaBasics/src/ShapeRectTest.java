public class ShapeRectTest {


//    public static void draw(Circle c) {
//        c.show();
//    }
//
//    public static void draw(Rect c) {
//        c.show();
//    }

    /**
     * 多态的实际意义：既能打印矩形又能打印圆形对象的特征，同时不限定形状
     * @param s
     */
    public static void draw(Shape s) {
        s.show();
    }

    public static void main(String[] args) {
        ShapeRectTest.draw(new Rect(1, 2, 3, 4));
        ShapeRectTest.draw(new Circle(5, 6, 7));
//        Shape shape = new Shape(1, 2);
//        shape.show();//1 2
//        System.out.println("----------------------------");
//        Rect rect = new Rect(3, 4, 5, 6);
//        rect.show();//3 4 5 6
//        System.out.println("-----------------------------");
//        Shape sr = new Rect(7, 8, 9, 10);
//        //sr调用的是Rect的show方法
//        sr.show();//7 8 9 10
//
//        int x = sr.getX();
//        System.out.println("获取到的横坐标是:" + x);
//
//        //调用静态方法
//        sr.test();//打印：Shape类中的静态方法
//
//        //使用父类类型的引用调用子类独有方法的方式，强制转换为子类
//        ((Rect) sr).getLen();
//
//        Circle c1 = (Circle) sr;//编译通过，运行阶段发生：类型转换异常ClassCastException
//
//        //判断sr指向堆区内存中的对象是否为Circle类型，如果是true。否则false
//        if (sr instanceof Circle) {
//
//        }
        ShapeRectTest.draw(new Rect(1, 2, 3, 4));
        ShapeRectTest.draw(new Circle(5, 6, 7));
    }
}
