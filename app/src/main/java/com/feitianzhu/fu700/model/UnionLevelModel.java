package com.feitianzhu.fu700.model;

/**
 * Created by Vya on 2017/9/21 0021.
 */

public class UnionLevelModel {

    /**
     * gradeId : 1
     * icon : http://118.190.156.13/images/1/images/agent/2017/09/1%402x.png
     * name : 一级VAP
     * title : 申购100PV
     * points : 100
     * rate : 5
     * haveRange : 1
     * bonusRate : 0
     * agentFeeRate : 0
     * agentType : 0
     */

    private int gradeId;
    private String icon;
    private String name;
    private String title;
    private int points;
    private int rate;
    private String haveRange;
    private int bonusRate;
    private int agentFeeRate;
    private String agentType;
    public boolean isSelect = false;

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

    public String getHaveRange() {
        return haveRange;
    }

    public void setHaveRange(String haveRange) {
        this.haveRange = haveRange;
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

    public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }
}
