package com.feitianzhu.fu700.model;

import java.util.List;

/**
 * Created by dicallc on 2017/9/11 0011.
 */

public class WalletModel {
  /**
   * wallentRecordList : [{"tradeType":"积分转入","amount":160,"isIncome":"1","tradeDate":"2017-09-09 13:28:52","user":{"userId":2,"nickName":"朱丽娜","headImg":"http://118.190.156.13/user/head/624aba498f06423cb3fcd8a5f4d4b1f6.png"}},{"tradeType":"积分转入","amount":160,"isIncome":"1","tradeDate":"2017-09-09 13:28:42","user":{"userId":2,"nickName":"朱丽娜","headImg":"http://118.190.156.13/user/head/624aba498f06423cb3fcd8a5f4d4b1f6.png"}},{"tradeType":"积分转入","amount":160,"isIncome":"1","tradeDate":"2017-09-09 13:28:41","user":{"userId":2,"nickName":"朱丽娜","headImg":"http://118.190.156.13/user/head/624aba498f06423cb3fcd8a5f4d4b1f6.png"}}]
   * merchantBalance : 160
   * balance : 9799
   * balanceRecordList : [{"tradeType":"为我买单手续费","amount":40,"isIncome":"0","tradeDate":"2017-09-08 23:58:59","user":{"userId":2,"nickName":"朱丽娜","headImg":"http://118.190.156.13/user/head/624aba498f06423cb3fcd8a5f4d4b1f6.png"}},{"tradeType":"购买服务","amount":1,"isIncome":"0","tradeDate":"2017-09-07 22:59:42","user":{"userId":2,"nickName":"朱丽娜","headImg":"http://118.190.156.13/user/head/624aba498f06423cb3fcd8a5f4d4b1f6.png"}}]
   */

  public String merchantBalance;
  public String balance;
  public List<WallentRecordListEntity> wallentRecordList;
  public List<WallentRecordListEntity> balanceRecordList;

  public static class WallentRecordListEntity {
    /**
     * tradeType : 积分转入
     * amount : 160
     * isIncome : 1
     * tradeDate : 2017-09-09 13:28:52
     * user : {"userId":2,"nickName":"朱丽娜","headImg":"http://118.190.156.13/user/head/624aba498f06423cb3fcd8a5f4d4b1f6.png"}
     */

    public String tradeType;
    public String amount;
    public String isIncome;
    public String tradeDate;
    public UserEntity user;

    public static class UserEntity {
      /**
       * userId : 2
       * nickName : 朱丽娜
       * headImg : http://118.190.156.13/user/head/624aba498f06423cb3fcd8a5f4d4b1f6.png
       */

      public int userId;
      public String nickName;
      public String headImg;
    }
  }

  //public int merchantBalance;
    //public int balance;
    //public List<WallentRecordListEntity> wallentRecordList;
    //public List<WallentRecordListEntity> balanceRecordList;
    //
    //public static class WallentRecordListEntity {
    //
    //  public String tradeType;
    //  public int amount;
    //  public String isIncome;
    //  public String tradeDate;
    //}

}
