package com.feitianzhu.fu700.me.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.model.WalletModel;
import java.util.List;

/**
 * Created by Administrator on 2017/8/28 0028.
 */

public class MyMoneyAdapter extends BaseQuickAdapter<WalletModel.WallentRecordListEntity, BaseViewHolder> {
  public MyMoneyAdapter(@Nullable List<WalletModel.WallentRecordListEntity> data) {
    super(R.layout.my_item_money, data);
  }

  @Override protected void convert(BaseViewHolder mBaseViewHolder, WalletModel.WallentRecordListEntity mShopTypeModel) {
    mBaseViewHolder.setText(R.id.txt_tradeType,mShopTypeModel.tradeType+"");
    if ("1".equals(mShopTypeModel.isIncome)){
      mBaseViewHolder.setText(R.id.txt_isincome,"收入");
    }else{
      mBaseViewHolder.setText(R.id.txt_isincome,"支出");
    }
    mBaseViewHolder.setText(R.id.txt_tradedate,mShopTypeModel.tradeDate+"");
    mBaseViewHolder.setText(R.id.txt_tradedate,mShopTypeModel.tradeDate+"");
    mBaseViewHolder.setText(R.id.txt_amount,mShopTypeModel.amount+"");



  }
}
