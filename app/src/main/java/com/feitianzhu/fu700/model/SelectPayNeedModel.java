package com.feitianzhu.fu700.model;

import java.io.Serializable;

/**
 * Created by Vya on 2017/9/13 0013.
 * 选择支付界面需要的Model
 */

public class SelectPayNeedModel implements Serializable {
    private String accessToken;
    private String userId;
    private String memberId;
    private double consumeAmount;//消费金额
    private double handleFee; //手续费
    private String feeId; //策划推广比例ID
    private String payChannel; //支付渠道
    private String placeImgFile;//消费场所图
    private String objImgFile;//消费实物图
    private String rcptImgFile;//消费发票图
    private String payProofFile;//转账凭证（线下支付时必传）
    private int type;


    //修改订单的参数
    private String orderNo; // 是 string 要修改的单号
    private String RetryMemberId; //是 number 会员编号


    // start 为我买单特有
    private String merchantName; //商户名称
    private String merchantAddr; //商户地址
    private String goodsName; //商品名称
    // end 为我买单特有

    // start 联盟级别独有
    public String gradeId;
    public String provinceId; //省id
    public String provinceName;
    public String cityId; //市id
    public String cityName;
    public String areaId; //区id
    public String areaName;
    public String agentName;//代理名
    public String agentType; //市代 还是区代
    // end



    /**
     * 商家录单
     */
    public static final int TYPE_SHOP_RECORD = 0;
    /**
     * 为我买单
     */
    public static final int TYPE_PAY_FOR_ME = 1;

    /**
     * 联盟级别
     *
     * @return
     */
    public static final int TYPE_UNION_LEVEL = 2;
    /**
     * 黄花梨
     *
     * @return
     */
    public static final int TYPE_HUANGHUALI = 3;

    public String getAccessToken() {
        return accessToken;
    }

    public SelectPayNeedModel setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public SelectPayNeedModel setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public SelectPayNeedModel setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public String getRetryMemberId() {
        return RetryMemberId;
    }

    public SelectPayNeedModel setRetryMemberId(String RetryMemberId) {
        this.RetryMemberId = RetryMemberId;
        return this;
    }


    public String getMemberId() {
        return memberId;
    }

    public SelectPayNeedModel setMemberId(String memberId) {
        this.memberId = memberId;
        return this;
    }

    public double getConsumeAmount() {
        return consumeAmount;
    }

    public SelectPayNeedModel setConsumeAmount(double consumeAmount) {
        this.consumeAmount = consumeAmount;
        return this;
    }

    public double getHandleFee() {
        return handleFee;
    }

    public SelectPayNeedModel setHandleFee(double handleFee) {
        this.handleFee = handleFee;
        return this;
    }

    public String getFeeId() {
        return feeId;
    }

    public SelectPayNeedModel setFeeId(String feeId) {
        this.feeId = feeId;
        return this;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public SelectPayNeedModel setPayChannel(String payChannel) {
        this.payChannel = payChannel;
        return this;
    }

    public String getPlaceImgFile() {
        return placeImgFile;
    }

    public SelectPayNeedModel setPlaceImgFile(String placeImgFile) {
        this.placeImgFile = placeImgFile;
        return this;
    }

    public String getObjImgFile() {
        return objImgFile;
    }

    public SelectPayNeedModel setObjImgFile(String objImgFile) {
        this.objImgFile = objImgFile;
        return this;
    }

    public String getRcptImgFile() {
        return rcptImgFile;
    }

    public SelectPayNeedModel setRcptImgFile(String rcptImgFile) {
        this.rcptImgFile = rcptImgFile;
        return this;
    }

    public String getPayProofFile() {
        return payProofFile;
    }

    public SelectPayNeedModel setPayProofFile(String payProofFile) {
        this.payProofFile = payProofFile;
        return this;
    }

    public int getType() {
        return type;
    }

    public SelectPayNeedModel setType(int type) {
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return "SelectPayNeedModel{" +
                "accessToken='" + accessToken + '\'' +
                ", userId='" + userId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", consumeAmount=" + consumeAmount +
                ", handleFee=" + handleFee +
                ", feeId='" + feeId + '\'' +
                ", payChannel='" + payChannel + '\'' +
                ", placeImgFile='" + placeImgFile + '\'' +
                ", objImgFile='" + objImgFile + '\'' +
                ", rcptImgFile='" + rcptImgFile + '\'' +
                ", payProofFile='" + payProofFile + '\'' +
                ", type=" + type +
                ", merchantName='" + merchantName + '\'' +
                ", merchantAddr='" + merchantAddr + '\'' +
                ", goodsName='" + goodsName + '\'' +
                '}';
    }

    public String getMerchantName() {
        return merchantName;
    }

    public SelectPayNeedModel setMerchantName(String merchantName) {
        this.merchantName = merchantName;
        return this;
    }

    public String getMerchantAddr() {
        return merchantAddr;
    }

    public SelectPayNeedModel setMerchantAddr(String merchantAddr) {
        this.merchantAddr = merchantAddr;
        return this;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public SelectPayNeedModel setGoodsName(String goodsName) {
        this.goodsName = goodsName;
        return this;
    }

}
