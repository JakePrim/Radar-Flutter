package task_01_04;

import java.io.*;

public class CopyRunnable implements Runnable {
    private File copyFile;
    private File targetFile;

    public CopyRunnable(File copyFile, File targetFile) {
        this.copyFile = copyFile;
        this.targetFile = targetFile;
    }

    @Override
    public void run() {
        //创建输入流和输出流
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(copyFile));
            //输出流 ，将拷贝的文件的名字，设置到目标的目录中
            bos = new BufferedOutputStream(new FileOutputStream(targetFile.getAbsolutePath() + "/" + copyFile.getName()));
            int res = 0;
            //循环读取 写入到目标目录中
            while ((res = bis.read()) != -1) {
                bos.write(res);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //当拷贝完毕后 关闭输入和输出流
            if (null != bos) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != bis) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
