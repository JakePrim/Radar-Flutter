package design_patterns;

public class Decorator implements Sourceable {
    private Sourceable source;

    public Decorator(Sourceable source) {
        this.source = source;
    }

    @Override
    public void method() {
        source.method();
        System.out.println("化妆之后你会更美丽");
    }
}
