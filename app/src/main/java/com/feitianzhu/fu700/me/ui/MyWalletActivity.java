package com.feitianzhu.fu700.me.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.bankcard.BankCardEvent;
import com.feitianzhu.fu700.bankcard.WithdrawActivity;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.common.base.LazyWebActivity;
import com.feitianzhu.fu700.common.base.SFActivity;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.common.impl.onNetFinishLinstenerT;
import com.feitianzhu.fu700.model.WalletModel;
import com.feitianzhu.fu700.shop.ShopDao;
import com.feitianzhu.fu700.shop.ShopHelp;
import com.feitianzhu.fu700.shop.adapter.MyPagerAdapter;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * description: 我的钱包界面
 * autour: dicallc
 */
public class MyWalletActivity extends SFActivity {

  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.viewpage) ViewPager mViewpage;

  List<Fragment> mFragments = new ArrayList<>();
  @BindView(R.id.txt_money) RelativeLayout mTxtMoney;
  @BindView(R.id.txt_my_wallet) RelativeLayout mTxtMyWallet;
  @BindView(R.id.img_one_select) ImageView mImgOneSelect;
  @BindView(R.id.img_two_select) ImageView mImgTwoSelect;
  @BindView(R.id.f_one) FrameLayout mFOne;
  @BindView(R.id.txt_chongzhi) TextView mTxtChongzhi;
  @BindView(R.id.duixian) TextView mDuixian;
  @BindView(R.id.f_two) FrameLayout mFTwo;
  @BindView(R.id.txt_shuoming) TextView mTxtShuoming;
  private String[] mTitles = { "余额", "商户钱包" };
  private WalletModel wallet;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my_wallet);
    ButterKnife.bind(this);
    EventBus.getDefault().register(this);
    mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
    initViewpage();
  }

  @Override protected void onStart() {
    super.onStart();
    ShopDao.loadUserAuthImpl();
    initData();
  }

  private void initData() {
    showloadDialog("");
    ShopDao.loadMyWalletInfo(new onNetFinishLinstenerT<WalletModel>() {
      @Override public void onSuccess(int code, WalletModel result) {
        goneloadDialog();
        wallet = result;
        EventBus.getDefault().post(result);
      }

      @Override public void onFail(int code, String result) {
        goneloadDialog();
        ToastUtils.showShortToast("" + result);
      }
    });
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }

  private void initViewpage() {
    mFragments.add(MyMoneyFragment.newInstance("", ""));
    mFragments.add(MyWalletFragment.newInstance("", ""));
    mViewpage.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), mTitles, mFragments));
    mViewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int currentItem = mViewpage.getCurrentItem();
        resetImage();
        switch (currentItem) {
          case 0:
            mTxtMoney.setSelected(true);
            mImgOneSelect.setVisibility(View.VISIBLE);
            mImgTwoSelect.setVisibility(View.GONE);
            mFTwo.setVisibility(View.VISIBLE);
            mFOne.setVisibility(View.GONE);
            break;
          case 1:
            mTxtMyWallet.setSelected(true);
            mImgOneSelect.setVisibility(View.GONE);
            mImgTwoSelect.setVisibility(View.VISIBLE);
            mFTwo.setVisibility(View.GONE);
            mFOne.setVisibility(View.VISIBLE);
            break;
          case 2:
            break;
          case 3:
            break;
        }
      }

      @Override public void onPageSelected(int position) {

      }

      @Override public void onPageScrollStateChanged(int state) {

      }
    });

    mTxtMoney.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        mViewpage.setCurrentItem(0);
        mTxtMoney.setSelected(true);
        mTxtMyWallet.setSelected(false);
        mImgOneSelect.setVisibility(View.VISIBLE);
        mImgTwoSelect.setVisibility(View.GONE);
        mFTwo.setVisibility(View.VISIBLE);
        mFOne.setVisibility(View.GONE);
        //mWeiXin.setImageResource(R.drawable.tab_weixin_pressed);
      }
    });
    mTxtMyWallet.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        mViewpage.setCurrentItem(1);
        mTxtMoney.setSelected(false);
        mTxtMyWallet.setSelected(true);
        mImgOneSelect.setVisibility(View.GONE);
        mImgTwoSelect.setVisibility(View.VISIBLE);
        mFTwo.setVisibility(View.GONE);
        mFOne.setVisibility(View.VISIBLE);
        //mWeiXin.setImageResource(R.drawable.tab_weixin_pressed);
      }
    });
  }

  private void resetImage() {
    mTxtMoney.setSelected(false);
    mTxtMyWallet.setSelected(false);
  }

  @OnClick({ R.id.duixian, R.id.f_one, R.id.txt_shuoming }) public void onViewClicked(View view) {
    if (wallet == null) {
      ToastUtils.showShortToast("余额获取失败，请重新获取");
      return;
    }
    switch (view.getId()) {
      case R.id.duixian:
        VeriPassword(1, wallet.balance);
        break;
      case R.id.txt_shuoming:
        Intent mIntent = new Intent(this, LazyWebActivity.class);
        mIntent.putExtra(Constant.URL, Constant.MYMONEY_INDEX);
        mIntent.putExtra(Constant.H5_TITLE, "说明");
        startActivity(mIntent);
        break;
      case R.id.f_one:
        VeriPassword(2, wallet.merchantBalance);
        break;
    }
  }

  private void VeriPassword(final int type, final String mBalance) {
    if (TextUtils.isEmpty(mBalance)) {
      ToastUtils.showShortToast("当前金额不足");
      return;
    }
    ShopHelp.veriPassword(this, new onConnectionFinishLinstener() {
      @Override public void onSuccess(int code, Object result) {
        Intent intent = new Intent(MyWalletActivity.this, WithdrawActivity.class);
        intent.putExtra(Constant.INTENT_BALANCE, Double.parseDouble(mBalance));
        intent.putExtra(Constant.INTENT_WITHDRAW_TYPE, type);
        startActivity(intent);
      }

      @Override public void onFail(int code, String result) {
        ToastUtils.showShortToast(result);
      }
    });
  }

  @OnClick(R.id.txt_chongzhi) public void onChongzhiClicked() {
    Intent intent = new Intent(this, WalletPayActivity.class);
    startActivity(intent);
  }

  @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
  public void onMessageEvent(BankCardEvent event) {
    switch (event) {
      case WITHDRAW_SUCCESS:
        KLog.i("兑换成功");

        break;
    }
  }
}
