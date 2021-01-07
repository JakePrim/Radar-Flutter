package course02.oom;

import java.nio.ByteBuffer;

/**
 * 直接内存OOM
 * 设置直接内存的大小
 * VM Args: -XX:MaxDirectMemorySize=100m
 */
public class DirectOom {
    public static void main(String[] args) {
        //直接分配128M给直接内存(100m)
        ByteBuffer bb = ByteBuffer.allocateDirect(128*1024*1024);
    }
}
