package design_patterns;

public class SmsSendFactory implements Provider {
    @Override
    public Sender produce() {
        return new SmsSender();
    }
}
