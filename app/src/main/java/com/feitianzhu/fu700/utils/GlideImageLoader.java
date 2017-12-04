package com.feitianzhu.fu700.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.feitianzhu.fu700.R;
import com.youth.banner.loader.ImageLoader;

/**
 * Description：
 * Author：Lee
 * Date：2017/8/30 16:45
 */

public class GlideImageLoader extends ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).placeholder(R.mipmap.pic_fuwutujiazaishibai).into(imageView);
    }
}
