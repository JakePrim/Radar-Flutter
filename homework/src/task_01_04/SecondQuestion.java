package task_01_04;

import java.io.File;

/**
 * 实现将指定目录中的所有内容删除，包含子目录中的内容都要全部删除。
 */
public class SecondQuestion {
    public static void main(String[] args) {
        String path = "/Users/prim/Desktop/a2";//指定的文件目录
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
                //1. 如果文件是目录的话
                if (f.isDirectory()) {
                    listFile(f);//就查找这个目录下的文件信息
                    //进行递归调用，当这个目录到底完毕后，依次将目录进行删除
                    boolean delete = f.delete();//
                    System.out.println(f.getName() + "目录" + (delete ? "删除成功" : "删除失败"));
                }
                //2. 如果是一个文件的话
                if (f.isFile()) {
                    boolean delete = f.delete();//直接删除即可
                    System.out.println(f.getName() + "文件" + (delete ? "删除成功" : "删除失败"));
                }
            }
        }
    }
}
