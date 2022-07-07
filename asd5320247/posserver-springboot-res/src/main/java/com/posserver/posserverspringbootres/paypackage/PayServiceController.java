package com.posserver.posserverspringbootres.paypackage;

import com.alibaba.fastjson.JSONObject;
import com.posserver.posserverspringbootres.commons.PayCommon;
import com.posserver.posserverspringbootres.commons.ReturnManage;
import com.posserver.posserverspringbootres.dao.PayMapper;
import com.posserver.posserverspringbootres.pojo.Pay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class PayServiceController {

    @Autowired
    PayMapper payMapper;

    @Autowired
    PayCommon payCommon;

    @Autowired
    ReturnManage returnManage;

    @RequestMapping("/pay.xj")
    @ResponseBody
    public String PayTest(@RequestBody JSONObject jsonObject) throws IOException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String str = sdf.format(date);
        String outTradeNo = str;
        String authCode = jsonObject.get("auth_code").toString();
//        String authCode = httpServletRequest.getParameter("auth_code");
        String payMethod = jsonObject.get("pay_method").toString();
//        String payMethod = httpServletRequest.getParameter("pay_method");
        JSONObject data = new JSONObject();
        String status = null;
        Pay pay = new Pay();
        if ("支付宝".equals(payMethod)){
            status = payCommon.doPay("2021003131604120",outTradeNo,"1",authCode,null,null,payMethod,outTradeNo);
            pay.setMchId("2021003131604120");
            pay.setOutTradeNo(outTradeNo);
            pay.setTotalFee("1");
            pay.setAuthCode(authCode);
            pay.setAppId(null);
            pay.setContent(null);
            pay.setPayStatus(status);
            pay.setPayMethod(payMethod);
            pay.setOnlyid(outTradeNo);
            payMapper.insertPayList(pay);
            data.put("alipay",payMapper.getNewPayList(outTradeNo));
        }else if ("微信".equals(payMethod)){
            status = payCommon.doPay("1513347121",outTradeNo,"1",authCode,"wx0cdf771af191dcee","123",payMethod,outTradeNo);
            pay.setMchId("1513347121");
            pay.setOutTradeNo(outTradeNo);
            pay.setTotalFee("1");
            pay.setAuthCode(authCode);
            pay.setAppId("wx0cdf771af191dcee");
            pay.setContent("123");
            pay.setPayStatus(status);
            pay.setPayMethod(payMethod);
            pay.setOnlyid(outTradeNo);
            payMapper.insertPayList(pay);
            data.put("WeChatPay",payMapper.getNewPayList(outTradeNo));
        }else{
            data.put("status","支付方式有误，请重新输入支付宝或微信");
            return returnManage.Defult(data);
        }
        return returnManage.Success(data);
    }
}
