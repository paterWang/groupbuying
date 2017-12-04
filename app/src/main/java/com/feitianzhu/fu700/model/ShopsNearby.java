package com.feitianzhu.fu700.model;

import java.util.List;

/**
 * Created by jiangdikai on 2017/9/9.
 */

public class ShopsNearby {

    /**
     * list : [{"merchantId":7,"merchantName":"哈哈哈","merchantHeadImg":"http://118.190.156.13/user/merchant/453044af7c0c4e239f1cdb800bf3af1a.png","provinceName":"北京市","cityName":"东城区","dtlAddr":"ssss","collectId":6,"distince":20021918},{"merchantId":8,"merchantName":"dd","merchantHeadImg":"http://118.190.156.13/user/merchant/453044af7c0c4e239f1cdb800bf3af1a.png","provinceName":"北京市","cityName":"东城区","dtlAddr":"明松","distince":19426216}]
     * pager : {"pageRows":10,"pageIndex":1,"hasPrevPage":false,"hasNextPage":false}
     */

    public PagerBean pager;
    public List<ShopsIndex.NearByMerchantListBean> list;

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
}
