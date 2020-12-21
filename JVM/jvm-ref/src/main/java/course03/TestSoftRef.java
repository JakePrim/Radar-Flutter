package course03;

import java.lang.ref.SoftReference;
import java.util.LinkedList;
import java.util.List;

/**
 * -Xms20m -Xmx20m
 */
public class TestSoftRef {
    public static class User{
        public int id = 0;
        public String name = "";

        public User(int id, String name) {
            super();
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        User u = new User(1,"jake");//new 是强引用
        SoftReference<User> userSoft = new SoftReference<>(u);//软引用
        u = null;//干掉强引用，确保这个实例只有userSoft软引用
        System.gc();//进行一次GC垃圾回收，
        System.out.println("After GC");
        System.out.println(userSoft.get());//对象还存活 User{id=1, name='jake'}
        //往堆中填充数据，导致OOM
        List<byte[]> list = new LinkedList<>();
        try {
            for (int i = 0; i < 100; i++) {
                list.add(new byte[1024*1024*1]);
            }
        } catch (Throwable e) {
            System.out.println("抛出异常时，打印软引用对象:"+userSoft.get());//抛出异常时，打印软引用对象:null
        }
    }
}
