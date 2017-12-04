package com.feitianzhu.fu700.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dicallc on 2017/9/6 0006.
 */

public class ShopsService {

  /**
   * list : [{"serviceId":3,"userId":2,"merchantId":3,"serviceName":"老王卖瓜","price":1,"rebate":1,"adImg":"http://118.190.156.13/user/merchant/service/7b128700e6bb4193b80eef1b164e90d5.png","photos":null,"contactPerson":null,"contactTel":null,"serviceDesc":"老王卖瓜xxxx","serviceAddr":"老王卖瓜.com","status":"0"},{"serviceId":4,"userId":2,"merchantId":3,"serviceName":"老王卖瓜","price":1,"rebate":1,"adImg":"http://118.190.156.13/user/merchant/service/1f368c17d2f1416b92cd2ff45439c010.png","photos":"http://118.190.156.13/user/merchant/service/a1d327d2f57c4253bacac137913c87ed.png,http://118.190.156.13/user/merchant/service/7747edbf52bc4cc7a3671f04df6a54bf.png,http://118.190.156.13/user/merchant/service/5891f819dc744365a48d76dd7ff76826.png,","contactPerson":null,"contactTel":null,"serviceDesc":"老王卖瓜xxxx","serviceAddr":"老王卖瓜.com","status":"0"},{"serviceId":5,"userId":2,"merchantId":3,"serviceName":"老王卖瓜","price":1,"rebate":1,"adImg":"http://118.190.156.13/user/merchant/service/057062f12e9644ab90b259f6a2449bd8.png","photos":"http://118.190.156.13/user/merchant/service/e5efe0bd2a0947c99610acefedae19e4.png,http://118.190.156.13/user/merchant/service/901005449d5d45d1ba2278aa02103dad.png,http://118.190.156.13/user/merchant/service/3530a7cb8336407c876ba27708e665a7.png,","contactPerson":null,"contactTel":null,"serviceDesc":"老王卖瓜xxxx","serviceAddr":"老王卖瓜.com","status":"0"},{"serviceId":6,"userId":2,"merchantId":3,"serviceName":"老王卖瓜","price":1,"rebate":1,"adImg":"http://118.190.156.13/user/merchant/service/13a815a386f24c0788b02f6f59aaa8d7.png","photos":"http://118.190.156.13/user/merchant/service/1e067b82cd964dcaaae1fc424048bd52.png,http://118.190.156.13/user/merchant/service/66a04956a41141abaacf73fb066fd5d0.png,http://118.190.156.13/user/merchant/service/0e4e91e8e1c246ce9135398b0822da89.png,","contactPerson":null,"contactTel":null,"serviceDesc":"老王卖瓜xxxx","serviceAddr":"老王卖瓜.com","status":"0"},{"serviceId":7,"userId":2,"merchantId":3,"serviceName":"老王卖瓜","price":1,"rebate":1,"adImg":"http://118.190.156.13/user/merchant/service/316fc915cf13450a9ba9f42d30ea2252.png","photos":"http://118.190.156.13/user/merchant/service/3ea045958cea4453896c92ecfb56e99f.png,http://118.190.156.13/user/merchant/service/9da787782ffa4f3b9ed582fc87b484a8.png,http://118.190.156.13/user/merchant/service/80a1b42084c441a1b631cae6c2bc1ea0.png,","contactPerson":null,"contactTel":null,"serviceDesc":"老王卖瓜xxxx","serviceAddr":"老王卖瓜.com","status":"0"}]
   * pager : {"totalRows":5,"pageRows":10,"pageIndex":1,"paged":false,"defaultPageRows":20,"totalPages":1,"currPageRows":5,"pageStartRow":0,"pageEndRow":9,"hasPrevPage":false,"hasNextPage":false}
   */

  public PagerEntity pager;
  public List<ListEntity> list;

  public static class PagerEntity {
    /**
     * totalRows : 5
     * pageRows : 10
     * pageIndex : 1
     * paged : false
     * defaultPageRows : 20
     * totalPages : 1
     * currPageRows : 5
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

  public static class ListEntity implements Serializable {
    /**
     * serviceId : 3
     * userId : 2
     * merchantId : 3
     * serviceName : 老王卖瓜
     * price : 1
     * rebate : 1
     * adImg : http://118.190.156.13/user/merchant/service/7b128700e6bb4193b80eef1b164e90d5.png
     * photos : null
     * contactPerson : null
     * contactTel : null
     * serviceDesc : 老王卖瓜xxxx
     * serviceAddr : 老王卖瓜.com
     * status : 0
     */

    public int serviceId;
    public int userId;
    public int merchantId;
    public String serviceName;
    public String price;
    public String rebate;
    public String adImg;
    public String headImg;
    public String photos;
    public String one_photo;
    public Object contactPerson;
    public Object contactTel;
    public String serviceDesc;
    public String serviceAddr;
    public String status;
  }
}
