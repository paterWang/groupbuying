package com.feitianzhu.fu700.me.adapter;

import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.model.FuFriendModel;
import com.feitianzhu.fu700.view.CircleImageView;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28 0028.
 */

public class UnionNumberAdapter extends BaseQuickAdapter<FuFriendModel.ListEntity, BaseViewHolder> {
  public UnionNumberAdapter(@Nullable List<FuFriendModel.ListEntity> data) {
    super(R.layout.fragment_union_number_item, data);
  }

  @Override protected void convert(BaseViewHolder holder, FuFriendModel.ListEntity item) {
    CircleImageView mHeadView = holder.getView(R.id.civ_head);
    TextView clickPhone = holder.getView(R.id.tv_clickphone);
    final String str = item.phone;
    SpannableString msp = new SpannableString(str);
    msp.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.txt_blue)), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    msp.setSpan(new UnderlineSpan(), 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    clickPhone.setText(msp);
    clickPhone.setTag(holder.getAdapterPosition());
    clickPhone.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(listener != null){
          listener.onPhoneClick((Integer) v.getTag(),str,v);
        }
      }
    });
    Glide.with(mContext).load(item.headImg).dontAnimate().placeholder(R.drawable.pic_fuwutujiazaishibai).into(mHeadView);
    holder.setText(R.id.tv_name,item.nickName).setText(R.id.tv_price,item.totalConsume+"")
            .setText(R.id.tv_local,item.liveCityName).setText(R.id.tv_age,item.age+"岁")
            .setText(R.id.tv_job,item.job);
  }



    private OnPhoneButtonClickListener listener;
    public void setOnPhoneClickListener(OnPhoneButtonClickListener listener){
        this.listener = listener;
    }
    public interface  OnPhoneButtonClickListener{
          void onPhoneClick(int position,String number,View v);
      }


}
