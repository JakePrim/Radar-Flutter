package course02.oom;

/**
 * VM Args: -Xms30m -Xmx30m -XX:+PrintGCDetails
 * 设置堆的大小来模拟对溢出异常
 */
public class HeapOom {
    public static void main(String[] args) {
        String[] strings = new String[35*1000*1000];
    }
}
