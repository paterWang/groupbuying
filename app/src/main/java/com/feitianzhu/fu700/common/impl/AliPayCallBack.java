package com.feitianzhu.fu700.common.impl;

import com.zhy.http.okhttp.callback.Callback;
import okhttp3.Call;
import okhttp3.Response;
import org.json.JSONObject;

import static com.feitianzhu.fu700.common.Constant.FailCode;
import static com.feitianzhu.fu700.common.Constant.SuccessCode;


/**
 * description: 专门给支付宝回调接口
 * autour: dicallc
*/
public class AliPayCallBack extends Callback {
  protected onConnectionFinishLinstener mLinstener;

  public AliPayCallBack(onConnectionFinishLinstener mLinstener) {
    this.mLinstener = mLinstener;
  }

  @Override public Object parseNetworkResponse(String mData, Response response, int id)
      throws Exception {
    JSONObject object = new JSONObject(mData);
    String payParam = object.getString("payParam");

    return payParam;
  }

  @Override public void onError(Call call, Exception e, int id) {
    mLinstener.onFail(FailCode, e.getMessage());
  }

  @Override public void onResponse(Object response, int id) {
    mLinstener.onSuccess(SuccessCode, response.toString());
  }
}
