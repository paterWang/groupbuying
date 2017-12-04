package com.feitianzhu.fu700.payforme.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.payforme.entity.PayForMeEntity;

import java.util.List;


/**
 * Created by Lee on 2016/6/24.
 */
public class PayForMeRecordAdapter extends BaseQuickAdapter<PayForMeEntity.ListBean, BaseViewHolder> {


    public PayForMeRecordAdapter(List<PayForMeEntity.ListBean> data) {
        super(R.layout.item_pay_for_me, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, PayForMeEntity.ListBean item) {

        String money = mContext.getString(R.string.money);

        holder.setText(R.id.tv_busName, item.merchantName)
                .setText(R.id.tv_date, item.createDate)
                .setText(R.id.tv_goods_name, item.goodsName)
                .setText(R.id.tv_price, String.format(money, item.consumeAmount + ""))
                .setText(R.id.tv_fee, String.format(money, item.handleFee + ""))
                .addOnClickListener(R.id.button);

        Glide.with(mContext).load(item.consumePlaceImg).into((ImageView) holder.getView(R.id.imageview));

        TextView button = holder.getView(R.id.button);
        TextView rejectReason = holder.getView(R.id.tv_reject_reason);

        button.setVisibility(View.GONE);
        rejectReason.setVisibility(View.GONE);

        int status = 0;
        try {
            status = Integer.parseInt(item.status);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (status == -1) {
            button.setVisibility(View.VISIBLE);
            rejectReason.setVisibility(View.VISIBLE);
            rejectReason.setText(String.format(mContext.getString(R.string.reject_reason), item.refuseReason));
        }

    }

}
