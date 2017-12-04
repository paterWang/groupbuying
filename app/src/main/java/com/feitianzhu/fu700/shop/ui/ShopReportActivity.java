package com.feitianzhu.fu700.shop.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.common.base.LazyBaseActivity;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.shop.ShopDao;
import com.feitianzhu.fu700.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class ShopReportActivity extends LazyBaseActivity {

  @BindView(R.id.content) EditText mContent;
  @BindView(R.id.btn_submit) TextView mBtnSubmit;
  private String mId;
  private String mtype;

  @Override protected int setView() {
    return R.layout.activity_shop_report;
  }

  @Override protected void initView() {

  }

  @Override protected void initLocal() {
    Intent mIntent = getIntent();
    mId = mIntent.getStringExtra(Constant.MERCHANTID);
    mtype = mIntent.getStringExtra(Constant.TYPE);
  }

  @Override protected void initData() {
  }


  @OnClick(R.id.btn_submit) public void onViewClicked() {
    String str = mContent.getText().toString().trim();
    if (TextUtils.isEmpty(str)){
      ToastUtils.showShortToast("举报内容不能为空");
      return;
    }
    ShopDao.postShopReport(mId, mtype, str, new onConnectionFinishLinstener() {
      @Override public void onSuccess(int code, Object result) {
        ToastUtils.showShortToast("举报成功");
        finish();
      }

      @Override public void onFail(int code, String result) {
        ToastUtils.showShortToast(result);
      }
    });
  }
}
