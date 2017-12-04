package com.feitianzhu.fu700.me.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MyOrderAdapter extends FragmentPagerAdapter {
  private String[] mTitles;
  private List<Fragment> mFragments;

  public MyOrderAdapter(FragmentManager fm, String[] mTitles, List<Fragment> mFragments) {
    super(fm);
    this.mTitles = mTitles;
    this.mFragments = mFragments;
  }

  public MyOrderAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public int getCount() {
      return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return mTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
      return mFragments.get(position);
    }
  }