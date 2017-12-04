package com.feitianzhu.fu700.shop.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.base.SFBaseAdapter;
import com.feitianzhu.fu700.model.ServeOrderModel;
import com.feitianzhu.fu700.model.ShopOrderModel;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28 0028.
 */

public class ServeOrderAdapter extends SFBaseAdapter<ServeOrderModel.ListBean, BaseViewHolder> {
  public ServeOrderAdapter(@Nullable List<ServeOrderModel.ListBean> data) {
    super(R.layout.item_serve_order_view, data);
  }

  @Override protected void convert(BaseViewHolder mBaseViewHolder, ServeOrderModel.ListBean mShopTypeModel) {
      ImageView mView = mBaseViewHolder.getView(R.id.item_icon);
      Glide.with(mContext).load(mShopTypeModel.service.adImg)
              .dontAnimate()
              .placeholder(R.mipmap.pic_fuwutujiazaishibai)
              .into(mView);
      mBaseViewHolder.setText(R.id.item_name,mShopTypeModel.service.serviceName+"");
      mBaseViewHolder.setText(R.id.item_time,mShopTypeModel.createDate+"");
      mBaseViewHolder.setText(R.id.item_money,""+mShopTypeModel.amount+"");
      mBaseViewHolder.setText(R.id.item_fanli,""+mShopTypeModel.rebatePv+"");
  }
}
