package annotation_demo;

import java.util.Objects;

@MyAnnotation(value2 = "2") //使用注解标记在Person类
public class Person {
    @MyAnnotation(value2 = "3")
    private String name;

    public Person(@MyAnnotation(value2 = "4") String name) {
        this.name = name;
    }

    public abstract class P{
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}
