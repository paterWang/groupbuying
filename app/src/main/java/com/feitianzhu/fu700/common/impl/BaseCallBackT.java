package com.feitianzhu.fu700.common.impl;

import com.zhy.http.okhttp.callback.Callback;
import okhttp3.Call;

import static com.feitianzhu.fu700.common.Constant.FailCode;
import static com.feitianzhu.fu700.common.Constant.SuccessCode;

/**
 * Created by dicallc on 2017/9/6 0006.
 */

public class BaseCallBackT<T> extends Callback<T> {

    protected onNetFinishLinstenerT mLinstener;

    public BaseCallBackT(onNetFinishLinstenerT mLinstener) {
        this.mLinstener = mLinstener;
    }


    @Override
    public void onError(Call call, Exception e, int id) {
        mLinstener.onFail(FailCode, e.getMessage());
    }

    @Override
    public void onResponse(T response, int id) {
        if (null==response){
            mLinstener.onFail(FailCode, "服务器出错了");
        }else{
        mLinstener.onSuccess(SuccessCode, response);
        }
    }

}
