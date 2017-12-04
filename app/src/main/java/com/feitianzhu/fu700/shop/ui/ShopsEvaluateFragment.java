package com.feitianzhu.fu700.shop.ui;

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
import com.feitianzhu.fu700.common.base.LazyFragment;
import com.feitianzhu.fu700.common.impl.onNetFinishLinstenerT;
import com.feitianzhu.fu700.model.ShopsEvali;
import com.feitianzhu.fu700.shop.ShopDao;
import com.feitianzhu.fu700.shop.adapter.ShopsEvaluateAdapter;
import com.feitianzhu.fu700.utils.ToastUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * description: 店铺评价列表
 * autour: dicallc
 */
public class ShopsEvaluateFragment extends LazyFragment
    implements BaseQuickAdapter.RequestLoadMoreListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.list)
    RecyclerView mList;
    Unbinder unbinder;

    private String mParam1;
    private String mParam2;

    List<ShopsEvali.ListBean> lists = new ArrayList<>();
    private ShopsEvaluateAdapter mAdapter;
    private int page=1;
    private boolean hasNextPage=true;
    private String merchantId;

    public void setMerchantId(String mMerchantId) {
        this.merchantId = mMerchantId;
    }
    public ShopsEvaluateFragment() {
    }

    public static ShopsEvaluateFragment newInstance(String param1, String param2) {
        ShopsEvaluateFragment fragment = new ShopsEvaluateFragment();
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

    @Override protected View initViews(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shops_evaluate, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override protected void initData() {
        loadData(false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View mEmptyView = View.inflate(getActivity(), R.layout.view_common_nodata, null);
        ImageView img_empty = (ImageView) mEmptyView.findViewById(R.id.img_empty);
        img_empty.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                page=1;
                loadData(false);
            }
        });
        mAdapter = new ShopsEvaluateAdapter(lists);
        mAdapter.setEmptyView(mEmptyView);
        mAdapter.setOnLoadMoreListener(this,mList);
        mList.setAdapter(mAdapter);
    }

    private void loadData(final boolean isLoadMore) {
        if (!isLoadMore) page=1;
        ShopDao.loadShopsServiceEvalu(page,merchantId,new onNetFinishLinstenerT<ShopsEvali>() {
            @Override
            public void onSuccess(int code, ShopsEvali result) {
                hasNextPage=result.pager.hasNextPage;
                mAdapter.addData(result.list);
                if (isLoadMore)mAdapter.loadMoreComplete();
                //goneloadDialog();
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



    @Override protected void setDefaultFragmentTitle(String title) {

    }

    @Override public void onLoadMoreRequested() {
        if (!hasNextPage){
            mAdapter.loadMoreEnd();
        }else{
            page+=1;
            loadData(true);
        }
    }
}
