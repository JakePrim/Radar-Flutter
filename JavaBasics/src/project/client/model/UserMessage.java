package project.client.model;

import java.io.Serializable;

public class UserMessage<T> implements Serializable {
    private static final long serialVersionUID = 6561528337196260120L;
    private String type;//消息类型
    private T content;// 消息的具体内容 这里定义泛型可以任意设置消息内容，需要服务器根据消息类型转换即可

    public UserMessage() {
    }

    public UserMessage(String type, T user) {
        this.type = type;
        this.content = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "UserManager{" +
                "type='" + type + '\'' +
                ", user=" + content +
                '}';
    }
}
