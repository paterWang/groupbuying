package com.feitianzhu.fu700.me.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.model.UnionApplyRecordModel;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28 0028.
 */

public class UnionApplyRecordAdapter extends BaseQuickAdapter<UnionApplyRecordModel, BaseViewHolder> {
  public UnionApplyRecordAdapter(@Nullable List<UnionApplyRecordModel> data) {
    super(R.layout.layout_union_level_record_item, data);
  }

  @Override protected void convert(BaseViewHolder holder, UnionApplyRecordModel item) {
        TextView mTextViewPass = holder.getView(R.id.tv_pass);
            String passTxt = "";
            if(item.getStatus().equals("0")){ //（0：审核中，1：审核通过，-1：拒绝）
                passTxt = "审核中";
                mTextViewPass.setTextColor(mContext.getResources().getColor(R.color.txt_pass));

            }else if(item.getStatus().equals("1")){
                passTxt = "已通过";
                mTextViewPass.setTextColor(mContext.getResources().getColor(R.color.edit_hint));
            }else{
                passTxt = "未通过";
                mTextViewPass.setTextColor(mContext.getResources().getColor(R.color.edit_hint));
            }
        mTextViewPass.setText(passTxt);
        holder.setText(R.id.tv_TypeName,item.getGrade().getTitle())
            .setText(R.id.tv_date,item.getCreateDate());


  }
}
