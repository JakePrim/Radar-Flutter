package design_patterns;

public class MailSendFactory implements Provider{
    @Override
    public Sender produce() {
        return new MailSender();
    }
}
