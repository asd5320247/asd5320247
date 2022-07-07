package com.posserver.posserverspringbootres.commons;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class ReturnManage {
    public String Success(JSONObject data){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data",data);
        jsonObject.put("return_code",0);
        jsonObject.put("return_msg","操作成功");
        return jsonObject.toJSONString();
    }

    public String Defult(JSONObject data){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data",data);
        jsonObject.put("return_code",1);
        jsonObject.put("return_msg","操作失败");
        return jsonObject.toJSONString();
    }
}
