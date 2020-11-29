package reflect_demo;

import java.io.*;
import java.util.Scanner;

/**
 * 利用反射动态创建对象
 */
public class PersonConstructorTest {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        //1. 使用无参的形式构造Person类型的对象并打印
        //原始方式 创建Person对象
        Person p1 = new Person();
        System.out.println("p1=" + p1);//{name='null', age=0}

        //运行阶段创建对象，而不是在编译阶段创建对象
//        System.out.println("请输入要创建的对象类型:");
//        Scanner sc = new Scanner(System.in);
//        String next = sc.next();
        //使用反射机制以无参机制构造Person类型的对象

        //创建对象的类型可以从配置文件中读取 创建对象
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/prim/Desktop/a1.txt")));
            String str;
            while ((str = br.readLine()) != null) {
                Class<?> pClass = Class.forName(str);
                System.out.println("p=" + pClass.newInstance());//{name='null', age=0}
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
