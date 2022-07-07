package com.posserver.posserverspringbootres.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.posserver.posserverspringbootres.commons.ReturnManage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
    @Autowired
    ReturnManage returnManage;

    //起始根接口，未更改
    @RequestMapping("/")
    public ModelAndView root(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("redirect:/manage/base");

//        if (!isLogin(request)) {
//            mav.setViewName("redirect:/login");
//            return mav;
//        }

        return mav;
    }

    //登录页面，未更改
    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    //登录判断接口，测试成功
    @RequestMapping("manage/login.xj")
    @ResponseBody
    public String login(@RequestBody JSONObject jsonObject) {
        String userName = jsonObject.get("userName").toString();
        String password = jsonObject.get("password").toString();
        JSONObject data = new JSONObject();
        if (userName.equals("admin") || userName.equals("bfuture")) {
            if (password.equals("20091208")) {
                data.put("userName",userName);
                data.put("password",password);
                data.put("massage","登录成功");
                return returnManage.Success(data);
            }
            data.put("userName", userName);
            data.put("message", "密码错误");
        } else {
            data.put("message", "用户名不正确");
        }
        return returnManage.Success(data);
    }

    //退出登录接口，未更改
    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("redirect:/login");
        request.getSession().removeAttribute("userName");
        return mav;
    }
}
