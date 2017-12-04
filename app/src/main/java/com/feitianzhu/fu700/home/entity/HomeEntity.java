package com.feitianzhu.fu700.home.entity;

import java.util.List;

/**
 * Created by Lee on 2017/9/10.
 */

public class HomeEntity {

    /**
     * code : 0
     * data : {"bannerList":[{"linkType":2,"idValue":5,"imagUrl":"/images/1/images/grade/2017/08/xx.jpg"}],"recommendList":[{"merchantId":7,"merchantName":"d哈哈哈","merchantHeadImg":"http://118.190.156.13/user/merchant/453044af7c0c4e239f1cdb800bf3af1a.png","collectId":6,"clsName":"餐饮酒店"},{"merchantId":8,"merchantName":"dd","merchantHeadImg":"http://118.190.156.13/user/merchant/453044af7c0c4e239f1cdb800bf3af1a.png","clsName":"餐饮酒店"}],"serviceRecommendList":[{"serviceId":3,"serviceName":"老王卖瓜","price":1,"rebate":1,"adImg":"http://118.190.156.13/user/merchant/service/7b128700e6bb4193b80eef1b164e90d5.png","serviceAddr":"老王卖瓜.com"},{"serviceId":7,"serviceName":"老王卖瓜","price":1,"rebate":1,"adImg":"http://118.190.156.13/user/merchant/service/316fc915cf13450a9ba9f42d30ea2252.png","serviceAddr":"老王卖瓜.com","collectId":2},{"serviceId":4,"serviceName":"老王卖瓜","price":1,"rebate":1,"adImg":"http://118.190.156.13/user/merchant/service/1f368c17d2f1416b92cd2ff45439c010.png","serviceAddr":"老王卖瓜.com"}]}
     */


    public List<BannerListBean> bannerList;
    public List<RecommendListBean> recommendList;
    public List<ServiceRecommendListBean> serviceRecommendList;

    public static class BannerListBean {
        /**
         * linkType : 2
         * idValue : 5
         * imagUrl : /images/1/images/grade/2017/08/xx.jpg
         */

        public int linkType; // 链接类型（1：商户，2：服务，3：文章，4：外部链接）
        public int idValue;   //链接ID，根据链接类型关联不同类型的内容(如果是商户则是商户id)
        public String outUrl;    //外部链接地址
        public String imagUrl;

        @Override
        public String toString() {
            return "BannerListBean{" +
                    "linkType=" + linkType +
                    ", idValue=" + idValue +
                    ", imagUrl='" + imagUrl + '\'' +
                    ", outUrl='" + outUrl + '\'' +
                    '}';
        }
    }

    public static class RecommendListBean {
        /**
         * merchantId : 7
         * merchantName : d哈哈哈
         * merchantHeadImg : http://118.190.156.13/user/merchant/453044af7c0c4e239f1cdb800bf3af1a.png
         * collectId : 6
         * clsName : 餐饮酒店
         */

        public int merchantId;
        public String merchantName;
        public String merchantHeadImg;
        public int collectId;
        public String clsName;

        @Override
        public String toString() {
            return "RecommendListBean{" +
                    "merchantId=" + merchantId +
                    ", merchantName='" + merchantName + '\'' +
                    ", merchantHeadImg='" + merchantHeadImg + '\'' +
                    ", collectId=" + collectId +
                    ", clsName='" + clsName + '\'' +
                    '}';
        }
    }

    public static class ServiceRecommendListBean {
        /**
         * serviceId : 3
         * serviceName : 老王卖瓜
         * price : 1
         * rebate : 1
         * adImg : http://118.190.156.13/user/merchant/service/7b128700e6bb4193b80eef1b164e90d5.png
         * serviceAddr : 老王卖瓜.com
         * collectId : 2
         */

        public int serviceId;
        public String serviceName;
        public double price;
        public double rebate;
        public String adImg;
        public String serviceAddr;
        public int collectId;

        @Override
        public String toString() {
            return "ServiceRecommendListBean{" +
                    "serviceId=" + serviceId +
                    ", serviceName='" + serviceName + '\'' +
                    ", price=" + price +
                    ", rebate=" + rebate +
                    ", adImg='" + adImg + '\'' +
                    ", serviceAddr='" + serviceAddr + '\'' +
                    ", collectId=" + collectId +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "HomeEntity{" +
                "bannerList=" + bannerList +
                ", recommendList=" + recommendList +
                ", serviceRecommendList=" + serviceRecommendList +
                '}';
    }
}
