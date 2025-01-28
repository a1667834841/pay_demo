package com.zhu.pay_demo.config;

import java.io.FileWriter;
import java.io.IOException;

public class AlipayConfig {
    //↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String APP_ID = "2016102300741650";

    // 商户私钥，您的PKCS8格式RSA2私钥
	public static String MERCHANT_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCOaT2a/Vq9FoAoXmoWJnaInJ6tCp7Ql/pauUi06pLmoGeXDo9JOqDzxubPWE1/cUR18sVl8ATtPgvx0UncIvcUJOAQoQJUbsuGtygt6BqI7l7/H1LTFMJJWCmV05CvUc/qEz1Lzcvyv1+USBi1rXL/6KhWFJLSDOXgSQamQGV8DvEMLDQZsV/v7lIpEU0XH0R6LYsDde2+WlGQG760vMOpTji1Z6fmawc9XVoQcNZacUuD0lmmrOssxUkUJEtCxQ7HwLqid+4fjTPPaIU3KUGqI7rzrOlWQHwbwRLf/8Gy+W4wVy9J+4M22QYHW/7O67fa5IJkb2S0c8njSrA+FURhAgMBAAECggEAPgGWoEBF3OTKIkmRmVlf7PV8pU1lft9dU1kvTg6ArcgTspLVUoYjhGELKCaDf8TakRyGVG8gRgfo/X4p0Z6rw+qWYMcqE4c2OM7QwMQ5oXtgg2apEl0MhxDK8oBuk9dkVr/PxYyyi1xGCnMlxxzhYIl1mh1VG/4frPs+2O5bAZt447vlV+CwwJtLUbx4/28Hh2zlAnJHZrfon4QhVaq7zKqfM1s28fpKHAWzaevFesS8aWq9PJTHyaFUy8bh71kgkNzfghcUwTV4Qh6m2MVdlLlS9uQQKQtyXixx1Tui8vUejX3u9MoCj0vtt4kO6ePmQB4UsdU8UF9rWIEvfIrgAQKBgQDyAuf5RL1QU5TB6KrY/W4x6oB8yYh2ZDt1yED3DTVnagobIf4TCbd8z/qIiaUwrJ5pRP/X01houAITIvz1hv757OTR7hpyxersio9zXp5uzLlaog6yxO4IrLmCJYFRRnAdqd6i+ctxe0eAvidl6HPVKzpB695JsYjCVB9VKIQ5YQKBgQCWpIc1uqhj5Q3Re4z/SLqL8x7fT8LxZkYoyaavINlRuFT0w9PavSokcjbfWJBjIgv8QJIDuqP7+HZSWDhtliNU1JDsIcjV4Nly1DZa4zUZMz8C1+3evsr4MktqkR9U0EMk9FlFB1cJvs/M3WpnZ8HjvqKp53IJPhQBLmYOVgXrAQKBgQDpwhDa3qCVpAW9vsS3TyNPJ5jv76HV6T7dp43Ik4OP3gbg6laFgQ8wiZsOoxViDHn+aF1O1J9Clum6HkODzN8f/MeQVpJJZg5iZ/vjjfMuRqSKfJoB5uGck2mj4iIkpQrKPDgF542wkUkhe4qGjp7DujtNTUyqlcbpHWQeV0JQQQKBgQCS9waWK3lWo8N8aJ3g/FJKcLDgyg70tYOjDUAZwOFjcYMom9gWoc/4aJkSAnQy0qeAvHn3O2H6U6cTpM/AUeY8P3j3depahpd+CHzS5LEL2cwSeRiu49Jem6qp19UKSHgJGH3y8zs93bMbObQyF664AmbtfUfqrm6TzHFycQgrAQKBgQDTEKmZ780DzHFUCvSU30QbufNi5eKD+MnWZxJz18byyR+z2myexsy29/cI6pGpFzBDtXCwLJQiqvyi1xfTui0chwaGiWZ7ZQRl5ZxQ4G49MTageO6emOtv9j/zSyu+u3IZ76pGm2gRoBrNJ4GLzxmuns4o0Nj/jNDZY21G+TXIFw==";
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjfD225PyiPkiBEPGA6T8cbGoHl2YZtVeQBa4UyjaktthnR7cdgL5X0f0JsmmAcBpCGsqP9iKT4jIS2W1x8mAPobZSUATvk1EYNAuIjWRr3qV0+GAh5R51rV4Dh4T7W3qz0rkXdtA3OhCLKR44ALd6KLN+PyO/UVxIZhAoHFF1zP17IaP6S02jGweNHog3rc9r3o94gggCXyNBdJPoHA+4S1jVfUj1IwzZKhbCCFj1fmwMh40vmfvxA+djnDvEWcIj8Roi5bv9+Dcsn20VKQFUgxY0OWlm4aM2AKLcRGsuMqjKUOAxfML1OaJwuPsn0V6Z+7XPG6aZO/0Bl6mwiqV5wIDAQAB";
    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String NOTIFY_URL = "http://127.0.0.1:9001/alipayNotifyNotice";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 前后端的话 设置成前端的地址
	public static String RETURN_URL = "http://127.0.0.1:9001/alipayReturnNotice";

    // 签名方式
    public static String SIGN_TYPE = "RSA2";

    // 字符编码格式
    public static String CHARSET = "utf-8";

    // 支付宝网关
    public static String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";

    // log path
	public static String LOG_PATH = "D:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(LOG_PATH + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
