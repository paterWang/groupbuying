package com.feitianzhu.fu700.me.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.model.ShopRecordRefuseModel;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28 0028.
 */

public class ShopRecordDetailRefuseAdapter extends BaseQuickAdapter<ShopRecordRefuseModel.ListBean, BaseViewHolder> {
  public ShopRecordDetailRefuseAdapter(@Nullable List<ShopRecordRefuseModel.ListBean> data) {
    super(R.layout.fragment_shop_detail_refuse_item, data);
  }

  @Override protected void convert(BaseViewHolder holder, ShopRecordRefuseModel.ListBean item) {
      ImageView iv = holder.getView(R.id.iv_icon);
      Button bt_retry = holder.getView(R.id.bt_retry);
      Glide.with(mContext).load(item.getConsumePlaceImg()).placeholder(R.drawable.pic_fuwutujiazaishibai).into(iv);
      holder.setText(R.id.orderIdTxt,item.getUserId()+"")
              .setText(R.id.tv_date,item.getCreateDate())
              .setText(R.id.tv_reason,item.getRefuseReason()==null?"":item.getRefuseReason())
              .setText(R.id.tv_percent,item.getHandleFeeRate()+"%")
              .setText(R.id.tv_money,"¥"+item.getConsumeAmount())
              .setText(R.id.handFee,"¥"+item.getHandleFee());
      bt_retry.setTag(holder.getAdapterPosition());
      bt_retry.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if(mCallBack != null){
                  mCallBack.onRetryClick((Integer) v.getTag());
              }
          }
      });
  }

  private SetRectryButtonClick mCallBack;
  public void setOnRetryButtonClick( SetRectryButtonClick mCallBack){
      this.mCallBack = mCallBack;
  }
  public interface SetRectryButtonClick{
      void onRetryClick(int position);
  }
}
