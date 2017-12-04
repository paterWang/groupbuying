package com.feitianzhu.fu700.model;

import java.io.Serializable;

/**
 * Created by Vya on 2017/9/25 0025.
 */

public class GetMoneyModel implements Serializable {
        public String type;//申请兑换的积分类型（1:推广积分，2：消费积分，3：汇联积分，4：志愿者积分，5：合伙人积分，6：分红积分，7：共享红利，8：黄花梨积分）
        public String payPass;
        public double points; //总积分
        public double rate; // 费率



    public GetMoneyModel(String payPass) {
        this.payPass = payPass;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPayPass() {
        return payPass;
    }

    public void setPayPass(String payPass) {
        this.payPass = payPass;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }
}
