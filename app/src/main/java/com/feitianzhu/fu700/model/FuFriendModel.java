package com.feitianzhu.fu700.model;

import java.util.List;

/**
 * Created by dicallc on 2017/9/13 0013.
 */

public class FuFriendModel {

  /**
   * pager : {"pageRows":10,"pageIndex":1,"hasPrevPage":false,"hasNextPage":false}
   * list : [{"totalConsume":0,"shareBenefit":0,"nickName":"q","userId":4},{"totalConsume":0,"shareBenefit":0,"nickName":"a","userId":5},{"totalConsume":0,"shareBenefit":0,"nickName":"s","userId":6},{"totalConsume":0,"shareBenefit":0,"nickName":"s","userId":7},{"totalConsume":0,"shareBenefit":0,"nickName":"s","userId":8},{"totalConsume":0,"shareBenefit":0,"nickName":"d","userId":9},{"totalConsume":0,"shareBenefit":0,"nickName":"f","userId":10},{"totalConsume":0,"shareBenefit":0,"nickName":"g","userId":11},{"headImg":"http://118.190.156.13/user/head/624aba498f06423cb3fcd8a5f4d4b1f6.png","totalConsume":0,"shareBenefit":0,"liveCityName":"深圳","nickName":"测试","job":"开发","userId":12,"age":20},{"totalConsume":0,"shareBenefit":0,"userId":13}]
   */

  public PagerEntity pager;
  public List<ListEntity> list;

  public static class PagerEntity {
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

  public static class ListEntity {
    /**
     * totalConsume : 0
     * shareBenefit : 0
     * nickName : q
     * userId : 4
     */

    public String totalConsume;
    public String headImg;
    public String shareBenefit;
    public String nickName;
    public String job;
    public String phone;
    public String liveCityName;
    public String age;
    public int userId;
  }
}
