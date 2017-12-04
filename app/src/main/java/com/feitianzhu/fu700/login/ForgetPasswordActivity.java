package com.feitianzhu.fu700.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.dao.NetworkDao;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.utils.EncryptUtils;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.socks.library.KLog;

import butterknife.BindView;


public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.edt_phone)
    EditText mEtPhone;
    @BindView(R.id.password1)
    EditText mPasswordEditText1;
    @BindView(R.id.sign_in_button)
    Button mSignInButton;
    @BindView(R.id.login_form)
    ScrollView mLoginFormView;
    @BindView(R.id.edt_code)
    EditText mEditTextCode;
    @BindView(R.id.tv_code)
    TextView mTextViewCode;
    @BindView(R.id.rl_code)
    RelativeLayout mLayoutCode;

    @BindView(R.id.password2)
    EditText mPasswordEditText2;
    @BindView(R.id.ll_code)
    LinearLayout mCodeLayout;


    private CountDownTimer mTimer = new CountDownTimer(6000 * 10, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            mTextViewCode.setText(millisUntilFinished / 1000 + "s");
        }

        @Override
        public void onFinish() {
            mLayoutCode.setEnabled(true);
            mTextViewCode.setText(R.string.resend);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_pwd;
    }

    @Override
    protected void initView() {
        mLayoutCode.setOnClickListener(this);
        mSignInButton.setOnClickListener(this);
        defaultNavigationBar = new DefaultNavigationBar
                .Builder(this, (ViewGroup) findViewById(R.id.Rl_titleContainer))
                .setTitle("找回密码")
                .setStatusHeight(ForgetPasswordActivity.this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }

    @Override
    protected void initData() {


    }


    @Override
    public void onClick(View v) {

        String phone = mEtPhone.getText().toString().trim();
        String smsCode = mEditTextCode.getText().toString().trim();
        String password1 = mPasswordEditText1.getText().toString().trim();
        String password2 = mPasswordEditText2.getText().toString().trim();

        switch (v.getId()) {

            case R.id.sign_in_button:

                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(this, R.string.please_input_phone, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(smsCode)) {
                    Toast.makeText(this, R.string.please_input_code, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password1)) {
                    Toast.makeText(this, R.string.please_input_newpassword, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password2)) {
                    Toast.makeText(this, R.string.please_input_newpassword2, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password1.equals(password2)) {
                    ToastUtils.showShortToast(getString(R.string.please_input_check_password));
                    return;
                }


                NetworkDao.getLoginPwd(phone, smsCode, EncryptUtils.encodePassword(password1), EncryptUtils.encodePassword(password2), new onConnectionFinishLinstener() {
                    @Override
                    public void onSuccess(int code, Object result) {

                        ToastUtils.showShortToast(mContext, R.string.change_ok);

                        startActivity(new Intent(mContext, LoginActivity.class));
                        finish();
                    }

                    @Override
                    public void onFail(int code, String result) {
                        ToastUtils.showShortToast(mContext, R.string.change_failure);
                    }
                });


                break;
            case R.id.rl_code:

                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(this, R.string.please_input_phone, Toast.LENGTH_SHORT).show();
                    return;
                }
//                if (!RegexUtils.isChinaPhoneLegal(phone)) {
//                    Toast.makeText(this, R.string.please_input_correct_phone, Toast.LENGTH_SHORT).show();
//                    return;
//                }
                mLayoutCode.setEnabled(false);
                mTimer.start();
                getValicationCode(phone);
                break;

        }
    }

    /**
     * 获取验证码
     */
    private void getValicationCode(String phone) {

        NetworkDao.getSmsCode(phone, "4", new onConnectionFinishLinstener() {
            @Override
            public void onSuccess(int code, Object result) {

            }

            @Override
            public void onFail(int code, String result) {
                Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
                KLog.e(result);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }
}

