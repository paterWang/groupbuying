package com.feitianzhu.fu700.huanghuali;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.dao.NetworkDao;
import com.feitianzhu.fu700.huanghuali.entity.HuangHuaLiRecordEntity;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HuangHuaLiRecordActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout mRefreshLayout;

    private List<HuangHuaLiRecordEntity> mDatas;
    private HuangHuaLiRecordAdapter mAdapter;
    private View mEmptyView;

    @Override
    protected void initTitle() {
        defaultNavigationBar = new DefaultNavigationBar
                .Builder(this, (ViewGroup) findViewById(R.id.Rl_titleContainer))
                .setTitle("购买记录")
                .setStatusHeight(this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_refresh_recyclerview;
    }

    @Override
    protected void initView() {
        mEmptyView = View.inflate(mContext, R.layout.view_common_nodata, null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRefreshLayout.setOnRefreshListener(this);
        mRefreshLayout.measure(0, 0);
        mRefreshLayout.setRefreshing(true);
        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.sf_blue));
    }

    @Override
    protected void initData() {

        mDatas = new ArrayList<>();
        mAdapter = new HuangHuaLiRecordAdapter(mDatas);
        mRecyclerView.setAdapter(mAdapter);

        getData();
    }

    private void getData() {

        NetworkDao.getHuangHuaLiRecord(new onConnectionFinishLinstener() {
            @Override
            public void onSuccess(int code, Object result) {

                mRefreshLayout.setRefreshing(false);

                List<HuangHuaLiRecordEntity> list = (List<HuangHuaLiRecordEntity>) result;
                if (list == null || list.isEmpty()) {
                    mAdapter.setEmptyView(mEmptyView);
                    mAdapter.getEmptyView().setVisibility(View.VISIBLE);
                    return;
                }
                if (mAdapter.getEmptyView()!= null) {
                    mAdapter.getEmptyView().setVisibility(View.GONE);
                }
                mDatas.clear();
                mDatas.addAll(list);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(int code, String result) {
                ToastUtils.showShortToast(result);
                mRefreshLayout.setRefreshing(false);
                mAdapter.loadMoreFail();
                mAdapter.setEmptyView(mEmptyView);
                mAdapter.getEmptyView().setVisibility(View.VISIBLE);

            }
        });
    }

    @Override
    public void onRefresh() {
        getData();
    }
}
