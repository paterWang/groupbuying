package com.feitianzhu.fu700.common.impl;

import com.socks.library.KLog;
import com.zhy.http.okhttp.callback.Callback;
import okhttp3.Call;
import okhttp3.Response;

import static com.feitianzhu.fu700.common.Constant.FailCode;
import static com.feitianzhu.fu700.common.Constant.SuccessCode;

/**
 * Created by dicallc on 2017/9/8 0008.
 */

public class NoDataCallBack extends Callback {

  private onConnectionFinishLinstener mLinstener;

  public NoDataCallBack(onConnectionFinishLinstener mLinstener) {
    this.mLinstener = mLinstener;
  }

  @Override public Object parseNetworkResponse(String mData, Response response, int id)
      throws Exception {
    return mData;
  }

  @Override public void onError(Call call, Exception e, int id) {
    if ("数据为空".equals(e.getMessage())) {
      mLinstener.onSuccess(SuccessCode, "成功");
    } else {
      mLinstener.onFail(FailCode, e.getMessage());
    }
  }

  @Override public void onResponse(Object response, int id) {
    KLog.e(response.toString());
  }
}
