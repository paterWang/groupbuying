package com.feitianzhu.fu700.model;

import java.util.List;

/**
 * Created by dicallc on 2017/9/8 0008.
 */

public class RecommndShopModel {

    /**
     * list : [{"merchantId":7,"merchantName":"哈哈哈","clsId":1,"clsName":"餐饮酒店","merchantHeadImg":"http://118.190.156.13/user/merchant/453044af7c0c4e239f1cdb800bf3af1a.png"},{"merchantId":8,"merchantName":"dd","clsId":1,"clsName":"餐饮酒店","merchantHeadImg":"http://118.190.156.13/user/merchant/453044af7c0c4e239f1cdb800bf3af1a.png"}]
     * pager : {"totalRows":2,"pageRows":4,"pageIndex":1,"paged":false,"hasPrevPage":false,"hasNextPage":false,"defaultPageRows":20,"totalPages":1,"currPageRows":2,"pageStartRow":0,"pageEndRow":3}
     */

    public PagerEntity pager;
    public List<ListEntity> list;

    public static class PagerEntity {
      /**
       * totalRows : 2
       * pageRows : 4
       * pageIndex : 1
       * paged : false
       * hasPrevPage : false
       * hasNextPage : false
       * defaultPageRows : 20
       * totalPages : 1
       * currPageRows : 2
       * pageStartRow : 0
       * pageEndRow : 3
       */

      public int totalRows;
      public int pageRows;
      public int pageIndex;
      public boolean paged;
      public boolean hasPrevPage;
      public boolean hasNextPage;
      public int defaultPageRows;
      public int totalPages;
      public int currPageRows;
      public int pageStartRow;
      public int pageEndRow;
    }

    public static class ListEntity {
      /**
       * merchantId : 7
       * merchantName : 哈哈哈
       * clsId : 1
       * clsName : 餐饮酒店
       * merchantHeadImg : http://118.190.156.13/user/merchant/453044af7c0c4e239f1cdb800bf3af1a.png
       */

      public int merchantId;
      public String merchantName;
      public int clsId;
      public String clsName;
      public String provinceName;
      public String cityName;
      public String dtlAddr;
      public String merchantHeadImg;
    }
}
