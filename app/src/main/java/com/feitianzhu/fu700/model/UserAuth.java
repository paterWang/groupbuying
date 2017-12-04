package com.feitianzhu.fu700.model;

/**
 * Created by dicallc on 2017/9/11 0011.
 */

public class UserAuth {

  /**
   * isRnAuth : 1
   * isMerchantAuth : 1
   * isMerchant : 1
   * isMerchantStatus : 1
   */

  public int isRnAuth;
  public int isMerchantAuth;
  public int isMerchant;
  public int isMerchantStatus;
  /**
   * 是否设置过二级密码
   */
  public int isPaypass;
  public String rnAuthRefuseReason;
  //
  public String merchantAuthRefuseReason;
  //商户店铺创建审核拒绝原因
  public String merchantStatusRefuseReason;
}
