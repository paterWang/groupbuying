package com.feitianzhu.fu700.me.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feitianzhu.fu700.R;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;


/**
 * Created by Vya on 2017/5/10 0010.
 */

public class DefaultNavigationBar<D extends DefaultNavigationBar.Builder.DefaultNavigationParams>
        extends AbsNavigationbar<DefaultNavigationBar.Builder.DefaultNavigationParams>{
    private RelativeLayout rl;
    protected int titleBarheight = 0;
    public DefaultNavigationBar(DefaultNavigationBar.Builder.DefaultNavigationParams params) {
        super(params);
    }

    @Override
    public int bindLayoutId() {
        return R.layout.builder_home_head_nav;
    }

    @Override
    public void applyView() {
        rl = (RelativeLayout) mNavigationView.findViewById(R.id.titlebarContainer);
        // 绑定效果
        setText(R.id.tv_title, getParams().mTitle);
        setTitleColor(R.id.titlebarContainer,getParams().ColorRes);
        setOnClickListener(R.id.right_container, getParams().mRightClickListener);
//        setOnClickListener(R.id.tv_right, getParams().mRightClickListener);
        setStatusColor(getParams().statusBarId,getParams().statusBarColor);
        setStatusHeight(R.id.build_titleBar,getParams().statusBarHeight);
        setRightText(R.id.tv_right,getParams().mRightText,getParams().listener);
        showRightPic(getParams().defaultRes,getParams().viewTag,getParams().listener);
        showMoreRightPic(getParams().defaultMoreRes,getParams().viewMoreTag,getParams().listener);
        setLeftViewIcon(R.id.tv_back,getParams().leftIcon);
        setMiddleButtonStr(R.id.tl_4,getParams().titles,getParams().tabListener);

        // 左边 要写一个默认的  finishActivity
        setOnClickListener(R.id.fl_back,getParams().mLeftClickListener);

    }



    public void setTitleBarHeight(int titleBarHeight){
        LinearLayout.LayoutParams params=new  LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,titleBarHeight);
        RelativeLayout rl = (RelativeLayout) mNavigationView.findViewById(R.id.titlebarContainer);
        if(null != rl){
            rl.setVisibility(View.VISIBLE);
            rl.setLayoutParams(params);
        }
    }
    /*
    *
    *
    * */
    /**
     * 设置沉浸式颜色
     * @param titlebarColor
     */
    public void setImmersion(int titlebarColor){
       View view =  mNavigationView.findViewById(R.id.build_titleBar);
       RelativeLayout rlView = (RelativeLayout) mNavigationView.findViewById(R.id.titlebarContainer);
        view.setBackgroundColor(getParams().res.getColor(titlebarColor));
        rlView.setBackgroundColor(getParams().res.getColor(titlebarColor));
    }

    public void changeTitleText(String text){
          TextView tv = (TextView) mNavigationView.findViewById(R.id.tv_title);
            tv.setText(text);
    }

    public void setCurrentTab(int position) {
       SegmentTabLayout item = (SegmentTabLayout) mNavigationView.findViewById(R.id.tl_4);
        if(item != null){
            item.setCurrentTab(position);
        }
    }


    public static class Builder extends AbsNavigationbar.Builder {
        DefaultNavigationParams P;
        int titleBarheight = 0;
        public Builder(Context context, ViewGroup parent) {
            super(context, parent);
            if(titleBarheight == 0){
                titleBarheight = UIUtil.dip2px(context,UIUtil.getStatusBarHeight(context));
                Log.e("wangyan","titleBarheight111--->"+titleBarheight);
            }
            P = new DefaultNavigationParams(context, parent);
        }
        public Builder(Context context) {
            super(context, null);
            P = new DefaultNavigationParams(context, null);
            if(titleBarheight == 0){
                titleBarheight = UIUtil.dip2px(context,UIUtil.getStatusBarHeight(context));
                Log.e("wangyan","titleBarheight222--->"+titleBarheight);
            }
        }


        @Override
        public DefaultNavigationBar builder() {
            DefaultNavigationBar defaultNavigationBar = null;
            if(defaultNavigationBar == null){
                defaultNavigationBar = new DefaultNavigationBar(P);
            }
            return defaultNavigationBar;
        }
        // 1. 设置所有效果

        public DefaultNavigationBar.Builder setTitle(String title) {
            P.mTitle = title;
            return this;
        }

        public DefaultNavigationBar.Builder setMiddleButton(String[] titles,OnTabSelectListener listener) {
            P.titles = titles;
            P.tabListener = listener;
            return this;
        }


        /**
         * 设置右边的文字
         * @param rightText
         * @return
         */
        public DefaultNavigationBar.Builder setRightText(String rightText, View.OnClickListener listener) {
            P.mRightText = rightText;
            P.listener = listener;
            return this;
        }

       public DefaultNavigationBar.Builder showRightPic(int defaultRes,int viewTag,View.OnClickListener listener){
           P.defaultRes = defaultRes;
           P.viewTag = viewTag;
           P.listener = listener;
           return this;
       }
        public DefaultNavigationBar.Builder showRightMorePic(int[] defaultRes,int[] viewTag,View.OnClickListener listener){
            P.defaultMoreRes = defaultRes;
            P.viewMoreTag = viewTag;
            P.listener = listener;
            return this;
        }


        /**
         * 设置右边的图片
         */
        public DefaultNavigationBar.Builder setRightIcon(int rightRes) {
            P.rightIcon = rightRes;
            return this;
        }

        public DefaultNavigationBar.Builder setLeftIcon(int leftRes) {
            P.leftIcon = leftRes;
            return this;
        }

        /**
         * 设置状态栏的颜色
         */
        public DefaultNavigationBar.Builder setStatusColor(int color) {
            P.statusBarColor = color;
            return this;
        }
        public DefaultNavigationBar.Builder setStatusHeight() {

            P.statusBarHeight = titleBarheight;
            return this;
        }
        public DefaultNavigationBar.Builder setStatusHeight(Activity context) {
            if(isNavigationBarShow(context)){
                return this;
            }
            P.statusBarHeight = titleBarheight;
            return this;
        }

        public boolean isNavigationBarShow(Activity context){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Display display = context.getWindowManager().getDefaultDisplay();
                Point size = new Point();
                Point realSize = new Point();
                display.getSize(size);
                display.getRealSize(realSize);
                boolean  result  = realSize.y!=size.y;
                return realSize.y!=size.y;
            }else {
                boolean menu = ViewConfiguration.get(context).hasPermanentMenuKey();
                boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
                if(menu || back) {
                    return false;
                }else {
                    return true;
                }
            }
        }

        /**
         * 设置标题颜色
         */
        public DefaultNavigationBar.Builder setTitleColor(int ColorRes) {
            P.ColorRes = ColorRes;
            return this;
        }


        //1 ,设置效果

        public static class DefaultNavigationParams extends AbsNavigationbar.Builder.AbsNavigationParams {
            // 2.所有效果放置
            public String mTitle;

            public String mRightText;

            public int rightIcon;

            public int leftIcon;

            public int statusBarHeight;

            public int statusBarColor;


           public int[] defaultMoreRes ;
           public int[] viewMoreTag ;

            public int statusBarId;

            public String[] titles;

            public OnTabSelectListener tabListener;

            public int defaultRes;

            public int viewTag;

            public View.OnClickListener listener;

            public int ColorRes;


            public Resources res = mContext.getResources();

            // 后面还有一些通用的
            public View.OnClickListener mRightClickListener;

            public View.OnClickListener mLeftClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 关闭当前Activity
                    ((Activity) mContext).finish();
                }
            };
            //2，放所有的效果
            DefaultNavigationParams(Context context, ViewGroup parent) {
                super(context, parent);
            }
        }



    }



}
