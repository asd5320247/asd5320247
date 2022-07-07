package com.posserver.posserverspringbootres.pojo;

import java.io.Serializable;

public class Shop extends ShopKey implements Serializable {

    private static final long serialVersionUID = 1L;

    private String groupName = "默认客户";
    private String shopName;
    private String dbHost;
    private String dbName;
    private String dbUser;
    private String dbPass;
    private Integer status;

    public Shop() {

    }

    public Shop(String shopId, String shopName) {
        super(shopId);
        this.shopName = shopName;
        this.status = 1;
    }

    public Shop(String shopId, String shopName, String dbHost, String dbName, String dbUser, String dbPass, Integer status) {
        this(shopId, shopName);
        this.dbHost = dbHost;
        this.dbName = dbName;
        this.dbUser = dbUser;
        this.dbPass = dbPass;
        this.status = status;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getDbHost() {
        return dbHost;
    }

    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPass() {
        return dbPass;
    }

    public void setDbPass(String dbPass) {
        this.dbPass = dbPass;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
