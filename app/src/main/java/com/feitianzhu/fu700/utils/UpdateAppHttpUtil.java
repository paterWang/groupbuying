package com.feitianzhu.fu700.utils;

import android.support.annotation.NonNull;

import com.vector.update_app.HttpManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.OkHttpUtilsForDown;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import static com.feitianzhu.fu700.common.Constant.ACCESSTOKEN;
import static com.feitianzhu.fu700.common.Constant.ACCESS_TOKEN;
import static com.feitianzhu.fu700.common.Constant.LOGIN_USERID;
import static com.feitianzhu.fu700.common.Constant.TYPE;
import static com.feitianzhu.fu700.common.Constant.USERID;

public class UpdateAppHttpUtil implements HttpManager {
    /**
     * 异步get
     *
     * @param url      get请求地址
     * @param params   get参数
     * @param callBack 回调
     */
    @Override
    public void asyncGet(@NonNull String url, @NonNull Map<String, String> params, @NonNull final Callback callBack) {
        OkHttpUtils.get()
                .url(url)
                .addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                .addParams(USERID, LOGIN_USERID)//
                .addParams(TYPE, "1")//
                .build()
                .execute(new StringCallback() {
                    @Override
                    public String parseNetworkResponse(String mData, Response response, int id) throws IOException {
                        return mData;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if(null!=callBack){
                            callBack.onError(e.getMessage());
                        }

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if(null!=callBack){
                            callBack.onResponse(response);
                        }
                    }
                });
    }

    /**
     * 异步post
     *
     * @param url      post请求地址
     * @param params   post请求参数
     * @param callBack 回调
     */
    @Override
    public void asyncPost(@NonNull String url, @NonNull Map<String, String> params, @NonNull final Callback callBack) {
        OkHttpUtils.post()
                .url(url)
                .addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                .addParams(USERID, LOGIN_USERID)//
                .addParams(TYPE, "1")//
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if(null != callBack){
                            callBack.onError(e.getMessage());
                        }

                    }

                    @Override
                    public void onResponse(String response, int id) {

                        if(null != callBack){
                            callBack.onResponse(response);
                        }
                    }
                });

    }

    /**
     * 下载
     *
     * @param url      下载地址
     * @param path     文件保存路径
     * @param fileName 文件名称
     * @param callback 回调
     */
    @Override
    public void download(@NonNull String url, @NonNull String path, @NonNull String fileName, @NonNull final FileCallback callback) {
        OkHttpUtilsForDown.get()

                .url(url)
                .build()
                .executeForDown(new FileCallBack(path, fileName) {
                    @Override
                    public void inProgress(float progress, long total, int id) {

                        if(null != callback){
                            callback.onProgress(progress, total);
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if(null != callback){
                            callback.onError(e.getMessage());
                        }

                    }

                    @Override
                    public void onResponse(File response, int id) {
                        if(null != callback){
                            callback.onResponse(response);
                        }


                    }

                    @Override
                    public void onBefore(Request request, int id) {
                        super.onBefore(request, id);
                        if(null != callback){
                            callback.onBefore();
                        }

                    }
                });

    }
}