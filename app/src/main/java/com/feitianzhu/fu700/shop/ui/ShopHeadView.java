package com.feitianzhu.fu700.shop.ui;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.model.RecommndShopModel;
import com.feitianzhu.fu700.model.ShopType;
import com.feitianzhu.fu700.shop.adapter.ShopRecommendAdapter;
import com.feitianzhu.fu700.shop.adapter.ShopTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShopHeadView extends LinearLayout {

  @BindView(R.id.list) RecyclerView mList;
  @BindView(R.id.txt_tuijian) TextView mTxtTuijian;
  @BindView(R.id.img_shuaxin) ImageView mImgShuaxin;
  @BindView(R.id.list_tuijian) RecyclerView mListTuijian;
  List<ShopType> mTypeModels = new ArrayList<>();
  List<RecommndShopModel.ListEntity> mRecmmondModels = new ArrayList<>();
  private ShopTypeAdapter mShopTypeAdapter;
  private ShopRecommendAdapter mShopRecommendAdapter;

  public ShopTypeAdapter getShopTypeAdapter() {
    return mShopTypeAdapter;
  }

  public ShopRecommendAdapter getShopRecommendAdapter() {
    return mShopRecommendAdapter;
  }

  public ShopHeadView(Context context) {
    this(context, null);
  }

  public ImageView getmImgShuaxin() {
    return mImgShuaxin;
  }

  public ShopHeadView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  private void init(Context mContext, AttributeSet mAttrs, int mI) {
    // 加载布局

  }

  public void setTypeModels(List<ShopType> mTypeModels) {
    mShopTypeAdapter.addData(mTypeModels);
  }

  public void setRecmmondModels(List<RecommndShopModel.ListEntity> mRecmmondModels) {
    mShopRecommendAdapter.addData(mRecmmondModels);
  }

  public ShopHeadView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    LayoutInflater.from(context).inflate(R.layout.view_shop_head, this);
    ButterKnife.bind(this);
    initView(context);
  }

  private void initView(Context mContext) {
    mList.setLayoutManager(new GridLayoutManager(mContext, 4));
    mShopTypeAdapter = new ShopTypeAdapter(mTypeModels);
    mList.setAdapter(mShopTypeAdapter);
    mListTuijian.setLayoutManager(new GridLayoutManager(mContext, 4));
    mShopRecommendAdapter = new ShopRecommendAdapter(mRecmmondModels);
    mListTuijian.setAdapter(mShopRecommendAdapter);
  }
}
