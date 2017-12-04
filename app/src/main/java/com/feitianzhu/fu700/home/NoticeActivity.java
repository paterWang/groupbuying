package com.feitianzhu.fu700.home;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.dao.NetworkDao;
import com.feitianzhu.fu700.events.NotifyEvent;
import com.feitianzhu.fu700.home.adapter.NoticeAdapter;
import com.feitianzhu.fu700.home.entity.NoticeEntity;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Vya on 2017/9/5 0005.
 */

public class NoticeActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout mRefreshlayout;

    private static final String PAGE_SIZE = "20";

    private int mCurPage = 1;
    private boolean mHasNextPage;
    private List<NoticeEntity.ListBean> mDatas;
    private NoticeAdapter mAdapter;
    private View mEmptyView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notice;
    }


    @Override
    protected void initTitle() {
        defaultNavigationBar = new DefaultNavigationBar
                .Builder(NoticeActivity.this, (ViewGroup)findViewById(R.id.Rl_titleContainer))
                .setTitle("通知")
                .setStatusHeight(NoticeActivity.this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }

    @Override
    protected void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRefreshlayout.setOnRefreshListener(this);
        mRefreshlayout.measure(0, 0);
        mRefreshlayout.setRefreshing(true);
        mRefreshlayout.setColorSchemeColors(getResources().getColor(R.color.sf_blue));

        mEmptyView = View.inflate(mContext, R.layout.view_common_nodata, null);
    }

    @Override
    protected void initData() {
        EventBus.getDefault().post(NotifyEvent.NO_NOTIFY);
        mDatas = new ArrayList<>();
        mAdapter = new NoticeAdapter(mDatas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);

        getData();
    }

    private void getData() {

        mRefreshlayout.setEnabled(false);

        NetworkDao.getNotices(mCurPage + "", PAGE_SIZE + "", new onConnectionFinishLinstener() {
            @Override
            public void onSuccess(int code, Object result) {
                mRefreshlayout.setRefreshing(false);
                mRefreshlayout.setEnabled(true);

                NoticeEntity entity = (NoticeEntity) result;
                mHasNextPage = entity.pager.hasNextPage;

                if ( null == entity.list || 0 == entity.list.size()) {
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
                mRefreshlayout.setRefreshing(false);
                mRefreshlayout.setEnabled(true);
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
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().post(NotifyEvent.NO_NOTIFY);
    }
}
