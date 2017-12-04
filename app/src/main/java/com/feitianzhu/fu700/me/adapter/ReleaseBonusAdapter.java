package com.feitianzhu.fu700.me.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.model.TotalScoreModel;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28 0028.
 */

public class ReleaseBonusAdapter extends BaseQuickAdapter<TotalScoreModel.RebateRecordsBean.RebateItemBean, BaseViewHolder> {

  public ReleaseBonusAdapter(@Nullable List<TotalScoreModel.RebateRecordsBean.RebateItemBean> data) {
    super(R.layout.release_bonus_item, data);

  }

  @Override protected void convert(BaseViewHolder holder, TotalScoreModel.RebateRecordsBean.RebateItemBean item) {
      TextView mTextView = holder.getView(R.id.tv_rebate);
        if("1".equals(item.getIsIncome())){
          mTextView.setText("+"+item.getRebateAmount()==null?"":"+"+item.getRebateAmount());
          mTextView.setTextColor(mContext.getResources().getColor(R.color.txt_red));
        }else{
          mTextView.setText("-"+item.getRebateAmount()==null?"":"-"+item.getRebateAmount());
          mTextView.setTextColor(mContext.getResources().getColor(R.color.txt_pass));
        }

        holder.setText(R.id.tv_date,item.getRebateDate());
  }
}
