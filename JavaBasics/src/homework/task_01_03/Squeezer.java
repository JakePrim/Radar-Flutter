package homework.task_01_03;

import java.util.Objects;

/**
 * 扑克牌类
 */
public class Squeezer implements Comparable<Squeezer>{
    //扑克牌的名称
    private String name;
    //扑克牌的花色
    private String color;
    //扑克牌的大小
    private int size;

    public Squeezer() {
    }

    public Squeezer(String name, String color, int size) {
        this.name = name;
        this.color = color;
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Squeezer squeezer = (Squeezer) o;
        return size == squeezer.size &&
                Objects.equals(name, squeezer.name) &&
                Objects.equals(color, squeezer.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, color, size);
    }

    @Override
    public String toString() {
        return "Squeezer{" +
                "name='" + name + '\'' +
                ", color='" + getColorStr() + '\'' +
                ", size=" + size +
                '}';
    }

    private String getColorStr() {
        return "A".equals(color) ? "红桃" : "B".equals(color) ? "黑桃" : "C".equals(color) ? "方片" : "D".equals(color) ? "梅花" : "";
    }

    @Override
    public int compareTo(Squeezer o) {
        int compare = this.getSize() - o.getSize();//比较扑克牌大小
        if (0 == compare){//大小相同比较扑克牌的花色
            return this.getColor().compareTo(o.getColor());
        }
        //比较扑克牌的大小
        return compare;
    }
}
