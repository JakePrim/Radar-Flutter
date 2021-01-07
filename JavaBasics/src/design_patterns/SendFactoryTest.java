package design_patterns;

public class SendFactoryTest {
    public static void main(String[] args) {
        Sender mail = SendFactory.produceMail();
        mail.send();

        Provider provider = new MailSendFactory();
        Sender mailSend = provider.produce();
        mailSend.send();
    }
}
