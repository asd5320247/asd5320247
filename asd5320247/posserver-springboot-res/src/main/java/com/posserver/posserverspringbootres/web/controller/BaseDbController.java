package com.posserver.posserverspringbootres.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.posserver.posserverspringbootres.commons.ReturnManage;
import com.posserver.posserverspringbootres.pojo.BaseDb;
import com.posserver.posserverspringbootres.web.service.BaseDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class BaseDbController {
    @Autowired
    BaseDbService baseDbService;

    @Autowired
    ReturnManage returnManage;

    //查询baseDb接口，测试成功
    @RequestMapping("manage/baselist.xj")
    @ResponseBody
    public String list(){
        JSONObject data = new JSONObject();
//        if (!isLogin(request)) {
//            mav.setViewName("redirect:/login");
//            return mav;
//        }
        data.put("list",baseDbService.selectAllBaseDb());
        return returnManage.Success(data);
    }

    //脱网接口，未更改
    @RequestMapping(value = "generate", method = RequestMethod.GET)
    public ModelAndView generate() {
        ModelAndView mav = new ModelAndView("redirect:/manage/base");
//        if (!isLogin(request)) {
//            mav.setViewName("redirect:/login");
//            return mav;
//        }
        baseDbService.generateBaseDb(true);
        return mav;
    }

    //删除baseDb接口，测试成功
    @RequestMapping("manage/basedel.xj")
    @ResponseBody
    public String del(@RequestBody JSONObject jsonObject){
//        if (!isLogin(request)) {
//            mav.setViewName("redirect:/login");
//            return mav;
//        }
        BaseDb baseDb = new BaseDb(jsonObject.get("shopId").toString(),jsonObject.get("fileName").toString(),
                jsonObject.get("version").toString(),jsonObject.get("hash").toString(),
                jsonObject.get("createTime").toString());
        baseDbService.deleteBaseDb(baseDb.getHash());
        return list();
    }
}
