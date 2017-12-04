package com.feitianzhu.fu700.me.ui;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ViewGroup;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.fragment.ShopRecordDetailFragment;
import com.feitianzhu.fu700.me.fragment.ShopRecordDetailRefuseFragment;
import com.feitianzhu.fu700.me.fragment.ShopRecordDetailReviewFragment;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.model.ShopRecordDetailModel;
import com.feitianzhu.fu700.shop.adapter.MyPagerAdapter;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

import static com.feitianzhu.fu700.common.Constant.ACCESSTOKEN;
import static com.feitianzhu.fu700.common.Constant.Common_HEADER;
import static com.feitianzhu.fu700.common.Constant.POST_SHOP_RECORDEDETAIL;
import static com.feitianzhu.fu700.common.Constant.USERID;

/**
 * Created by Vya on 2017/9/13 0013.
 * 商家录单的详情页
 */

public class ShopRecordDetailActivity extends BaseActivity {

    @BindView(R.id.tl_2)
    SlidingTabLayout mTl_2;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    private List<Fragment> mFragments;
    private List<String> mListTitles ;

    private String[] mTitles = { "审核中", "已通过","已驳回"};
    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_record_detail;
    }

    @Override
    protected void initTitle() {
        defaultNavigationBar = new DefaultNavigationBar
                .Builder(ShopRecordDetailActivity.this, (ViewGroup)findViewById(R.id.Rl_titleContainer))
                .setStatusHeight(ShopRecordDetailActivity.this)
                .setTitle("商家录单")
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }

    @Override
    protected void initView() {
        mFragments = new ArrayList<>();
        mListTitles = new ArrayList<>();
        mFragments.add(new ShopRecordDetailFragment());
        mFragments.add(new ShopRecordDetailReviewFragment());
        mFragments.add(new ShopRecordDetailRefuseFragment());
        mTl_2.setDividerWidth(0);

    }

    @Override
    protected void initData() {
        requestDataSetTitle();
    }

    /**
     * 请求服务器的数据，更新Title的数字
     */
    private void requestDataSetTitle() {
        OkHttpUtils.post()//
                .url(Common_HEADER + POST_SHOP_RECORDEDETAIL)
                .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                .addParams(USERID, Constant.LOGIN_USERID)
                .addParams("status", "0")//
                .addParams("pageIndex", "0")//
                .addParams("pageRows", "6")//
                .build()
                .execute(new Callback<ShopRecordDetailModel>() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("wangyan","onError---->"+e.getMessage());
                        ToastUtils.showShortToast(e.getMessage());

                    }

                    @Override
                    public void onResponse(ShopRecordDetailModel response, int id) {
                            setShowData(response);
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("Test","-----onStart-----ShopRecordDetailActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Test","----onResume------ShopRecordDetailActivity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("Test","----onDestroy------ShopRecordDetailActivity");
    }

    private void setShowData(ShopRecordDetailModel response) {
        if (response==null||response.getStatusCnts()==null||response.getStatusCnts().size()<=2){
            mListTitles.add(String.format(getString(R.string.pay_for_me_ing),"0"));
            mListTitles.add(String.format(getString(R.string.pay_for_me_com),"0"));
            mListTitles.add(String.format(getString(R.string.pay_for_me_rej),"0"));
        }else{
            mListTitles.add(String.format(getString(R.string.pay_for_me_ing),response.getStatusCnts().get(1).getCnt()+""));
            mListTitles.add(String.format(getString(R.string.pay_for_me_com),response.getStatusCnts().get(2).getCnt()+""));
            mListTitles.add(String.format(getString(R.string.pay_for_me_rej),response.getStatusCnts().get(0).getCnt()+""));
        }

        String [] mTemp = mListTitles.toArray(new String[3]);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),mTitles,mFragments));
        mTl_2.setViewPager(mViewPager,mTemp);
        //mViewPager.setCurrentItem(0);
    }
}
