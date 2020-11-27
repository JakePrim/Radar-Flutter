package task_01_04.five_question;

import java.io.File;

public class User3 {
    public static void main(String[] args) {
        User user = new User("用户3", "", new File("/Users/prim/Desktop/a"));
        Client.launch("127.0.0.1", 9898, user);
    }
}
