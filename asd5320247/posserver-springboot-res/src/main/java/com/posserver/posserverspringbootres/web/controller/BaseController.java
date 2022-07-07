package com.posserver.posserverspringbootres.web.controller;
import com.alibaba.fastjson.JSONObject;
import com.posserver.posserverspringbootres.commons.ReturnManage;
import com.posserver.posserverspringbootres.web.utils.InitBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import javax.servlet.http.HttpServletRequest;
import java.io.File;

public class BaseController {

    //定义一个受保护的 静态 最终 字符串
    protected static final String SUCCESS_MSG_2_KEY = "successMsg2";
    protected static final String ERROR_MSG_2_KEY = "errorMsg2";

    @Autowired
    ReturnManage returnManage;

    JSONObject data = new JSONObject();

    //ModelAttribute 注解 用在方法上代表会在此controller的每个方法执行前被执行 ，如果有返回值，则自动将该返回值加入到ModelMap中
    //注解中的括号代表的是key的名字，方法返回值是key对应的值
    @ModelAttribute("serverName")
    public String getServerName() {
        data.put("serverName","收银服务管理平台");
        return "收银服务管理平台" + returnManage.Success(data);
    }

    @ModelAttribute("serverVersion")
    public String getServerVersion() {
        data.put("serverVersion","20180908");
        return "20180908" + returnManage.Success(data);
    }

    //判断支付宝支付文件路径
    @ModelAttribute("hasAlipay")
    public String hasAlipay() {
        File propFile = new File(InitBean.getDataPath() + "/alipay.properties");
        data.put("hasAlipay",propFile.exists());
        return returnManage.Success(data);
    }

    //判断微信钱包付款吗支付文件路径
    @ModelAttribute("hasMicropay")
    public String hasMicropay() {
        File propFile = new File(InitBean.getDataPath() + "/micropay.properties");
        data.put("hasAlipay",propFile.exists());
        return returnManage.Success(data);
    }

    @ModelAttribute("hasWzqrcode")
    public String hasWzqrcode() {
        File propFile = new File(InitBean.getDataPath() + "/wzqrcode.properties");
        data.put("hasAlipay",propFile.exists());
        return returnManage.Success(data);
    }

    @ModelAttribute("hasYoudian")
    public String hasYoudian() {
        File propFile = new File(InitBean.getDataPath() + "/youdian.properties");
        data.put("hasAlipay",propFile.exists());
        return returnManage.Success(data);
    }

    @ModelAttribute("hasBestPay")
    public String hasBestPay() {
        File propFile = new File(InitBean.getDataPath() + "/bestpay.properties");
        data.put("hasAlipay",propFile.exists());
        return returnManage.Success(data);
    }

    //判断付呗支付文件路径
    @ModelAttribute("hasFubei")
    public String hasFuBei() {
        File propFile = new File(InitBean.getDataPath() + "/fubei.properties");
        data.put("hasAlipay",propFile.exists());
        return returnManage.Success(data);
    }

    @ModelAttribute("hasAEPay")
    public String hasAEPay() {
        File propFile = new File(InitBean.getDataPath() + "/aepay.properties");
        data.put("hasAlipay",propFile.exists());
        return returnManage.Success(data);
    }

    //判断微信支付文件路径
    @ModelAttribute("hasWeChatPay")
    public String hasWeChatPay() {
        File propFile = new File(InitBean.getDataPath() + "/wechatpay.properties");
        data.put("hasAlipay",propFile.exists());
        return returnManage.Success(data);
    }

    //判断登录
    protected String isLogin(HttpServletRequest request) {
        //新建一个username空字符串变量。
        String userName = null;
        //判断当前登录登录状态中的用户数据里的username是否为空，如果不为空就把值赋给上面的变量userName
        if (request.getSession().getAttribute("userName") != null) {
            userName = (String) request.getSession().getAttribute("userName");
        }

        //当前面的userName 有值的情况下，判断当前登录的servername是否为localhost，如果是则将userName变量的值改为admin
        if (request.getServerName().equals("localhost") && StringUtils.isEmpty(userName)) {
            userName = "admin";
        }
//返回判断userName的值是否为空值的判断结果
        data.put("isLogin",!StringUtils.isEmpty(userName));
        return returnManage.Success(data);
    }

}
