package com.feitianzhu.fu700.payforme.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.common.base.LazyFragment;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.dao.NetworkDao;
import com.feitianzhu.fu700.payforme.PayForMeRejectActivity;
import com.feitianzhu.fu700.payforme.adapter.PayForMeRecordAdapter;
import com.feitianzhu.fu700.payforme.entity.PayForMeEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Vya on 2017/9/5 0005.
 */

public class AuditRejFragment extends LazyFragment implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout mRefreshlayout;


    private PayForMeRecordAdapter mAdapter;
    private List<PayForMeEntity.ListBean> mDatas;

    private static final String STATUS_REJ = "-1";
    private static final String PAGE_SIZE = "20";

    private int mCurPage = 1;
    private boolean mHasNextPage;
    private Unbinder mUnbinder;
    private View mEmptyView;


    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_refresh_recyclerview, null);
        mUnbinder = ButterKnife.bind(this, v);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRefreshlayout.setOnRefreshListener(this);
        mRefreshlayout.measure(0, 0);
        mRefreshlayout.setRefreshing(true);
        mRefreshlayout.setColorSchemeColors(getResources().getColor(R.color.sf_blue));
        mEmptyView = View.inflate(getActivity(), R.layout.view_common_nodata, null);
        return v;
    }

    @Override
    protected void initData() {

        mDatas = new ArrayList<>();
        mAdapter = new PayForMeRecordAdapter(mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (view.getId() == R.id.button) {
                    Intent intent = new Intent(getActivity(), PayForMeRejectActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constant.INTENT_REJECT_RECORD, mDatas.get(i));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        getData();
    }

    @Override
    protected void setDefaultFragmentTitle(String title) {

    }

    private void getData() {

        mRefreshlayout.setEnabled(false);

        NetworkDao.payForMeRecord(STATUS_REJ, mCurPage + "", PAGE_SIZE, new onConnectionFinishLinstener() {
            @Override
            public void onSuccess(int code, Object result) {

                if (mRefreshlayout !=null) {
                    mRefreshlayout.setRefreshing(false);
                    mRefreshlayout.setEnabled(true);
                }
                PayForMeEntity entity = (PayForMeEntity) result;

                mHasNextPage = entity.pager.hasNextPage;

                if (null == entity.list || 0 == entity.list.size()) {
                    mAdapter.setEmptyView(mEmptyView);
                    mAdapter.getEmptyView().setVisibility(View.VISIBLE);
                    return;
                }
                if (mAdapter.getEmptyView() != null) {
                    mAdapter.getEmptyView().setVisibility(View.GONE);
                }

                if (mCurPage == 1) {
                    mDatas.clear();
                    mDatas.addAll(entity.list);
                    mAdapter.setNewData(mDatas);
                } else {
                    mAdapter.addData(entity.list);
                    mAdapter.loadMoreComplete();
                }

            }

            @Override
            public void onFail(int code, String result) {
                if (mRefreshlayout !=null) {
                    mRefreshlayout.setRefreshing(false);
                    mRefreshlayout.setEnabled(true);
                }
                mAdapter.loadMoreFail();
                mAdapter.setEmptyView(mEmptyView);
                mAdapter.getEmptyView().setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    public void onRefresh() {
        mCurPage = 1;
        mHasNextPage = true;
        getData();
    }

    @Override
    public void onLoadMoreRequested() {

        if (mHasNextPage) {
            mCurPage++;
            getData();
        } else {
            mAdapter.loadMoreEnd();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
