package course02.oom;

import java.util.LinkedList;
import java.util.List;

/**
 * VM Args: -Xms30m -Xmx30m -XX:+PrintGCDetails  堆的大小为30m
 * 造成一个堆内存溢出 分析一下JVM的分代收集
 * GC调优---生成服务器推荐开启(默认是关闭的)
 * -XX:+HeapDumpOnOutOfMemoryError
 */
public class HeapOom2 {
    public static void main(String[] args) throws InterruptedException {
        List<Object> list = new LinkedList<>();//list 当前虚拟机栈 局部变量表中引用的对象
        int i = 0;
        while (true){
            i++;
            if (i%1000==0) Thread.sleep(10);
            list.add(new Object());//不能回收2， 优先回收再来抛出异常
            // GC overhead limit exceeded ：当GC 占据了98%的资源 回收不足2%。就抛出OOM
        }
    }
}
