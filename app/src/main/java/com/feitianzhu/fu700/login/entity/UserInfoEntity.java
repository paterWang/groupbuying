package com.feitianzhu.fu700.login.entity;

import java.util.List;

/**
 * Created by Lee on 2017/9/12.
 */

public class UserInfoEntity {


    /**
     * userId : 18
     * phone : 15147022345
     * nickName : 孟凡迪
     * headImg : http://118.190.156.13/user/head/0dda0bb29e9f485797279d11cdb890c5.jpg
     * gradeName : 八级
     * agentName : 粉丝VIP
     * age : 116
     * sex : 0
     * constellation : 摩羯座
     * liveAdress : 石家庄市 河北省
     * homeAdress : 石家庄市 河北省
     * personSign : or不迷信客厅
     * interest : 化妆
     * interestLabel : []
     * birthday : 1901/01/01
     * industry : 18
     * industryLabel : ["产品"]
     * company : 凯神明后
     * job : 明明
     * totalPoints : 821918.8
     * parentId : 2
     * parentNickName : 朱丽娜
     * parentCompany : yyyyyysd
     * parentJob : eeeeeee
     * parentHeadImg : http://118.190.156.13/user/head/head.png
     * realName : 没工夫
     * isRnAuth : 1
     * isMerchantAuth : 1
     * rate : 40
     * userType : 1
     */

    private int userId;
    private String phone;
    public String nickName;
    private String headImg;
    private String gradeName;
    private String agentName;
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
    private List<String> industryLabel;

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

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
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

    public List<String> getIndustryLabel() {
        return industryLabel;
    }

    public void setIndustryLabel(List<String> industryLabel) {
        this.industryLabel = industryLabel;
    }
}
