package com.feitianzhu.fu700.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vya on 2017/11/2 0002.
 */

public class ShopRecordWxModel {

    /**
     * package : Sign=WXPay
     * appid : wxdd9af54974229ca3
     * sign : 1899e2a1ed2f9d1492b31e9456d470f5
     * partnerid : 1490194492
     * prepayid : wx2017110217341178f03789320111123524
     * noncestr : g0ttuacgjfcrcz3liaidg9g4l7dxuj84
     * timestamp : 1509615251
     */

    @SerializedName("package")
    private String packageX;
    private String appid;
    private String sign;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private int timestamp;

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
