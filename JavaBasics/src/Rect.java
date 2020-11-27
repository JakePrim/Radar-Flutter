public class Rect extends Shape {
    private int len;
    private int wid;

    public Rect(int x, int y, int len, int wid) {
        super(x, y);
        this.len = len;
        this.wid = wid;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        if (len > 0) {
            this.len = len;
        } else {
            System.out.println("长度不合理哦！！");
        }
    }

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        if (wid > 0) {
            this.wid = wid;
        } else {
            System.out.println("宽度不合理哦！！");
        }
    }

    @Override
    public void show() {
        super.show();
        System.out.println("长度是：" + getLen() + ",宽度是：" + getWid());
    }

    public static void test(){
        System.out.println("Rect类中的静态方法");
    }
}
