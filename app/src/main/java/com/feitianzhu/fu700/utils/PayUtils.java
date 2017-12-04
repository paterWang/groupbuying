package com.feitianzhu.fu700.utils;

import android.app.Activity;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.model.PayResult;
import com.socks.library.KLog;

import java.util.Map;

import static com.feitianzhu.fu700.common.Constant.FailCode;
import static com.feitianzhu.fu700.common.Constant.SuccessCode;

/**
 * Created by dicallc on 2017/10/12 0012.
 */

public class PayUtils {

    /**
     * @param mActivity  调用所在Activity
     * @param result     后台返回字段payParamd的value
     * @param mLinstener 接口回调
     */
    public static void aliPay(final Activity mActivity, Object result,
                              final onConnectionFinishLinstener mLinstener) {
        final String orderInfo = result.toString();
        KLog.i("aliPay orderInfo: " + orderInfo);
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(mActivity);
                final Map<String, String> result = alipay.payV2(orderInfo, true);
                KLog.i("map" + result.toString());
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        @SuppressWarnings("unchecked") PayResult payResult = new PayResult(result);
                        /**
                         对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                         */
                        String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                        String resultStatus = payResult.getResultStatus();
                        if (TextUtils.equals(resultStatus, "9000")) {
                            mLinstener.onSuccess(SuccessCode, resultInfo);
                        } else {
                            mLinstener.onFail(FailCode, resultStatus);
                        }
                    }
                });
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
