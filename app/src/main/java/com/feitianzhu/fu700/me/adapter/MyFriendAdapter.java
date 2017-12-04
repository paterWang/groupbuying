package com.feitianzhu.fu700.me.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.model.FuFriendModel;
import java.util.List;

/**
 * description:  我的福友的适配器
 * autour: dicallc
*/
public class MyFriendAdapter extends BaseQuickAdapter<FuFriendModel.ListEntity, BaseViewHolder> {
  public MyFriendAdapter(@Nullable List<FuFriendModel.ListEntity> data) {
    super(R.layout.my_item_friend, data);
  }

  @Override protected void convert(BaseViewHolder mBaseViewHolder, FuFriendModel.ListEntity mShopTypeModel) {
    //if (!TextUtils.isEmpty(mShopTypeModel.headImg)){
      ImageView mView = mBaseViewHolder.getView(R.id.item_icon);
      Glide.with(mContext).load(mShopTypeModel.headImg+"").dontAnimate().placeholder(R.mipmap.pic_fuwutujiazaishibai).into(mView);
    //}
    if (!TextUtils.isEmpty(mShopTypeModel.nickName)){
      mBaseViewHolder.setText(R.id.item_name,mShopTypeModel.nickName);
      mBaseViewHolder.setVisible(R.id.item_name,true);
    }else{
      mBaseViewHolder.setVisible(R.id.item_name,false);
    }
    if (!TextUtils.isEmpty(mShopTypeModel.age)){
      mBaseViewHolder.setVisible(R.id.ly_beijing,true);
      mBaseViewHolder.setText(R.id.item_age,mShopTypeModel.age);
      mBaseViewHolder.setText(R.id.item_job,mShopTypeModel.job);
    }else{
      mBaseViewHolder.setVisible(R.id.ly_beijing,false);
    }
    mBaseViewHolder.setText(R.id.item_xiaofei,"总消费金额:"+mShopTypeModel.totalConsume);
    mBaseViewHolder.setText(R.id.item_money,""+mShopTypeModel.shareBenefit);


  }
}
