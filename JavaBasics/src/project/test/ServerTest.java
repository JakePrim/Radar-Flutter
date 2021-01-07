package project.test;

import project.server.dao.ServerDao;
import project.server.ServerInitClose;
import project.server.ServerView;

import java.io.IOException;

/**
 * “服务端收到客户端A的”管理员登录“请求，请求数据为”XXX“,调用”XXX“方法，响应结果为：{"result","success"}“
 */
public class ServerTest {
    public static void main(String[] args) {
        ServerInitClose sic = null;
        try {
            sic = new ServerInitClose();
            sic.serverInit();
            ServerDao serverDao = new ServerDao();
            ServerView sv = new ServerView(sic, serverDao);
            sv.serverReceive();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (null != sic) {
                try {
                    sic.serverClose();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
