package com.feitianzhu.fu700.me.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.model.MineCollectionServiceModel;
import com.feitianzhu.fu700.view.CircleImageView;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28 0028.
 */

public class ServiceAdapter extends BaseQuickAdapter<MineCollectionServiceModel.ListBean, BaseViewHolder> {
  public ServiceAdapter(@Nullable List<MineCollectionServiceModel.ListBean> data) {
    super(R.layout.fragment_goods, data);
  }

  @Override protected void convert(BaseViewHolder holder, MineCollectionServiceModel.ListBean item) {
    ImageView mBigIcon = holder.getView(R.id.iv_bigIcon);
    CircleImageView mCivPic = holder.getView(R.id.civ_pic);

    Glide.with(mContext).load(item.getCover()).placeholder(R.drawable.pic_fuwutujiazaishibai).into(mBigIcon);
    Glide.with(mContext).load(item.getHeadImg()).dontAnimate().placeholder(R.drawable.pic_fuwutujiazaishibai)
            .into(mCivPic);

    holder.setText(R.id.tv_name,item.getName()).setText(R.id.tv_youhui,"返"+item.getRebate()+"PV")
            .setText(R.id.tv_price,"¥"+item.getPrice()).setText(R.id.tv_personName,item.getNickName()+"")
            .setText(R.id.tv_personNum,item.getContactTel()+"");
  }
}
