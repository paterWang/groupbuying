package com.feitianzhu.fu700.me.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.common.base.LazyWebActivity;
import com.feitianzhu.fu700.me.fragment.PartnerBonusFragment;
import com.feitianzhu.fu700.me.fragment.PromotionBonusFragment;
import com.feitianzhu.fu700.me.fragment.ReleaseBonusFragment;
import com.feitianzhu.fu700.me.fragment.SharedBonusFragment;
import com.feitianzhu.fu700.model.TotalScoreModel;
import com.feitianzhu.fu700.shop.adapter.MyPagerAdapter;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.feitianzhu.fu700.common.Constant.ACCESSTOKEN;
import static com.feitianzhu.fu700.common.Constant.Common_HEADER;
import static com.feitianzhu.fu700.common.Constant.POST_TOTALSCORE;
import static com.feitianzhu.fu700.common.Constant.USERID;

/**
 * 总积分
 */
public class TotalScoreActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.viewpage)
    ViewPager mViewpage;

    List<Fragment> mFragments = new ArrayList<>();
    @BindView(R.id.txt_money)
    RelativeLayout mTxtMoney;
    @BindView(R.id.txt_my_wallet)
    RelativeLayout mTxtMyWallet;
    @BindView(R.id.txt_my_hehuoren)
    RelativeLayout mTxtHehuoren;
    @BindView(R.id.txt_my_gongxiang)
    RelativeLayout mTxtGongxiang;

    @BindView(R.id.iv_trigleOne)
    ImageView iv_trigleOne;
    @BindView(R.id.iv_trigleTwo)
    ImageView iv_trigleTwo;
    @BindView(R.id.iv_trigleThree)
    ImageView iv_trigleThree;
    @BindView(R.id.iv_trigleFour)
    ImageView iv_trigleFour;
    protected MaterialDialog mDialog;
    private String[] mTitles = {"释放红利", "推广红利", "合伙人红利", "共享红利"};
    private int defaultIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_score);
        ButterKnife.bind(this);

        initView();
        initData();

    }


    private void initView() {
        requestData(defaultIndex);
    }

    private void requestData(final int ItemIndex) {
        showloadDialog("正在加载");
        OkHttpUtils.post()//
                .url(Common_HEADER + POST_TOTALSCORE)
                .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                .addParams(USERID, Constant.LOGIN_USERID)
                .build().execute(new Callback<TotalScoreModel>() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("Test", "--Error-->" + e.getMessage());
                goneloadDialog();
                ToastUtils.showShortToast(e.getMessage());
            }

            @Override
            public void onResponse(TotalScoreModel response, int id) {
                AsyncSetData(response);
                goneloadDialog();
            }
        });
    }

    private void AsyncSetData(TotalScoreModel response) {
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("model", response);
        ReleaseBonusFragment releaseBonusFragment = new ReleaseBonusFragment();
        releaseBonusFragment.setArguments(mBundle);
        PromotionBonusFragment promotionBonusFragment = new PromotionBonusFragment();
        promotionBonusFragment.setArguments(mBundle);
        PartnerBonusFragment partnerBonusFragment = new PartnerBonusFragment();
        partnerBonusFragment.setArguments(mBundle);
        SharedBonusFragment sharedBonusFragment = new SharedBonusFragment();
        sharedBonusFragment.setArguments(mBundle);
        mFragments.add(releaseBonusFragment);
        mFragments.add(promotionBonusFragment);
        mFragments.add(partnerBonusFragment);
        mFragments.add(sharedBonusFragment);
        mViewpage.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), mTitles, mFragments));
    }

    protected void showloadDialog(String title) {
        mDialog = new MaterialDialog.Builder(TotalScoreActivity.this).title(title)
                .content("加载中,请稍候...")
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show();
    }

    protected void goneloadDialog() {
        if (!isFinishing() && null != mDialog && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    private void initData() {

        mViewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int currentItem = mViewpage.getCurrentItem();
                resetImage();
                switch (currentItem) {
                    case 0:
                        mTxtMoney.setSelected(true);
                        iv_trigleOne.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        mTxtMyWallet.setSelected(true);
                        iv_trigleTwo.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        mTxtHehuoren.setSelected(true);
                        iv_trigleThree.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        mTxtGongxiang.setSelected(true);
                        iv_trigleFour.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mTxtMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetImage();
                mViewpage.setCurrentItem(0);
                mTxtMoney.setSelected(true);
                //mWeiXin.setImageResource(R.drawable.tab_weixin_pressed);
            }
        });
        mTxtMyWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetImage();
                mViewpage.setCurrentItem(1);
                mTxtMyWallet.setSelected(true);
                //mWeiXin.setImageResource(R.drawable.tab_weixin_pressed);
            }
        });

        mTxtHehuoren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetImage();
                mViewpage.setCurrentItem(2);
                mTxtHehuoren.setSelected(true);
            }
        });
        mTxtGongxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetImage();
                mViewpage.setCurrentItem(3);
                mTxtGongxiang.setSelected(true);
            }
        });

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void resetImage() {
        mTxtMoney.setSelected(false);
        mTxtMyWallet.setSelected(false);
        mTxtHehuoren.setSelected(false);
        mTxtGongxiang.setSelected(false);
        iv_trigleOne.setVisibility(View.INVISIBLE);
        iv_trigleTwo.setVisibility(View.INVISIBLE);
        iv_trigleThree.setVisibility(View.INVISIBLE);
        iv_trigleFour.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.tv_detail)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_detail:
                Intent intent = new Intent(TotalScoreActivity.this, LazyWebActivity.class);
                intent.putExtra(Constant.URL, Constant.TOTAL_DETAIL);
                intent.putExtra(Constant.H5_TITLE, "总积分规则");
                startActivity(intent);
                break;
        }
    }

    private void sendRequest() {
   /* OkHttpUtils.post()//
            .url(Common_HEADER + Constant.POST_SCORE_GET)
            .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
            .addParams(USERID, Constant.LOGIN_USERID)
            .build()
            .execute(new Callback<MineQRcodeModel>() {

              @Override
              public void onError(Call call, Exception e, int id) {
                Log.e("wangyan","onError---->"+e.getMessage());
              }

              @Override
              public void onResponse(MineQRcodeModel response, int id) {
                setShowData(response);
              }

            });*/
    }
}
