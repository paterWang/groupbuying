package com.feitianzhu.fu700.me.base;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.feitianzhu.fu700.common.SelectPhotoActivity;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.feitianzhu.fu700.view.CustomPopWindow;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by Vya on 2017/9/12 0012.
 */

public abstract class BaseTakePhotoActivity extends SelectPhotoActivity {
    protected DefaultNavigationBar defaultNavigationBar;
    protected MaterialDialog mDialog;
    protected CustomPopWindow mCustomPopWindow;
    @Override
    protected void onWheelSelect(int num, ArrayList<String> mList) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)&&!isNavigationBarShow()) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        // 设置布局layout
        View v = LayoutInflater.from(mContext).inflate(getLayoutId(),null,false);
        setContentView(v);

        //Log.e("TAG", viewRoot + "");

        // 一些特定的算法，子类基本都会使用的
        ButterKnife.bind(BaseTakePhotoActivity.this);

        // 初始化头部
        initTitle();

        // 初始化界面
        initView();

        // 初始化数据
        initData();
    }

    public boolean isNavigationBarShow(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            boolean  result  = realSize.y!=size.y;
            return realSize.y!=size.y;
        }else {
            boolean menu = ViewConfiguration.get(this).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if(menu || back) {
                return false;
            }else {
                return true;
            }
        }
    }


    protected void showloadDialog(String title) {
        mDialog= new MaterialDialog.Builder(this)
                .content("加载中,请稍等")
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show();
    }

    protected void goneloadDialog() {
        if (null != mDialog&&mDialog.isShowing()) if (mDialog.isShowing()) mDialog.dismiss();
    }
    /**
     * 子类传入一个布局,父类创建View
     */
    protected abstract int getLayoutId();
    /**
     * 创建一个titlebar
     */
    protected  void initTitle() {

    }

    /**
     * 初始化界面,比如创建dialog，创建其他的view
     */
    protected abstract void initView();

    /**
     * 初始化ViewData
     */
    protected abstract void initData();

    protected final boolean checkEditText(EditText editText, String tips) {

        if (editText == null) return true;

        String busName = editText.getText().toString().trim();
        if (TextUtils.isEmpty(busName)) {
            ToastUtils.showShortToast(tips);
            return true;
        }
        return false;
    }
}
