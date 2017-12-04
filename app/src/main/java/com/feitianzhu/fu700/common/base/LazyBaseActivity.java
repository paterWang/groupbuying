package com.feitianzhu.fu700.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.feitianzhu.fu700.R;

/**
 * Created by dicallc on 2017/9/11 0011.
 */

public abstract class LazyBaseActivity extends SFActivity {
  protected LinearLayout mContent;
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.title) TextView mTitle;
  private TextView mRight;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.common_a_lazy_view);
    mContent = (LinearLayout) findViewById(R.id.ll_content);
    View.inflate(this, setView(), mContent);
    ButterKnife.bind(this);
    initLocal();
    mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
    initView();
    initData();
  }

  protected void setTitleName(String name) {
    mTitle.setText(name);
  }

  protected abstract int setView();

  protected abstract void initView();

  protected abstract void initLocal();

  protected abstract void initData();

  @Override protected void onDestroy() {
    super.onDestroy();
  }
}
