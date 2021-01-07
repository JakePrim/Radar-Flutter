package task_01_04;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用线程池将一个目录中的所有内容拷贝到另外一个目录中，包含子目录中的内容。
 */
public class ThirdQuestion {
    public static void main(String[] args) {
        String path = "/Users/prim/Desktop/a";//1. 指定要拷贝的文件目录
        String targetPath = "/Users/prim/Desktop/b";//2. 指定拷贝到的目标文件目录
        //3. 创建File对象
        File file = new File(path);
        //拷贝的目标文件目录
        File targetFile = new File(targetPath);
        //4. 目标文件目录不存在则创建目录
        if (!targetFile.exists()) {
            boolean mkdirs = targetFile.mkdirs();
            System.out.println(mkdirs ? "目标文件目录创建成功" : "目标文件目录创建失败");
        }
        //5. 创建线程池-10个线程进行并行的拷贝文件
        ExecutorService es = Executors.newFixedThreadPool(10);
        if (file.exists()) {//如果要拷贝的文件存在
            queryFile(file, targetFile, es);
            es.shutdown();
        } else {
            System.out.println("指定文件目录不存在");
        }

    }

    /**
     * 去查找文件目录中的所有内容
     * @param file
     * @param targetFile
     * @param es
     */
    private static void queryFile(File file, File targetFile, ExecutorService es) {
        //获取目录下的所有文件列表
        File[] files = file.listFiles();
        System.out.println("文件的个数:" + files.length);
        for (File f : files) {
            //如果是一个文件的话
            if (f.isFile()) {
                //拷贝文件 通过线程池启动一个线程 执行拷贝操作
                es.submit(new CopyRunnable(f, targetFile));
            }
            //如果是一个目录的话，就判断是否存在子目录 如果存在子目录就查找文件进行拷贝
            if (f.isDirectory()) {
                //如果是目录则查找文件
                queryFile(f, targetFile, es);
            }
        }
    }
}
