package com.feitianzhu.fu700.model;

import java.io.Serializable;

/**
 * Created by Vya on 2017/9/11 0011.
 */

public class ServiceDetailModel implements Serializable {


    /**
     * serviceId : 3
     * userId : 2
     * headImg : http://118.190.156.13/user/head/head.png
     * merchantId : 7
     * merchantName : 华为手看看机店
     * serviceName : iphone 6s 64G 银灰色
     * price : 1.1
     * rebate : 1.1
     * adImg : http://118.190.156.13/user/merchant/service/7b128700e6bb4193b80eef1b164e90d5.png
     * photos : http://118.190.156.13/user/merchant/service/7b128700e6bb4193b80eef1b164e90d5.png,http://118.190.156.13/user/merchant/service/031c2ddea9c2490ea74d7afb65828ac5.png,http://118.190.156.13/user/merchant/service/dsadadas.png
     * contactPerson : dsa
     * contactTel : 12388885555
     * serviceDesc : 老王卖瓜xxxx
     * serviceAddr : 老王卖瓜.com
     * status : 1
     * collectId : null
     * shareUrl : http://118.190.156.13:32819/fhwl/invest?serviceId=3&type=1&noncestr=ow4oa1ixd3gvctajewus5c53malz2v2v&timestamp=1506330111859&&sign=09c40f5c80f61d6f141f1c157141ef36
     */

    private int serviceId;
    private int userId;
    private String headImg;
    private int merchantId;
    private String merchantName;
    private String serviceName;
    private double price;
    private double rebate;
    private String adImg;
    private String photos;
    private String contactPerson;
    private String contactTel;
    private String serviceDesc;
    private String serviceAddr;
    private String status;
    private int collectId;
    private String shareUrl;

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRebate() {
        return rebate;
    }

    public void setRebate(double rebate) {
        this.rebate = rebate;
    }

    public String getAdImg() {
        return adImg;
    }

    public void setAdImg(String adImg) {
        this.adImg = adImg;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getServiceDesc() {
        return serviceDesc;
    }

    public void setServiceDesc(String serviceDesc) {
        this.serviceDesc = serviceDesc;
    }

    public String getServiceAddr() {
        return serviceAddr;
    }

    public void setServiceAddr(String serviceAddr) {
        this.serviceAddr = serviceAddr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCollectId() {
        return collectId;
    }

    public void setCollectId(int collectId) {
        this.collectId = collectId;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }
}
