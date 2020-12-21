package course03;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

/**
 * -Xms20m -Xmx20m
 */
public class TestWeakRef {
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
        WeakReference<User> userSoft = new WeakReference<>(u);//弱引用
        u = null;//干掉强引用，确保这个实例只有userSoft弱引用
        System.out.println(userSoft.get());//User{id=1, name='jake'}
        System.gc();//进行一次GC垃圾回收，
        System.out.println("After GC");
        System.out.println(userSoft.get());//null
        //往堆中填充数据，导致OOM
//        List<byte[]> list = new LinkedList<>();
//        try {
//            for (int i = 0; i < 100; i++) {
//                list.add(new byte[1024*1024*1]);
//            }
//        } catch (Throwable e) {
//            System.out.println("抛出异常时，打印软引用对象:"+userSoft.get());//抛出异常时，打印软引用对象:null
//        }
    }
}
