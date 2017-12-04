package com.feitianzhu.fu700.model;

import java.util.List;

/**
 * Created by jiangdikai on 2017/9/9.
 */

public class ShopsIndex {

    public static class NearByMerchantListBean {
        /**
         * merchantId : 7
         * merchantName : 哈哈哈
         * merchantHeadImg : http://118.190.156.13/user/merchant/453044af7c0c4e239f1cdb800bf3af1a.png
         * provinceName : 北京市
         * cityName : 东城区
         * dtlAddr : ssss
         * collectId : 6
         * distince : 0
         */

        public int merchantId;
        public String merchantName;
        public String merchantHeadImg;
        public String provinceName;
        public String cityName;
        public String dtlAddr;
        public int collectId;
        public int distince;
        public String distinceStr;
    }


    public List<ShopType> clsList;
    public List<RecommndShopModel.ListEntity> recommendList;
    public List<NearByMerchantListBean> nearByMerchantList;
    public String mallUrl;
    public NextPagerModel nextPager;
    public static class NextPagerModel {
        public boolean recommendHashNext;
        public boolean nearByMerchantHashNext;

    }

//
//
}
