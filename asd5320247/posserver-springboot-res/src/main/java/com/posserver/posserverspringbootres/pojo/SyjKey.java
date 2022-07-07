package com.posserver.posserverspringbootres.pojo;

import java.io.Serializable;

public class SyjKey implements Serializable {

    private static final long serialVersionUID = 1L;
    private String groupId = "0000";
    private String shopId;
    private String syjId;

    public SyjKey() {

    }

    public SyjKey(String shopId, String syjId) {
        super();
        this.shopId = shopId;
        this.syjId = syjId;
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

    public String getSyjId() {
        return syjId;
    }

    public void setSyjId(String syjId) {
        this.syjId = syjId;
    }
}
