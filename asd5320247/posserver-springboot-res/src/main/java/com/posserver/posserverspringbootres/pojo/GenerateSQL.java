package com.posserver.posserverspringbootres.pojo;

import java.io.Serializable;

public class GenerateSQL implements Serializable {

    private static final long serialVersionUID = 1L;
    private String groupId = "0000";
    private String shopId;
    private String tableName;
    private String memo;
    private String createTable;
    private String selectTable;
    private Integer status;

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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCreateTable() {
        return createTable;
    }

    public void setCreateTable(String createTable) {
        this.createTable = createTable;
    }

    public String getSelectTable() {
        return selectTable;
    }

    public void setSelectTable(String selectTable) {
        this.selectTable = selectTable;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
