package com.feitianzhu.fu700.shop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
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
import com.feitianzhu.fu700.me.ui.ServiceDetailActivity;
import com.feitianzhu.fu700.model.ShopsService;
import com.feitianzhu.fu700.shop.ShopDao;
import com.feitianzhu.fu700.shop.adapter.ShopsServeAdapter;
import com.feitianzhu.fu700.utils.ToastUtils;
import java.util.ArrayList;
import java.util.List;
public class ShopServiceFragment extends LazyFragment
    implements BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

  @BindView(R.id.list) RecyclerView mList;
  Unbinder unbinder;
  List<ShopsService.ListEntity> Lists = new ArrayList<>();
  private ShopsServeAdapter mAdapter;
  private boolean hasNextPage=true;
  private int page=1;
  private String merchantId;
  private static final String ARG_PARAM1 = "param1";
  private boolean mIsadmin;

  public void setMerchantId(String mMerchantId) {
    this.merchantId = mMerchantId;
  }
  public ShopServiceFragment() {
  }

  public static ShopServiceFragment newInstance(boolean mIsadmin) {
    Bundle args = new Bundle();
    ShopServiceFragment fragment = new ShopServiceFragment();
    args.putBoolean(ARG_PARAM1, mIsadmin);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mIsadmin = getArguments().getBoolean(ARG_PARAM1);
    }
  }

  @Override protected View initViews(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_shop_service, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override protected void initData() {
    loadData(false);
  }

  @Override public void onStart() {
    super.onStart();
    lazyLoad();

  }


  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    mAdapter = new ShopsServeAdapter(Lists);
    View mEmptyView = View.inflate(getActivity(), R.layout.view_common_nodata, null);
    ImageView img_empty = (ImageView) mEmptyView.findViewById(R.id.img_empty);
    img_empty.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        page=1;
        loadData(false);
      }
    });
    mAdapter.setEmptyView(mEmptyView);
    mAdapter.setOnLoadMoreListener(this,mList);
    mAdapter.setOnItemClickListener(this);
    GridLayoutManager mManager = new GridLayoutManager(getActivity(), 2);
    mList.setLayoutManager(mManager);
    mList.setAdapter(mAdapter);
  }

  private void loadData(final boolean isLoadMore) {
    if (mIsadmin){
      merchantId="";
    }
    if (!isLoadMore) page=1;
    ShopDao.loadShopsServiceInfo(page,merchantId, new onNetFinishLinstenerT<ShopsService>() {
      @Override
      public void onSuccess(int code, ShopsService result) {
        if (!isLoadMore){
          mAdapter.getData().clear();
        }
        hasNextPage=result.pager.hasNextPage;
        List<ShopsService.ListEntity> mList = result.list;
        try {
          for (ShopsService.ListEntity mEntity : mList) {
            String[] arr = mEntity.photos.split("\\,");
            mEntity.one_photo = arr[0];
          }
        } catch (Exception e) {

        }
        if (isLoadMore)mAdapter.loadMoreComplete();
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

  @Override public void onDestroyView() {
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

  @Override public void onItemClick(BaseQuickAdapter mBaseQuickAdapter, View mView, int mI) {
    ShopsService.ListEntity mListEntity = mAdapter.getData().get(mI);
    Intent mIntent=new Intent(getActivity(), ServiceDetailActivity.class);
    mIntent.putExtra("serviceid",mListEntity.serviceId+"");
    startActivity(mIntent);
  }
}
