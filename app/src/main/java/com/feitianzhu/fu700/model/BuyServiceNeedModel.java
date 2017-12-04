package com.feitianzhu.fu700.model;

import java.io.Serializable;

/**
 * Created by Vya on 2017/9/12.
 * 购买服务界面需要存储的数据
 */

public class BuyServiceNeedModel implements Serializable{

    public static final int  SERVICE_DETAIL_BEAN = 0x0010;  //来自于服务详情页
    public static final int  HOT_SERVICE_BEAN = 0x0011;  //来自于热门服务


    public int serviceId;
    public String serviceName;
    public double price;
    public double rebate;
    public int userId;//商户ID
    public String headImg;
    public String contactPerson;
    public String contactTel;
    public String serviceAddr;
    public String payPass;
    public String payChannel;
    public int type;
    public int merchantId;



    public BuyServiceNeedModel(int serviceId, String serviceName, double price, double rebate, int userId, String headImg, String contactPerson, String contactTel, String serviceAddr) {

        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.price = price;
        this.rebate = rebate;
        this.userId = userId;
        this.headImg = headImg;
        this.contactPerson = contactPerson;
        this.contactTel = contactTel;
        this.serviceAddr = serviceAddr;
    }
}
