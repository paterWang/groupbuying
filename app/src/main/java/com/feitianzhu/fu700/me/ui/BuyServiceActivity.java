package com.feitianzhu.fu700.me.ui;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.dao.NetworkDao;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.model.BuyServiceNeedModel;
import com.feitianzhu.fu700.model.PayInfo;
import com.feitianzhu.fu700.model.ShopRecordWxModel;
import com.feitianzhu.fu700.payforme.PayForMeEvent;
import com.feitianzhu.fu700.shop.ShopDao;
import com.feitianzhu.fu700.shop.ShopHelp;
import com.feitianzhu.fu700.utils.PayUtils;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.feitianzhu.fu700.view.CircleImageView;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.feitianzhu.fu700.common.Constant.ACCESSTOKEN;
import static com.feitianzhu.fu700.common.Constant.Common_HEADER;
import static com.feitianzhu.fu700.common.Constant.POST_BUY_SERVICE;
import static com.feitianzhu.fu700.common.Constant.USERID;

/**
 * Created by Vya on 2017/8/31 0031.
 */

public class BuyServiceActivity extends BaseActivity {
    private BuyServiceNeedModel mData;

    @BindView(R.id.tv_TradeName)
    TextView mTradeName;
    @BindView(R.id.tv_TradePrice)
    TextView mTradePrice;
    @BindView(R.id.tv_TradePreferential) //优惠
    TextView mTradePreferential;
    @BindView(R.id.tv_personName)
    TextView mPersonName;
    @BindView(R.id.tv_personId)
    TextView mPersonId;
    @BindView(R.id.civ_pic)
    CircleImageView mCivPic;
    @BindView(R.id.tv_buyner)
    TextView mBuyner;
    @BindView(R.id.tv_buynerPhone)
    TextView mBuynerPhone;
    @BindView(R.id.iv_check1)
    ImageView mIvCheck1;
    @BindView(R.id.iv_check2)
    ImageView mIvCheck2;
    @BindView(R.id.iv_check3)
    ImageView mIvCheck3;
/*    @BindView(R.id.iv_check4)
    ImageView mIvCheck4;*/

    private List<ImageView> mClick;
    private String nickName = "";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_buy_service;
    }

    @Override
    protected void initTitle() {
        defaultNavigationBar = new DefaultNavigationBar
                .Builder(BuyServiceActivity.this, (ViewGroup)findViewById(R.id.ll_Container))
                .setTitle("购买服务")
                .setStatusHeight(BuyServiceActivity.this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }

    @Override
    protected void initView() {
        if(Constant.USER_INFO==null){
            nickName = "";
        }else{
            if(Constant.USER_INFO.nickName==null){
                nickName = "";
            }else {
                nickName = Constant.USER_INFO.nickName;
            }
        }
        ShopDao.loadUserAuthImpl();
        mClick = new ArrayList<>();
          Intent mIntent = getIntent();
        mData = (BuyServiceNeedModel) mIntent.getSerializableExtra("serviceDetailBean");
        if(mData == null){
            mData =  (BuyServiceNeedModel) mIntent.getSerializableExtra("hotServiceBean");
        }

    }

    @Override
    protected void initData() {
        setShowData();
    }


    private void setShowData() {
        Glide.with(this).load(mData.headImg).dontAnimate().placeholder(R.drawable.pic_fuwutujiazaishibai)
                .into(mCivPic);
        mTradeName.setText(mData.serviceName);
        mTradePrice.setText(mData.price+"");
        mTradePreferential.setText("返 "+mData.rebate+"PV");
        mPersonName.setText(mData.contactPerson);
        mPersonId.setText("(ID:"+mData.userId+")");
        mBuyner.setText(nickName);
        mBuynerPhone.setText(mData.contactTel);
        initCheckPic();
        mIvCheck1.setImageResource(R.drawable.icon_xuanze);
        mClick.add(mIvCheck1);
    }

    @OnClick({R.id.iv_check1,R.id.iv_check2,R.id.iv_check3,R.id.bt_ImmediatePay})
    public void onClick(View v){
            switch (v.getId()){
                case R.id.iv_check1:
                   if(!mClick.contains(mIvCheck1)){
                       mClick.remove(0).setImageResource(R.drawable.icon_weixuanze);
                       mIvCheck1.setImageResource(R.drawable.icon_xuanze);
                       mClick.clear();
                       mClick.add(mIvCheck1);

                   }
                    break;
                case R.id.iv_check2:

                    if(!mClick.contains(mIvCheck2)){
                        mClick.remove(0).setImageResource(R.drawable.icon_weixuanze);
                        mIvCheck2.setImageResource(R.drawable.icon_xuanze);
                        mClick.clear();
                        mClick.add(mIvCheck2);

                    }
                    break;
                case R.id.iv_check3:
                    if(!mClick.contains(mIvCheck3)){
                        mClick.remove(0).setImageResource(R.drawable.icon_weixuanze);
                        mIvCheck3.setImageResource(R.drawable.icon_xuanze);
                        mClick.clear();
                        mClick.add(mIvCheck3);

                    }
                    break;
             /*   case R.id.iv_check4:  //服务没有线下支付
                    if(!mClick.contains(mIvCheck4)){
                        mClick.remove(0).setImageResource(R.drawable.icon_weixuanze);
                        mIvCheck4.setImageResource(R.drawable.icon_xuanze);
                        mClick.clear();
                        mClick.add(mIvCheck4);

                    }
                    break;*/

                case R.id.bt_ImmediatePay:
                    String str = "";
                   String text = (String) mClick.get(0).getTag();
                    mData.payChannel = text;
                    sendParamsData();
                    break;
            }
    }


    private void sendParamsData() {
        Log.e("wangyan","打印----->mData "+mData);
        if (mData == null) {
            return;
        }
        showloadDialog("正在支付...");
           /* Log.e("wangyan","打印-----merchantId>"+mData.merchantId+"---serviceId--"+mData.serviceId+"---price---"+mData.price+"--rebate--"+ mData.rebate+"---contactTel--"+mData.contactTel
                    +"----payChannel---"+);*/
           final String mPayChanel =  mData.payChannel;
        ShopHelp.veriPassword(this, new onConnectionFinishLinstener() {

                    @Override
                    public void onSuccess(int code, Object result) {
                       // payMoney(result.toString());
                        String psw = result.toString();
                        NetworkDao.PayBuyService(mData.merchantId + "", mData.serviceId + "", mData.price + "", mData.rebate + "", mData.contactTel,mPayChanel, psw, new onConnectionFinishLinstener() {
                            @Override
                            public void onSuccess(int code, Object result) {
                                if(mPayChanel.equals("balance")){
                                    ToastUtils.showLongToast("支付成功");
                                    finish();
                                }
                                if(mPayChanel.equals("wx")){
                                    if (result == null) {
                                        ToastUtils.showShortToast("微信支付失败");
                                        return;
                                    }
                                    Constant.PayFlag = PayInfo.BUY_SERVICE;
                                    CallWxPay(result.toString());
                                }
                                if(mPayChanel.equals("alipay")){
                                    final String orderInfo = result.toString();
                                    PayUtils.aliPay(BuyServiceActivity.this, orderInfo, new onConnectionFinishLinstener() {
                                        @Override
                                        public void onSuccess(int code, Object result) {
                                            ToastUtils.showShortToast("支付成功");
                                            finish();
                                        }

                                        @Override
                                        public void onFail(int code, String result) {
                                            ToastUtils.showShortToast("支付失败");
                                            goneloadDialog();
                                            EventBus.getDefault().post(PayForMeEvent.PAY_FAILURE);
                                        }
                                    });
                                }

                            }

                            @Override
                            public void onFail(int code, String result) {
                                ToastUtils.showLongToast(result);
                                goneloadDialog();
                            }
                        });
                    }

                    @Override
                    public void onFail(int code, String result) {
                        if("没有设置二级密码".equals(result)){
                            goneloadDialog();
                        }else {
                            ToastUtils.showLongToast(result);
                            goneloadDialog();
                        }

                    }
                });



    }

    private void CallWxPay(String result) {

        Log.e("Test", "--------result----->" + result);
        Gson gson = new Gson();
        ShopRecordWxModel mResult = gson.fromJson(result.toString(), ShopRecordWxModel.class);
        IWXAPI api = WXAPIFactory.createWXAPI(BuyServiceActivity.this, mResult.getAppid());
        api.registerApp(mResult.getAppid());
        PayReq mPayReq = new PayReq();
        Constant.WX_APP_ID = mResult.getAppid() + "";
        mPayReq.appId = mResult.getAppid();
        mPayReq.partnerId = mResult.getPartnerid();
        mPayReq.prepayId = mResult.getPrepayid();
        mPayReq.packageValue = "Sign=WXPay";
        mPayReq.nonceStr = mResult.getNoncestr();
        mPayReq.timeStamp = mResult.getTimestamp() + "";
        mPayReq.sign = mResult.getSign();
        api.sendReq(mPayReq);
        ToastUtils.showShortToast("正在打开微信中");
    }

    private void payMoney(String psw) {
        PostFormBuilder mPost = OkHttpUtils.post();
        mPost.url(Common_HEADER + POST_BUY_SERVICE)
                .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                .addParams(USERID, Constant.LOGIN_USERID)
                .addParams("merchantId", mData.merchantId+"")
                .addParams("serviceId", mData.serviceId + "")
                .addParams("amount", mData.price + "")
                .addParams("rebatePv", mData.rebate+"")
                .addParams("linkPhone", mData.contactTel)
                .addParams("payChannel", mData.payChannel)
                .addParams("payPass", psw)

                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(String mData, Response response, int id)
                            throws Exception {
                        return mData;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if ("数据为空".equals(e.getMessage())) {
                            ToastUtils.showShortToast("支付成功");
                            finish();
                        } else {
                            ToastUtils.showShortToast("支付失败");
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onPayMessageCall(PayInfo msg) {
        switch (msg.getCurrentInfo()) {
            case PayInfo.BUY_SERVICE:
                goneloadDialog();
                ToastUtils.showShortToast("支付成功");
                finish();
                break;

        }
    }


    public void initCheckPic(){
      mIvCheck1.setBackgroundResource(R.drawable.icon_weixuanze);
      mIvCheck2.setBackgroundResource(R.drawable.icon_weixuanze);
      mIvCheck3.setBackgroundResource(R.drawable.icon_weixuanze);
     // mIvCheck4.setBackgroundResource(R.drawable.icon_weixuanze);
    }
}
