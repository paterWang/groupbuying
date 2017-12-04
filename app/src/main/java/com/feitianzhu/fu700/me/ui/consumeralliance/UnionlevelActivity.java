package com.feitianzhu.fu700.me.ui.consumeralliance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.fragment.UnionLevelApplicationFragment;
import com.feitianzhu.fu700.me.fragment.UnionNumberFragment;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.me.ui.UnionApplyRecordActivity;
import com.feitianzhu.fu700.shop.adapter.MyPagerAdapter;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Vya on 2017/9/4 0004.
 */

public class UnionlevelActivity extends BaseActivity {
    @BindView(R.id.tl_2)
    SlidingTabLayout mTl_2;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    private String[] mTitles = { "联盟人数", "联盟级别申请" };
    private List<Fragment> mFragments;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_unionlevel_application;
    }

    @Override
    protected void initTitle() {
        defaultNavigationBar = new DefaultNavigationBar
                .Builder(UnionlevelActivity.this, (ViewGroup)findViewById(R.id.Rl_titleContainer))
                .setTitle("联盟")
                .setStatusHeight(UnionlevelActivity.this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .setRightText("记录", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(UnionlevelActivity.this, UnionApplyRecordActivity.class);
                        startActivity(intent);
                    }
                })
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }

    @Override
    protected void initView() {
        String agentName = (String) getIntent().getSerializableExtra("AgentName");
        String mRate = (String) getIntent().getSerializableExtra("Rate");
        mFragments = new ArrayList<>();
        UnionLevelApplicationFragment unFragment = new UnionLevelApplicationFragment();
        Bundle mBudle = new Bundle();
        mBudle.putString("AgentName",agentName);
        mBudle.putString("Rate",mRate);
        unFragment.setArguments(mBudle);
        mFragments.add(new UnionNumberFragment());
        mFragments.add(unFragment);
        mTl_2.setDividerWidth(0);

    }

    @Override
    protected void initData() {
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),mTitles,mFragments));
        mTl_2.setViewPager(mViewPager,mTitles);
    }
}
