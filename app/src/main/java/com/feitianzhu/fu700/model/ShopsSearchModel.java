package com.feitianzhu.fu700.model;

import java.util.List;

/**
 * Created by jiangdikai on 2017/9/10.
 */

public class ShopsSearchModel {

    /**
     * list : [{"merchantId":7,"merchantName":"d哈哈哈","merchantHeadImg":"http://118.190.156.13/user/merchant/453044af7c0c4e239f1cdb800bf3af1a.png","provinceName":"北京市","cityName":"东城区","dtlAddr":"ssss"},{"merchantId":8,"merchantName":"dd","merchantHeadImg":"http://118.190.156.13/user/merchant/453044af7c0c4e239f1cdb800bf3af1a.png","provinceName":"北京市","cityName":"东城区","dtlAddr":"明松"},{"merchantId":10,"merchantName":"d哈哈哈","merchantHeadImg":"http://118.190.156.13/user/merchant/453044af7c0c4e239f1cdb800bf3af1a.png","provinceName":"北京市","cityName":"东城区","dtlAddr":"ssss"},{"merchantId":11,"merchantName":"dd","merchantHeadImg":"http://118.190.156.13/user/merchant/453044af7c0c4e239f1cdb800bf3af1a.png","provinceName":"北京市","cityName":"东城区","dtlAddr":"明松"},{"merchantId":12,"merchantName":"dd","merchantHeadImg":"http://118.190.156.13/user/merchant/453044af7c0c4e239f1cdb800bf3af1a.png","provinceName":"北京市","cityName":"东城区","dtlAddr":"明松"},{"merchantId":13,"merchantName":"dd","merchantHeadImg":"http://118.190.156.13/user/merchant/453044af7c0c4e239f1cdb800bf3af1a.png","provinceName":"北京市","cityName":"东城区","dtlAddr":"明松"},{"merchantId":14,"merchantName":"dd","merchantHeadImg":"http://118.190.156.13/user/merchant/453044af7c0c4e239f1cdb800bf3af1a.png","provinceName":"北京市","cityName":"东城区","dtlAddr":"明松"},{"merchantId":15,"merchantName":"dd","merchantHeadImg":"http://118.190.156.13/user/merchant/453044af7c0c4e239f1cdb800bf3af1a.png","provinceName":"北京市","cityName":"东城区","dtlAddr":"明松"},{"merchantId":16,"merchantName":"dd","merchantHeadImg":"http://118.190.156.13/user/merchant/453044af7c0c4e239f1cdb800bf3af1a.png","provinceName":"北京市","cityName":"东城区","dtlAddr":"明松"}]
     * pager : {"pageRows":10,"pageIndex":1,"hasPrevPage":false,"hasNextPage":false}
     */

    public PagerBean pager;
    public List<ListBean> list;

    public static class PagerBean {
        /**
         * pageRows : 10
         * pageIndex : 1
         * hasPrevPage : false
         * hasNextPage : false
         */

        public int pageRows;
        public int pageIndex;
        public boolean hasPrevPage;
        public boolean hasNextPage;
    }

    public static class ListBean {
        /**
         * merchantId : 7
         * merchantName : d哈哈哈
         * merchantHeadImg : http://118.190.156.13/user/merchant/453044af7c0c4e239f1cdb800bf3af1a.png
         * provinceName : 北京市
         * cityName : 东城区
         * dtlAddr : ssss
         */

        public int merchantId;
        public String merchantName;
        public String merchantHeadImg;
        public String provinceName;
        public String cityName;
        public String dtlAddr;
    }
}
