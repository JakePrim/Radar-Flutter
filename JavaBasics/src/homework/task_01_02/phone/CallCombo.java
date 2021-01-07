package homework.task_01_02.phone;

/**
 * 通话套餐类
 */
public class CallCombo extends AbsCombo implements CallServiceInterface {
    /**
     * 通话时长(分钟)
     */
    private int callDuration;

    /**
     * 短信条数
     */
    private int smsCount;

    public CallCombo() {
    }

    public CallCombo(double monthConsume, int callDuration, int smsCount) {
        super(monthConsume);
        setCallDuration(callDuration);
        setSmsCount(smsCount);
    }

    public int getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(int callDuration) {
        if (callDuration > 0) {
            this.callDuration = callDuration;
        } else {
            System.out.println("通过时长必须大于0");
        }
    }

    public int getSmsCount() {
        return smsCount;
    }

    public void setSmsCount(int smsCount) {
        if (smsCount >= 0) {
            this.smsCount = smsCount;
        } else {
            System.out.println("短信条数不能小于0");
        }
    }

    @Override
    public void show() {
        super.show();
        System.out.println("通话套餐：通话时长:" + getCallDuration() + "分钟，短信条数:" + getSmsCount());
    }

    @Override
    public void callService(int duration, PhoneCard phoneCard) {
        System.out.println("通话话时长：" + duration + "分钟");
        phoneCard.setCallDuration(phoneCard.getCallDuration() - duration);
        phoneCard.show();
    }
}
