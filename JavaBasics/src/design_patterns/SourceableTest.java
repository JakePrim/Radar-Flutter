package design_patterns;

public class SourceableTest {
    public static void main(String[] args) {
        Sourceable s = new Source();
        s.method();

        Sourceable sourceable = new Decorator(s);
        sourceable.method();

        Sourceable sourceable1 = new Proxy();
        sourceable1.method();
    }
}
