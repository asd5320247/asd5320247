package com.posserver.posserverspringbootres.pojo;

import java.io.Serializable;

public class ShopKey implements Serializable {

    private static final long serialVersionUID = 1L;

    private String groupId = "0000";
    private String shopId;

    public ShopKey() {

    }

    public ShopKey(String shopId) {
        this.shopId = shopId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
