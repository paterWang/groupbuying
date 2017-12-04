package com.feitianzhu.fu700.settings;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.dao.NetworkDao;
import com.feitianzhu.fu700.login.entity.SmsCodeEntity;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.socks.library.KLog;

import butterknife.BindView;
import butterknife.OnClick;

public class ChangePhone1Activity extends BaseActivity {

    @BindView(R.id.tv_current_phone)
    TextView mTvCurrentPhone;
    @BindView(R.id.edt_code)
    EditText mEdtCode;
    @BindView(R.id.tv_code)
    TextView mTvCode;
    @BindView(R.id.rl_code)
    RelativeLayout mRlCode;
    @BindView(R.id.button)
    Button mButton;

    private String mSmsCode;


    private CountDownTimer mTimer = new CountDownTimer(6000 * 10, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            mTvCode.setText(millisUntilFinished / 1000 + "s");
        }

        @Override
        public void onFinish() {
            mRlCode.setEnabled(true);
            mTvCode.setText(R.string.resend);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initTitle() {

        defaultNavigationBar = new DefaultNavigationBar
                .Builder(this, (ViewGroup) findViewById(R.id.Rl_titleContainer))
                .setTitle("更换手机号码")
                .setStatusHeight(ChangePhone1Activity.this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_phone1;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

        mTvCurrentPhone.setText(String.format(getString(R.string.current_phone), Constant.PHONE));

    }

    @OnClick({R.id.button, R.id.rl_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:

                if (TextUtils.isEmpty(Constant.PHONE)) {
                    ToastUtils.showShortToast("手机号不能为空");
                    return;
                }

                if (TextUtils.isEmpty(mSmsCode)) {
                    ToastUtils.showShortToast("验证码不能为空");
                    return;
                }

                ChangePhone2Activity.startActivity(this, Constant.PHONE, mSmsCode);

                break;
            case R.id.rl_code:

                mRlCode.setEnabled(false);
                mTimer.start();

                getSmsCode(Constant.PHONE);
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void getSmsCode(String phone) {

        NetworkDao.getSmsCode(phone, "2", new onConnectionFinishLinstener() {

            @Override
            public void onSuccess(int code, Object result) {

                mSmsCode = ((SmsCodeEntity) result).smsCode;

                KLog.i("mSmsCode:%s", mSmsCode);
            }

            @Override
            public void onFail(int code, String result) {
                Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }
}
