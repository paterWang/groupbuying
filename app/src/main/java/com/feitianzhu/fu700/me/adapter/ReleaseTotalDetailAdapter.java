package com.feitianzhu.fu700.me.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.model.ReleaseTotalDetailModel;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28 0028.
 */

public class ReleaseTotalDetailAdapter extends BaseQuickAdapter<ReleaseTotalDetailModel.ListBean, BaseViewHolder> {
  public ReleaseTotalDetailAdapter(@Nullable List<ReleaseTotalDetailModel.ListBean> data) {
    super(R.layout.release_totaldetail_item, data);
  }

  @Override protected void convert(BaseViewHolder holder, ReleaseTotalDetailModel.ListBean item) {
            holder.setText(R.id.tv_date,item.getCreateDate()==null?"":item.getCreateDate())
                    .setText(R.id.tv_rebate,item.getTotalAmount()<=0?"0":"+"+item.getTotalAmount());


  }

}
