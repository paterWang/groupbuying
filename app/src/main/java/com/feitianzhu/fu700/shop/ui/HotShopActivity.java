package com.feitianzhu.fu700.shop.ui;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.common.impl.onNetFinishLinstenerT;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.navigationbar.AbsNavigationbar;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.model.RecommndShopModel;
import com.feitianzhu.fu700.shop.ShopDao;
import com.feitianzhu.fu700.shop.adapter.HotShopAdapter;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HotShopActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout mRefreshlayout;

    private HotShopAdapter mAdapter;
    private List<RecommndShopModel.ListEntity> mDatas = new ArrayList<>();
    private int mCurPage = 1;
    private boolean mHasNextPag;

    private static final String PAGE_SIZE = "20";
    private View mEmptyView;


    @Override
    protected void initTitle() {
        defaultNavigationBar = new DefaultNavigationBar
                .Builder(this, (ViewGroup) findViewById(R.id.Rl_titleContainer))
                .setTitle("人气商户")
                .setStatusHeight(this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .showRightPic(R.mipmap.icon_sousuo, AbsNavigationbar.PIC_THREE, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(mContext, ShopSearchActivity.class));
                    }
                })
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_hot_shop;
    }

    @Override
    protected void initData() {
        getData();
    }

    private void getData() {

        ShopDao.LoadReCommondShop("1", mCurPage, PAGE_SIZE, new onNetFinishLinstenerT<RecommndShopModel>() {
            @Override
            public void onSuccess(int code, RecommndShopModel result) {

                mRefreshlayout.setRefreshing(false);
                mRefreshlayout.setEnabled(true);

                mHasNextPag = result.pager.hasNextPage;

                if (null == result.list || 0 == result.list.size()) {
                    mAdapter.setEmptyView(mEmptyView);
                    mAdapter.getEmptyView().setVisibility(View.VISIBLE);
                    return;
                }
                if (mAdapter.getEmptyView() != null) {
                    mAdapter.getEmptyView().setVisibility(View.GONE);
                }
                if (mCurPage == 1) {
                    mDatas.clear();
                    mDatas.addAll(result.list);
                    mAdapter.setNewData(mDatas);
                } else {
                    mAdapter.addData(result.list);
                    mAdapter.loadMoreComplete();
                }
            }

            @Override
            public void onFail(int code, String result) {
                KLog.e(result);
                ToastUtils.showShortToast(result);
                mRefreshlayout.setRefreshing(false);
                mRefreshlayout.setEnabled(true);
                mAdapter.loadMoreFail();
                mAdapter.setEmptyView(mEmptyView);
                mAdapter.getEmptyView().setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void initView() {

        mRefreshlayout.setOnRefreshListener(this);
        mRefreshlayout.measure(0, 0);
        mRefreshlayout.setRefreshing(true);
        mRefreshlayout.setColorSchemeColors(getResources().getColor(R.color.sf_blue));

        mAdapter = new HotShopAdapter(mDatas);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                RecommndShopModel.ListEntity listEntity = mAdapter.getData().get(i);
                Intent mIntent = new Intent(HotShopActivity.this, ShopsActivity.class);
                mIntent.putExtra(Constant.ISADMIN, false);
                mIntent.putExtra(Constant.MERCHANTID, listEntity.merchantId + "");
                startActivity(mIntent);
            }
        });
        mEmptyView = View.inflate(mContext, R.layout.view_common_nodata, null);
        list.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(this, list);
    }

    @Override
    public void onLoadMoreRequested() {

        if (mHasNextPag) {
            mCurPage++;
            getData();
        } else {
            mAdapter.loadMoreEnd();
        }
    }

    @Override
    public void onRefresh() {
        mCurPage = 1;
        mHasNextPag = true;
        getData();
    }
}
