package throwable_demo;

public class PersonTest {
    public static void main(String[] args) {
        Person person = null;
        try {
            person = new Person("张飞", -30);
            System.out.println(person);
        } catch (AgeException e) {
            e.printStackTrace();
        }
    }
}
