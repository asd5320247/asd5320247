package com.posserver.posserverspringbootres.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.posserver.posserverspringbootres.commons.ReturnManage;
import com.posserver.posserverspringbootres.pojo.Syj;
import com.posserver.posserverspringbootres.web.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ManageController {
    @Autowired
    SystemService systemService;

    @Autowired
    ReturnManage returnManage;

    //跳转base接口，未更改
    @RequestMapping()
    public ModelAndView manage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("redirect:/manage/base");

//        if (!isLogin(request)) {
//            mav.setViewName("redirect:/login");
//            return mav;
//        }

        return mav;
    }

    //收银机查询接口，测试成功
    @RequestMapping("manage/syjlist.xj")
    @ResponseBody
    public String syjList() {
        JSONObject data = new JSONObject();
//        if (!isLogin(request)) {
//            mav.setViewName("redirect:/login");
//            return mav;
//        }
        data.put("list", systemService.selectAllSyj());
        return returnManage.Success(data);
    }

    //收银机删除接口，测试成功
    @RequestMapping("manage/syjdel.xj")
    @ResponseBody
    public String syjDel(@RequestBody JSONObject jsonObject) {
//        if (!isLogin(request)) {
//            mav.setViewName("redirect:/login");
//            return mav;
//        }
        Syj syj = new Syj(jsonObject.get("shopId").toString(),jsonObject.get("syjId").toString()
        ,jsonObject.get("version").toString(),jsonObject.get("dbVersion").toString(),jsonObject.getInteger("type"),
                jsonObject.get("updateTime").toString());
        systemService.deleteSyj(syj);
        return syjList();
    }

    //收银机更改接口测试成功，只能更改type，并且只能更改groupId为默认0000的那一条的type
    @RequestMapping("manage/syjtype.xj")
    @ResponseBody
    public String syjType(@RequestBody JSONObject jsonObject){
        JSONObject data = new JSONObject();
        Syj syj = new Syj(jsonObject.get("shopId").toString(),jsonObject.get("syjId").toString()
                ,jsonObject.get("version").toString(),jsonObject.get("dbVersion").toString(),jsonObject.getInteger("type"),
                jsonObject.get("updateTime").toString());

//        if (!isLogin(request)) {
//            return null;
//        }

        if (syj.getGroupId() == null || syj.getGroupId().isEmpty()) {
            syj.setGroupId(systemService.getDefaultGroupId());
        }
        syj.setType(666);
        systemService.updateSyjType(syj);
        data.put("syj",systemService.selectSyj(syj.getShopId(), syj.getSyjId()));
        return returnManage.Success(data);
    }
}

