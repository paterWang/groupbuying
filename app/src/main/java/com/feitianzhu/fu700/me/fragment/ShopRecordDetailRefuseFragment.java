package com.feitianzhu.fu700.me.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.common.base.LazyFragment;
import com.feitianzhu.fu700.me.adapter.ShopRecordDetailRefuseAdapter;
import com.feitianzhu.fu700.me.ui.ShopRecordActivity;
import com.feitianzhu.fu700.model.ShopRecordRefuseModel;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

import static com.feitianzhu.fu700.common.Constant.ACCESSTOKEN;
import static com.feitianzhu.fu700.common.Constant.Common_HEADER;
import static com.feitianzhu.fu700.common.Constant.POST_SHOP_RECORDEDETAIL;
import static com.feitianzhu.fu700.common.Constant.USERID;
import static com.taobao.accs.ACCSManager.mContext;

/**
 * Created by Vya on 2017/9/13 0013.
 * 商家录单详情拒绝页
 */

public class ShopRecordDetailRefuseFragment extends LazyFragment implements BaseQuickAdapter.RequestLoadMoreListener, ShopRecordDetailRefuseAdapter.SetRectryButtonClick {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private List<String> mData;

    private List<ShopRecordRefuseModel.ListBean> mDatas;
    private ShopRecordDetailRefuseAdapter mAdapter;


    private int pageRows=6;
    private final static int LOAD_NORMAL = 0;
    private final static int LOAD_MORE = 1;
    private int currPage = 0;
    private int totalPage = 0;
    private boolean hasNextPage = false;
    private View mEmptyView;

    private List<ShopRecordRefuseModel.ListBean> mTemp;
    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mEmptyView = View.inflate(getActivity(), R.layout.view_common_nodata, null);
        View v = inflater.inflate(R.layout.fragment_shop_recodedetail,null,false);
        ButterKnife.bind(this,v);
        return v;
    }

    @Override
    protected void initData() {
        mDatas = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mAdapter = new ShopRecordDetailRefuseAdapter(mDatas);
        mAdapter.setOnLoadMoreListener(this,mRecyclerView);
        mAdapter.setEmptyView(mEmptyView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setEnableLoadMore(true);
        requestData(LOAD_NORMAL);
        mAdapter.setOnRetryButtonClick(this);


    }

    @Override
    protected void setDefaultFragmentTitle(String title) {

    }

    private void requestData(final int state) {
        OkHttpUtils.post()//
                .url(Common_HEADER + POST_SHOP_RECORDEDETAIL)
                .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                .addParams(USERID, Constant.LOGIN_USERID)
                .addParams("status", "-1")//
                .addParams("pageIndex", currPage+"")//
                .addParams("pageRows", pageRows+"")//
                .build()
                .execute(new Callback<ShopRecordRefuseModel>() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("Test","onError---->"+e.getMessage());
                        mAdapter.loadMoreFail();
                    }

                    @Override
                    public void onResponse(ShopRecordRefuseModel response, int id) {
                        if(response == null || response.getPager() == null || response.getList()==null){
                            mAdapter.loadMoreFail();
                            return;
                        }
                        mTemp = response.getList();
                        hasNextPage = response.getPager().isHasNextPage();
                        switch (state){
                            case LOAD_NORMAL:
                                mDatas.addAll(response.getList());
                                mAdapter.notifyDataSetChanged();
                                break;
                            case LOAD_MORE:
                                mDatas.addAll(0,response.getList());
                                mAdapter.addData(mDatas);
                                mAdapter.notifyDataSetChanged();
                                break;
                        }
                        if(hasNextPage){
                            currPage++;
                            mAdapter.loadMoreComplete();
                        }else{
                            mAdapter.loadMoreEnd(true);
                        }

                    }
                });
    }



    @Override
    public void onLoadMoreRequested() {
        if(hasNextPage){
            requestData(LOAD_MORE);
        }else{
            mAdapter.loadMoreEnd(true);
        }
    }

    @Override
    public void onRetryClick(int position) {
        ToastUtils.showShortToast("---"+position);
        //POST_SHOP_RECORED_RETRY
       Intent intent = new Intent(getContext(), ShopRecordActivity.class);
        intent.putExtra("OrderNo",mTemp.get(position).getOrderNo()+"");
        intent.putExtra("RetryMemberId",mTemp.get(position).getUserId()+"");
        intent.putExtra("ConsumeAmount",mTemp.get(position).getConsumeAmount()+"");
        getActivity().startActivity(intent);
        getActivity().finish();
    }
}
