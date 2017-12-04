package com.feitianzhu.fu700.me.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.model.InterestOutModel;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28 0028.
 */

public class InterestHobbiesItemAdapter extends BaseQuickAdapter<InterestOutModel.ChildrenListBean, BaseViewHolder> {

  public InterestHobbiesItemAdapter(@Nullable List<InterestOutModel.ChildrenListBean> data) {
    super(R.layout.interest_recycler_item_view, data);
  }

  @Override protected void convert(BaseViewHolder holder, final InterestOutModel.ChildrenListBean item) {
      //holder.setText(R.id.bt_text,item);
      Button bt = holder.getView(R.id.bt_text);
      bt.setText(item.getInterestName());
      bt.setTag(holder.getAdapterPosition());
      bt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(listener != null){
                if(item.getInterestName().equals("其他")){
                    listener.onOtherButtonClick();
                }else{
                    listener.onItemClick((Integer) v.getTag(),v);
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
    void onItemClick(int position,View v);
      void onOtherButtonClick();
  }
}
