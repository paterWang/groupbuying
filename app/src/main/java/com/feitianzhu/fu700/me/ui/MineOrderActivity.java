package com.feitianzhu.fu700.me.ui;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.me.adapter.MyOrderAdapter;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.fragment.OrderUnUseFragment;
import com.feitianzhu.fu700.me.fragment.OrderUseFragment;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Vya on 2017/9/4 0004.
 */

public class MineOrderActivity extends BaseActivity {
    @BindView(R.id.tl_2)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    private String[] mTitles = { "未使用", "已使用" };
    private List<Fragment> mFragments;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine_order;
    }

    @Override
    protected void initTitle() {
        defaultNavigationBar = new DefaultNavigationBar
                .Builder(MineOrderActivity.this, (ViewGroup)findViewById(R.id.ll_Container))
                .setTitle("我的订单")
                .setStatusHeight(MineOrderActivity.this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }

    @Override
    protected void initView() {
        mFragments = new ArrayList<>();
        mFragments.add(new OrderUnUseFragment());
        mFragments.add(new OrderUseFragment());
    }

    @Override
    protected void initData() {
        mViewPager.setAdapter(new MyOrderAdapter(getSupportFragmentManager(),mTitles,mFragments));
        mSlidingTabLayout.setViewPager(mViewPager,mTitles);

    }
}
