package com.feitianzhu.fu700.huanghuali;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.huanghuali.entity.HuangHuaLiRecordEntity;

import java.util.List;


/**
 * Created by Lee on 2016/6/24.
 */
public class HuangHuaLiRecordAdapter extends BaseQuickAdapter<HuangHuaLiRecordEntity, BaseViewHolder> {


    public HuangHuaLiRecordAdapter(List<HuangHuaLiRecordEntity> data) {
        super(R.layout.item_huanghuali, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, HuangHuaLiRecordEntity item) {

        String money = mContext.getString(R.string.money);

        holder.setText(R.id.tv_date, item.createDate)
                .setText(R.id.tv_money, String.format(money, item.price + ""));


        TextView rejectReason = holder.getView(R.id.tv_reject_reason);
        TextView statusView = holder.getView(R.id.tv_status);

        rejectReason.setVisibility(View.GONE);

        int status = 0;
        try {
            status = Integer.parseInt(item.status);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (status == -1) {
            rejectReason.setVisibility(View.VISIBLE);
            rejectReason.setText(String.format(mContext.getString(R.string.huanghuali_reject_reason), item.refuseReason));
            statusView.setTextColor(mContext.getResources().getColor(R.color.txt_hint));
            holder.setText(R.id.tv_status, "审核拒绝");
        } else if (status == 0) {
            holder.setText(R.id.tv_status, "审核中");
            statusView.setTextColor(mContext.getResources().getColor(R.color.txt_hint));
        } else if (status == 1) {
            holder.setText(R.id.tv_status, "审核通过");
            statusView.setTextColor(mContext.getResources().getColor(R.color.txt_pass));
        }

    }

}
