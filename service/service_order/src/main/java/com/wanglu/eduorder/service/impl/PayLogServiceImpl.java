package com.wanglu.eduorder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.wanglu.eduorder.entity.Order;
import com.wanglu.eduorder.entity.PayLog;
import com.wanglu.eduorder.mapper.PayLogMapper;
import com.wanglu.eduorder.service.OrderService;
import com.wanglu.eduorder.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanglu.eduorder.utils.HttpClient;
import com.wanglu.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-12-02
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {
    @Autowired
    private OrderService orderService;
    //生成二维码接口
    @Override
    public Map createActive(String orderNo) {
        try {
            //1根据订单号查询订单信息
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no", orderNo);
            Order order = orderService.getOne(wrapper);
            //2.使用map设置二维码相关参数
            Map m = new HashMap();
            //1、设置支付参数
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            m.put("body", order.getCourseTitle());
            m.put("out_trade_no", orderNo);
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
            m.put("spbill_create_ip", "127.0.0.1");
            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
            m.put("trade_type", "NATIVE");
            //3 发送httpclient轻汽油 传递参数xml格式
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            //执行请求
            client.post();
            //4 得到发送请求信息返回
            String content = client.getContent();
            //把xml转化成map集合 把map集合返回
            Map<String, String> resultMap = WXPayUtil.xmlToMap(content);
            Map map = new HashMap<>();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            map.put("result_code", resultMap.get("result_code"));//返回二维码操作状态码
            map.put("code_url", resultMap.get("code_url"));//二维码地主
            return map;
        } catch (Exception e) {
            throw new GuliException(20001, "生成二维码失败");
        }
    }
}
