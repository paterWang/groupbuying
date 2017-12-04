package com.feitianzhu.fu700.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dicallc on 2017/9/28 0028.
 */

public class WXModel {

  /**
   * sign : 8006c15bb9695f7da257de66d8921726
   * timestamp : 1506518784
   * noncestr : 1wpl1n1q8ekdksbpckpqmh9ftqm7dsld
   * partnerid : 1244914402
   * prepayid : 1111111111222222222222222223
   * package : Sign=WXPay
   * appid : wxffd14b94d7649d25
   */

  public String sign;
  public int timestamp;
  public String noncestr;
  public String partnerid;
  public String prepayid;
  @SerializedName("package") public String packageX;
  public String appid;
}
