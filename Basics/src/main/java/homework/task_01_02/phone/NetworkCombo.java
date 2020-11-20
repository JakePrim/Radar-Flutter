package homework.task_01_02.phone;

public class NetworkCombo extends AbsCombo implements NetWorkServiceInterface {
    public NetworkCombo() {
    }

    public NetworkCombo(double monthConsume, String flow) {
        super(monthConsume);
        setFlow(flow);
    }

    private String flow;

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    @Override
    public void show() {
        super.show();
        System.out.println("上网套餐: 上网流量:" + getFlow());
    }

    @Override
    public void netService(String flow, PhoneCard phoneCard) {
        System.out.println("上网流量:" + flow);
        phoneCard.show();
    }
}
