package task_01_04.four_question;

import java.io.Serializable;

/**
 * 用户管理类
 */
public class UserManager implements Serializable {
    private static final long serialVersionUID = -3056405940393398377L;
    private String type;
    private User user;

    public UserManager() {
    }

    public UserManager(String type, User user) {
        this.type = type;
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserManager{" +
                "type='" + type + '\'' +
                ", user=" + user +
                '}';
    }
}
