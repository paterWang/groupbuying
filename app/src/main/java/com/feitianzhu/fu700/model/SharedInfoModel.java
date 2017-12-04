package com.feitianzhu.fu700.model;

/**
 * Created by Vya on 2017/9/26 0026.
 */

public class SharedInfoModel {


    /**
     * headImg : http://118.190.156.13/user/head/06ba4f8a22324d1191265d5f1f74ce04.png
     * nickName : 朱丽娜
     * link : http://118.190.156.13:32819/fhwl/invest?type=2&userId=11&noncestr=vsuk7zdkhzm1707qx2pq9n5gd5fwdf1v&timestamp=1506391504493&&sign=15749678dce388fb3f68f23cfb37f3c8
     * company : sdafinewf
     * job : afsdiowjefo
     */

    private String headImg;
    private String nickName;
    private String link;
    private String company;
    private String job;

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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

    @Override
    public String toString() {
        return "SharedInfoModel{" +
                "headImg='" + headImg + '\'' +
                ", nickName='" + nickName + '\'' +
                ", link='" + link + '\'' +
                ", company='" + company + '\'' +
                ", job='" + job + '\'' +
                '}';
    }
}
