package course03;

public class ObjectCreate {
    private int age;
    private boolean isKing;

    public static void main(String[] args) {
        //JVM 遇到 new 指令
        // 检查加载
        // 分配内存
        // 划分内存的方式
        //      1. 内存空间连续 指针碰撞
        //      2. 内存空间不连续 jvm维护空闲列表 根据空闲列表分配
        // 并发安全问题：
        //      1.CAS加失败重试
        //      2. 本地线程分配缓冲(TLAB Thread Local Allocation Buffer) 每个线程在Eden区
        //      单独的把某一块内存区域划分给该线程，每个线程有单独的存储区域，所以效率会更高一些.
        //      XX:+UseTLAB 默认是开启的

        ObjectCreate objectCreate = new ObjectCreate();
        System.out.println(objectCreate.age);
        System.out.println(objectCreate.isKing);
    }
}
