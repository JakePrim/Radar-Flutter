package socket_demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UrlTest {
    public static void main(String[] args) {
        //1. 使用参数指定的字符串来构造对象
        URL url = null;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL("https://www.lagou.com/");
            //2. 获取相关信息并打印
            System.out.println("协议:" + url.getProtocol());//https
            System.out.println("主机名:" + url.getHost());//www.lagou.com
            System.out.println("端口号:" + url.getPort());//-1
            //3. 建立连接并读取相关信息
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();//得到一个输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String str = null;
            while ((str = br.readLine()) != null) {
                System.out.println(str);
            }
            br.close();
            //断开连接
            urlConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
