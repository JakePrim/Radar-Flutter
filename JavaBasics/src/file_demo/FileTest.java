package file_demo;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class FileTest {
    public static void main(String[] args) {
        //1 构造file对象
        File file = new File("/Users/prim/Desktop/a.txt");
        //2 若文件存在获取文件相关的特征信息
        if (file.exists()) {
            System.out.println("文件名称:" + file.getName());
            System.out.println("文件大小:" + file.length());
            Date ldt = new Date(file.lastModified());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = simpleDateFormat.format(ldt);
            System.out.println("文件最后的修改时间:" + format);
            //相对路径:主要指以当前目录所在位置开始的路径信息 ./ ../
            //绝对路径：主要指以根目录开始的路径信息 c:/ d:/
            System.out.println("文件的绝对路径是:" + file.getAbsolutePath());
            System.out.println(file.delete() ? "文件删除成功" : "文件删除失败");
        } else {
            //3 若文件不存在则创建新的空文件
            try {
                System.out.println(file.createNewFile() ? "文件创建成功" : "文件创建失败");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //目录的创建和删除
//        File fs = new File("/Users/prim/Desktop/test/1/2/3");
//        if (fs.exists()) {
//            System.out.println("目录名称:" + fs.getName());
//            //删除单个目录
////            System.out.println(fs.delete() ? "目录删除成功" : "目录删除失败");
//
//            //多层目录需要层层删除目录
//            System.out.println(fs.delete() ? "目录删除成功" : "目录删除失败");
//        } else {
//            //创建单个目录
////            System.out.println(fs.mkdir() ? "目录创建成功" : "目录创建失败");
//            //创建多层目录
//            System.out.println(fs.mkdirs() ? "目录创建成功" : "目录创建失败");
//        }

        // 实现指定目录中所有内容打印出来 listFiles()方法
        File f2 = new File("/Users/prim/Desktop/test");
        File[] files = f2.listFiles();//获取test2目录下的所有内容
        for (File f : files) {
            String name = f.getName();
            //判断是否为文件
            if (f.isFile()) {
                System.out.println(name);
            }
            if (f.isDirectory()) {
                System.out.println("[" + name + "]");
            }
            //判断是否为目录
            System.out.println("f:");
        }
        //实现目录中所有内容获取的同时进行过滤
        System.out.println("-------------------------------------");
        File[] files1 = f2.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".txt");
            }
        });
        for (File file1 : files1) {
            String name = file1.getName();
            //判断是否为文件
            if (file1.isFile()) {
                System.out.println(name);
            }
            //判断是否为目录
            if (file1.isDirectory()) {
                System.out.println("[" + name + "]");
            }
        }
        System.out.println("---------------------");
        show(f2);
    }

    public static void show(File f2){
        File[] files = f2.listFiles();//获取test2目录下的所有内容
        for (File f : files) {
            String name = f.getName();
            //判断是否为文件
            if (f.isFile()) {
                System.out.println(name);
            }
            //判断是否为目录
            if (f.isDirectory()) {
                System.out.println("[" + name + "]");
                //获取目录中的所有内容
                show(f);
            }
        }
    }
}
