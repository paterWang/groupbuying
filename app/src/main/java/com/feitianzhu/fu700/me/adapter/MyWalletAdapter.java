package com.feitianzhu.fu700.me.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.model.WalletModel;
import java.util.List;

/**
 * Created by Administrator on 2017/8/28 0028.
 */

public class MyWalletAdapter extends BaseQuickAdapter<WalletModel.WallentRecordListEntity, BaseViewHolder> {
  public MyWalletAdapter(@Nullable List<WalletModel.WallentRecordListEntity> data) {
    super(R.layout.my_item_wolley, data);
  }

  @Override protected void convert(BaseViewHolder helper, WalletModel.WallentRecordListEntity mShopTypeModel) {
    if (null==mShopTypeModel.user){
      helper.setVisible(R.id.item_icon,true);
      helper.setVisible(R.id.item_name,true);
      ImageView mView = helper.getView(R.id.item_icon);
      Glide.with(mContext).load(mShopTypeModel.user.headImg).dontAnimate().placeholder(R.mipmap.pic_fuwutujiazaishibai).error(R.mipmap.pic_fuwutujiazaishibai).into(mView);
    }else{
      helper.setVisible(R.id.item_icon,false);
      helper.setVisible(R.id.item_name,false);
    }
    helper.setText(R.id.item_type,mShopTypeModel.tradeType+"");
    if ("1".equals(mShopTypeModel.isIncome)){
      helper.setText(R.id.item_status,"收入");
    }else{
      helper.setText(R.id.item_status,"支出");
    }
    helper.setText(R.id.item_money,mShopTypeModel.amount+"");
    helper.setText(R.id.item_name,mShopTypeModel.user.nickName+"");
    helper.setText(R.id.item_time,mShopTypeModel.tradeDate+"");
      }
}
