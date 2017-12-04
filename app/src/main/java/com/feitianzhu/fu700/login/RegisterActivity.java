package com.feitianzhu.fu700.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.dao.NetworkDao;
import com.feitianzhu.fu700.home.WebViewActivity;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.utils.EncryptUtils;
import com.feitianzhu.fu700.utils.Urls;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;


public class RegisterActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.account)
    EditText mAccountLayout;
    @BindView(R.id.password1)
    EditText mPasswordEditText1;
    @BindView(R.id.sign_in_button)
    Button mSignInButton;
    @BindView(R.id.edt_code)
    EditText mEditTextCode;
    @BindView(R.id.et_parentId)
    EditText mEditTextParentId;
    @BindView(R.id.tv_code)
    TextView mTextViewCode;
    @BindView(R.id.rl_code)
    RelativeLayout mLayoutCode;

    //    @BindView(R.id.password2)
//    EditText mPasswordEditText2;
    @BindView(R.id.ll_code)
    LinearLayout mCodeLayout;
    @BindView(R.id.tv_forget_password)
    TextView mForgetLayout;
    @BindView(R.id.tv_regist)
    TextView mRegister;

    @BindView(R.id.ll_protocol)
    LinearLayout mProtocolLayout;
    @BindView(R.id.tv_protocol)
    TextView mTvProtocol;
    @BindView(R.id.cb_protocol)
    CheckBox mCheckBox;

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
    private String mAccount;
    private String mPassword;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {

        mLayoutCode.setOnClickListener(this);
        mSignInButton.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        mForgetLayout.setOnClickListener(this);
        mTvProtocol.setOnClickListener(this);
        mCheckBox.setOnCheckedChangeListener(this);

        mRegister.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mForgetLayout.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        defaultNavigationBar = new DefaultNavigationBar
                .Builder(this, (ViewGroup) findViewById(R.id.Rl_titleContainer))
                .setTitle("注册")
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .setStatusHeight(RegisterActivity.this)
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);

    }

    @Override
    protected void initData() {
        showRegisterLayout();
    }

    private void showRegisterLayout() {

        resetEditText();
        mForgetLayout.setVisibility(View.GONE);
        mCodeLayout.setVisibility(View.VISIBLE);
        mEditTextParentId.setVisibility(View.VISIBLE);
        mRegister.setVisibility(View.GONE);
        mSignInButton.setText(R.string.no_account);
        defaultNavigationBar.changeTitleText(getString(R.string.no_account));
        mProtocolLayout.setVisibility(View.VISIBLE);
        mCheckBox.setChecked(true);
    }

    private void resetEditText() {
        mAccountLayout.setText("");
        mEditTextCode.setText("");
        mPasswordEditText1.setText("");
        mEditTextParentId.setText("");
    }

    private void showLoginLayout() {

        resetEditText();
        mForgetLayout.setVisibility(View.VISIBLE);
        mRegister.setVisibility(View.VISIBLE);
        mCodeLayout.setVisibility(View.GONE);
        mEditTextParentId.setVisibility(View.GONE);
        mRegister.setText(R.string.no_account);
        mSignInButton.setText(R.string.sign_in);
        defaultNavigationBar.changeTitleText(getString(R.string.sign_in));
        mProtocolLayout.setVisibility(View.GONE);
        mAccountLayout.setText(mAccount);
    }


    private void register(final String account, final String password, String code, String parentId) {

        OkHttpUtils
                .post()
                .url(Urls.REGISTER)
                .addParams("phone", account)
                .addParams("password", password)
                .addParams("parentId", parentId)
                .addParams("smsCode", code)
                .addParams("nickName", "")
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(String mData, Response response, int id) throws Exception {
                        return mData;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        KLog.e(e);
                        if ("数据为空".equals(e.getMessage())) {
                            Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                            finish();
//                            resetEditText();
//                            showLoginLayout();
                        } else {
                            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {
                        KLog.i("response:%s", response);
                        Toast.makeText(mContext, "注册成功", Toast.LENGTH_SHORT).show();
                        LoginActivity.startActivity(mContext);
                        finish();
                    }
                });

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rl_code:
                String phone = mAccountLayout.getText().toString().trim();
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
            case R.id.sign_in_button:
                register();
                break;
//            case R.id.tv_regist:
//                    showRegisterLayout();
//                break;
//            case R.id.tv_forget_password:
//                startActivity(new Intent(this, ForgetPasswordActivity.class));
//                break;
            case R.id.tv_protocol:
                WebViewActivity.startActivity(this, Urls.H5_REGISTER_PROTOCOL, "FU700-惠宝服务条款");
                break;

        }
    }


    private void register() {

        mAccount = stringTrim(mAccountLayout);
        mPassword = stringTrim(mPasswordEditText1);

        String code = stringTrim(mEditTextCode);
        String parentId = stringTrim(mEditTextParentId);

        if (TextUtils.isEmpty(mAccount)) {
            Toast.makeText(this, R.string.please_input_phone, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(this, R.string.please_input_code, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(mPassword)) {
            Toast.makeText(this, R.string.please_input_password, Toast.LENGTH_SHORT).show();
            return;
        }
        if (mPassword.length() < 6) {
            Toast.makeText(this, R.string.please_input_six_password, Toast.LENGTH_SHORT).show();
            return;
        }
        String base64Ps = EncryptUtils.encodePassword(mPassword);
        register(mAccount, base64Ps, code, parentId);
    }

    private String stringTrim(EditText editText) {
        return editText.getText().toString().trim();
    }

    /**
     * 获取验证码
     */
    private void getValicationCode(String phone) {

        NetworkDao.getSmsCode(phone, "1", new onConnectionFinishLinstener() {

            @Override
            public void onSuccess(int code, Object result) {

                KLog.i("response:%s", result.toString());
            }

            @Override
            public void onFail(int code, String result) {
                Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (buttonView.getId() == R.id.cb_protocol) {
            if (!isChecked) {
                mSignInButton.setEnabled(false);
                mSignInButton.setBackgroundResource(R.drawable.button_shape_gray);
            } else {
                mSignInButton.setEnabled(true);
                mSignInButton.setBackgroundResource(R.drawable.button_shape_blue);
            }
        }
    }
}

