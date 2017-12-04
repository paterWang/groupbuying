package com.feitianzhu.fu700.model;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * description: 支付识别符
 * autour: dicallc
*/
public class PayInfo {
  /**
   * 我的钱包充值
   */
  public static final int MyMoneyPay = 0;
  /**
   * 商铺买单
   */
  public static final int ShopPay = 1;
  public static final int NOTIFYADAPTER = 2;
  public static final int getDisList = 3;
  public static final int THURSDAY = 4;
  public static final int FRIDAY = 5;
  public static final int NoPay = 6;
  public static final int SHOPRECORDER = 7; //商家录单
  public static final int UNIONLEVEL = 8; //联盟级别
  public static final int PAY_FORME = 9; //为我买单
  public static final int BUY_SERVICE = 10; //购买服务

  @IntDef({ MyMoneyPay, ShopPay, NOTIFYADAPTER, getDisList, THURSDAY, FRIDAY, NoPay,SHOPRECORDER,UNIONLEVEL,PAY_FORME,BUY_SERVICE})
  @Retention(RetentionPolicy.SOURCE) public @interface WeekDays {
  }

  public PayInfo(@WeekDays int mCurrentInfo) {
    currentInfo = mCurrentInfo;
  }


  @WeekDays int currentInfo = NoPay;

  @WeekDays public int getCurrentInfo() {
    return currentInfo;
  }
}
