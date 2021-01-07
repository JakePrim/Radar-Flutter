/**
 * 多态的demo
 */
public class Shape {
    private int x;
    private int y;

    public Shape() {
    }

    public Shape(int x, int y) {
        setX(x);
        setY(y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void show() {
        System.out.println("横坐标:" + getX() + ",纵坐标:" + getY());
    }


    public static void test(){
        System.out.println("Shape类中的静态方法");
    }
}
