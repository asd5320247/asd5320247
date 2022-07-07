package com.posserver.posserverspringbootres.pojo;

import java.io.Serializable;

public class PackPos extends PackPosKey implements Serializable {

    private static final long serialVersionUID = 1L;
    private String version;
    private String hash;
    private Integer type;
    private String createTime;

    public PackPos() {
    }

    public PackPos(String shopId, String fileName, String version, String hash, Integer type, String createTime) {
        super(shopId, fileName);
        this.version = version;
        this.hash = hash;
        this.type = type;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
