package com.feitianzhu.fu700.me.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.feitianzhu.fu700.R;
import com.jaeger.library.StatusBarUtil;
/**
 * description: 显示没有创建商户界面
 * autour: dicallc
*/
public class ShopShowNoCreateActivity extends Activity {

  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.btn_create) TextView mBtnCreate;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shop_admin);
    ButterKnife.bind(this);
    StatusBarUtil.setColor(ShopShowNoCreateActivity.this, getResources().getColor(R.color.sf_blue), 0);
    mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
  }

  @OnClick(R.id.btn_create) public void onViewClicked() {
    Intent mIntent=new Intent(this,ShopsCreateActivity.class);
    startActivity(mIntent);
    finish();
  }
}
