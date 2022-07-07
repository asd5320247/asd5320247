package com.posserver.posserverspringbootres.pojo;

import java.io.Serializable;

public class Syj extends SyjKey implements Serializable {

    private static final long serialVersionUID = 1L;
    private String version;
    private String dbVersion;
    private Integer type;
    private String updateTime;

    public Syj() {

    }

    public Syj(String shopId, String syjId, String version, String dbVersion, Integer type, String updateTime) {
        super(shopId, syjId);
        this.version = version;
        this.dbVersion = dbVersion;
        this.type = type;
        this.updateTime = updateTime;
    }

    public Syj(String shopId, String syjId) {
        super(shopId, syjId);
        this.type = 9;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDbVersion() {
        return dbVersion;
    }

    public void setDbVersion(String dbVersion) {
        this.dbVersion = dbVersion;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String string) {
        this.updateTime = string;
    }

}
