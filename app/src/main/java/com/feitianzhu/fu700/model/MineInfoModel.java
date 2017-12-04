package com.feitianzhu.fu700.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Vya on 2017/9/6 0006.
 */

public class MineInfoModel implements Serializable{


    /**
     * userId : 18
     * phone : 15147022345
     * nickName : 朱丽了是了了我娜
     * headImg : http://118.190.156.13/user/head/56919c76517e4928a2e94a2ccfff8d13.png
     * gradeName : 三级志愿者
     * agentName : null
     * age : 117
     * sex : 1
     * constellation : 摩羯座
     * liveAdress : 丰台区 北京市
     * homeAdress : 四平市 吉林省
     * personSign : 新坡坡坡度
     * interest : 宠物
     * interestLabel : []
     * birthday : -2209017600000
     * industry : IT互联网 市场
     * industryLabel : []
     * company : 你明
     * job : 外婆破GZ
     * totalPoints : 10916.28
     * parentId : 2
     * parentNickName : 朱丽娜
     * parentCompany : yyyyyysd
     * parentJob : eeeeeee
     * parentHeadImg : http://118.190.156.13/user/head/head.png
     * realName : 没工夫
     * isRnAuth : 1
     * isMerchantAuth : 1
     * rate : 20
     * userType : 1
     */

    private int userId;
    private String phone;
    private String nickName;
    private String headImg;
    private String gradeName;
    private Object agentName;
    private int age;
    private int sex;
    private String constellation;
    private String liveAdress;
    private String homeAdress;
    private String personSign;
    private String interest;
    private String birthday;
    private String industry;
    private String company;
    private String job;
    private double totalPoints;
    private int parentId;
    private String parentNickName;
    private String parentCompany;
    private String parentJob;
    private String parentHeadImg;
    private String realName;
    private int isRnAuth;
    private int isMerchantAuth;
    private int rate;
    private int userType;
    private List<?> interestLabel;
    private List<?> industryLabel;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public Object getAgentName() {
        return agentName;
    }

    public void setAgentName(Object agentName) {
        this.agentName = agentName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getLiveAdress() {
        return liveAdress;
    }

    public void setLiveAdress(String liveAdress) {
        this.liveAdress = liveAdress;
    }

    public String getHomeAdress() {
        return homeAdress;
    }

    public void setHomeAdress(String homeAdress) {
        this.homeAdress = homeAdress;
    }

    public String getPersonSign() {
        return personSign;
    }

    public void setPersonSign(String personSign) {
        this.personSign = personSign;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public double getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(double totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getParentNickName() {
        return parentNickName;
    }

    public void setParentNickName(String parentNickName) {
        this.parentNickName = parentNickName;
    }

    public String getParentCompany() {
        return parentCompany;
    }

    public void setParentCompany(String parentCompany) {
        this.parentCompany = parentCompany;
    }

    public String getParentJob() {
        return parentJob;
    }

    public void setParentJob(String parentJob) {
        this.parentJob = parentJob;
    }

    public String getParentHeadImg() {
        return parentHeadImg;
    }

    public void setParentHeadImg(String parentHeadImg) {
        this.parentHeadImg = parentHeadImg;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getIsRnAuth() {
        return isRnAuth;
    }

    public void setIsRnAuth(int isRnAuth) {
        this.isRnAuth = isRnAuth;
    }

    public int getIsMerchantAuth() {
        return isMerchantAuth;
    }

    public void setIsMerchantAuth(int isMerchantAuth) {
        this.isMerchantAuth = isMerchantAuth;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public List<?> getInterestLabel() {
        return interestLabel;
    }

    public void setInterestLabel(List<?> interestLabel) {
        this.interestLabel = interestLabel;
    }

    public List<?> getIndustryLabel() {
        return industryLabel;
    }

    public void setIndustryLabel(List<?> industryLabel) {
        this.industryLabel = industryLabel;
    }
}
