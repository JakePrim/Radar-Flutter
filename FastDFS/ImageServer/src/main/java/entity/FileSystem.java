package entity;

import java.io.Serializable;

/**
 * @program: ImageServer
 * @Description: 上传文件实体类
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-01 21:46
 * @PackageName: entity
 * @ClassName: FileSystem.java
 **/
public class FileSystem implements Serializable {
    private String fileId;
    private String filePath;
    private String fileName;

    public FileSystem() {
    }

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

    @Override
    public String toString() {
        return "FileSystem{" +
                "fileId='" + fileId + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
