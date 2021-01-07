package course03;

public class Isalive {
    public Object instance = null;
    //占据内存，便于判断分析GC
    private byte[] bigSize = new byte[10 * 1024 * 1024];

    public static void main(String[] args) {
        Isalive objectA=new Isalive();//objectA 局部变量表 GCRoots
        Isalive objectB = new Isalive();
        //互相引用
        objectA.instance = objectB;
        objectB.instance = objectA;
        //切断可达
        objectA = null;
        objectB = null;
        //强制垃圾回收
        System.gc();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
