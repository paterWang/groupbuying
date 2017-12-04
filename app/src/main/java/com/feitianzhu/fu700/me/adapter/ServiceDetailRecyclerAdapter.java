package com.feitianzhu.fu700.me.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feitianzhu.fu700.R;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28 0028.
 */

public class ServiceDetailRecyclerAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
  public ServiceDetailRecyclerAdapter(@Nullable List<String> data) {
    super(R.layout.service_detail_recycler_item, data);
  }

  @Override protected void convert(BaseViewHolder holder, String item) {
       ImageView iv =  holder.getView(R.id.iv_photos);
    Glide.with(mContext).load(item).dontAnimate().placeholder(R.drawable.pic_fuwutujiazaishibai)
            .into(iv);
  }
}
