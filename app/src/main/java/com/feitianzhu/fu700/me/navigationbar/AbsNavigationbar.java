package com.feitianzhu.fu700.me.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feitianzhu.fu700.R;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;

/**
 * Created by Vya on 2017/5/10 0010.
 * 头部的builder基类
 */

public abstract class AbsNavigationbar<P extends AbsNavigationbar.Builder.AbsNavigationParams> implements INavigationbar {
    private P mParams;
    protected View mNavigationView;

    public AbsNavigationbar(P params) {
        this.mParams = params;
        createAndBindView();
    }

    public static final int PIC_ONE = 1001;
    public static final int PIC_TWO = 1002;
    public static final int PIC_THREE = 1003;
    public static final int TXT_THREE = 1004;

    public P getParams() {
        return mParams;
    }


    /**
     * 设置文本
     *
     * @param viewId
     * @param text
     */
    protected void setText(int viewId, String text) {
        TextView tv = findViewById(viewId);
        if (!TextUtils.isEmpty(text)) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }
    }

    protected void setRightText(int viewId, String text, View.OnClickListener listener) {

        TextView tv = findViewById(viewId);
        View rightLayout = findViewById(R.id.right_container);

        if (!TextUtils.isEmpty(text)) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
            if (rightLayout != null) {
                rightLayout.setOnClickListener(listener);
            }
//            tv.setOnClickListener(listener);
        }
    }

    protected void setStatusColor(int title_id, int titleColor) {
        View ll = findViewById(title_id);
        if (null != ll) {
            ll.setVisibility(View.VISIBLE);
            ll.setBackgroundResource(titleColor);
        }
    }


    protected void setStatusHeight(int title_id, int height) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
        View ll = findViewById(title_id);
        if (null != ll) {
            ll.setVisibility(View.VISIBLE);
            ll.setLayoutParams(params);
        }
    }

    protected void setMiddleButtonStr(int viewId, String[] titles, OnTabSelectListener listener) {
        if (titles == null || titles.length <= 0) {
            return;
        }
        SegmentTabLayout item = findViewById(viewId);
        if (null != item) {
            item.setVisibility(View.VISIBLE);
            item.setTabData(titles);
            item.setOnTabSelectListener(listener);
        }
    }


    protected void setTitleBarHeight(int title_id, int height) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
        View ll = findViewById(title_id);
        if (null != ll) {
            ll.setVisibility(View.VISIBLE);
            ll.setLayoutParams(params);
        }
    }

    protected void showMoreRightPic(int[] defaultRes, int[] viewTag, View.OnClickListener listener) {
        if (defaultRes == null || defaultRes.length <= 0) {
            return;
        }
        for (int i = 0; i < defaultRes.length; i++) {
            showRightPic(defaultRes[i], viewTag[i], listener);
        }
    }

    protected void showRightPic(int defaultRes, int viewTag, View.OnClickListener listener) {
        if (defaultRes == 0 || defaultRes <= 0) {
            return;
        }
        int pic_id = 0;
        switch (viewTag) {
            case PIC_ONE:
                pic_id = R.id.iv_right_leftOne;
                break;
            case PIC_TWO:
                pic_id = R.id.iv_right_leftTwo;
                break;
            case PIC_THREE:
                pic_id = R.id.iv_right;
                break;

        }
        Log.e("wangyan", "打印 viewTag:" + viewTag);
        if (pic_id > 0) {
            ImageView ll = findViewById(pic_id);
            if (null != ll && defaultRes > 0) {
                ll.setVisibility(View.VISIBLE);
                ll.setBackgroundResource(defaultRes);
                ll.setTag(viewTag);
                ll.setOnClickListener(listener);
            } else {
                ll.setVisibility(View.GONE);
            }
        }


    }


    protected void setLeftViewIcon(int ib_left_icon, int leftIcon) {
        ImageView ll = findViewById(ib_left_icon);
        if (null != ll && leftIcon > 0) {
            ll.setVisibility(View.VISIBLE);
            ll.setImageResource(leftIcon);
        } else {
//            ll.setVisibility(View.GONE);
        }
    }

    protected void setRightViewIcon(int right_icon, int leftIcon) {
        ImageView ll = findViewById(right_icon);
        if (null != ll && leftIcon > 0) {
            ll.setVisibility(View.VISIBLE);
            ll.setBackgroundResource(leftIcon);
        } else {
//            ll.setVisibility(View.GONE);
        }
    }


    protected void setTitleColor(int ib_right_icon, int rightIcon) {
        RelativeLayout ll = findViewById(ib_right_icon);
        if (null != ll) {
            ll.setVisibility(View.VISIBLE);
            ll.setBackgroundResource(rightIcon);
        }
    }


    /**
     * 设置点击
     *
     * @param viewId
     * @param listener
     */
    protected void setOnClickListener(int viewId, View.OnClickListener listener) {
        findViewById(viewId).setOnClickListener(listener);
    }


    public <T extends View> T findViewById(int viewId) {
        return (T) mNavigationView.findViewById(viewId);
    }

    /**
     * 创建并绑定View
     */
    private void createAndBindView() {


        if (mParams.mViewGroup == null) {
            // 获取activity的根布局，View源码
            ViewGroup activityRoot = (ViewGroup) ((Activity) (mParams.mContext))
                    .getWindow().getDecorView();
            mParams.mViewGroup = (ViewGroup) activityRoot.getChildAt(0);
            Log.e("wangyan", mParams.mViewGroup + "");
        }

        // 处理Activity的源码，后面再去看

        if (mParams.mViewGroup == null) {
            return;
        }

        //创建view
        mNavigationView = LayoutInflater.from(mParams.mContext).inflate(bindLayoutId(), mParams.mViewGroup, false);//插件换肤

        // 添加
        mParams.mViewGroup.addView(mNavigationView, 0); //把view添加到第0个位置

        applyView();
    }


    public abstract static class Builder {

        public Builder(Context context, ViewGroup parent) {
        }

        public abstract AbsNavigationbar builder();

        public static class AbsNavigationParams {
            public Context mContext;
            public ViewGroup mViewGroup;

            public AbsNavigationParams(Context context, ViewGroup parent) {
                this.mContext = context;
                this.mViewGroup = parent;
            }
        }
    }
}
