package com.feitianzhu.fu700.settings;

import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.dao.NetworkDao;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Vya on 2017/9/4 0004.
 */

public class FeedbackActivity extends BaseActivity {

    @BindView(R.id.editText)
    EditText mEditText;
    @BindView(R.id.button)
    Button mButton;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initTitle() {

        defaultNavigationBar = new DefaultNavigationBar
                .Builder(FeedbackActivity.this, (ViewGroup) findViewById(R.id.ll_Container))
                .setTitle("意见反馈")
                .setStatusHeight(FeedbackActivity.this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.button})
    public void onClick() {

        String content = mEditText.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtils.showShortToast(mContext, R.string.content_must_not_be_null);
            return;
        }
        NetworkDao.feedback(content, new onConnectionFinishLinstener() {
            @Override
            public void onSuccess(int code, Object result) {
                ToastUtils.showShortToast("反馈成功");
                finish();
            }

            @Override
            public void onFail(int code, String result) {
                ToastUtils.showShortToast(result);
            }
        });
    }
}
