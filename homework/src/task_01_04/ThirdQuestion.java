package task_01_04;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用线程池将一个目录中的所有内容拷贝到另外一个目录中，包含子目录中的内容。
 */
public class ThirdQuestion {
    public static void main(String[] args) {
        String path = "/Users/prim/Desktop/a";//指定的文件目录
        String targetPath = "/Users/prim/Desktop/b";//拷贝的目标文件目录
        File file = new File(path);
        //拷贝的目标文件目录
        File targetFile = new File(targetPath);
        //目标文件目录不存在则创建目录
        if (!targetFile.exists()) {
            boolean mkdirs = targetFile.mkdirs();
            System.out.println(mkdirs ? "目标文件目录创建成功" : "目标文件目录创建失败");
        }
        //创建文件个数的线程池
        ExecutorService es = Executors.newFixedThreadPool(10);
        if (file.exists()) {
            queryFile(file, targetFile, es);
            es.shutdown();
        } else {
            System.out.println("指定文件目录不存在");
        }

    }

    private static void queryFile(File file, File targetFile, ExecutorService es) {
        File[] files = file.listFiles();
        System.out.println("文件的个数:" + files.length);
        for (File f : files) {
            if (f.isFile()) {
                //拷贝文件
                es.submit(new CopyRunnable(f, targetFile));
            }
            if (f.isDirectory()) {
                //如果是目录则查找文件
                queryFile(f, targetFile, es);
            }
        }
    }
}
