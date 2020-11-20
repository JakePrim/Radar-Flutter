package homework.task_01_02.phone;

public class PhoneTest {
    public static void main(String[] args) {
        //手机卡
        PhoneCard phoneCard = new PhoneCard(CardType.BIG, "19320922039", "张飞", "123456", 10.0f, 100, "20G");
        phoneCard.show();
        //通话套餐
        AbsCombo callCombo = new CallCombo(30, 100, 50);
        callCombo.show();
        //上网套餐
        AbsCombo networkCombo = new NetworkCombo(10, "20G");
        networkCombo.show();

        CallCombo combo1 = (CallCombo) callCombo;
        combo1.callService(10, phoneCard);

        NetworkCombo combo2 = (NetworkCombo) networkCombo;
        combo2.netService("10G", phoneCard);
    }
}
