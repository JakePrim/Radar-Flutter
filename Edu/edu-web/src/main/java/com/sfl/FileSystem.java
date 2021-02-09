package com.sfl;

import java.io.Serializable;

/**
 * @program: edu-web
 * @Description: 文件上传Javabean
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-07 23:19
 * @PackageName: com.sfl
 * @ClassName: FileSystem.java
 **/
public class FileSystem implements Serializable {
    private String fileId;
    private String filePath;
    private String fileName;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
