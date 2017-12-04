package com.feitianzhu.fu700.me.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.model.MineCollectionMerchantsModel;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28 0028.
 */

public class MerchantsAdapter extends BaseQuickAdapter<MineCollectionMerchantsModel.ListBean, BaseViewHolder> {
  public MerchantsAdapter(@Nullable List<MineCollectionMerchantsModel.ListBean> data) {
    super(R.layout.fragment_merchants, data);
  }

  @Override protected void convert(BaseViewHolder holder, MineCollectionMerchantsModel.ListBean item) {
      ImageView mIcon = holder.getView(R.id.iv_Icon);
      Glide.with(mContext).load(item.getCover()).dontAnimate().placeholder(R.drawable.pic_fuwutujiazaishibai)
              .into(mIcon);
      holder.setText(R.id.tv_Name,item.getName())
              .setText(R.id.tv_local,item.getProvinceName()+item.getCityName())
              .setText(R.id.tv_localDetail,item.getAddress());



  }
}
