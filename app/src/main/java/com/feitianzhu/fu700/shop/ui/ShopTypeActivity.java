package com.feitianzhu.fu700.shop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.common.base.SFActivity;
import com.feitianzhu.fu700.common.impl.onNetFinishLinstenerT;
import com.feitianzhu.fu700.model.ShopsIndex;
import com.feitianzhu.fu700.model.ShopsNearby;
import com.feitianzhu.fu700.shop.ShopDao;
import com.feitianzhu.fu700.shop.adapter.ShopHomeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: 商店分类列表
 * autour: dicallc
 */
public class ShopTypeActivity extends SFActivity implements BaseQuickAdapter.RequestLoadMoreListener {

  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.list) RecyclerView mList;
  @BindView(R.id.img_sousuo) ImageView mImgSousuo;
  private ShopHomeAdapter mAdapter;
  private List<ShopsIndex.NearByMerchantListBean> mTypeModels=new ArrayList<>();
  private int page=1;
  private String clsId;
  private boolean hasNextPage=true;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shop_type);
    ButterKnife.bind(this);
    Intent intent = getIntent();
    clsId = intent.getStringExtra("putClsId");
    initView();
    initData(false);
    mAdapter = new ShopHomeAdapter(mTypeModels);
    View mEmptyView = View.inflate(this, R.layout.view_common_nodata, null);
    ImageView img_empty = (ImageView) mEmptyView.findViewById(R.id.img_empty);
    img_empty.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        initData(false);
      }
    });
    mAdapter.setEmptyView(mEmptyView);
    mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
      @Override public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        ShopsIndex.NearByMerchantListBean mListBean = mTypeModels.get(i);
        Intent mIntent = new Intent(ShopTypeActivity.this, ShopsActivity.class);
        mIntent.putExtra(Constant.ISADMIN,false);
        mIntent.putExtra(Constant.MERCHANTID,mListBean.merchantId+"");
        startActivity(mIntent);
      }
    });
    mAdapter.setOnLoadMoreListener(this,mList);
    mList.setAdapter(mAdapter);
  }

  private void initData(final boolean isLoadmore) {
    showloadDialog("");
    ShopDao.LoadNearbyShops(page, Constant.provinceId, Constant.cityId, clsId, new onNetFinishLinstenerT<ShopsNearby>() {
      @Override
      public void onSuccess(int code, ShopsNearby result) {
        if(result == null || result.pager == null){
          goneloadDialog();
          return;
        }
        hasNextPage = result.pager.hasNextPage;
        List<ShopsIndex.NearByMerchantListBean> list = result.list;
        mAdapter.addData(list);
        if (isLoadmore)mAdapter.loadMoreComplete();
        goneloadDialog();
      }

      @Override
      public void onFail(int code, String result) {
        goneloadDialog();
        if (isLoadmore)
        mAdapter.loadMoreFail();

      }
    });
  }

  private void initView() {

    mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
  }

  @OnClick(R.id.img_sousuo) public void onViewClicked() {
    Intent mIntent=new Intent(this,ShopSearchActivity.class);
    startActivity(mIntent);
  }

  @Override
  public void onLoadMoreRequested() {
    if (!hasNextPage){
      mAdapter.loadMoreEnd();
    }else{
      page+=1;
      initData(true);
    }
  }
}
