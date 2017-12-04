package com.feitianzhu.fu700.common.impl;

import com.zhy.http.okhttp.callback.Callback;
import okhttp3.Call;

import static com.feitianzhu.fu700.common.Constant.FailCode;
import static com.feitianzhu.fu700.common.Constant.SuccessCode;

/**
 * Created by dicallc on 2017/9/6 0006.
 */

public abstract class BaseCallBack extends Callback {

  protected onConnectionFinishLinstener mLinstener;

  public BaseCallBack(onConnectionFinishLinstener mLinstener) {
    this.mLinstener = mLinstener;
  }



  @Override public void onError(Call call, Exception e, int id) {
    mLinstener.onFail(FailCode, e.getMessage());
  }

  @Override public void onResponse(Object response, int id) {
    mLinstener.onSuccess(SuccessCode, response);
  }
}
