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
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(copyFile));
            bos = new BufferedOutputStream(new FileOutputStream(targetFile.getAbsolutePath() + "/" + copyFile.getName()));
            int res = 0;
            while ((res = bis.read()) != -1) {
                bos.write(res);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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
