package project.server;

import java.net.Socket;

/**
 * 服务端返回的响应类
 * “服务端收到客户端A的”管理员登录“请求，请求数据为”XXX“,调用”XXX“方法，响应结果为：{"result","success"}“
 */
public class Result {
    private String type;
    private Socket socket;


    @Override
    public String toString() {
        return "服务端收到客户端"+"的"+"{" +
                "type='" + type + '\'' +
                '}';
    }
}
