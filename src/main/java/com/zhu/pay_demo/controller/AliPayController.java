package com.zhu.pay_demo.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.zhu.pay_demo.config.AlipayConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author ggBall
 * @version 1.0.0
 * @ClassName PayController.java
 * @Description TODO
 * @createTime 2021年09月12日 15:54:00
 */
@RestController
@Slf4j
public class AliPayController {


    @RequestMapping("/pay")
    public void payController(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.GATEWAY_URL, AlipayConfig.APP_ID, AlipayConfig.MERCHANT_PRIVATE_KEY, "json", AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGN_TYPE);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.RETURN_URL);
        alipayRequest.setNotifyUrl(AlipayConfig.NOTIFY_URL);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"), "UTF-8");
        //付款金额，必填
        String total_amount = new String(request.getParameter("WIDtotal_amount").getBytes("ISO-8859-1"), "UTF-8");
        //订单名称，必填
        String subject = new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"), "UTF-8");
        //商品描述，可空
        String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"), "UTF-8");

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //若想给BizContent增加其他可选请求参数，以增加自定义超时时间参数timeout_express来举例说明
        //alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
        //		+ "\"total_amount\":\""+ total_amount +"\","
        //		+ "\"subject\":\""+ subject +"\","
        //		+ "\"body\":\""+ body +"\","
        //		+ "\"timeout_express\":\"10m\","
        //		+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求参数可查阅【电脑网站支付的API文档-alipay.trade.page.pay-请求参数】章节

        //请求
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        // 后台直接返回 from表单
        response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
        response.getWriter().write(form);//直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
        // 也可以将form表单信息返回给前端
    }

    /**
     * @Description: 支付宝同步通知页面
     */
    @RequestMapping(value = "/alipayReturnNotice")
    public ModelAndView alipayReturnNotice(HttpServletRequest request, HttpServletRequest response) throws Exception {
        log.info("支付成功, 进入同步通知接口...");
        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        //调用SDK验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGN_TYPE);
        ModelAndView mv = new ModelAndView("redirect:/returnUrl");
        //——请在这里编写您的程序（以下代码仅作参考）——
        if(signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

            // 修改订单状态为支付成功，已付款; 同时新增支付流水
//            orderService.updateOrderStatus(out_trade_no, trade_no, total_amount);
//
//            Order order = orderService.getOrderById(out_trade_no);
//            Product product = productService.getProductById(order.getProductId());
            log.info("********************** 支付成功(支付宝同步通知) **********************");
            log.info("* 订单号: {}", out_trade_no);
            log.info("* 支付宝交易号: {}", trade_no);
            log.info("* 实付金额: {}", total_amount);
//            log.info("* 购买产品: {}", product.getName());
            log.info("***************************************************************");
            mv.addObject("out_trade_no", out_trade_no);
            mv.addObject("trade_no", trade_no);
            mv.addObject("total_amount", total_amount);
//            mv.addObject("productName", product.getName());
        }else {
            log.info("支付, 验签失败...");
        }
        return mv;
    }


    /**
     * @Description: 支付宝异步 通知页面
     */
    @RequestMapping(value = "/alipayNotifyNotice")
    @ResponseBody
    public String alipayNotifyNotice(HttpServletRequest request, HttpServletRequest response) throws Exception {

        log.info("支付成功, 进入异步通知接口...");

        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            /*valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");*/
            params.put(name, valueStr);
        }

        //调用SDK验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, AlipayConfig.SIGN_TYPE);

        //——请在这里编写您的程序（以下代码仅作参考）——

   /* 实际验证过程建议商户务必添加以下校验：
   1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
   2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
   3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
   4、验证app_id是否为该商户本身。
   */
        //验证成功
        if(signVerified) {
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

            if(trade_status.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意： 尚自习的订单没有退款功能, 这个条件判断是进不来的, 所以此处不必写代码
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            }else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //付款完成后，支付宝系统发送该交易状态通知

                // 修改叮当状态，改为 支付成功，已付款; 同时新增支付流水
//                orderService.updateOrderStatus(out_trade_no, trade_no, total_amount);
//
//                Order order = orderService.getOrderById(out_trade_no);
//                Product product = productService.getProductById(order.getProductId());

                log.info("********************** 支付成功(支付宝异步通知) **********************");
                log.info("* 订单号: {}", out_trade_no);
                log.info("* 支付宝交易号: {}", trade_no);
                log.info("* 实付金额: {}", total_amount);
//                log.info("* 购买产品: {}", product.getName());
                log.info("***************************************************************");
            }
            log.info("支付成功...");
        }else {//验证失败
            log.info("支付, 验签失败...");
        }
        return "success";
    }

}
