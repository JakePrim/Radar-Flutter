package com.pbatis.constructor;

/**
 * @program: PBatisTest
 * @Description:
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-03-15 10:24
 * @PackageName: com.pbatis.constructor
 * @ClassName: ComputerBuilder.java
 **/
public class ComputerBuilder {
    private Computer computer = new Computer();

    /**
     * 安装显示器
     */
    public void installDisplayer(String displayer) {
        computer.setDisplayer(displayer);
    }

    public void installMainUnit(String mainUnit) {
        computer.setMainUnit(mainUnit);
    }

    public void installMouse(String mouse) {
        computer.setMouse(mouse);
    }

    public void installKeyboard(String keyboard) {
        computer.setKeyboard(keyboard);
    }

    public Computer getComputer() {
        return computer;
    }
}
