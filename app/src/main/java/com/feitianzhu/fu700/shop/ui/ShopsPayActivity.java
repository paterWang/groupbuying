package com.feitianzhu.fu700.shop.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.common.base.LazyBaseActivity;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.common.impl.onNetFinishLinstenerT;
import com.feitianzhu.fu700.me.ui.totalScore.TransferVoucherActivity;
import com.feitianzhu.fu700.model.PayInfo;
import com.feitianzhu.fu700.model.ShopsInfo;
import com.feitianzhu.fu700.model.WXModel;
import com.feitianzhu.fu700.payforme.FlowEvent;
import com.feitianzhu.fu700.shop.ShopDao;
import com.feitianzhu.fu700.shop.ShopHelp;
import com.feitianzhu.fu700.utils.PayUtils;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.socks.library.KLog;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * description: 商铺买单
 * autour: dicallc
 */
public class ShopsPayActivity extends LazyBaseActivity {

  @BindView(R.id.item_icon) ImageView mItemIcon;
  @BindView(R.id.item_name) TextView mItemName;
  @BindView(R.id.item_address) TextView mItemAddress;
  @BindView(R.id.item_msg) TextView mItemMsg;
  @BindView(R.id.edt_money) EditText mEdtMoney;
  @BindView(R.id.iv_check1) ImageView mIvCheck1;
  @BindView(R.id.iv_check2) ImageView mIvCheck2;
  @BindView(R.id.iv_check3) ImageView mIvCheck3;
  @BindView(R.id.linearLayout) LinearLayout mLinearLayout;
  @BindView(R.id.iv_check4) ImageView mIvCheck4;
  @BindView(R.id.view2) View mView2;
  @BindView(R.id.rl_bottomContainer) Button mRlBottomContainer;
  @BindView(R.id.ly_one) RelativeLayout mLyOne;
  @BindView(R.id.ly_two) RelativeLayout mLyTwo;
  @BindView(R.id.ly_three) RelativeLayout mLyThree;
  @BindView(R.id.ly_four) RelativeLayout mLyFour;
  private String merchantid;
  private String consumeamount;
  private String payChannel;
  private String merchantId;

  @Override protected int setView() {
    return R.layout.activity_shops_pay;
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }

  @Override protected void initView() {
    EventBus.getDefault().register(this);
    setTitleName("买单");
    mIvCheck1.setSelected(true);
  }



  private void initShop(ShopsInfo mShopsInfo) {
    Glide.with(this)
        .load(mShopsInfo.merchantHeadImg+"")
        .placeholder(R.mipmap.pic_fuwutujiazaishibai)
        .into(mItemIcon);
    mItemName.setText(mShopsInfo.merchantName + "");
    mItemAddress.setText(mShopsInfo.provinceName + mShopsInfo.cityName + "");
    mItemMsg.setText(mShopsInfo.dtlAddr + "");
    merchantid = mShopsInfo.merchantId + "";
  }

  @Override protected void initLocal() {
    ShopDao.loadUserAuthImpl();
    ShopsInfo mShopsInfo = getIntent().getParcelableExtra(Constant.SHOPS_MODEL);
    merchantId = getIntent().getStringExtra(Constant.MERCHANTID);
    if (!TextUtils.isEmpty(merchantId)) {
      showloadDialog("");
      ShopDao.loadShopsInfo(merchantId, new onConnectionFinishLinstener() {
        @Override public void onSuccess(int code, Object result) {
          ShopsInfo info = (ShopsInfo) result;
          initShop(info);
          goneloadDialog();
        }

        @Override public void onFail(int code, String result) {
          goneloadDialog();
          ToastUtils.showLongToast(result);
        }
      });
    } else {
      initShop(mShopsInfo);
    }
  }

  @Override protected void initData() {

  }

  private void pay(String mPassowrd) {
    if ("wx".equals(payChannel)){
      showloadDialog("");
      ShopDao.postShopWxPay(merchantid, consumeamount, payChannel, mPassowrd,
          new onNetFinishLinstenerT<WXModel>() {
            @Override public void onSuccess(int code, WXModel result) {
              Constant.PayFlag= PayInfo.ShopPay;
              IWXAPI api = WXAPIFactory.createWXAPI(ShopsPayActivity.this, result.appid);
              api.registerApp(result.appid);
              PayReq mPayReq = new PayReq();
              Constant.WX_APP_ID=result.appid+"";
              mPayReq.appId = result.appid;
              mPayReq.partnerId = result.partnerid;
              mPayReq.prepayId = result.prepayid;
              mPayReq.packageValue = "Sign=WXPay";
              mPayReq.nonceStr = result.noncestr;
              mPayReq.timeStamp = result.timestamp + "";
              mPayReq.sign = result.sign;
              api.sendReq(mPayReq);
              goneloadDialog();
              ToastUtils.showShortToast("正在打开微信中");
            }

            @Override public void onFail(int code, String result) {
              goneloadDialog();
              ToastUtils.showShortToast(result);
            }
          });
    }else{
      ShopDao.postShopPay(merchantid, consumeamount, payChannel, mPassowrd,
          new onConnectionFinishLinstener() {
            @Override public void onSuccess(int code, Object result) {
              if ("alipay".equals(payChannel)) {
                aliPay(result);
              } else {
                ToastUtils.showShortToast("付款成功");
                KLog.e(result);
                onPaySuccess();
              }
            }

            @Override public void onFail(int code, String result) {
              ToastUtils.showShortToast(result);
            }
          });
    }

  }

  private void aliPay(Object result) {
    final String orderInfo = result.toString();
    PayUtils.aliPay(this, orderInfo, new onConnectionFinishLinstener() {
      @Override public void onSuccess(int code, Object result) {
        ToastUtils.showShortToast("支付成功");
        onPaySuccess();
      }

      @Override public void onFail(int code, String result) {
        ToastUtils.showShortToast("支付失败");
      }
    });
  }

  private void onPaySuccess() {
    Intent mIntent=new Intent(ShopsPayActivity.this,MyOrderActivity.class);
    startActivity(mIntent);
    EventBus.getDefault().post(new FlowEvent(FlowEvent.SHOP_PAY_FLOW));
    finish();
  }

  @OnClick({ R.id.ly_one, R.id.ly_two, R.id.ly_three, R.id.ly_four, R.id.rl_bottomContainer })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.ly_one:
        selected();
        mIvCheck1.setSelected(true);
        break;
      case R.id.ly_two:
        selected();
        mIvCheck2.setSelected(true);
        break;
      case R.id.ly_three:
        selected();
        mIvCheck3.setSelected(true);
        break;
      case R.id.ly_four:
        selected();
        mIvCheck4.setSelected(true);
        break;
      case R.id.rl_bottomContainer:
        //if (!Constant.loadUserAuth){
        //  ShopDao.loadUserAuthImpl();
        //  return;
        //}
        ShopHelp.veriPassword(this, new onConnectionFinishLinstener() {
          @Override public void onSuccess(int code, Object result) {
            String passowrd = (String) result;
            String money = mEdtMoney.getText().toString().trim();
            consumeamount = money;
            if (TextUtils.isEmpty(money)) {
              ToastUtils.showShortToast("金额还没有填写");
              return;
            }
            if (mIvCheck1.isSelected()) {
              payChannel = "wx";
            } else if (mIvCheck2.isSelected()) {
              payChannel = "alipay";
            } else if (mIvCheck3.isSelected()) {
              payChannel = "balance";
            } else {
              // TODO: 2017/9/14 0014 传参
              Intent mIntent = new Intent(ShopsPayActivity.this, TransferVoucherActivity.class);
              startActivity(mIntent);
              return;
            }
            pay(passowrd);
          }

          @Override public void onFail(int code, String result) {

          }
        });

        break;
    }
  }

  //重置所有文本的选中状态
  public void selected() {
    mIvCheck1.setSelected(false);
    mIvCheck2.setSelected(false);
    mIvCheck3.setSelected(false);
    mIvCheck4.setSelected(false);
  }

  @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
  public void onPayMessageCall(PayInfo msg) {
    switch (msg.getCurrentInfo()) {
      case PayInfo.ShopPay:
        onPaySuccess();
        break;
    }
  }
}
