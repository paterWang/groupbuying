package com.feitianzhu.fu700.me.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.me.adapter.GoodsAdapter;
import com.feitianzhu.fu700.me.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Vya on 2017/9/5 0005.
 * 商品界面
 */

public class GoodsFragment extends BaseFragment {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        List<String> mList = new ArrayList<>();
        mList.add("AAA");
        mList.add("AAA");
        mList.add("AAA");
        mList.add("AAA");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new GoodsAdapter(mList));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.merchants_recyclerview;
    }
}
