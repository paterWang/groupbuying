package com.feitianzhu.fu700.model;

import java.util.List;

/**
 * Created by jiangdikai on 2017/9/22.
 */

public class ServeOrderModel {

        /**
         * list : [{"orderNo":"BSB20170922225525269614","amount":1.3,"rebatePv":1.1,"linkPhone":"12388885555","isPay":"1","createDate":"2017-09-22 22:55:25","service":{"serviceId":15,"serviceName":"老王卖瓜","adImg":"http://118.190.156.13/user/merchant/service/057062f12e9644ab90b259f6a2449bd8.png"}},{"orderNo":"BSB20170922225510195838","amount":1.5,"rebatePv":1.1,"linkPhone":"12388885555","isPay":"1","createDate":"2017-09-22 22:55:10","service":{"serviceId":17,"serviceName":"老王卖瓜","adImg":"http://118.190.156.13/user/merchant/service/316fc915cf13450a9ba9f42d30ea2252.png"}},{"orderNo":"BSB20170922225452221876","amount":1.5,"rebatePv":1.1,"linkPhone":"12388885555","isPay":"1","createDate":"2017-09-22 22:54:52","service":{"serviceId":17,"serviceName":"老王卖瓜","adImg":"http://118.190.156.13/user/merchant/service/316fc915cf13450a9ba9f42d30ea2252.png"}}]
         * pager : {"totalRows":3,"pageRows":10,"pageIndex":1,"hasPrevPage":false,"hasNextPage":false}
         */

        public PagerBean pager;
        public List<ListBean> list;

        public static class PagerBean {
            /**
             * totalRows : 3
             * pageRows : 10
             * pageIndex : 1
             * hasPrevPage : false
             * hasNextPage : false
             */

            public int totalRows;
            public int pageRows;
            public int pageIndex;
            public boolean hasPrevPage;
            public boolean hasNextPage;
        }

        public static class ListBean {
            /**
             * orderNo : BSB20170922225525269614
             * amount : 1.3
             * rebatePv : 1.1
             * linkPhone : 12388885555
             * isPay : 1
             * createDate : 2017-09-22 22:55:25
             * service : {"serviceId":15,"serviceName":"老王卖瓜","adImg":"http://118.190.156.13/user/merchant/service/057062f12e9644ab90b259f6a2449bd8.png"}
             */

            public String orderNo;
            public double amount;
            public double rebatePv;
            public String linkPhone;
            public String isPay;
            public String createDate;
            public ServiceBean service;

            public static class ServiceBean {
                /**
                 * serviceId : 15
                 * serviceName : 老王卖瓜
                 * adImg : http://118.190.156.13/user/merchant/service/057062f12e9644ab90b259f6a2449bd8.png
                 */

                public int serviceId;
                public String serviceName;
                public String adImg;
            }
    }
}
