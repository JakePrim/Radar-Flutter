package homework.task_01_02.phone;

/**
 * 手机卡类型
 */
public enum CardType {
    BIG("大卡"),
    SMALL("小卡"),
    MINIATURE("微型卡");

    private String name;

    CardType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
