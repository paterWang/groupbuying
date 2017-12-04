package com.feitianzhu.fu700.shop.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.base.SFBaseAdapter;
import com.feitianzhu.fu700.model.ShopOrderModel;
import java.util.List;

/**
 * Created by Administrator on 2017/8/28 0028.
 */

public class ShopOrderAdapter extends SFBaseAdapter<ShopOrderModel.ListEntity, BaseViewHolder> {
  public ShopOrderAdapter(@Nullable List<ShopOrderModel.ListEntity> data) {
    super(R.layout.item_shop_order_view, data);
  }

  @Override protected void convert(BaseViewHolder mBaseViewHolder, ShopOrderModel.ListEntity mShopTypeModel) {
    if ("0".equals(mShopTypeModel.isEval)){
      mBaseViewHolder.setVisible(R.id.to_revalate,true);
    }else{
      mBaseViewHolder.setVisible(R.id.to_revalate,false);
    }
      ImageView mView = mBaseViewHolder.getView(R.id.item_icon);
      Glide.with(mContext).load(mShopTypeModel.merchant.merchantHeadImg)
              .dontAnimate()
              .placeholder(R.mipmap.pic_fuwutujiazaishibai)
              .into(mView);
      mBaseViewHolder.setText(R.id.item_name,mShopTypeModel.merchant.merchantName+"");
      mBaseViewHolder.setText(R.id.item_time,mShopTypeModel.createDate+"");
      mBaseViewHolder.setText(R.id.item_money,""+mShopTypeModel.consumeAmount+"");
      mBaseViewHolder.addOnClickListener(R.id.to_revalate);
  }
}
