package course02;

import org.openjdk.jol.info.ClassLayout;

/**
 * VM 参数
 * 设置堆的初始大小 最大大小 元空间大小
 * -Xms30m -Xmx30m -XX:MaxMetaspaceSize=30m -XX:+UseConcMarkSweepGC -XX:-UseCompressedOops
 * -XX:+UseConcMarkSweepGC  -- 使用CMS垃圾回收器
 * -XX:-UseCompressedOops  -- 禁止压缩指针
 */
public class JVMObject {
    public final static String MAN_TYPE="man";//常量
    public static String WOMAN_TYPE = "woman";//静态变量
    //
    public static void main(String[] args) throws InterruptedException {
        Teacher t1 = new Teacher();
        t1.setName("Mark");
        t1.setSexType(MAN_TYPE);
        t1.setAge(36);

//        System.gc(); 主动触发GC
        for (int i = 0; i < 15; i++) {
            System.gc();///演示程序 主动触发垃圾回收15次
        }


        //打印对象结构布局信息
        System.err.println(ClassLayout.parseInstance(t1).toPrintable());

        Teacher t2 = new Teacher();
        t2.setName("King");
        t2.setSexType(MAN_TYPE);
        t2.setAge(18);

        Thread.sleep(Integer.MAX_VALUE);
    }
}

class Teacher {
    String name;
    String sexType;
    int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSexType() {
        return sexType;
    }

    public void setSexType(String sexType) {
        this.sexType = sexType;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
