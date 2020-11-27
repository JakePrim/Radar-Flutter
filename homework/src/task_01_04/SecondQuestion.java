package task_01_04;

import java.io.File;

/**
 * 实现将指定目录中的所有内容删除，包含子目录中的内容都要全部删除。
 */
public class SecondQuestion {
    public static void main(String[] args) {
        String path = "/Users/prim/Desktop/a";//指定的文件目录
        File file = new File(path);
        if (file.exists()) {
            //获取所有文件
            listFile(file);
        }
    }

    private static void listFile(File file) {
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    listFile(f);
                    boolean delete = f.delete();
                    System.out.println(f.getName() + "目录" + (delete ? "删除成功" : "删除失败"));
                }
                if (f.isFile()) {
                    boolean delete = f.delete();
                    System.out.println(f.getName() + "文件" + (delete ? "删除成功" : "删除失败"));
                }
            }
        }
    }
}
