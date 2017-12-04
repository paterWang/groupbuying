package com.feitianzhu.fu700.home.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.home.entity.HomeEntity;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


/**
 * Created by Lee on 2016/6/24.
 */
public class HomeRecommendAdapter extends BaseQuickAdapter<HomeEntity.RecommendListBean, BaseViewHolder> {


    public HomeRecommendAdapter(List<HomeEntity.RecommendListBean> data) {
        super(R.layout.layer_home_category_item, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, HomeEntity.RecommendListBean item) {

        holder.setText(R.id.tv_category, item.merchantName);
        Glide.with(mContext).load(item.merchantHeadImg).bitmapTransform(new CropCircleTransformation(mContext)).placeholder(R.mipmap.pic_fuwutujiazaishibai).into((ImageView) holder.getView(R.id.iv_category));
    }

}
