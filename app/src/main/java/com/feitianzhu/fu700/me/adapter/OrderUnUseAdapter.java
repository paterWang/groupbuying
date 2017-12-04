package com.feitianzhu.fu700.me.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feitianzhu.fu700.R;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28 0028.
 */

public class OrderUnUseAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
  public OrderUnUseAdapter(@Nullable List<String> data) {
    super(R.layout.order_un_use_recycleritem, data);
  }

  @Override protected void convert(BaseViewHolder mBaseViewHolder, String item) {

  }
}
