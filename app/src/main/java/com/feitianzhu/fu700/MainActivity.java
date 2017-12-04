package com.feitianzhu.fu700;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.common.base.SFActivity;
import com.feitianzhu.fu700.home.HomeFragment;
import com.feitianzhu.fu700.me.MeFragment;
import com.feitianzhu.fu700.me.ui.CollectMoneyActivity;
import com.feitianzhu.fu700.me.ui.PushServiceActivity;
import com.feitianzhu.fu700.me.ui.ScannerActivity;
import com.feitianzhu.fu700.me.ui.ShopRecordActivity;
import com.feitianzhu.fu700.message.MessageFragment;
import com.feitianzhu.fu700.model.LocationPost;
import com.feitianzhu.fu700.model.MyPoint;
import com.feitianzhu.fu700.model.UpdateAppModel;
import com.feitianzhu.fu700.payforme.PayForMeActivity;
import com.feitianzhu.fu700.shop.ShopDao;
import com.feitianzhu.fu700.shop.ShopFragment;
import com.feitianzhu.fu700.shop.ShopHelp;
import com.feitianzhu.fu700.shop.ShopHelpTwo;
import com.feitianzhu.fu700.splash.SplashDao;
import com.feitianzhu.fu700.utils.AnimationUtils;
import com.feitianzhu.fu700.utils.LocationUtils;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.feitianzhu.fu700.utils.UpdateAppHttpUtil;
import com.feitianzhu.fu700.utils.VersionManagementUtil;
import com.google.gson.Gson;
import com.socks.library.KLog;
import com.umeng.message.IUmengCallback;
import com.umeng.message.PushAgent;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;
import com.vector.update_app.utils.AppUpdateUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.feitianzhu.fu700.common.Constant.Common_HEADER;
import static com.feitianzhu.fu700.common.Constant.UAPDATE;

public class MainActivity extends SFActivity implements View.OnClickListener {

    @BindView(R.id.txt_index)
    TextView mTxtIndex;
    @BindView(R.id.txt_shop)
    TextView mTxtShop;
    @BindView(R.id.img_fu)
    ImageView mImgFu;
    @BindView(R.id.txt_jiaoliu)
    TextView mTxtJiaoliu;
    @BindView(R.id.txt_me)
    TextView mTxtMe;
    @BindView(R.id.tab_menu)
    LinearLayout mTabMenu;
    @BindView(R.id.div_tab_bar)
    View mDivTabBar;
    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;
    @BindView(R.id.img_index)
    ImageView mImgIndex;
    @BindView(R.id.img_shop)
    ImageView mImgShop;
    @BindView(R.id.img_jiaoliu)
    ImageView mImgJiaoliu;
    @BindView(R.id.img_me)
    ImageView mImgMe;
    @BindView(R.id.ly_index)
    LinearLayout mLyIndex;
    @BindView(R.id.ly_shop)
    LinearLayout mLyShop;
    @BindView(R.id.ly_jiaoliu)
    LinearLayout mLyJiaoliu;
    @BindView(R.id.ly_me)
    LinearLayout mLyMe;
    private HomeFragment mHomeFragment;
    private ShopFragment mShopFragment;
    private MessageFragment mMessageFragment;
    private MeFragment mMeFragment;
    private FragmentTransaction mTransaction;
    private ObjectAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_view);
        ButterKnife.bind(this);
        LocationUtils.getInstance().start();
        initView();
        initData();
        showFragment();
        SplashDao.postTokie();
    }

    private void initData() {
        updateDiy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true) //在ui线程执行
    public void onLocationDataSynEvent(LocationPost mMoel) {
        KLog.e("onLocationDataSynEvent" + Constant.mPoint);
        if (!mMoel.isLocationed || null == Constant.mPoint || 0 == Constant.mPoint.longitude) {
            ToastUtils.showShortToast("当前无法定位，选择城市为北京");
            Constant.mPoint = new MyPoint(116.232934, 39.541997);
            Constant.mCity = "北京";
        } else {
            KLog.e("用户经纬度" + Constant.mPoint);
        }
    }

    private void showFragment() {
        mTransaction = getSupportFragmentManager().beginTransaction();
        mHomeFragment = HomeFragment.newInstance();
        mTransaction.add(R.id.fragment_container, mHomeFragment);
        mTransaction.commit();
    }

    private void initView() {
        mLyIndex.setOnClickListener(this);
        mTxtIndex.setSelected(true);
        mImgIndex.setSelected(true);
        mLyShop.setOnClickListener(this);
        mLyJiaoliu.setOnClickListener(this);
        mLyMe.setOnClickListener(this);
        mImgFu.setOnClickListener(this);
        initShakeAnimation();
    }

    private void initShakeAnimation() {
        animator = AnimationUtils.tada(mImgFu);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.start();
    }


    @Override
    public void onClick(View v) {
        mTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(mTransaction);
        switch (v.getId()) {
            case R.id.ly_index:
                selected();
                mTxtIndex.setSelected(true);
                mImgIndex.setSelected(true);
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.newInstance();
                    mTransaction.add(R.id.fragment_container, mHomeFragment);
                } else {
                    mTransaction.show(mHomeFragment);
                }
                mTransaction.commit();
                break;

            case R.id.ly_shop:
                selected();
                mTxtShop.setSelected(true);
                mImgShop.setSelected(true);
                if (mShopFragment == null) {
                    mShopFragment = ShopFragment.newInstance("", "");
                    mTransaction.add(R.id.fragment_container, mShopFragment);
                } else {
                    mTransaction.show(mShopFragment);
                }
                mTransaction.commit();
                ShopDao.loadUserAuthImpl();
                break;

            case R.id.ly_jiaoliu:
                selected();
                mTxtJiaoliu.setSelected(true);
                mImgJiaoliu.setSelected(true);
                if (mMessageFragment == null) {
                    mMessageFragment = MessageFragment.newInstance("", "");
                    mTransaction.add(R.id.fragment_container, mMessageFragment);
                } else {
                    mTransaction.show(mMessageFragment);
                }
                mTransaction.commit();
                break;

            case R.id.ly_me:
                selected();
                mTxtMe.setSelected(true);
                mImgMe.setSelected(true);
                if (mMeFragment == null) {
                    mMeFragment = MeFragment.newInstance("", "");
                    mTransaction.add(R.id.fragment_container, mMeFragment);
                } else {
                    mTransaction.show(mMeFragment);
                }
                if (mTransaction != null) {
                    mTransaction.commitAllowingStateLoss();
                }

                break;
            case R.id.img_fu:
                setDialog();
                break;
        }
    }

    //隐藏所有Fragment
    public void hideAllFragment(FragmentTransaction transaction) {
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment);
        }
        if (mShopFragment != null) {
            transaction.hide(mShopFragment);
        }
        if (mMessageFragment != null) {
            transaction.hide(mMessageFragment);
        }
        if (mMeFragment != null) {
            transaction.hide(mMeFragment);
        }
    }

    //重置所有文本的选中状态
    public void selected() {
        mTxtIndex.setSelected(false);
        mImgIndex.setSelected(false);

        mTxtShop.setSelected(false);
        mImgShop.setSelected(false);

        mTxtJiaoliu.setSelected(false);
        mImgJiaoliu.setSelected(false);

        mTxtMe.setSelected(false);
        mImgMe.setSelected(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopAnimation();
    }

    private void stopAnimation() {
        if (null != animator)
            animator.cancel();
    }

    private void setDialog() {
        // TODO: 2017/9/25 获取授权信息
        stopAnimation();
        ShopDao.loadUserAuthImpl();
        final Dialog mCameraDialog = new Dialog(this, R.style.BottomDialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.fu_dialog, null);
        ImageView mView = (ImageView) root.findViewById(R.id.cancel);
        ImageView iv_weiwomaidan = (ImageView) root.findViewById(R.id.iv_weiwomaidan);
        ImageView iv_saoyisao = (ImageView) root.findViewById(R.id.iv_saoyisao);
        ImageView iv_woyaoshoukuan = (ImageView) root.findViewById(R.id.iv_woyaoshoukuan);
        ImageView iv_shangjialudan = (ImageView) root.findViewById(R.id.iv_shangjialudan);
        ImageView iv_fabufuwu = (ImageView) root.findViewById(R.id.iv_fabufuwu);

        /**
         * 为我买单
         */
        iv_weiwomaidan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PayForMeActivity.class);
                startActivity(intent);
                mCameraDialog.dismiss();
            }
        });
        /**
         * 扫一扫
         */
        iv_saoyisao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
                mCameraDialog.dismiss();
            }
        });
        /**
         * 我要收款
         */
        iv_woyaoshoukuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent collIntent = new Intent(MainActivity.this, CollectMoneyActivity.class);
                ShopHelp.veriUserShopJumpActivity(MainActivity.this, collIntent);
            }
        });
        /**
         * 商家录单
         */
        iv_shangjialudan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2017/9/25 第二步 
                Intent intent = new Intent(MainActivity.this, ShopRecordActivity.class);
                ShopHelpTwo.veriUserShopJumpActivity(MainActivity.this, intent);
//                startActivity(intent);
            }
        });
        /**
         * 发布服务
         */
        iv_fabufuwu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pushIntent = new Intent(MainActivity.this, PushServiceActivity.class);
                ShopHelp.veriUserShopJumpActivity(MainActivity.this, pushIntent);
            }
        });

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCameraDialog.dismiss();
            }
        });
        //初始化视图
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        //        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
    }

    private void requestPermission() {
        AndPermission.with(this)
                .requestCode(200)
                .permission(
                        // 多个权限，以数组的形式传入。
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.CAMERA
                )
                .callback(
                        new RationaleListener() {
                            @Override
                            public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                                // 此对话框可以自定义，调用rationale.resume()就可以继续申请。
                                AndPermission.rationaleDialog(App.getAppContext(), rationale).show();
                            }
                        }
                )
                .callback(listener)
                .start();
    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。

            // 这里的requestCode就是申请时设置的requestCode。
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
            if (requestCode == 200) {
                Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
                startActivity(intent);
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == 200) {
                Toast.makeText(MainActivity.this, "请求权限失败!", Toast.LENGTH_SHORT).show();
            }
        }
    };


    public void updateDiy() {
        final String versionName = AppUpdateUtils.getVersionName(this);
        new UpdateAppManager
                .Builder()
                .setActivity(this)
                .setHttpManager(new UpdateAppHttpUtil())
                .setUpdateUrl(Common_HEADER + UAPDATE)
                .setPost(false)
                .hideDialogOnDownloading(false)
                .setThemeColor(0xffffac5d)
                .build()
                .checkNewApp(new UpdateCallback() {
                    /**
                     * 解析json,自定义协议
                     *
                     * @param json 服务器返回的json
                     * @return UpdateAppBean
                     */
                    @Override
                    protected UpdateAppBean parseJson(String json) {
                        UpdateAppBean updateAppBean = new UpdateAppBean();
                        UpdateAppModel updateAppModel = new Gson().fromJson(json, UpdateAppModel.class);
                        boolean constraint = false;
                        String update = "No";
                        if (VersionManagementUtil.VersionComparison(updateAppModel.versionName + "", versionName) == 1) {
                            update = "Yes";
                        } else {
                            update = "No";
                        }
                        if ("1".equals(updateAppModel.isForceUpdate)) {
                            constraint = true;
                        } else {
                            constraint = false;
                        }
                        updateAppBean
                                //（必须）是否更新Yes,No
                                .setUpdate(update)
                                //（必须）新版本号，
                                .setNewVersion(updateAppModel.versionName + "")
//                                .setNewVer、sion("1.1.0"+ "")
                                //（必须）下载地址
                                .setApkFileUrl(updateAppModel.downloadUrl)
//                                    .setUpdateLog("测试")
                                .setUpdateLog(updateAppModel.updateDesc)
//                                    .setUpdateLog("今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说\r\n")
                                //大小，不设置不显示大小，可以不设置
                                .setTargetSize(updateAppModel.packSize + "Mb")
                                //是否强制更新，可以不设置
                                .setConstraint(constraint);
                        return updateAppBean;
                    }

                    @Override
                    protected void hasNewApp(UpdateAppBean updateApp, UpdateAppManager updateAppManager) {
                        updateAppManager.showDialogFragment();
                    }

                    /**
                     * 网络请求之前
                     */
                    @Override
                    public void onBefore() {
//                showloadDialog("");
//                CProgressDialogUtils.showProgressDialog(JavaActivity.this);
                    }

                    /**
                     * 网路请求之后
                     */
                    @Override
                    public void onAfter() {
//                CProgressDialogUtils.cancelProgressDialog(JavaActivity.this);
                    }

                    /**
                     * 没有新版本
                     */
                    @Override
                    public void noNewApp() {
                        KLog.e("没有新版本");
//                        ToastUtils.showShortToast("没有新版本");
                    }
                });

    }

    //退出时的时间
    private long mExitTime;

    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtils.showShortToast("再按一次退出" + getString(R.string.app_name));
            mExitTime = System.currentTimeMillis();
        } else {
            PushAgent mPushAgent = PushAgent.getInstance(this);
            mPushAgent.disable(new IUmengCallback() {
                @Override
                public void onSuccess() {
                    KLog.i("关闭推送成功");
                    finish();
                    System.exit(0);
                }

                @Override
                public void onFailure(String s, String s1) {
                    KLog.e("关闭推送失败: " + s + " ---- s1" + s1);
                    finish();
                    System.exit(0);
                }
            });
        }
    }
}
