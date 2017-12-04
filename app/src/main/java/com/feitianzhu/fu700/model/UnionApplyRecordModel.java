package com.feitianzhu.fu700.model;

/**
 * Created by Vya on 2017/9/23.
 */

public class UnionApplyRecordModel {

    /**
     * orderNo : GA20170921163940604925
     * amount : 1000
     * createDate : 2017-09-21 16:39:40
     * status : 0
     * grade : {"gradeId":0,"icon":"http://118.190.156.13/images/1/images/agent/2017/09/2%402x.png","name":"二级VAP","title":"申购1000PV","points":0,"rate":0,"bonusRate":0,"agentFeeRate":0}
     */

    private String orderNo;
    private int amount;
    private String createDate;
    private String status;
    private GradeBean grade;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GradeBean getGrade() {
        return grade;
    }

    public void setGrade(GradeBean grade) {
        this.grade = grade;
    }

    public static class GradeBean {
        /**
         * gradeId : 0
         * icon : http://118.190.156.13/images/1/images/agent/2017/09/2%402x.png
         * name : 二级VAP
         * title : 申购1000PV
         * points : 0
         * rate : 0
         * bonusRate : 0
         * agentFeeRate : 0
         */

        private int gradeId;
        private String icon;
        private String name;
        private String title;
        private int points;
        private int rate;
        private int bonusRate;
        private int agentFeeRate;

        public int getGradeId() {
            return gradeId;
        }

        public void setGradeId(int gradeId) {
            this.gradeId = gradeId;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }

        public int getRate() {
            return rate;
        }

        public void setRate(int rate) {
            this.rate = rate;
        }

        public int getBonusRate() {
            return bonusRate;
        }

        public void setBonusRate(int bonusRate) {
            this.bonusRate = bonusRate;
        }

        public int getAgentFeeRate() {
            return agentFeeRate;
        }

        public void setAgentFeeRate(int agentFeeRate) {
            this.agentFeeRate = agentFeeRate;
        }
    }
}
