package throwable_demo;

/**
 * 自定义异常类
 */
public class AgeException extends Exception {
    //序列号 与序列化操作有关
    static final long serialVersionUID = -3387516993124229948L;

    public AgeException() {
    }

    public AgeException(String message) {
        super(message);
    }
}
