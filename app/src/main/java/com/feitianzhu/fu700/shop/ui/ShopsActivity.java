package com.feitianzhu.fu700.shop.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.example.zhouwei.library.CustomPopWindow;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.common.TabEntity;
import com.feitianzhu.fu700.common.base.SFActivity;
import com.feitianzhu.fu700.common.impl.DataCallBack;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.common.impl.onNetFinishLinstenerT;
import com.feitianzhu.fu700.me.ui.SeviceAdminActivity;
import com.feitianzhu.fu700.me.ui.ShopsEditActivity;
import com.feitianzhu.fu700.model.ShopsInfo;
import com.feitianzhu.fu700.payforme.FlowEvent;
import com.feitianzhu.fu700.shop.ShopDao;
import com.feitianzhu.fu700.shop.adapter.MyPagerAdapter;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.feitianzhu.fu700.view.NoScrollViewPager;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jaeger.library.StatusBarUtil;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.feitianzhu.fu700.common.Constant.ISADMIN;
import static com.feitianzhu.fu700.common.Constant.MERCHANTID;
import static com.feitianzhu.fu700.common.Constant.SHOPS_MODEL;
import static com.feitianzhu.fu700.common.Constant.TYPE;

/**
 * description: 商铺管理
 * autour: dicallc
 */
public class ShopsActivity extends SFActivity {

  @BindView(R.id.banner) RelativeLayout mBanner;
  @BindView(R.id.viewpager) NoScrollViewPager mViewpager;
  @BindView(R.id.img_back) ImageView mImgBack;
  @BindView(R.id.title_name) TextView mTitleName;
  @BindView(R.id.img_more) ImageView mImgMore;
  @BindView(R.id.appbar) AppBarLayout mAppbar;
  @BindView(R.id.ly_look) LinearLayout mLyLook;
  @BindView(R.id.ly_admin) LinearLayout mLyAdmin;
  @BindView(R.id.ly_look_title) LinearLayout mLyLookTitle;
  @BindView(R.id.img_celect) ImageView mImgCelect;
  @BindView(R.id.img_share) ImageView mImgShare;
  @BindView(R.id.jubao) ImageView mJubao;
  @BindView(R.id.shops_name) TextView mShopsName;
  @BindView(R.id.shops_ratting) MaterialRatingBar mShopsRatting;
  @BindView(R.id.txt_zixun) TextView mTxtZixun;
  @BindView(R.id.ll_pay_container) LinearLayout llPayContainer;
  @BindView(R.id.ly_zixun) FrameLayout lyZixun;
  @BindView(R.id.img_banner) ImageView mImgBanner;
  @BindView(R.id.tab) CommonTabLayout tab;
  @BindView(R.id.look_shop_name) TextView mLookShopName;

  private String[] mTitles = { "服务", "评价", "商家" };
  private LinearLayout ly_edit;
  private LinearLayout ly_service;
  private LinearLayout ly_share;
  private CustomPopWindow mPopWindow;
  private boolean mIsadmin = false;
  private ShopsSellerFragment mSellerFragment;
  private String merchantid;
  private String servicePhone;
  private int mMerchantId;
  private ShopsInfo mShopsModel;
  private long start;
  private long end;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shops);
    ButterKnife.bind(this);
    EventBus.getDefault().register(this);
    mIsadmin = getIntent().getBooleanExtra(ISADMIN, false);
    merchantid = getIntent().getStringExtra(MERCHANTID);
    KLog.e("merchantid" + merchantid);

    initView();
    initTab();
    StatusBarUtil.setTransparent(ShopsActivity.this);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    EventBus.getDefault().unregister(this);
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void onFlowEvent(FlowEvent event) {
    switch (event.getEvent()) {
      case FlowEvent.SHOP_PAY_FLOW:
        finish();
        break;
    }
  }

  private void initTab() {
    ArrayList<CustomTabEntity> mTabEntities = initTabData();
    tab.setTabData(mTabEntities);
    //商家资料先显示
    tab.setCurrentTab(2);
    List<Fragment> mFragments = new ArrayList<>();
    mSellerFragment = ShopsSellerFragment.newInstance(merchantid, "");
    mFragments.add(mSellerFragment);
    final ShopServiceFragment mServiceFragment = ShopServiceFragment.newInstance(mIsadmin);
    final ShopsEvaluateFragment mEvaluateFragment = ShopsEvaluateFragment.newInstance("", "");
    mFragments.add(mServiceFragment);
    mFragments.add(mEvaluateFragment);

    mViewpager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), mTitles, mFragments));
    tab.setOnTabSelectListener(new OnTabSelectListener() {
      @Override public void onTabSelect(int position) {
        if (position == 2) {
          mViewpager.setCurrentItem(0);
          lyZixun.postDelayed(new Runnable() {
            @Override public void run() {
              runOnUiThread(new Runnable() {
                @Override public void run() {
                  lyZixun.setVisibility(View.VISIBLE);
                }
              });
            }
          }, 100);
        } else if (position == 0) {
          mViewpager.setCurrentItem(1);
          lyZixun.setVisibility(View.GONE);
        } else {
          mViewpager.setCurrentItem(2);
          lyZixun.setVisibility(View.GONE);
        }
      }

      @Override public void onTabReselect(int position) {

      }
    });
    mSellerFragment.setDataCallBack(new DataCallBack() {
      @Override public void CallBack(ShopsInfo mShopsInfo) {
        if (null==mShopsInfo){
          tab.setOnTabSelectListener(null);
          tab.setOnClickListener(null);
          return;
        }
        Glide.with(ShopsActivity.this)
            .load(mShopsInfo.adImgs)
            .placeholder(R.mipmap.pic_fuwutujiazaishibai)
            .into(mImgBanner);
        mShopsModel = mShopsInfo;
        servicePhone = mShopsInfo.servicePhone;
        mMerchantId = mShopsInfo.merchantId;
        if (null != mShopsInfo) {
          //不是管理员角色
          if (!mIsadmin) {
            if (TextUtils.isEmpty(mShopsInfo.star)) mShopsInfo.star = "0";
            mLookShopName.setText(mShopsInfo.merchantName + "");
            mTitleName.setText("");
            mShopsRatting.setRating(Float.parseFloat(mShopsInfo.star));
          } else {
            mShopsName.setText(mShopsInfo.merchantName + "");
          }
          mServiceFragment.setMerchantId(mMerchantId + "");
          mEvaluateFragment.setMerchantId(mMerchantId + "");

          String id = mShopsInfo.collectId;
          if (TextUtils.isEmpty(id)) {
            mImgCelect.setSelected(false);
          } else {
            mImgCelect.setSelected(true);
          }
        }
      }
    });
  }

  @NonNull private ArrayList<CustomTabEntity> initTabData() {
    ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    for (int i = 0; i < mTitles.length; i++) {

      mTabEntities.add(new TabEntity(mTitles[i], 0, 0));
    }
    return mTabEntities;
  }

  private final int CONTENTS_VIEW_POSITION = 1;

  private void initView() {
    mImgCelect.setSelected(true);
    if (mIsadmin) {
      mLyLook.setVisibility(View.GONE);
      mShopsRatting.setVisibility(View.GONE);
      mLyAdmin.setVisibility(View.VISIBLE);
      mImgMore.setVisibility(View.VISIBLE);
      mLyLookTitle.setVisibility(View.GONE);
    } else {
      mShopsRatting.setVisibility(View.VISIBLE);
      mLyLook.setVisibility(View.VISIBLE);
      mLyAdmin.setVisibility(View.GONE);
      mImgMore.setVisibility(View.GONE);
      mLyLookTitle.setVisibility(View.VISIBLE);
    }
  }

  @OnClick({
      R.id.img_back, R.id.img_more, R.id.img_celect, R.id.img_share, R.id.jubao,
      R.id.ll_pay_container
  })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.jubao:
        if(null==mShopsModel){
          return;
        }
        Intent mIntent = new Intent(this, ShopReportActivity.class);
        mIntent.putExtra(MERCHANTID, mShopsModel.merchantId + "");
        mIntent.putExtra(TYPE, "2");
        startActivity(mIntent);
        break;
      case R.id.img_share:
        if(null==mShopsModel){
          return;
        }
        showShare();
        break;
      case R.id.img_back:
        finish();
        break;
      case R.id.img_celect:
        if(null==mShopsModel){
          return;
        }
        showloadDialog("收藏中");
        if (mImgCelect.isSelected()) {
          deleteShops();
        } else {
          doCelectShops();
        }

        break;
      case R.id.img_more:
        View mView = View.inflate(ShopsActivity.this, R.layout.shops_pop_view, null);
        ly_edit = (LinearLayout) mView.findViewById(R.id.ly_edit);
        ly_service = (LinearLayout) mView.findViewById(R.id.ly_service);
        ly_share = (LinearLayout) mView.findViewById(R.id.ly_share);
        ly_edit.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            if (TextUtils.isEmpty(mMerchantId + "") && null != mShopsModel) {
              ToastUtils.showShortToast("服务器出差了");
              return;
            }
            Intent mIntent = new Intent(ShopsActivity.this, ShopsEditActivity.class);
            startActivity(mIntent);
            mPopWindow.dissmiss();
          }
        });
        ly_service.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            mPopWindow.dissmiss();
            if (TextUtils.isEmpty(mMerchantId + "") && null != mShopsModel) {
              ToastUtils.showShortToast("服务器出差了");
              return;
            }
            Intent mIntent = new Intent(ShopsActivity.this, SeviceAdminActivity.class);
            mIntent.putExtra(Constant.MERCHANTID, mMerchantId + "");
            startActivity(mIntent);
          }
        });
        ly_share.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            mPopWindow.dissmiss();
            if (TextUtils.isEmpty(mMerchantId + "") && null != mShopsModel) {
              ToastUtils.showShortToast("服务器出差了");
              return;
            }
            showShare();
          }
        });
        //显示的布局，还可以通过设置一个View
        //.size(120,140) //设置显示的大小，不设置就默认包裹内容
        //是否获取焦点，默认为ture
        //是否PopupWindow 以外触摸dissmiss
        //创建PopupWindow
        //显示PopupWindow
        mPopWindow =
            new CustomPopWindow.PopupWindowBuilder(this).setView(mView)//显示的布局，还可以通过设置一个View
                //.size(120,140) //设置显示的大小，不设置就默认包裹内容
                .setFocusable(true)//是否获取焦点，默认为ture
                .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
                .create()//创建PopupWindow
                .showAsDropDown(mImgMore, -20, 20);
        break;
      case R.id.ll_pay_container:
        if (TextUtils.isEmpty(mMerchantId + "")||null == mShopsModel) {
          ToastUtils.showShortToast("该商铺无法买单");
          return;
        }
        Intent mPayIntent = new Intent(ShopsActivity.this, ShopsPayActivity.class);
        mPayIntent.putExtra(SHOPS_MODEL, mShopsModel);
        startActivity(mPayIntent);
        break;
    }
  }

  private void showShare() {

    OnekeyShare oks = new OnekeyShare();
    //关闭sso授权
    oks.disableSSOWhenAuthorize();

    // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
    //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
    // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
    oks.setTitle(mShopsModel.merchantName + "");
    // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
    oks.setTitleUrl(mShopsModel.shareUrl + "");
    // text是分享文本，所有平台都需要这个字段
    oks.setText(mShopsModel.introduce + "");
    // imagePath是图片地址，Linked-In以外的平台都支持此参数
    // 如果不用本地图片，千万不要调用这个方法！！！
    //        oks.setImagePath("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
    oks.setImageUrl(mShopsModel.merchantHeadImg + "");
    // url仅在微信（包括好友和朋友圈）中使用
    oks.setUrl(mShopsModel.shareUrl + "");
    // comment是我对这条分享的评论，仅在人人网和QQ空间使用
    oks.setComment(mShopsModel.introduce + "");
    // site是分享此内容的网站名称，仅在QQ空间使用
    oks.setSite(getString(R.string.app_name));
    // siteUrl是分享此内容的网站地址，仅在QQ空间使用
    oks.setSiteUrl(mShopsModel.shareUrl + "");
    oks.setCallback(new PlatformActionListener() {
      @Override public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        KLog.i("share onComplete...");
      }

      @Override public void onError(Platform platform, int i, Throwable throwable) {
        KLog.i("share onError..." + throwable);
      }

      @Override public void onCancel(Platform platform, int i) {
        KLog.i("share onCancel...");
      }
    });
    // 启动分享GUI
    oks.show(this);
  }

  private void deleteShops() {
    ShopDao.DeleteCollect(mSellerFragment.getCollectId(), new onConnectionFinishLinstener() {
      @Override public void onSuccess(int code, Object result) {
        goneloadDialog();
        mImgCelect.setSelected(false);
      }

      @Override public void onFail(int code, String result) {
        goneloadDialog();
        ToastUtils.showShortToast(result);
      }
    });
  }

  private void doCelectShops() {
    ShopDao.PostCollect(1, mSellerFragment.getMerchantId(), new onNetFinishLinstenerT() {
      @Override public void onSuccess(int code, Object result) {
        mSellerFragment.setmCollectId(result.toString());
        goneloadDialog();
        ToastUtils.showShortToast("收藏成功");
        mImgCelect.setSelected(true);
      }

      @Override public void onFail(int code, String result) {
        goneloadDialog();
        ToastUtils.showShortToast(result);
      }
    });
  }

  private TextView txt_content;
  private Button bt_cancel;
  private Button bt_sure;
  private MaterialDialog mDialog;

  @OnClick(R.id.txt_zixun) public void onZiXunClicked() {
    if (TextUtils.isEmpty(servicePhone)){
      ToastUtils.showShortToast("该商户暂时未添加电话");
      return;
    }
    View mView = View.inflate(this, R.layout.cmmon_confirm_dialog, null);
    initZixunView(mView);
    mDialog = new MaterialDialog.Builder(this).title("是否拨打商家电话").customView(mView, false).show();
  }

  private void initZixunView(View view) {
    txt_content = (TextView) view.findViewById(R.id.txt_content);
    txt_content.setText(servicePhone + "");
    bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
    bt_sure = (Button) view.findViewById(R.id.bt_sure);
    bt_cancel.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        mDialog.dismiss();
      }
    });
    bt_sure.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + servicePhone + "");
        intent.setData(data);
        startActivity(intent);
        mDialog.dismiss();
      }
    });
  }
}
