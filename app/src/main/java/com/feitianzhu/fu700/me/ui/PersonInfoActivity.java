package com.feitianzhu.fu700.me.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.me.fragment.PersonInfoFragment;
import com.feitianzhu.fu700.me.navigationbar.AbsNavigationbar;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.me.testFragment;
import com.feitianzhu.fu700.view.CustomPopWindow;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Vya on 2017/8/30 0030.
 * 该类已被弃用
 */

public class PersonInfoActivity extends AppCompatActivity {

    @BindView(R.id.tl_2)
    SlidingTabLayout mTl_2;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
  /*  @BindView(R.id.titlebar_menu)
    ImageView titleBar_menu;*/

    private DefaultNavigationBar defaultNavigationBar;
    private String[] mTitles = { "资料", "商铺" };
    private List<Fragment> mFragments;
    private CustomPopWindow mCustomPopWindow;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_shop_detail);
        ButterKnife.bind(this);
        initTitle();
        mFragments = new ArrayList<>();
        mFragments.add(new PersonInfoFragment());
        mFragments.add(new testFragment());

        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTl_2.setViewPager(mViewPager,mTitles);
    }

    private void initTitle() {
        defaultNavigationBar = new DefaultNavigationBar
                .Builder(PersonInfoActivity.this, (ViewGroup)findViewById(R.id.Rl_titleContainer))
//                .setTitle("标题文字")
//                .setStatusHeight()
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .showRightPic(R.drawable.icon_gengduo, AbsNavigationbar.PIC_THREE, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPopMenu(v);

                    }
                })
                .builder();
    }
    private void showPopMenu(View v){
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_menu,null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        //创建并显示popWindow
        mCustomPopWindow= new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .create();
        int width = mCustomPopWindow.getWidth();
        mCustomPopWindow.showAsDropDown(v, -width+v.getWidth(),20);


    }
    private void handleLogic(View contentView) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCustomPopWindow!=null){
                    mCustomPopWindow.dissmiss();
                }
                String showContent = "";
                switch (v.getId()){
                    case R.id.menu1:
                        showContent = "点击 Item菜单1";
                        break;
                    case R.id.menu2:
                        showContent = "点击 Item菜单2";
                        break;
                }
                Toast.makeText(PersonInfoActivity.this,showContent,Toast.LENGTH_SHORT).show();
            }
        };
        contentView.findViewById(R.id.menu1).setOnClickListener(listener);
        contentView.findViewById(R.id.menu2).setOnClickListener(listener);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
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
}
