package com.feitianzhu.fu700.splash;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.feitianzhu.fu700.MainActivity;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.dao.NetworkDao;
import com.feitianzhu.fu700.login.LoginActivity;
import com.feitianzhu.fu700.utils.LocationUtils;
import com.feitianzhu.fu700.utils.SPUtils;
import com.feitianzhu.fu700.view.CustomVideoView;
import com.socks.library.KLog;
import com.tencent.bugly.crashreport.CrashReport;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.videoview)
    CustomVideoView mVideoView;
    private static final int REQUEST_CODE_PERMISSION = 100;
    @BindView(R.id.btn)
    TextView mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initPermision();
    }

    private void doLogin() {

        mVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video));
        //播放
        mVideoView.start();
        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                KLog.e("视频播放失败");
                realLogin();
                return true;
            }
        });
        //循环播放
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                realLogin();
            }
        });

    }

    private void realLogin() {
        final String phone = SPUtils.getString(SplashActivity.this, Constant.SP_PHONE);
        final String password = SPUtils.getString(SplashActivity.this, Constant.SP_PASSWORD);

        KLog.i("phone: " + phone );

        if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password)) {

            NetworkDao.login(SplashActivity.this, phone, password, new onConnectionFinishLinstener() {
                @Override
                public void onSuccess(int code, Object result) {

                    NetworkDao.getUserInfo(new onConnectionFinishLinstener() {
                        @Override
                        public void onSuccess(int code, Object result) {

                            startMainActivity();

                        }

                        @Override
                        public void onFail(int code, String result) {
                            startMainActivity();

                        }
                    });

                }

                @Override
                public void onFail(int code, String result) {
                    Toast.makeText(SplashActivity.this, result, Toast.LENGTH_SHORT).show();
                    KLog.e(result);
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            });
        } else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }
    }


    @Override
    protected void onStop() {
        realLogin();
        super.onStop();
    }

    private void startMainActivity() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    private void initPermision() {
        AndPermission.with(SplashActivity.this)
                .requestCode(REQUEST_CODE_PERMISSION)
                .permission(Permission.LOCATION, Permission.STORAGE)
                .callback(permissionListener)
                // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框；
                // 这样避免用户勾选不再提示，导致以后无法申请权限。
                // 你也可以不设置。
                .rationale(rationaleListener)
                .start();
        CrashReport.initCrashReport(getApplicationContext(), "dab3904ceb", false);

    }

    private void doSomeThing() {
        doLogin();
        LocationUtils.getInstance().start();
    }

    private static final int REQUEST_CODE_SETTING = 300;

    /**
     * 回调监听。
     */
    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, @NonNull List<String> grantPermissions) {
            switch (requestCode) {
                case REQUEST_CODE_PERMISSION: {
                    mBtn.setVisibility(View.VISIBLE);
                    doSomeThing();
                    break;
                }
            }
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            switch (requestCode) {
                case REQUEST_CODE_PERMISSION: {
                    mBtn.setVisibility(View.GONE);
                    finish();
                    return;
                }
            }
            if (AndPermission.hasAlwaysDeniedPermission(SplashActivity.this, deniedPermissions)) {
                // 第一种：用默认的提示语。
                AndPermission.defaultSettingDialog(SplashActivity.this, REQUEST_CODE_SETTING).show();
            }
        }
    };

    /**
     * Rationale支持，这里自定义对话框。
     */
    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            // 这里使用自定义对话框，如果不想自定义，用AndPermission默认对话框：
            AndPermission.rationaleDialog(SplashActivity.this, rationale).show();

        }
    };

    @OnClick(R.id.btn)
    public void onClick() {
        mVideoView.stopPlayback();
        realLogin();
    }
}
