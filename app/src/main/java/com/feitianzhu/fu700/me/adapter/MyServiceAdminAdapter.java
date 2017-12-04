package com.feitianzhu.fu700.me.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.base.SFBaseAdapter;
import com.feitianzhu.fu700.model.ShopsService;
import java.util.List;

/**
 * description:  我服务的适配器
 * autour: dicallc
 */
public class MyServiceAdminAdapter extends SFBaseAdapter<ShopsService.ListEntity, BaseViewHolder> {
  public MyServiceAdminAdapter(@Nullable List<ShopsService.ListEntity> data) {
    super(R.layout.my_item_service_admin, data);
  }

  @Override protected void convert(BaseViewHolder mBaseViewHolder, ShopsService.ListEntity mShopTypeModel) {
      ImageView mView = mBaseViewHolder.getView(R.id.item_icon);
    Glide.with(mContext).load(mShopTypeModel.one_photo).dontAnimate().placeholder(R.mipmap.pic_fuwutujiazaishibai).into(mView);
      ImageView mViews = mBaseViewHolder.getView(R.id.item_person_icon);
          Glide.with(mContext).load(mShopTypeModel.adImg).dontAnimate().placeholder(R.mipmap.pic_fuwutujiazaishibai).into(mViews);
          mBaseViewHolder.setText(R.id.item_name,mShopTypeModel.serviceName+"");
          mBaseViewHolder.setText(R.id.item_money,mShopTypeModel.price+"");
          mBaseViewHolder.setText(R.id.item_rebate,mShopTypeModel.rebate+"");
          mBaseViewHolder.setText(R.id.item_person,mShopTypeModel.contactPerson+"");
          mBaseViewHolder.setText(R.id.item_phone,mShopTypeModel.contactTel+"");
    mBaseViewHolder.addOnClickListener(R.id.btn_edit);
    mBaseViewHolder.addOnClickListener(R.id.btn_delete);

  }

}
