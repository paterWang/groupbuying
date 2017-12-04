package com.feitianzhu.fu700.huanghuali.entity;

/**
 * Created by Lee on 2017/9/24.
 */

public class HuangHuaLiRecordEntity {

    /**
     * orderNo : YO20170921214345686999
     * price : 100000
     * payChannel : offline
     * refuseReason : 未收到款
     * status : -1
     * createDate : 2017-09-21 21:43:45
     */

    public String orderNo;
    public int price;
    public String payChannel;
    public String refuseReason;
    public String status;
    public String createDate;

    @Override
    public String toString() {
        return "HuangHuaLiRecordEntity{" +
                "orderNo='" + orderNo + '\'' +
                ", price=" + price +
                ", payChannel='" + payChannel + '\'' +
                ", refuseReason='" + refuseReason + '\'' +
                ", status='" + status + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
