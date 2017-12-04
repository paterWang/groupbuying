package com.feitianzhu.fu700.me.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.model.HotServiceModel;
import com.feitianzhu.fu700.view.CircleImageView;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28 0028.
 */

public class HotServiceAdapter extends BaseQuickAdapter<HotServiceModel.ListBean, BaseViewHolder> {
  public HotServiceAdapter(@Nullable List<HotServiceModel.ListBean> data) {
    super(R.layout.fragment_hot_service, data);
  }

  @Override protected void convert(BaseViewHolder holder, HotServiceModel.ListBean item) {
     Button button =  holder.getView(R.id.bt_buyNow);
     LinearLayout linearLayout = holder.getView(R.id.ll_container);

    ImageView iv_bigIcon = holder.getView(R.id.iv_bigIcon);
    CircleImageView civ_pic = holder.getView(R.id.civ_pic);
    Glide.with(mContext).load(item.getAdImg()).dontAnimate().placeholder(R.mipmap.pic_fuwutujiazaishibai).into(iv_bigIcon);
    Glide.with(mContext).load(item.getHeadImg()).dontAnimate().placeholder(R.mipmap.pic_fuwutujiazaishibai).into(civ_pic);

    holder.setText(R.id.tv_name,item.getServiceName()).setText(R.id.tv_rebate,"返"+item.getRebate()+"PV")
            .setText(R.id.tv_price,"¥"+item.getPrice()).setText(R.id.tv_personName,item.getContactPerson())
            .setText(R.id.tv_personNum,item.getContactTel());
      button.setTag(holder.getAdapterPosition());
      button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(listener!=null){
                    listener.onBuyNowButtonClick((Integer) v.getTag());
              }
          }
      });



  }

  private OnBuyNowClickListener listener;
    public void setButtonClickListener(OnBuyNowClickListener listener){
        this.listener = listener;
    }
  public interface OnBuyNowClickListener{
      void  onBuyNowButtonClick(int postion);
  }
}
