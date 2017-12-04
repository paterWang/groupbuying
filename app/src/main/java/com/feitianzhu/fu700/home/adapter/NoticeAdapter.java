package com.feitianzhu.fu700.home.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.home.entity.NoticeEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28 0028.
 */

public class NoticeAdapter extends BaseQuickAdapter<NoticeEntity.ListBean, BaseViewHolder> {

    public NoticeAdapter(@Nullable List<NoticeEntity.ListBean> data) {
        super(R.layout.activity_notice_item, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, NoticeEntity.ListBean item) {
        holder.setText(R.id.tv_date, item.pushDate).setText(R.id.tv_content, item.pushContent);
    }
}
