package com.feitianzhu.fu700.me.ui.totalScore;

import android.view.ViewGroup;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;

/**
 * Created by Vya on 2017/9/4 0004.
 */

public class BalanceActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_to_balance;
    }

    @Override
    protected void initTitle() {
        defaultNavigationBar = new DefaultNavigationBar
                .Builder(BalanceActivity.this, (ViewGroup)findViewById(R.id.ll_Container))
                .setTitle("转入余额")
                .setStatusHeight(BalanceActivity.this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
