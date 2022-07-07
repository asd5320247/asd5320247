package com.posserver.posserverspringbootres.pojo;

public class Pay {
    private String mchId;
    private String outTradeNo;
    private String totalFee;
    private String authCode;
    private String appId;
    private String content;
    private String payStatus;
    private String payMethod;
    private String onlyid;

    @Override
    public String toString() {
        return "Pay{" +
                "mchId='" + mchId + '\'' +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", totalFee='" + totalFee + '\'' +
                ", authCode='" + authCode + '\'' +
                ", appId='" + appId + '\'' +
                ", content='" + content + '\'' +
                ", payStatus='" + payStatus + '\'' +
                ", payMethod='" + payMethod + '\'' +
                ", onlyid='" + onlyid + '\'' +
                '}';
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public String getOnlyid() {
        return onlyid;
    }

    public void setOnlyid(String onlyid) {
        this.onlyid = onlyid;
    }

    public Pay() {
    }

    public Pay(String mchId, String outTradeNo, String totalFee, String authCode, String appId, String content, String payStatus, String payMethod, String onlyid) {
        this.mchId = mchId;
        this.outTradeNo = outTradeNo;
        this.totalFee = totalFee;
        this.authCode = authCode;
        this.appId = appId;
        this.content = content;
        this.payStatus = payStatus;
        this.payMethod = payMethod;
        this.onlyid = onlyid;
    }
}
