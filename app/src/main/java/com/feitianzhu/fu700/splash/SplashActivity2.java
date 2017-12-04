package com.feitianzhu.fu700.splash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.login.LoginActivity;
import com.feitianzhu.fu700.login.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity2 extends AppCompatActivity {

    @BindView(R.id.imageview)
    ImageView mImageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_register, R.id.btn_login})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                RegisterActivity.startActivity(this);
                finish();
                break;
            case R.id.btn_login:
                LoginActivity.startActivity(this);
                finish();
                break;
        }
    }

    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
            System.exit(0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
