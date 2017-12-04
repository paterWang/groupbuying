package com.feitianzhu.fu700.splash;

import com.feitianzhu.fu700.common.Constant;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import okhttp3.Call;

import static com.feitianzhu.fu700.common.Constant.ACCESSTOKEN;
import static com.feitianzhu.fu700.common.Constant.ACCESS_TOKEN;
import static com.feitianzhu.fu700.common.Constant.CLIENTTYPE;
import static com.feitianzhu.fu700.common.Constant.Common_HEADER;
import static com.feitianzhu.fu700.common.Constant.DEVICETOKEN;
import static com.feitianzhu.fu700.common.Constant.LOGIN_USERID;
import static com.feitianzhu.fu700.common.Constant.PUSHMSG;
import static com.feitianzhu.fu700.common.Constant.USERID;

/**
 * Created by jiangdikai on 2017/11/7.
 */

public class SplashDao {
    public static void postTokie() {
        OkHttpUtils.get()//
                .url(Common_HEADER + PUSHMSG).addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                .addParams(USERID, LOGIN_USERID)//
                .addParams(CLIENTTYPE, "1")//
                .addParams(DEVICETOKEN, Constant.DeviceToken_Value)//
                .build().execute(new Callback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                if ("数据为空".equals(e.getMessage())) {
                    KLog.e("DEVICETOKEN 成功");
                } else {
                    KLog.e("DEVICETOKEN 失败");
                }
            }

            @Override
            public void onResponse(Object response, int id) {
                KLog.e("DEVICETOKEN onResponse ");
            }
        });
    }
}
