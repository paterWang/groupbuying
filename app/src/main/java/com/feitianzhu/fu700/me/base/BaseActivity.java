package com.feitianzhu.fu700.me.base;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.feitianzhu.fu700.view.CustomPopWindow;

import java.util.TreeMap;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/3/22.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected TreeMap<String, String> maps;
    protected DefaultNavigationBar defaultNavigationBar;
    /**
     * 代替Context
     */
    protected Context mContext;
    protected MaterialDialog mDialog;
    protected CustomPopWindow mCustomPopWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        useNavigationBar();
        mContext = BaseActivity.this;
        maps = new TreeMap<String, String>();

        // 设置布局layout
        View v = LayoutInflater.from(mContext).inflate(getLayoutId(), null, false);
        setContentView(v);

        //Log.e("TAG", viewRoot + "");

        // 一些特定的算法，子类基本都会使用的
        ButterKnife.bind(BaseActivity.this);

        // 初始化头部
        initTitle();

        // 初始化界面
        initView();

        // 初始化数据
        initData();


    }

    protected void useNavigationBar() {
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) && !isNavigationBarShow()) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        onBaseResume();
    }

    protected void onBaseResume() {

    }

    public boolean isNavigationBarShow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            boolean result = realSize.y != size.y;
            return realSize.y != size.y;
        } else {
            boolean menu = ViewConfiguration.get(this).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if (menu || back) {
                return false;
            } else {
                return true;
            }
        }
    }

    protected void showPopMenu(View v) {
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_menu_personview, null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        //创建并显示popWindow
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .create();
        int width = mCustomPopWindow.getWidth();
        mCustomPopWindow.showAsDropDown(v, -width + v.getWidth(), 0);


    }

    private void handleLogic(View contentView) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCustomPopWindow != null) {
                    mCustomPopWindow.dissmiss();
                }

                switch (v.getId()) {
                    case R.id.menu1:
                        onMenuOneClick();
                        break;
                    case R.id.menu2:
                        onMenuTwoClick();
                        break;
                }

            }
        };
        contentView.findViewById(R.id.menu1).setOnClickListener(listener);
        contentView.findViewById(R.id.menu2).setOnClickListener(listener);
    }

    public void onMenuOneClick() {
    }

    public void onMenuTwoClick() {
    }


    protected void showloadDialog(String title) {
        mDialog = new MaterialDialog.Builder(this).title(title)
                .content("加载中,请稍候...")
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show();
    }

    protected void goneloadDialog() {
        if (null != mDialog && mDialog.isShowing()) {
            if (mDialog.isShowing()){
                mDialog.dismiss();
            }
        }

    }


    /**
     * 子类传入一个布局,父类创建View
     */
    protected abstract int getLayoutId();

    /**
     * 创建一个titlebar
     */
    protected void initTitle() {
     /*   defaultNavigationBar = new DefaultNavigationBar
                .Builder(BaseActivity.this, (ViewGroup)findViewById(R.id.ll_Container))
                .setTitle(setTitleText())
                // .setRightText("发布")
                .setTitleColor(titlebar_home)
                .setStatusColor(titlebar_home)
                .setStatusHeight()
                .setLeftIcon(R.mipmap.icon_left)
                .setRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(BaseActivity.this,"点击了发布",Toast.LENGTH_SHORT).show();
                    }
                })

                .builder();*/
    }

    /**
     * 子类去实现更改头部的名字
     *
     * @return
     */
    protected String setTitleText() {
        return "";
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

    protected final boolean checkTextView(TextView textView, String tips) {

        if (textView == null) return true;

        String busName = textView.getText().toString().trim();
        if (TextUtils.isEmpty(busName)) {
            ToastUtils.showShortToast(tips);
            return true;
        }
        return false;
    }

}
