package com.feitianzhu.fu700.common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by jiangdikai on 2017/9/4.
 */

public class SFActivity extends AppCompatActivity {
    private MaterialDialog mDialog;
    protected Context sfContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       sfContext= this;
    }

    protected void showloadDialog(String title) {
        mDialog= new MaterialDialog.Builder(this)
                .content("加载中,请稍等")
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show();
    }
    protected void showloadDialogText(String title) {
        mDialog= new MaterialDialog.Builder(this)
                .content(title)
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show();
    }

    protected void goneloadDialog() {
        if (null != mDialog&&mDialog.isShowing()) mDialog.dismiss();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        mDialog=null;
    }
}
