package task_01_04.five_question;

import java.io.File;
import java.io.Serializable;

/**
 * 用户信息
 */
public class User implements Serializable {
    private static final long serialVersionUID = 2989577185863174556L;
    //用户名
    private String name;
    //用户聊天内容
    private String content;
    //用户上传的文件
    private File file;

    public User() {
    }

    public User(String name, String content, File file) {
        this.name = name;
        this.content = content;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", file=" + file +
                '}';
    }
}
