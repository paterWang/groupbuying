package com.feitianzhu.fu700.model;

/**
 * Created by Vya on 2017/9/13 0013.
 * 我要收款
 */

public class MineCollectionMoneyModel {


    /**
     * qrCodeUrl : http://www.fu700.cn/?id=7
     * merchantName : 华为手看看机店
     * merchantHeadImg : http://118.190.156.13/user/merchant/453044af7c0c4e239f1cdb800bf3af1a.png
     */

    private String qrCodeUrl;
    private String merchantName;
    private String merchantHeadImg;

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantHeadImg() {
        return merchantHeadImg;
    }

    public void setMerchantHeadImg(String merchantHeadImg) {
        this.merchantHeadImg = merchantHeadImg;
    }
}
