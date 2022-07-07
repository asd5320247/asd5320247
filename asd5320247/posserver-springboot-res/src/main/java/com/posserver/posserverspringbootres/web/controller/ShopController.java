package com.posserver.posserverspringbootres.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.posserver.posserverspringbootres.db.OracleUtils;
import com.posserver.posserverspringbootres.web.utils.PosException;
import org.apache.commons.lang3.StringUtils;
import com.posserver.posserverspringbootres.commons.ReturnManage;
import com.posserver.posserverspringbootres.pojo.Shop;
import com.posserver.posserverspringbootres.web.service.SystemService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import static com.posserver.posserverspringbootres.web.controller.BaseController.ERROR_MSG_2_KEY;

@Controller
public class ShopController {

    private final static Logger logger = Logger.getLogger(ShopController.class);

    @Autowired
    SystemService systemService;

    @Autowired
    ReturnManage returnManage;

    //shop查询接口，测试成功
    @RequestMapping("/manage/shoplist.xj")
    @ResponseBody
    public String select(@RequestBody JSONObject jsonObject) {
        JSONObject data = new JSONObject();
        List AllShop = systemService.selectAllShop();
        String groupId = systemService.getDefaultGroupId();
        String shopId = jsonObject.get("shopId").toString();
        if (shopId != null && !shopId.isEmpty()) {
            logger.debug("shopId: " + shopId);
            Shop shop = systemService.selectShop(shopId);
            if (shop != null) {
                data.put("shop", shop);
            }
        }
        data.put("groupId", groupId);
        data.put("datalist", AllShop);
        return returnManage.Success(data);
    }

    //shop增加或修改接口，oracle测试链接失败，需要询问是否更改oracle数据库的数据库名字或者用户名密码
    @RequestMapping("/manage/shopadd.xj")
    @ResponseBody
    public String add(@RequestBody JSONObject jsonObject) throws UnsupportedEncodingException {

        JSONObject data = new JSONObject();
        Shop shop = systemService.selectShop(jsonObject.get("shopId").toString());

        if (shop.getGroupId() == null || shop.getGroupId().isEmpty()) {
            shop.setGroupId(systemService.getDefaultGroupId());
        }
        if (shop.getGroupName() == null || shop.getGroupName().isEmpty()) {
            shop.setGroupName(systemService.getDefaultGroupName());
        }

        if (StringUtils.isEmpty(shop.getDbHost()) || StringUtils.isEmpty(shop.getDbName()) || StringUtils.isEmpty(shop.getDbUser()) || StringUtils.isEmpty(shop.getDbPass())) {
            data.put(ERROR_MSG_2_KEY, "数据库连接信息输入错误");
            data.put("shop", shop);
            return data.toJSONString() + select(jsonObject);
        }

        OracleUtils oracle = new OracleUtils(shop.getDbHost(), shop.getDbName(), shop.getDbUser(), shop.getDbPass());
        try {
            oracle.connect();
        } catch (PosException | SQLException e) {
            logger.error(e);
            data.put(ERROR_MSG_2_KEY, e.getMessage());
            data.put("shop", shop);
            return data.toJSONString() + select(jsonObject);
        }
        shop.setShopId(oracle.getShopId());
        shop.setShopName(oracle.getShopName());
        oracle.close();
        systemService.saveShop(shop);
        return data.toJSONString() + select(jsonObject);
    }

    //shop删除接口，测试成功
    @RequestMapping("manage/shopdel.xj")
    @ResponseBody
    public String delete(@RequestBody JSONObject jsonObject) {
        String shopId = jsonObject.get("shopId").toString();
        systemService.deleteShop(shopId);
        String list = select(jsonObject);
        return list;
    }

}

