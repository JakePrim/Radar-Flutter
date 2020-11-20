package homework.task_01_02.phone;

/**
 * 手机卡类
 */
public class PhoneCard {
    /**
     * 手机卡类型
     */
    private CardType cardType;

    /**
     * 卡号
     */
    private String cardNum;

    /**
     * 用户名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 账户余额
     */
    private float balance;

    /**
     * 通话时长(分钟)
     */
    private int callDuration;

    /**
     * 上网流量
     */
    private String traffic;

    public PhoneCard() {
    }

    public PhoneCard(CardType cardType, String cardNum, String name, String password, float balance, int callDuration, String traffic) {
        setCardType(cardType);
        setCardNum(cardNum);
        setName(name);
        setPassword(password);
        setBalance(balance);
        setCallDuration(callDuration);
        setTraffic(traffic);
    }

    /**
     * 显示信息
     */
    public void show() {
        System.out.println("卡号:" + getCardNum() + " 用户名:" + getName() + " 当前余额:" + getBalance());
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        if (balance > 0) {
            this.balance = balance;
        }else {
            System.out.println("账户余额为0，请充值");
        }
    }

    public int getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(int callDuration) {
        if (callDuration > 0) {
            this.callDuration = callDuration;
        } else {
            System.out.println("通话时长不足，请充值");
        }
    }

    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic;
    }
}
