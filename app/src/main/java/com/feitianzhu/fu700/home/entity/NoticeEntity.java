package com.feitianzhu.fu700.home.entity;

import java.util.List;

/**
 * Description：
 * Author：Lee
 * Date：2017/9/25 16:39
 */

public class NoticeEntity {


    /**
     * list : [{"msgId":1,"pushContent":"你的实名认证已审核通过","pushDate":"2017-09-23 13:17:54","type":"1"}]
     * pager : {"totalRows":1,"pageRows":20,"pageIndex":1,"hasPrevPage":false,"hasNextPage":false}
     */

    public PagerBean pager;
    public List<ListBean> list;

    public static class PagerBean {
        /**
         * totalRows : 1
         * pageRows : 20
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
         * msgId : 1
         * pushContent : 你的实名认证已审核通过
         * pushDate : 2017-09-23 13:17:54
         * type : 1
         */

        public int msgId;
        public String pushContent;
        public String pushDate;
        public String type;
    }
}
