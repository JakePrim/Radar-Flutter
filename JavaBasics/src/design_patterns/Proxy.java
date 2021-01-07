package design_patterns;

public class Proxy implements Sourceable {
    private Source source;

    public Proxy() {
        this.source = new Source();
    }

    @Override
    public void method() {
        source.method();
        System.out.println("我和装饰器模式是不一样的");
    }
}
