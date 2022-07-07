package com.posserver.posserverspringbootres.pojo;

import java.io.Serializable;

public class BaseDbKey implements Serializable {

    private static final long serialVersionUID = 1L;

    private String groupId = "0000";
    private String shopId;
    private String fileName;

    public BaseDbKey() {
    }

    public BaseDbKey(String shopId, String fileName) {
        this.shopId = shopId;
        this.fileName = fileName;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
