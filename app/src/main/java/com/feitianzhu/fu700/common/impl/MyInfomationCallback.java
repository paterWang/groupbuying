package com.feitianzhu.fu700.common.impl;

import com.feitianzhu.fu700.model.UserInformation;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.Callback;
import okhttp3.Response;

/**
 * Created by dicallc on 2017/9/4 0004.
 */

public abstract class MyInfomationCallback extends Callback<UserInformation> {
  @Override public UserInformation parseNetworkResponse(String mData, Response response, int id)
      throws Exception {
    UserInformation user = new Gson().fromJson(mData, UserInformation.class);
    return user;
  }
}

