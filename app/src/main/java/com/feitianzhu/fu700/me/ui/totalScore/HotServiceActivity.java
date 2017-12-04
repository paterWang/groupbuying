package com.feitianzhu.fu700.me.ui.totalScore;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.me.adapter.HotServiceAdapter;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.me.ui.BuyServiceActivity;
import com.feitianzhu.fu700.me.ui.ServiceDetailActivity;
import com.feitianzhu.fu700.model.BuyServiceNeedModel;
import com.feitianzhu.fu700.model.HotServiceModel;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

import static com.feitianzhu.fu700.common.Constant.ACCESSTOKEN;
import static com.feitianzhu.fu700.common.Constant.Common_HEADER;
import static com.feitianzhu.fu700.common.Constant.POST_HOT_SERVICE;
import static com.feitianzhu.fu700.common.Constant.USERID;

/**
 * Created by Vya on 2017/9/5 0005.
 */

public class HotServiceActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private String PageRows = "6";
    private List<HotServiceModel.ListBean> mDatas;
    private HotServiceAdapter mAdapter;

    private final static int LOAD_NORMAL = 0;
    private final static int LOAD_MORE = 1;
    private int currPage = 1;
    private int totalPage = 0;
    private View mEmptyView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hot_service;
    }

    @Override
    protected void initTitle() {
        defaultNavigationBar = new DefaultNavigationBar
                .Builder(HotServiceActivity.this, (ViewGroup) findViewById(R.id.Rl_titleContainer))
                .setTitle("热门服务")
                .setStatusHeight(HotServiceActivity.this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }


    @Override
    protected void initView() {
        mEmptyView = View.inflate(this, R.layout.view_common_nodata, null);
    }

    @Override
    protected void initData() {
        mDatas = new ArrayList<>();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new HotServiceAdapter(mDatas);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setButtonClickListener(new HotServiceAdapter.OnBuyNowClickListener() {
            @Override
            public void onBuyNowButtonClick(int position) {
                Intent intent = new Intent(HotServiceActivity.this, BuyServiceActivity.class);
                HotServiceModel.ListBean bean = mDatas.get(position);
                String headImg = "";
                if (bean.getHeadImg() == null) {
                    headImg = "";
                } else {
                    if (bean.getHeadImg().toString() == null) {
                        headImg = "";
                    } else {
                        headImg = bean.getHeadImg().toString();
                    }
                }
                BuyServiceNeedModel mModel = new BuyServiceNeedModel(bean.getServiceId(), bean.getServiceName(), bean.getPrice(), bean.getRebate(), bean.getUserId(), headImg, bean.getContactPerson(), bean.getContactTel(), bean.getServiceAddr());
                mModel.merchantId = bean.getMerchantId();
                mModel.type = BuyServiceNeedModel.HOT_SERVICE_BEAN;
                intent.putExtra("hotServiceBean", mModel);
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setEnableLoadMore(true);
        requestData(LOAD_NORMAL);

    }

    /**
     * accessToken 是 String 登录凭证
     * userId 是 number 用户id
     * type 是 number 1-热门 2-推荐
     * pageIndex 是 number 页数
     * pageRows 是 number 每页行数
     */
    private void requestData(final int state) {
        OkHttpUtils.post()//
                .url(Common_HEADER + POST_HOT_SERVICE)
                .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                .addParams(USERID, Constant.LOGIN_USERID)
                .addParams("type", "1")//1表示热门 2表示推荐
                .addParams("pageIndex", currPage + "")//
                .addParams("pageRows", PageRows)//
                .build()
                .execute(new Callback<HotServiceModel>() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("wangyan", "onError---->" + e.getMessage());
                        mAdapter.loadMoreFail();
                    }

                    @Override
                    public void onResponse(HotServiceModel response, int id) {

                        totalPage = response.getPager().getTotalPages();

                        if (null == response.getList() || 0 == response.getList().size()) {
                            mAdapter.setEmptyView(mEmptyView);
                            mAdapter.getEmptyView().setVisibility(View.VISIBLE);
                            return;
                        }
                        if (mAdapter.getEmptyView() != null) {
                            mAdapter.getEmptyView().setVisibility(View.GONE);
                        }

                        switch (state) {
                            case LOAD_NORMAL:
                                mDatas.addAll(response.getList());
                                mAdapter.notifyDataSetChanged();
                                break;
                            case LOAD_MORE:
                                mAdapter.addData(response.getList());
                                break;
                        }
                        if (currPage < response.getPager().getTotalPages()) {
                            currPage++;
                        } else {
                            currPage = response.getPager().getTotalPages();
                        }
                        mAdapter.loadMoreComplete();

                    }
                });
    }


    @Override
    public void onLoadMoreRequested() {
        Log.e("wangyan", "----currPage---" + currPage + "-----totalPage----" + totalPage);
        if (currPage < totalPage) {
            requestData(LOAD_MORE);
        } else {
            mAdapter.loadMoreEnd();
        }

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(HotServiceActivity.this, ServiceDetailActivity.class);
        intent.putExtra("serviceid", mDatas.get(position).getServiceId() + "");
        startActivity(intent);
    }
}
