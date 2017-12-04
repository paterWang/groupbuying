package com.feitianzhu.fu700.model;

import java.util.List;

/**
 * Created by jiangdikai on 2017/9/6.
 */

public class ShopsEvali {

    /**
     * list : [{"userId":2,"nickName":"老王","headImg":"http://118.190.156.13/user/head/624aba498f06423cb3fcd8a5f4d4b1f6.png","content":"xxxx","evalDate":1504575336000,"star":1.1}]
     * pager : {"totalRows":1,"pageRows":10,"pageIndex":1,"paged":false,"defaultPageRows":20,"totalPages":1,"currPageRows":1,"pageStartRow":0,"pageEndRow":9,"hasPrevPage":false,"hasNextPage":false}
     */

    public PagerBean pager;
    public List<ListBean> list;

    public static class PagerBean {
        /**
         * totalRows : 1
         * pageRows : 10
         * pageIndex : 1
         * paged : false
         * defaultPageRows : 20
         * totalPages : 1
         * currPageRows : 1
         * pageStartRow : 0
         * pageEndRow : 9
         * hasPrevPage : false
         * hasNextPage : false
         */

        public int totalRows;
        public int pageRows;
        public int pageIndex;
        public boolean paged;
        public int defaultPageRows;
        public int totalPages;
        public int currPageRows;
        public int pageStartRow;
        public int pageEndRow;
        public boolean hasPrevPage;
        public boolean hasNextPage;
    }

    public static class ListBean {
        /**
         * userId : 2
         * nickName : 老王
         * headImg : http://118.190.156.13/user/head/624aba498f06423cb3fcd8a5f4d4b1f6.png
         * content : xxxx
         * evalDate : 1504575336000
         * star : 1.1
         */

        public int userId;
        public String nickName;
        public String headImg;
        public String content;
        public long evalDate;
        public float star;
    }
}
