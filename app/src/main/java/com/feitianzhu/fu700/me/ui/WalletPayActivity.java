package com.feitianzhu.fu700.me.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.OnClick;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.base.LazyBaseActivity;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.model.PayInfo;
import com.feitianzhu.fu700.shop.ShopDao;
import com.feitianzhu.fu700.shop.ShopHelp;
import com.feitianzhu.fu700.utils.PayUtils;
import com.feitianzhu.fu700.utils.ToastUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * dicallc
 * 钱包充值界面
 */
public class WalletPayActivity extends LazyBaseActivity {

  @BindView(R.id.edt_money) EditText mEdtMoney;
  @BindView(R.id.iv_check1) ImageView mIvCheck1;
  @BindView(R.id.ly_one) RelativeLayout mLyOne;
  @BindView(R.id.iv_check2) ImageView mIvCheck2;
  @BindView(R.id.ly_two) RelativeLayout mLyTwo;
  @BindView(R.id.rl_bottomContainer) Button mRlBottomContainer;

  @Override protected int setView() {
    return R.layout.activity_wallet_pay;
  }

  @Override protected void initView() {
    EventBus.getDefault().register(this);
    setTitleName("充值");
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }

  @Override protected void initLocal() {

  }

  @Override protected void initData() {

  }

  //重置所有文本的选中状态
  public void selected() {
    mIvCheck1.setSelected(false);
    mIvCheck2.setSelected(false);
  }

  @OnClick({ R.id.ly_one, R.id.ly_two, R.id.rl_bottomContainer })
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
      case R.id.rl_bottomContainer:
        String money = mEdtMoney.getText().toString().trim();
        if (TextUtils.isEmpty(money)) {
          ToastUtils.showShortToast("金额还没有填写");
          return;
        }
        if (mIvCheck1.isSelected()) {
          //微信支付
          ShopHelp.WxPay(money, sfContext);
        } else {
          aliPay(money);
        }

        break;
    }
  }

  private void aliPay(String mMoney) {
    ShopDao.postAliPayPay(mMoney, new onConnectionFinishLinstener() {
      @Override public void onSuccess(int code, Object result) {
        String str = result.toString();
        PayUtils.aliPay(WalletPayActivity.this, str, new onConnectionFinishLinstener() {
          @Override public void onSuccess(int code, Object result) {
            ToastUtils.showShortToast("充值成功");
            finish();
          }

          @Override public void onFail(int code, String result) {
            ToastUtils.showShortToast("充值失败");
          }
        });
      }

      @Override public void onFail(int code, String result) {
        ToastUtils.showShortToast(result);
      }
    });
  }

  @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
  public void onPayMessageCall(PayInfo msg) {
    switch (msg.getCurrentInfo()) {
      case PayInfo.MyMoneyPay:
        finish();
        break;
    }
  }
}
