package project.test;

import project.client.ClientInitClose;
import project.client.ClientScanner;
import project.client.ClientView;

import java.io.IOException;

/**
 * 在线考试系统
 */
public class ClientTest {
    public static void main(String[] args) {
        ClientInitClose cic = null;
        try {
            cic = new ClientInitClose();
            cic.clientInit();
            ClientView cv = new ClientView(cic);
            cv.clientMainPage();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != cic) {
                    cic.clientClose();
                }
                ClientScanner.scannerClose();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
