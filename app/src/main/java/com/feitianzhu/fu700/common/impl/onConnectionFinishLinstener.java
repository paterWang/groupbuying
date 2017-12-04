package com.feitianzhu.fu700.common.impl;

public interface onConnectionFinishLinstener {
  public void onSuccess(int code, Object result);

  public void onFail(int code, String result);
}