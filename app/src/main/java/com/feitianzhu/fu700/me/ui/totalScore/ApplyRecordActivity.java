package com.feitianzhu.fu700.me.ui.totalScore;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.me.adapter.ApplyRecordAdapter;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Vya on 2017/9/5 0005.
 */

public class ApplyRecordActivity extends BaseActivity {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_record;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initTitle() {
        defaultNavigationBar = new DefaultNavigationBar
                .Builder(ApplyRecordActivity.this, (ViewGroup)findViewById(R.id.Rl_titleContainer))
                .setTitle("申请记录")
                .setStatusHeight(ApplyRecordActivity.this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }

    @Override
    protected void initData() {
        List<String> mList = new ArrayList<>();
        mList.add("AAA");
        mList.add("AAA");
        mList.add("AAA");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new ApplyRecordAdapter(mList));
    }
}
