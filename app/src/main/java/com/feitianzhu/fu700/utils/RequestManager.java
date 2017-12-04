package com.feitianzhu.fu700.utils;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by dicallc on 2017/9/7 0007.
 */

public class RequestManager {

  private static final String TAG = "RequestManager";
  private final OkHttpClient mOkHttpClient;
  /**
   * 全局处理子线程和M主线程通信
   */
  private final Handler okHttpHandler;
  private static volatile RequestManager mInstance;//单利引用
  /**
   * 初始化RequestManager
   */
  public RequestManager(Context context) {
    //初始化OkHttpClient
    mOkHttpClient = new OkHttpClient().newBuilder().connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
        .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
        .writeTimeout(10, TimeUnit.SECONDS)//设置写入超时时间
        .build();
    //初始化Handler
    okHttpHandler = new Handler(context.getMainLooper());
  }
  /**
   * 获取单例引用
   *
   * @return
   */
  public static RequestManager getInstance(Context context) {
    RequestManager inst = mInstance;
    if (inst == null) {
      synchronized (RequestManager.class) {
        inst = mInstance;
        if (inst == null) {
          inst = new RequestManager(context.getApplicationContext());
          mInstance = inst;
        }
      }
    }
    return inst;
  }

  /**
   * okHttp post异步请求表单提交
   *
   * @param actionUrl 接口地址
   * @param paramsMap 请求参数
   * @param callBack 请求返回数据回调
   * @param <T> 数据泛型
   */
  private <T> Call requestPostByAsynWithForm(String actionUrl, HashMap<String, String> paramsMap,
      final ReqCallBack<T> callBack) {
    try {
      FormBody.Builder builder = new FormBody.Builder();
      for (String key : paramsMap.keySet()) {
        builder.add(key, paramsMap.get(key));
      }
      RequestBody formBody = builder.build();
      //String requestUrl = String.format("%s/%s", BASE_URL, actionUrl);
      final Request request = addHeaders().url(actionUrl).post(formBody).build();
      final Call call = mOkHttpClient.newCall(request);
      call.enqueue(new Callback() {
        @Override public void onFailure(Call call, IOException e) {
          failedCallBack("访问失败", callBack);
          Log.e(TAG, e.toString());
        }

        @Override public void onResponse(Call call, Response response) throws IOException {
          if (response.isSuccessful()) {
            String string = response.body().string();
            Log.e(TAG, "response ----->" + string);
            successCallBack((T) string, callBack);
          } else {
            failedCallBack("服务器错误", callBack);
          }
        }
      });
      return call;
    } catch (Exception e) {
      Log.e(TAG, e.toString());
    }
    return null;
  }

  /**
   * 统一为请求添加头信息
   */
  private Request.Builder addHeaders() {
    Request.Builder builder = new Request.Builder().addHeader("Connection", "keep-alive")
        .addHeader("platform", "2")
        .addHeader("phoneModel", Build.MODEL)
        .addHeader("systemVersion", Build.VERSION.RELEASE)
        .addHeader("appVersion", "3.2.0");
    return builder;
  }

  /**
   * 统一处理失败信息
   */
  private <T> void failedCallBack(final String errorMsg, final ReqCallBack<T> callBack) {
    okHttpHandler.post(new Runnable() {
      @Override public void run() {
        if (callBack != null) {
          callBack.onReqFailed(errorMsg);
        }
      }
    });
  }

  /**
   * 统一同意处理成功信息
   */
  private <T> void successCallBack(final T result, final ReqCallBack<T> callBack) {
    okHttpHandler.post(new Runnable() {
      @Override public void run() {
        if (callBack != null) {
          callBack.onReqSuccess(result);
        }
      }
    });
  }

  public interface ReqCallBack<T> {
    /**
     * 响应成功
     */
    void onReqSuccess(T result);

    /**
     * 响应失败
     */
    void onReqFailed(String errorMsg);
  }
}
