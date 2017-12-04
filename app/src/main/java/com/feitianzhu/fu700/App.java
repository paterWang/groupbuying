package com.feitianzhu.fu700;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.widget.Toast;

import com.feitianzhu.fu700.common.Constant;
import com.mob.MobApplication;
import com.socks.library.KLog;
import com.umeng.message.IUmengCallback;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by dicallc on 2017/9/4 0004.
 */

public class App extends MobApplication {
    static App context;
    //true 代表测试服务器   false代表 生产服务器
public final static  boolean isDebug = false;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        ZXingLibrary.initDisplayOpinion(this);
        initOkUtils();
        //SDKInitializer.initialize(getApplicationContext());
        initPush();
    }

    private void initPush() {
        final PushAgent mPushAgent = PushAgent.getInstance(this);
        PushAgent.getInstance(this).onAppStart();

        //sdk开启通知声音
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
        mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
        mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);

        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Constant.DeviceToken_Value = deviceToken;
                KLog.e("TestPush: " + deviceToken);

                mPushAgent.enable(new IUmengCallback() {
                    @Override
                    public void onSuccess() {
                        KLog.i("启用推送成功");
                    }

                    @Override
                    public void onFailure(String s, String s1) {
                        KLog.e("启用推送失败: " + s + " ---- s1" + s1);
                    }
                });
            }

            @Override
            public void onFailure(String s, String s1) {
                Constant.DeviceToken_Value = "";
                KLog.e("TestPush" + s + s1);
            }
        });

        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
            }
        };

        mPushAgent.setNotificationClickHandler(notificationClickHandler);

//        UmengMessageHandler messageHandler = new UmengMessageHandler() {
//            @Override
//            public Notification getNotification(Context context, UMessage msg) {
//
//                EventBus.getDefault().post(NotifyEvent.HAS_NOTIFY);
////                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
////                RemoteViews myNotificationView = new RemoteViews(context.getPackageName(), R.layout.notification_view);
////                myNotificationView.setTextViewText(R.id.notification_title, msg.title);
////                myNotificationView.setTextViewText(R.id.notification_text, msg.text);
////                myNotificationView.setImageViewBitmap(R.id.notification_large_icon, getLargeIcon(context, msg));
////                myNotificationView.setImageViewResource(R.id.notification_small_icon, getSmallIconId(context, msg));
////                builder.setContent(myNotificationView);
////                Notification mNotification = builder.build();
////                //由于Android v4包的bug，在2.3及以下系统，Builder创建出来的Notification，并没有设置RemoteView，故需要添加此代码
////                mNotification.contentView = myNotificationView;
////                return mNotification;
//                return super.getNotification(context, msg);
//
//            }
//        };

//        mPushAgent.setMessageHandler(messageHandler);
        mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);

    }


    private void initOkUtils() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("feitianzhu"))
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

    public static Context getAppContext() {
        return context;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
