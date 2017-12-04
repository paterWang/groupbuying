package com.feitianzhu.fu700.me.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.model.UnionLevelModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/28 0028.
 */

public class UnionLevelAdapter extends BaseQuickAdapter<UnionLevelModel, BaseViewHolder> {
  private List<ImageView> clickView;
  private List<UnionLevelModel> lastModel;
  public UnionLevelAdapter(@Nullable List<UnionLevelModel> data) {
    super(R.layout.union_level_item, data);
    clickView = new ArrayList<>();
    lastModel = new ArrayList<>();
  }

  @Override protected void convert(BaseViewHolder holder, final UnionLevelModel item) {
    ImageView mImageView = holder.getView(R.id.iv_icon);
    Glide.with(mContext).load(item.getIcon()).placeholder(R.drawable.pic_fuwutujiazaishibai).into(mImageView);
    holder.setText(R.id.tv_1ji,item.getName()).setText(R.id.tv_content,item.getTitle()).setText(R.id.tv_content_percent,item.getRate()+"%");
    ImageView mButton = holder.getView(R.id.iv_selectButton);
    mButton.setTag(holder.getAdapterPosition());
    if(item.isSelect){
      mButton.setImageResource(R.drawable.icon_ixuanze);
    }else{
      mButton.setImageResource(R.drawable.icon_weixuanze);
    }


    mButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ImageView currentView = (ImageView) v;
        if(listener != null){

          if(clickView.size()==0){
            item.isSelect = true;
            lastModel.add(item);
            currentView.setImageResource(R.drawable.icon_ixuanze);
            clickView.add(currentView);
          }else{
            lastModel.remove(0).isSelect = false;
            ImageView lastView = clickView.remove(0);
            lastView.setImageResource(R.drawable.icon_weixuanze);
            currentView.setImageResource(R.drawable.icon_ixuanze);

            lastModel.add(item);
            clickView.add(currentView);
          }
          listener.onItemImageClick((Integer) v.getTag());
        }
      }
    });
  }

  public interface OnItemImageClickListener{
    void onItemImageClick(int position);
  }

  private OnItemImageClickListener listener;
  public void setOnItemImageClickListener(OnItemImageClickListener listener){
      this.listener = listener;
  }


}


