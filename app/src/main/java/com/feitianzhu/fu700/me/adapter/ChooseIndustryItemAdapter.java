package com.feitianzhu.fu700.me.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.model.IndustryOutModel;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28 0028.
 */

public class ChooseIndustryItemAdapter extends BaseQuickAdapter<IndustryOutModel.ChildrenListBean, BaseViewHolder> {

  public ChooseIndustryItemAdapter(@Nullable List<IndustryOutModel.ChildrenListBean> data) {
    super(R.layout.choose_dustry_item_view, data);
  }

  @Override protected void convert(BaseViewHolder holder, final IndustryOutModel.ChildrenListBean item) {

     Button bt =  holder.getView(R.id.bt_text);
      bt.setText(item.getIndustryName());
      bt.setTag(item.getIndustryId());
      bt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if(listener !=null){
              if(item.getIndustryName().equals("更多")){
                  listener.onMoreButtonClick();
              }else{
                  listener.onItemClick(v.getTag()+"",v);
              }

          }
        }
      });

  }
  private OnItemClickListener listener;
  public void SetOnItemClickListener(OnItemClickListener listener){
    this.listener = listener;
  }
  public interface  OnItemClickListener{
      void onItemClick(String id,View v);

      void onMoreButtonClick();
  }

}
