package com.feitianzhu.fu700.shop.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.base.SFBaseAdapter;
import com.feitianzhu.fu700.model.ShopType;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28 0028.
 */

public class ShopTypeAdapter extends SFBaseAdapter<ShopType, BaseViewHolder> {
  public ShopTypeAdapter(@Nullable List<ShopType> data) {
    super(R.layout.shop_item_type, data);
  }

  @Override protected void convert(BaseViewHolder mBaseViewHolder, ShopType mShopTypeModel) {
    ImageView imageView  = mBaseViewHolder.getView(R.id.iv_icon);
    mBaseViewHolder.setText(R.id.item_name,mShopTypeModel.clsName+"");
    Glide.with(mContext).load(mShopTypeModel.clsImg).error(R.drawable.pic_fuwutujiazaishibai).into(imageView);
  }
}
