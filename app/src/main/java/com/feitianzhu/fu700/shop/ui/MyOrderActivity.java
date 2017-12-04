package com.feitianzhu.fu700.shop.ui;

import android.support.v4.app.Fragment;
import butterknife.BindView;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.base.LazyBaseActivity;
import com.feitianzhu.fu700.me.ui.ServeOrderFragment;
import com.feitianzhu.fu700.me.ui.ShopOrderFragment;
import com.feitianzhu.fu700.shop.adapter.MyPagerAdapter;
import com.feitianzhu.fu700.view.NoScrollViewPager;
import com.flyco.tablayout.SlidingTabLayout;
import java.util.ArrayList;
/**
 * description: 我的订单界面
 * autour: dicallc
*/
public class MyOrderActivity extends LazyBaseActivity {

  @BindView(R.id.tl_2) SlidingTabLayout mTl2;
  @BindView(R.id.viewpager) NoScrollViewPager mViewpager;
  private ArrayList<Fragment> mFragments = new ArrayList<>();
  private String[] mTitles = {"商户订单", "服务订单"};
  private MyPagerAdapter mAdapter;

  @Override protected int setView() {
    return R.layout.activity_my_order;
  }

  @Override protected void initView() {
    setTitleName("我的订单");
    mFragments.add(ShopOrderFragment.newInstance("",""));
    mFragments.add(ServeOrderFragment.newInstance("",""));
    mAdapter = new MyPagerAdapter(getSupportFragmentManager(),mTitles,mFragments);
    mViewpager.setAdapter(mAdapter);
    mTl2.setViewPager(mViewpager);
  }

  @Override protected void initLocal() {

  }

  @Override protected void initData() {

  }

}
