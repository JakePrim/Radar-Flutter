package com.pbatis.constructor;

/**
 * @program: PBatisTest
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-15 10:27
 * @PackageName: com.pbatis.constructor
 * @ClassName: ConstructorTest.java
 **/
public class ConstructorTest {
    public static void main(String[] args) {
        ComputerBuilder builder = new ComputerBuilder();
        builder.installDisplayer("显示器");
        builder.installMouse("鼠标");
        builder.installMainUnit("主机");
        builder.installKeyboard("键盘");

        Computer computer = builder.getComputer();
        System.out.println(computer);
    }
}
