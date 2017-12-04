package com.feitianzhu.fu700.me.ui.consumeralliance;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.me.adapter.NewBecomeVolunteerAdapter;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.fragment.ApplyCityAgentFragment;
import com.feitianzhu.fu700.me.fragment.BecomVolunteerFragment;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Vya on 2017/9/4 0004.
 * 新的成为志愿者界面
 */

public class NewBecomeVolunteerActivity extends BaseActivity {
    private String[] titles = {"申请志愿者","申请市区代"};

    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    private List<Fragment> list;
    @Override
    protected int getLayoutId() {
        return R.layout.new_become_volunteer;
    }

    @Override
    protected void initView() {
        list = new ArrayList<>();
        list.add(new BecomVolunteerFragment());
        list.add(new ApplyCityAgentFragment());
        mViewPager.setAdapter(new NewBecomeVolunteerAdapter(getSupportFragmentManager(),titles,list));
    }

    @Override
    protected void initTitle() {
        defaultNavigationBar = new DefaultNavigationBar
                .Builder(NewBecomeVolunteerActivity.this, (ViewGroup)findViewById(R.id.Rl_titleContainer))
                //.setTitle("成为志愿者")
                .setMiddleButton(titles, new OnTabSelectListener() {
                    @Override
                    public void onTabSelect(int position) {
                       // Toast.makeText(NewBecomeVolunteerActivity.this,"AAAA",Toast.LENGTH_SHORT).show();
                        mViewPager.setCurrentItem(position);
                    }

                    @Override
                    public void onTabReselect(int position) {

                    }
                })
                .setStatusHeight(NewBecomeVolunteerActivity.this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .setRightText("记录", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(NewBecomeVolunteerActivity.this,"点击保存",Toast.LENGTH_SHORT).show();
                    }
                })
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }

    private List<String> getTestData(){
        List<String> mList = new ArrayList<>();
        mList.add("AAa");
        return mList;
    }

    @Override
    protected void initData() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    defaultNavigationBar.setCurrentTab(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        mViewPager.setCurrentItem(0);
    }
}
