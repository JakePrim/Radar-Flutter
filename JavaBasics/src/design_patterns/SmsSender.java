package design_patterns;

public class SmsSender implements Sender {
    @Override
    public void send() {
        System.out.println("正在发送短信...");
    }
}
