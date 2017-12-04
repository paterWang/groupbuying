package com.zhy.http.okhttp;

import android.text.TextUtils;
import android.util.Log;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.HeadBuilder;
import com.zhy.http.okhttp.builder.OtherRequestBuilder;
import com.zhy.http.okhttp.builder.PostFileBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.builder.PostStringBuilder;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.request.RequestCall;
import com.zhy.http.okhttp.utils.Platform;
import java.io.IOException;
import java.util.concurrent.Executor;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.json.JSONObject;

public class OkHttpUtils {
  public static final long DEFAULT_MILLISECONDS = 10_000L;
  private volatile static OkHttpUtils mInstance;
  private static boolean mIsdown=false;
  private OkHttpClient mOkHttpClient;
  private Platform mPlatform;

  public OkHttpUtils(OkHttpClient okHttpClient) {
    if (okHttpClient == null) {
      mOkHttpClient = new OkHttpClient();
    } else {
      mOkHttpClient = okHttpClient;
    }

    mPlatform = Platform.get();
  }

  public static OkHttpUtils initClient(OkHttpClient okHttpClient) {
    if (mInstance == null) {
      synchronized (OkHttpUtils.class) {
        if (mInstance == null) {
          mInstance = new OkHttpUtils(okHttpClient);
        }
      }
    }
    return mInstance;
  }

  public static OkHttpUtils getInstance() {
    return initClient(null);
  }

  public Executor getDelivery() {
    return mPlatform.defaultCallbackExecutor();
  }

  public OkHttpClient getOkHttpClient() {
    return mOkHttpClient;
  }

  public static GetBuilder get() {
    mIsdown=false;
    return new GetBuilder();
  }
  public static GetBuilder get(boolean isdown) {
    mIsdown=isdown;
    return new GetBuilder();
  }

  public static PostStringBuilder postString() {
    return new PostStringBuilder();
  }

  public static PostFileBuilder postFile() {
    return new PostFileBuilder();
  }

  public static PostFormBuilder post() {
    return new PostFormBuilder();
  }

  public static OtherRequestBuilder put() {
    return new OtherRequestBuilder(METHOD.PUT);
  }

  public static HeadBuilder head() {
    return new HeadBuilder();
  }

  public static OtherRequestBuilder delete() {
    return new OtherRequestBuilder(METHOD.DELETE);
  }

  public static OtherRequestBuilder patch() {
    return new OtherRequestBuilder(METHOD.PATCH);
  }

  public void execute(final RequestCall requestCall, Callback callback) {
    if (callback == null) callback = Callback.CALLBACK_DEFAULT;
    final Callback finalCallback = callback;
    final int id = requestCall.getOkHttpRequest().getId();

    requestCall.getCall().enqueue(new okhttp3.Callback() {
      @Override public void onFailure(Call call,  IOException e) {
        if (e.getCause().toString().contains("java.net.ConnectException")){
          e=  new IOException("网络开了点小差，请稍候再试");
        }
        sendFailResultCallback(call, e, finalCallback, id);
      }

      @Override public void onResponse(final Call call, final Response response) {
        try {
          if (call.isCanceled()) {
            sendFailResultCallback(call, new IOException("Canceled!"), finalCallback, id);
            return;
          }

          if (!finalCallback.validateReponse(response, id)) {
            sendFailResultCallback(call,
                new IOException("request failed , reponse's code is : " + response.code()),
                finalCallback, id);
            return;
          }
          //是否是下载文件，不是下载文件走下面
            String json = response.body().string();
            Log.e("JSON",">>>"+json);
            if (TextUtils.isEmpty(json)) {
              OkHttpUtils.this.sendFailResultCallback(call, new Exception("加载失败"), finalCallback, id);
            }
            String mCode = (new JSONObject(json)).getString("code");
            if ("0".equals(mCode)) {
              if (TextUtils.isEmpty((new JSONObject(json)).getString("data"))) {
                OkHttpUtils.this.sendFailResultCallback(call, new Exception("数据为空"), finalCallback,
                        id);
              } else {
                try {
                  String mData = (new JSONObject(json)).getString("data");
                  Object o = finalCallback.parseNetworkResponse(mData, response, id);
                  OkHttpUtils.this.sendSuccessResultCallback(o, finalCallback, id);
                } catch (Exception e ) {
                  sendFailResultCallback(call, new Exception("服务器错误"), finalCallback, id);
                }
              }
              return;
            } else {
              String msg = (new JSONObject(json)).getString("msg");
              sendFailResultCallback(call, new Exception(msg), finalCallback, id);
            }
//          }else{
//            Object o = finalCallback.parseNetworkResponse("",response, id);
//            sendSuccessResultCallback(o, finalCallback, id);
//          }



        } catch (Exception e) {
          sendFailResultCallback(call, e, finalCallback, id);
        } finally {
          if (response.body() != null) response.body().close();
        }
      }
    });
  }

  public void sendFailResultCallback(final Call call, final Exception e, final Callback callback,
      final int id) {
    if (callback == null) return;

    mPlatform.execute(new Runnable() {
      @Override public void run() {
        callback.onError(call, e, id);
        callback.onAfter(id);
      }
    });
  }

  public void sendSuccessResultCallback(final Object object, final Callback callback,
      final int id) {
    if (callback == null) return;
    mPlatform.execute(new Runnable() {
      @Override public void run() {
        callback.onResponse(object, id);
        callback.onAfter(id);
      }
    });
  }

  public void cancelTag(Object tag) {
    for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
      if (tag.equals(call.request().tag())) {
        call.cancel();
      }
    }
    for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
      if (tag.equals(call.request().tag())) {
        call.cancel();
      }
    }
  }

  public static class METHOD {
    public static final String HEAD = "HEAD";
    public static final String DELETE = "DELETE";
    public static final String PUT = "PUT";
    public static final String PATCH = "PATCH";
  }
}

