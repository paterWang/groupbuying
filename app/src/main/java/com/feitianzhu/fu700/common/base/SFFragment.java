package com.feitianzhu.fu700.common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by dicallc on 2017/9/8 0008.
 */

public class SFFragment extends Fragment {

  protected Context mContext;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mContext = getActivity();
  }

  private MaterialDialog mDialog;

  protected void showloadDialog(String title) {
    mDialog= new MaterialDialog.Builder(getActivity())
            .content("加载中,请稍等")
            .progress(true, 0)
            .progressIndeterminateStyle(false)
            .show();
  }

  protected void goneloadDialog() {
    if (null != mDialog&&mDialog.isShowing()) if (mDialog.isShowing()) mDialog.dismiss();
  }
}
