package com.posserver.posserverspringbootres.pojo;

import java.io.Serializable;

public class BaseDb extends BaseDbKey implements Serializable {

    private static final long serialVersionUID = 1L;

    private String version;
    private String hash;
    private String createTime;

    public BaseDb() {

    }

    public BaseDb(String shopId, String fileName, String version, String hash, String createTime) {
        super(shopId, fileName);
        this.version = version;
        this.hash = hash;
        this.createTime = createTime;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
