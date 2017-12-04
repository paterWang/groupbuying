package com.feitianzhu.fu700.me.ui.consumeralliance;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vya on 2017/9/4 0004.
 * 成为志愿者界面，由于需求变更该界面不再使用
 */

public class BecomeVolunteerActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_become_volunteer;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initTitle() {
        defaultNavigationBar = new DefaultNavigationBar
                .Builder(BecomeVolunteerActivity.this, (ViewGroup)findViewById(R.id.Rl_titleContainer))
                .setTitle("成为志愿者")
                .setStatusHeight(BecomeVolunteerActivity.this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .setRightText("记录", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(BecomeVolunteerActivity.this,"点击保存",Toast.LENGTH_SHORT).show();
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

    }
}
