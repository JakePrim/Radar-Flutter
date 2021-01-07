package org.prim.ioc;

/**
 * @author prim
 */
public class Food {
    private String name;
    private String taste;
    private String kind;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", taste='" + taste + '\'' +
                ", kind='" + kind + '\'' +
                '}';
    }
}
