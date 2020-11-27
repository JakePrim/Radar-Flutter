package interface_demo;

//接口只能继承接口 不能继承类
public interface Hunter extends Runner,Money {
    void hunt();
}
