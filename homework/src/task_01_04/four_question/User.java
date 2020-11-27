package task_01_04.four_question;

import java.io.Serializable;

/**
 * 用户类
 */
public class User implements Serializable {
    private static final long serialVersionUID = -762997924636517581L;
    private String name;
    private String password;

    public User() {
    }

    public User(String name, String password) {
        setName(name);
        setPassword(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
