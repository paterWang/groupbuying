package com.feitianzhu.fu700.shop.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.model.ShopsEvali;
import java.util.List;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by Administrator on 2017/8/28 0028.
 */

public class ShopsEvaluateAdapter extends BaseQuickAdapter<ShopsEvali.ListBean, BaseViewHolder> {
  public ShopsEvaluateAdapter( @Nullable List<ShopsEvali.ListBean> data) {
    super(R.layout.shops_item_evaluate, data);
  }

  @Override protected void convert(BaseViewHolder mBaseViewHolder, ShopsEvali.ListBean mShopTypeModel) {
    ImageView mView = mBaseViewHolder.getView(R.id.item_icon);
    MaterialRatingBar materialRatingBar = mBaseViewHolder.getView(R.id.item_ratting);
    Glide.with(mContext).load(mShopTypeModel.headImg).dontAnimate().placeholder(R.mipmap.pic_fuwutujiazaishibai).into(mView);
    mBaseViewHolder.setText(R.id.item_title,mShopTypeModel.nickName+"");
    mBaseViewHolder.setText(R.id.item_content,mShopTypeModel.content+"");
    mBaseViewHolder.setText(R.id.item_time,mShopTypeModel.evalDate+"");
    materialRatingBar.setRating(mShopTypeModel.star);

  }
}
