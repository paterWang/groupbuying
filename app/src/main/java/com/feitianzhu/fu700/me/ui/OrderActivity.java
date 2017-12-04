package com.feitianzhu.fu700.me.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;

/**
 * Created by Vya on 2017/9/4 0004.
 */

public class OrderActivity extends BaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_qrcode;
    }

    @Override
    protected void initTitle() {
        defaultNavigationBar = new DefaultNavigationBar
                .Builder(OrderActivity.this, (ViewGroup)findViewById(R.id.Rl_titleContainer))
                .setTitle("订单二维码")
                .setStatusHeight(OrderActivity.this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .setRightText("保存", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(OrderActivity.this,"点击保存",Toast.LENGTH_SHORT).show();
                    }
                })
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
