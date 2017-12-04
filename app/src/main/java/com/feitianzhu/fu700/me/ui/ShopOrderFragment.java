package com.feitianzhu.fu700.me.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.base.SFFragment;
import com.feitianzhu.fu700.common.impl.onNetFinishLinstenerT;
import com.feitianzhu.fu700.model.ShopOrderModel;
import com.feitianzhu.fu700.shop.ShopDao;
import com.feitianzhu.fu700.shop.adapter.ShopOrderAdapter;
import com.feitianzhu.fu700.utils.ToastUtils;
import java.util.ArrayList;
import java.util.List;
/**
 * description: 商户订单
 * autour: dicallc
*/
public class ShopOrderFragment extends SFFragment implements BaseQuickAdapter.RequestLoadMoreListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.list)
    RecyclerView mList;
    Unbinder unbinder;

    private String mParam1;
    private String mParam2;
    private ShopOrderAdapter mAdapter;
    private List<ShopOrderModel.ListEntity> Lists=new ArrayList<>();
    private int page=1;
    private boolean hasNextPage=true;

    public ShopOrderFragment() {
    }

    public static ShopOrderFragment newInstance(String param1, String param2) {
        ShopOrderFragment fragment = new ShopOrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override public void onStart() {
        super.onStart();
        loadData(false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new ShopOrderAdapter(Lists);
        View mEmptyView = View.inflate(getActivity(), R.layout.view_common_nodata, null);
        ImageView img_empty = (ImageView) mEmptyView.findViewById(R.id.img_empty);
        img_empty.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                page=1;
                loadData(false);
            }
        });
        mAdapter.setEmptyView(mEmptyView);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter mBaseQuickAdapter, View mView, int mI) {
                ShopOrderModel.ListEntity mEntity = Lists.get(mI);
                Intent mIntent=new Intent(getActivity(),OrdeRevaluateActivity.class);
                mIntent.putExtra("model",mEntity);
                startActivity(mIntent);
            }
        });
        mAdapter.setOnLoadMoreListener(this,mList);
        mList.setAdapter(mAdapter);

    }

    private void loadData(final boolean isLoadMore) {
        if (!isLoadMore){
            page=1;
        }
        ShopDao.loadShopOrderList(page, new onNetFinishLinstenerT<ShopOrderModel>() {
            @Override
            public void onSuccess(int code, ShopOrderModel result) {
                if(null==result||null==result.pager){
                    onFail(404,"服务器出问题了");
                    return; 
                }
                hasNextPage=result.pager.hasNextPage;
                List<ShopOrderModel.ListEntity> mList = result.list;
                if (isLoadMore)mAdapter.loadMoreComplete();
                if (!isLoadMore){
                    mAdapter.getData().clear();
                }
                mAdapter.addData(mList);

            }

            @Override
            public void onFail(int code, String result) {
                if (isLoadMore)
                    mAdapter.loadMoreFail();
                //goneloadDialog();
                ToastUtils.showShortToast(result);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onLoadMoreRequested() {
        if (!hasNextPage){
            mAdapter.loadMoreEnd();
        }else{
            page+=1;
            loadData(true);
        }
    }

}
