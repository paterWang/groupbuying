package com.feitianzhu.fu700.common.impl;

public interface onNetFinishLinstenerT<T>  {
  public void onSuccess(int code, T result);

  public void onFail(int code, String result);
}