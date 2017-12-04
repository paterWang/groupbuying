package com.feitianzhu.fu700.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.account)
    EditText mAccountLayout;
    @BindView(R.id.password1)
    EditText mPasswordEditText1;
    @BindView(R.id.sign_in_button)
    Button mSignInButton;

    @BindView(R.id.tv_forget_password)
    TextView mForgetLayout;
    @BindView(R.id.tv_regist)
    TextView mRegister;

    private String mAccount;
    private String mPassword;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

        mSignInButton.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        mForgetLayout.setOnClickListener(this);

        mRegister.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mForgetLayout.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        defaultNavigationBar = new DefaultNavigationBar
                .Builder(this, (ViewGroup) findViewById(R.id.Rl_titleContainer))
                .setTitle("登录")
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .setStatusHeight(LoginActivity.this)
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);

    }

    @Override
    protected void initData() {

        mAccount = SPUtils.getString(this, Constant.SP_PHONE);
        showLoginLayout();
    }

    private void resetEditText() {
        mAccountLayout.setText("");
        mPasswordEditText1.setText("");
    }

    private void showLoginLayout() {

        resetEditText();
        mForgetLayout.setVisibility(View.VISIBLE);
        mRegister.setVisibility(View.VISIBLE);
        mRegister.setText(R.string.no_account);
        mSignInButton.setText(R.string.sign_in);
        defaultNavigationBar.changeTitleText(getString(R.string.sign_in));
        mAccountLayout.setText(mAccount);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.sign_in_button:
                login();
                break;
            case R.id.tv_regist:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.tv_forget_password:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;
        }
    }


    private void login() {

        mAccount = stringTrim(mAccountLayout);
        mPassword = stringTrim(mPasswordEditText1);


        if (TextUtils.isEmpty(mAccount)) {
            Toast.makeText(this, R.string.please_input_phone, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(mPassword)) {
            Toast.makeText(this, R.string.please_input_password, Toast.LENGTH_SHORT).show();
            return;
        }

        String base64Ps = EncryptUtils.encodePassword(mPassword);

        NetworkDao.login(this, mAccount, base64Ps, new onConnectionFinishLinstener() {
            @Override
            public void onSuccess(int code, Object result) {

                NetworkDao.getUserInfo(new onConnectionFinishLinstener() {
                    @Override
                    public void onSuccess(int code, Object result) {
//                        startMainActivity();
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            LoginActivity.this.finish();
                    }

                    @Override
                    public void onFail(int code, String result) {
                        Log.e("Test","--------------result------------->"+result);
                        ToastUtils.showShortToast(result);
                        startMainActivity();
                    }
                });
            }

            @Override
            public void onFail(int code, String result) {
                Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
                KLog.e(result);
            }
        });
    }

    private void startMainActivity() {
        startActivity(new Intent(mContext, MainActivity.class));
        LoginActivity.this.finish();
    }

    private String stringTrim(EditText editText) {
        return editText.getText().toString().trim();
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }
}

