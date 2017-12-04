package com.feitianzhu.fu700.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.model.PayInfo;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.socks.library.KLog;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by jiangdikai on 2017/9/26.
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constant.WX_APP_ID);
        api.registerApp(Constant.WX_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
        KLog.i("WXPayEntryActivity onReq..." + baseReq.toString());
    }

    @Override
    public void onResp(BaseResp resp) {
        KLog.i("WXPayEntryActivity onResp..." + resp.toString());
            Log.e("Test","-------WXPayEntryActivity  onResp..." + resp.toString());
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == 0) {
                switch (Constant.PayFlag) {
                    case PayInfo.MyMoneyPay:
                        EventBus.getDefault().post(new PayInfo(PayInfo.MyMoneyPay));
                        break;
                    case PayInfo.ShopPay:
                        EventBus.getDefault().post(new PayInfo(PayInfo.ShopPay));
                        break;
                    case PayInfo.SHOPRECORDER:
                        EventBus.getDefault().post(new PayInfo(PayInfo.SHOPRECORDER));
                        break;
                    case PayInfo.UNIONLEVEL:
                        EventBus.getDefault().post(new PayInfo(PayInfo.UNIONLEVEL));
                        break;
                    case PayInfo.PAY_FORME:
                        EventBus.getDefault().post(new PayInfo(PayInfo.PAY_FORME));
                        break;
                    case PayInfo.BUY_SERVICE:
                        EventBus.getDefault().post(new PayInfo(PayInfo.BUY_SERVICE));
                        break;
                }
            } else {
                Toast.makeText(WXPayEntryActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

            }
        } else {
            ToastUtils.showShortToast(resp.getType() + "");

        }
        finish();
    }
}
