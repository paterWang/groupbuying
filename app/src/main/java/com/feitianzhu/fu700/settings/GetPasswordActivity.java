package com.feitianzhu.fu700.settings;

import android.content.Context;
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

import com.feitianzhu.fu700.MainActivity;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.dao.NetworkDao;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.utils.EncryptUtils;
import com.feitianzhu.fu700.utils.SPUtils;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.socks.library.KLog;

import butterknife.BindView;


public class GetPasswordActivity extends BaseActivity implements View.OnClickListener {

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
    @BindView(R.id.tv_old_account)
    TextView mTvCurrentPhone;
    @BindView(R.id.tv_second_tips)
    TextView mTvSecondPwdTips;

    private int mType;

    /**
     * 找回登录密码
     */
    public static final int TYPE_GET_LOGIN_PWD = 0;
    /**
     * 找回二级密码密码
     */
    public static final int TYPE_GET_PAY_PASSWORD_PWD = 1;
    /**
     * 设置二级密码
     */
    public static final int TYPE_SET_PAY_PASSWORD_PWD = 2;


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
        return R.layout.activity_get_pwd;
    }

    @Override
    protected void initView() {
        mLayoutCode.setOnClickListener(this);
        mSignInButton.setOnClickListener(this);
        defaultNavigationBar = new DefaultNavigationBar
                .Builder(this, (ViewGroup) findViewById(R.id.Rl_titleContainer))
                .setTitle("找回登录密码")
                .setStatusHeight(GetPasswordActivity.this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }

    @Override
    protected void initData() {

        mTvCurrentPhone.setText(String.format(getString(R.string.current_phone), Constant.PHONE));
        Intent intent = getIntent();
        if (intent != null) {
            mType = intent.getIntExtra(Constant.INTENT_GET_SET_PSW_TYPE, 0);
        }
        KLog.i("mType: %d", mType);
        if (mType == TYPE_GET_LOGIN_PWD) { //找回登录密码
            mPasswordEditText1.setHint(R.string.hint_input_login_pwd);
            mTvSecondPwdTips.setVisibility(View.GONE);
        } else if (mType == TYPE_GET_PAY_PASSWORD_PWD) { //找回二级密码
            mPasswordEditText1.setHint(R.string.hint_input_second_pwd);
            mTvSecondPwdTips.setVisibility(View.GONE);
            defaultNavigationBar.changeTitleText(getString(R.string.get_second_pwd));
        } else if (mType == TYPE_SET_PAY_PASSWORD_PWD) {//设置二级密码
            mPasswordEditText1.setHint(R.string.hint_input_second_pwd);
            mTvSecondPwdTips.setVisibility(View.VISIBLE);
            defaultNavigationBar.changeTitleText(getString(R.string.set_second_pwd));
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.sign_in_button:

                String smsCode = mEditTextCode.getText().toString();
                String newPassword1 = mPasswordEditText1.getText().toString();
                String newPassword2 = mPasswordEditText2.getText().toString();

                if (TextUtils.isEmpty(smsCode)) {
                    ToastUtils.showShortToast(getString(R.string.please_input_code));
                    return;
                }
                if (TextUtils.isEmpty(newPassword1)) {
                    ToastUtils.showShortToast(getString(R.string.please_input_newpassword));
                    return;
                }
                if (TextUtils.isEmpty(newPassword2)) {
                    ToastUtils.showShortToast(getString(R.string.please_input_newpassword2));
                    return;
                }
                if (!newPassword1.equals(newPassword2)) {
                    ToastUtils.showShortToast(getString(R.string.please_input_check_password));
                    return;
                }
                if (newPassword1.length() < 6) {
                    Toast.makeText(this, R.string.please_input_six_password, Toast.LENGTH_SHORT).show();
                    return;
                }
                final String encryptPssword1 = EncryptUtils.encodePassword(newPassword1);
                String encryptPssword2 = EncryptUtils.encodePassword(newPassword2);

                if (mType == TYPE_GET_LOGIN_PWD) { //找回登录密码

                    NetworkDao.getLoginPwd(Constant.PHONE, smsCode, encryptPssword1, encryptPssword2, new onConnectionFinishLinstener() {
                        @Override
                        public void onSuccess(int code, Object result) {

                            ToastUtils.showShortToast(mContext, R.string.change_ok);
                            SPUtils.putString(mContext, Constant.SP_PHONE, Constant.PHONE);
                            SPUtils.putString(mContext, Constant.SP_PASSWORD, encryptPssword1);

                            startActivity(new Intent(mContext, MainActivity.class));
                            finish();
                        }

                        @Override
                        public void onFail(int code, String result) {
                            ToastUtils.showShortToast(result);
                        }
                    });
                } else if (mType == TYPE_GET_PAY_PASSWORD_PWD) { //找回二级密码

                    NetworkDao.getPayPwd(Constant.PHONE, smsCode, encryptPssword1, encryptPssword2, new onConnectionFinishLinstener() {
                        @Override
                        public void onSuccess(int code, Object result) {
                            if (Constant.mUserAuth!=null) {
                                Constant.mUserAuth.isPaypass = 1;
                            }
                            startMainActivity();
                        }

                        @Override
                        public void onFail(int code, String result) {
                            ToastUtils.showLongToast( result);
                        }
                    });

                } else if (mType == TYPE_SET_PAY_PASSWORD_PWD) {//设置二级密码

                    NetworkDao.setPayPassword(Constant.PHONE, smsCode, encryptPssword1, encryptPssword2, new onConnectionFinishLinstener() {
                        @Override
                        public void onSuccess(int code, Object result) {
                            if (Constant.mUserAuth!=null) {
                                Constant.mUserAuth.isPaypass = 1;
                            }
                            startMainActivity();
                        }

                        @Override
                        public void onFail(int code, String result) {
                            ToastUtils.showShortToast(result);
                        }
                    });
                }

                break;
            case R.id.rl_code:
                String phone = SPUtils.getString(this, Constant.SP_PHONE, "");
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(this, "手机号为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                mLayoutCode.setEnabled(false);
                mTimer.start();
                getValicationCode(phone);
                break;

        }
    }

    private void startMainActivity() {
        ToastUtils.showShortToast(mContext, R.string.change_ok);
//        startActivity(new Intent(mContext, MainActivity.class));
        finish();
    }

    /**
     * 获取验证码
     */
    private void getValicationCode(String phone) {

        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showShortToast(mContext, R.string.please_input_phone);
            return;
        }

        if (mType == TYPE_GET_LOGIN_PWD) { //找回登录密码
            getSmsCode(phone, "4");
        } else if (mType == TYPE_GET_PAY_PASSWORD_PWD) { //找回二级密码
            getSmsCode(phone, "6");
        } else if (mType == TYPE_SET_PAY_PASSWORD_PWD) {//设置二级密码
            getSmsCode(phone, "5");
        }

    }

    private void getSmsCode(String phone, String type) {
        NetworkDao.getSmsCode(phone, type, new onConnectionFinishLinstener() {
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

    public static void startActivity(Context context, int type) {
        Intent intent = new Intent(context, GetPasswordActivity.class);
        intent.putExtra(Constant.INTENT_GET_SET_PSW_TYPE, type);
        context.startActivity(intent);
    }
}

