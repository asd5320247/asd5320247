package com.posserver.posserverspringbootres.commons;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PayCommon {
    String host = "http://tfs.whpdl.cn:15299/";
    String route = "wechat-service/flame/";

    public String doPay(String mcnId, String outTradeNo, String totalFee, String authCode, String appId, String content,String payMethod,String onlyId) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        JSONObject repJson = new JSONObject();
        repJson.put("mch_id", mcnId);
        repJson.put("out_trade_no", outTradeNo);
        repJson.put("total_fee", totalFee);
        repJson.put("auth_code", authCode);
        repJson.put("app_id", appId);
        repJson.put("content", content);
        repJson.put("pay_method",payMethod);
        repJson.put("only_id",onlyId);
        String method = null;
        if (appId != null && content != null && !"".equals(appId) && !"".equals(content)){
            method = "micro_pay";
        }else {
            method = "ali_face_pay";
        }
        RequestBody body = RequestBody.create(repJson.toJSONString(), mediaType);
        String url = host + route + method;
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            return "支付失败";
        }
        Integer returnCode = null;
        Integer code = null;
        String result = null;
            try {
                result = response.body().string();
            } catch (IOException e) {
                return "支付失败";
            }
            JSONObject jsonObject = JSONObject.parseObject(result);
            returnCode = jsonObject.getInteger("return_code");
            JSONObject data = jsonObject.getJSONObject("data");
            code = data.getInteger("code");
        if (returnCode == 0 && code == 0){
            return "支付成功";
        }else {
            return "支付失败";
        }
    }
}
