package com.sfl.pay;

/**
 * @program: edu-web
 * @Description: 支付配置信息
 * @author: sufulu
 * @version: 1.0.0
 * @create: 2021-02-27 20:20
 * @PackageName: com.sfl.pay
 * @ClassName: PayConfig.java
 **/
public class PayConfig {
    //企业公众号ID
    public static String appid = "wx8397f8696b538317"; // 财付通平台的商户帐号
    public static String partner = "1473426802";
    // 财付通平台的商户密钥
    public static String partnerKey = "8A627A4578ACE384017C997F12D68B23"; // 回调URL
    public static String notifyurl = "http://a31ef7db.ngrok.io/WeChatPay/WeChatPayNotify";
}
