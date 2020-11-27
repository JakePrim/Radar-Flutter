package interface_demo;

public interface InterfaceTest {
    //接口只能有常量
    /*public static final*/int cnt = 0;

    //从jdk1.9开始允许接口中出现私有方法
//    private void show(){
//
//    }
    //只能有抽象方法 注释中的关键字可以省略
    /*public abstract*/void show();
}
