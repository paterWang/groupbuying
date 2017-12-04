package com.feitianzhu.fu700.dao;

import android.content.Context;
import android.text.TextUtils;

import com.feitianzhu.fu700.bankcard.entity.BankCardEntity;
import com.feitianzhu.fu700.bankcard.entity.UserBankCardEntity;
import com.feitianzhu.fu700.bankcard.entity.WithdrawFeeRateEntity;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.common.entity.DefaultRate;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.home.entity.NoticeEntity;
import com.feitianzhu.fu700.huanghuali.entity.HuangHuaLiHMLEntity;
import com.feitianzhu.fu700.huanghuali.entity.HuangHuaLiRecordEntity;
import com.feitianzhu.fu700.login.LoginEvent;
import com.feitianzhu.fu700.login.entity.LoginEntity;
import com.feitianzhu.fu700.login.entity.SmsCodeEntity;
import com.feitianzhu.fu700.login.entity.UserInfoEntity;
import com.feitianzhu.fu700.me.helper.CityModel;
import com.feitianzhu.fu700.model.OfflineModel;
import com.feitianzhu.fu700.payforme.entity.PayForMeEntity;
import com.feitianzhu.fu700.utils.SPUtils;
import com.feitianzhu.fu700.utils.Urls;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Response;

import static com.feitianzhu.fu700.common.Constant.ACCESSTOKEN;
import static com.feitianzhu.fu700.common.Constant.ACCESS_TOKEN;
import static com.feitianzhu.fu700.common.Constant.Common_HEADER;
import static com.feitianzhu.fu700.common.Constant.FailCode;
import static com.feitianzhu.fu700.common.Constant.LOGIN_USERID;
import static com.feitianzhu.fu700.common.Constant.POST_BUY_SERVICE;
import static com.feitianzhu.fu700.common.Constant.POST_SHOP_RECORD_SEND;
import static com.feitianzhu.fu700.common.Constant.THPASS;
import static com.feitianzhu.fu700.common.Constant.USERID;

/**
 * Created by lijia on 2017/9/4 0004.
 */

public class NetworkDao {


    private NetworkDao() {
    }

    public static void login(final Context context, final String phone, final String password, final onConnectionFinishLinstener linstener) {

        OkHttpUtils
                .post()
                .url(Urls.LOGIN)
                .addParams("phone", phone)
                .addParams("password", password)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(String mData, Response response, int id) throws Exception {
                        return new Gson().fromJson(mData, LoginEntity.class);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {

                        Constant.ACCESS_TOKEN = "";
                        Constant.LOGIN_USERID = "";
                        Constant.PHONE = "";
                        EventBus.getDefault().post(LoginEvent.LOGIN_FAILURE);

                        SPUtils.putString(context, Constant.SP_PHONE, "");
                        SPUtils.putString(context, Constant.SP_PASSWORD, "");
                        linstener.onFail(FailCode, e.getMessage());
                        KLog.e(e);
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                        if (response == null) {
                            linstener.onFail(FailCode, "response is null!");
                            return;
                        }

                        KLog.i("response:%s", response.toString());

                        LoginEntity loginEntity = (LoginEntity) response;
//                        Constant.CommonACCESSTOKEN = Constant.ACCESS_TOKEN = loginEntity.accessToken;
//                        Constant.CommonUSERID = Constant.LOGIN_USERID = loginEntity.userId;
                        Constant.ACCESS_TOKEN = loginEntity.accessToken;
                        Constant.LOGIN_USERID = loginEntity.userId;
                        Constant.PHONE = phone;

                        EventBus.getDefault().post(LoginEvent.LOGIN_SUCCESS);

                        SPUtils.putString(context, Constant.SP_PHONE, phone);
                        SPUtils.putString(context, Constant.SP_PASSWORD, password);

                        linstener.onSuccess(0, loginEntity);
                    }
                });

    }

    /**
     * 获取验证码
     */
    public static void getSmsCode(String phone, String type, final onConnectionFinishLinstener linstener) {

        OkHttpUtils
                .get()
                .url(Urls.GET_SMSCODE)
                .addParams("phone", phone)
                .addParams("type", type)
                .addParams("accessToken", Constant.ACCESS_TOKEN)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(String mData, Response response, int id) throws Exception {
                        KLog.i("mData: %s", mData);
                        return new Gson().fromJson(mData, SmsCodeEntity.class);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        linstener.onFail(FailCode, e.getMessage());
                        KLog.e(e);
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                        if (response == null) {
                            linstener.onFail(FailCode, "response is null!");
                            return;
                        }
                        KLog.i("response:%s", response.toString());

                        linstener.onSuccess(0, response);
                    }
                });
    }


    /**
     * 更换手机号
     */
    public static void updatePhone(String oldPhone, String oldSmsCode, String newPhone, String newSmsCode, final onConnectionFinishLinstener linstener) {

        OkHttpUtils
                .post()
                .url(Urls.UPDATE_PHONE)
                .addParams("oldPhone", oldPhone)
                .addParams("oldSmsCode", oldSmsCode)
                .addParams("newPhone", newPhone)
                .addParams("newSmsCode", newSmsCode)
                .addParams("accessToken", Constant.ACCESS_TOKEN)
                .addParams("userId", Constant.LOGIN_USERID)
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

                            linstener.onSuccess(0, e.getMessage());
                        } else {
                            linstener.onFail(FailCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                        if (response == null) {
                            linstener.onFail(FailCode, "response is null!");
                            return;
                        }
                        KLog.i("response:%s", response.toString());

                        linstener.onSuccess(0, response);
                    }
                });
    }


    /**
     * 查询个人信息
     */
    public static void getUserInfo(final onConnectionFinishLinstener linstener) {

        OkHttpUtils
                .get()
                .url(Urls.GET_USERINFO)
                .addParams("accessToken", Constant.ACCESS_TOKEN)
                .addParams("userId", Constant.LOGIN_USERID)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(String mData, Response response, int id) throws Exception {
                        return new Gson().fromJson(mData, UserInfoEntity.class);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        KLog.e(e);
                        linstener.onFail(FailCode, e.getMessage());
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                        if (response == null) {
                            linstener.onFail(FailCode, "response is null!");
                            return;
                        }
                        Constant.USER_INFO = ((UserInfoEntity) response);
                        if (Constant.USER_INFO.getNickName() == null) {
                            Constant.USER_INFO.setNickName("");
                        }
                        KLog.i("Constant.USER_INFO:%s", Constant.USER_INFO.toString());
                        linstener.onSuccess(0, response);
                    }
                });
    }

    /**
     * 修改密码
     */
    public static void changePassword(String oldPassword, String newPassword, String confirmPassword, final onConnectionFinishLinstener linstener) {

        OkHttpUtils
                .post()
                .url(Urls.UPDATE_ULPASS)
                .addParams("oldPassword", oldPassword)
                .addParams("newPassword", newPassword)
                .addParams("confirmPassword", confirmPassword)
                .addParams("accessToken", Constant.ACCESS_TOKEN)
                .addParams("userId", Constant.LOGIN_USERID)
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
                            linstener.onSuccess(0, e.getMessage());
                        } else {
                            linstener.onFail(FailCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                        if (response == null) {
                            linstener.onFail(FailCode, "response is null!");
                            return;
                        }
                        KLog.i("response:%s", response.toString());

                        linstener.onSuccess(0, response);
                    }
                });
    }


    /**
     * 设置二级密码
     */
    public static void setPayPassword(String phone, String smsCode, String paypass, String confirmPaypass, final onConnectionFinishLinstener linstener) {

        OkHttpUtils
                .post()
                .url(Urls.SET_PAYPASS)
                .addParams("phone", phone)
                .addParams("smsCode", smsCode)
                .addParams("paypass", paypass)
                .addParams("confirmPaypass", confirmPaypass)
                .addParams("accessToken", Constant.ACCESS_TOKEN)
                .addParams("userId", Constant.LOGIN_USERID)
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
                            linstener.onSuccess(0, e.getMessage());
                        } else {
                            linstener.onFail(FailCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                        if (response == null) {
                            linstener.onFail(FailCode, "response is null!");
                            return;
                        }
                        KLog.i("response:%s", response.toString());

                        linstener.onSuccess(0, response);
                    }
                });
    }


    /**
     * 重置二级密码
     */
    public static void updatePayPassword(String oldPassword, String newPassword, String confirmPassword, final onConnectionFinishLinstener linstener) {

        OkHttpUtils
                .post()
                .url(Urls.UPDATE_UPAYPASS)
                .addParams("oldPassword", oldPassword)
                .addParams("newPassword", newPassword)
                .addParams("confirmPassword", confirmPassword)
                .addParams("accessToken", Constant.ACCESS_TOKEN)
                .addParams("userId", Constant.LOGIN_USERID)
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
                            linstener.onSuccess(0, e.getMessage());
                        } else {
                            linstener.onFail(FailCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                        if (response == null) {
                            linstener.onFail(FailCode, "response is null!");
                            return;
                        }
                        KLog.i("response:%s", response.toString());

                        linstener.onSuccess(0, response);
                    }
                });
    }


    /**
     * 找回登录密码
     */
    public static void getLoginPwd(String phone, String smsCode, String newPassword, String confirmPassword, final onConnectionFinishLinstener linstener) {

        OkHttpUtils
                .post()
                .url(Urls.GET_MYPASSWORD)
                .addParams("phone", phone)
                .addParams("smsCode", smsCode)
                .addParams("newPassword", newPassword)
                .addParams("confirmPassword", confirmPassword)
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

                            linstener.onSuccess(0, e.getMessage());
                        } else {
                            linstener.onFail(FailCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                        if (response == null) {
                            linstener.onFail(FailCode, "response is null!");
                            return;
                        }
                        KLog.i("response:%s", response.toString());

                        linstener.onSuccess(0, response);
                    }
                });
    }


    /**
     * 重置二级密码
     */
    public static void getPayPwd(String phone, String smsCode, String newPassword, String confirmPassword, final onConnectionFinishLinstener linstener) {

        OkHttpUtils
                .post()
                .url(Urls.GET_UPAYPASS)
                .addParams("phone", phone)
                .addParams("smsCode", smsCode)
                .addParams("newPassword", newPassword)
                .addParams("confirmPassword", confirmPassword)
                .addParams("accessToken", Constant.ACCESS_TOKEN)
                .addParams("userId", Constant.LOGIN_USERID)
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

                            linstener.onSuccess(0, e.getMessage());
                        } else {
                            linstener.onFail(FailCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                        if (response == null) {
                            linstener.onFail(FailCode, "response is null!");
                            return;
                        }
                        KLog.i("response:%s", response.toString());

                        linstener.onSuccess(0, response);
                    }
                });
    }


    /**
     * 二级密码校验
     */
    public static void checkPayPwd(String paypass, final onConnectionFinishLinstener linstener) {

        OkHttpUtils
                .post()
                .url(Urls.CHECK_PAYPASS)
                .addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                .addParams(USERID, LOGIN_USERID)//
                .addParams("paypass", paypass)
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

                            linstener.onSuccess(0, e.getMessage());
                        } else {
                            linstener.onFail(FailCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                        if (response == null) {
                            linstener.onFail(FailCode, "response is null!");
                            return;
                        }
                        KLog.i("response:%s", response.toString());

                        linstener.onSuccess(0, response);
                    }
                });
    }
/**
 * TH密码校验
 */
    /**
     * 二级密码校验
     */
    public static void checkTHPwd(String paypass, final onConnectionFinishLinstener linstener) {

        OkHttpUtils
                .post()
                .url(Urls.POST_THPASS)
                .addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                .addParams(USERID, LOGIN_USERID)//
                .addParams("thPass", paypass)
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

                            linstener.onSuccess(0, e.getMessage());
                        } else {
                            linstener.onFail(FailCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                        if (response == null) {
                            linstener.onFail(FailCode, "response is null!");
                            return;
                        }
                        KLog.i("response:%s", response.toString());

                        linstener.onSuccess(0, response);
                    }
                });
    }


    /**
     * 意见反馈
     */
    public static void feedback(String content, final onConnectionFinishLinstener linstener) {

        OkHttpUtils
                .post()
                .url(Urls.USER_FEEDBACK)
                .addParams("content", content)
                .addParams("accessToken", Constant.ACCESS_TOKEN)
                .addParams("userId", Constant.LOGIN_USERID)
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

                            linstener.onSuccess(0, e.getMessage());
                        } else {
                            linstener.onFail(FailCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                        if (response == null) {
                            linstener.onFail(FailCode, "response is null!");
                            return;
                        }
                        KLog.i("response:%s", response.toString());

                        linstener.onSuccess(0, response);
                    }
                });
    }


    /**
     * 删除银行卡
     */
    public static void deleteBankCard(String bankCardId, final onConnectionFinishLinstener linstener) {

        OkHttpUtils
                .post()
                .url(Urls.DELETE_UBC)
                .addParams("bankCardId", bankCardId)
                .addParams("accessToken", Constant.ACCESS_TOKEN)
                .addParams("userId", Constant.LOGIN_USERID)
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
                            linstener.onSuccess(0, e.getMessage());
                        } else {
                            linstener.onFail(FailCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                        if (response == null) {
                            linstener.onFail(FailCode, "response is null!");
                            return;
                        }
                        KLog.i("response:%s", response.toString());

                        linstener.onSuccess(0, response);
                    }
                });
    }

    /**
     * 新增银行卡信息
     *
     * @param bankId
     * @param openSubbranch 开户支行
     * @param bankCardNo    银行卡账号
     * @param paypass
     * @param linstener
     */
    public static void addBankCard(String realName, String bankId, String openSubbranch, String bankCardNo, String paypass, final onConnectionFinishLinstener linstener) {

        OkHttpUtils
                .post()
                .url(Urls.ADD_UBC)
                .addParams("realName", realName)
                .addParams("bankId", bankId)
                .addParams("openSubbranch", openSubbranch)
                .addParams("bankCardNo", bankCardNo)
                .addParams("paypass", paypass)
                .addParams("accessToken", Constant.ACCESS_TOKEN)
                .addParams("userId", Constant.LOGIN_USERID)
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
                            linstener.onSuccess(0, e.getMessage());
                        } else {
                            linstener.onFail(FailCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                        if (response == null) {
                            linstener.onFail(FailCode, "response is null!");
                            return;
                        }
                        KLog.i("response:%s", response.toString());

                        linstener.onSuccess(0, response);
                    }
                });
    }

    /**
     * 获取线下支付信息
     */
    public static void getOfflineInfo(final onConnectionFinishLinstener linstener) {

        OkHttpUtils
                .post()
                .url(Urls.GET_OFFLINE)
                .addParams("accessToken", Constant.ACCESS_TOKEN)
                .addParams("userId", Constant.LOGIN_USERID)
                .build()
                .execute(new Callback<OfflineModel>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        KLog.e(e);
                        if ("数据为空".equals(e.getMessage())) {
                            linstener.onSuccess(0, e.getMessage());
                        } else {
                            linstener.onFail(FailCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(OfflineModel response, int id) {

                        if (response == null) {
                            linstener.onFail(FailCode, "response is null!");
                            return;
                        }
                        KLog.i("response:%s", response.toString());

                        linstener.onSuccess(0, response);
                    }
                });
    }

    /**
     * 获取用户银行卡列表
     */
    public static void getUserBankCardList(final onConnectionFinishLinstener linstener) {

        OkHttpUtils
                .post()
                .url(Urls.GET_USER_BCLIST)
                .addParams("accessToken", Constant.ACCESS_TOKEN)
                .addParams("userId", Constant.LOGIN_USERID)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(String mData, Response response, int id) throws Exception {
                        Type listType = new TypeToken<ArrayList<UserBankCardEntity>>() {
                        }.getType();
                        return new Gson().fromJson(mData, listType);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        KLog.e(e);
                        if ("数据为空".equals(e.getMessage())) {
                            linstener.onSuccess(0, e.getMessage());
                        } else {
                            linstener.onFail(FailCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                        if (response == null) {
                            linstener.onFail(FailCode, "response is null!");
                            return;
                        }
                        KLog.i("response:%s", response.toString());

                        linstener.onSuccess(0, response);
                    }
                });
    }

    /**
     * 获取银行卡列表
     */
    public static void getBankCardList(final onConnectionFinishLinstener linstener) {

        OkHttpUtils
                .post()
                .url(Urls.GET_BANKLIST)
                .addParams("accessToken", Constant.ACCESS_TOKEN)
                .addParams("userId", Constant.LOGIN_USERID)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(String mData, Response response, int id) throws Exception {
                        Type listType = new TypeToken<ArrayList<BankCardEntity>>() {
                        }.getType();
                        return new Gson().fromJson(mData, listType);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        KLog.e(e);
                        if ("数据为空".equals(e.getMessage())) {
                            linstener.onSuccess(0, e.getMessage());
                        } else {
                            linstener.onFail(FailCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                        if (response == null) {
                            linstener.onFail(FailCode, "response is null!");
                            return;
                        }
                        KLog.i("response:%s", response.toString());

                        linstener.onSuccess(0, response);
                    }
                });
    }

    public static void PayUnionLevel(String gradeId, String psw, final String PayChannel, CityModel model, String thPass,final onConnectionFinishLinstener linstener) {
        PostFormBuilder mPost = OkHttpUtils.post();
        mPost.setMultipart(true);
        mPost.url(Common_HEADER + Constant.POST_UNION_LEVEL_PAY);
        if (model != null) {
            if ("4".equals(model.agentType)) {//市代3  区代4
                mPost.addParams("provinceId", model.cityId)
                        .addParams("provinceName", model.city)
                        .addParams("cityId", model.areaId)
                        .addParams("cityName", model.area)
                        .addParams("areaId", model.localId)
                        .addParams("areaName", model.local);
            }
            if ("3".equals(model.agentType)) {
                mPost.addParams("provinceId", model.cityId)
                        .addParams("provinceName", model.city)
                        .addParams("cityId", model.areaId)
                        .addParams("cityName", model.area);

            }
            if ("2".equals(model.agentType)) {
                mPost.addParams("provinceId", model.cityId)
                        .addParams("provinceName", model.city);


            }

        }
        mPost.addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                .addParams(USERID, Constant.LOGIN_USERID)
                .addParams("gradeId", gradeId)
                .addParams("payPass", psw)
                .addParams(THPASS, thPass)
                .addParams("payChannel", PayChannel)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(String mData, Response response, int id)
                            throws Exception {
                        if (PayChannel.equals("alipay")) {
                            JSONObject object = new JSONObject(mData);
                            return object.getString("payParam");
                        }
                        return mData;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if ("数据为空".equals(e.getMessage())) {
                            linstener.onSuccess(0, e.getMessage());
                        } else {
                            linstener.onFail(FailCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {
                        if (response == null) {
                            linstener.onFail(FailCode, "response is null!");
                            return;
                        }
                        KLog.i("response:%s", response.toString());

                        linstener.onSuccess(0, response);
                    }
                });
    }

    /**
     * 购买服务
     *
     * @param merchantId
     * @param serviceId
     * @param price
     * @param rebate
     * @param contactTel
     * @param PayChannel
     * @param psw
     * @param linstener
     */
    public static void PayBuyService(String merchantId, String serviceId, String price, String rebate, String contactTel, final String PayChannel, String psw, final onConnectionFinishLinstener linstener) {
        PostFormBuilder mPost = OkHttpUtils.post();
        mPost.url(Common_HEADER + POST_BUY_SERVICE)
                .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                .addParams(USERID, Constant.LOGIN_USERID)
                .addParams("merchantId", merchantId)
                .addParams("serviceId", serviceId)
                .addParams("amount", price)
                .addParams("rebatePv", rebate)
                .addParams("linkPhone", contactTel)
                .addParams("payChannel", PayChannel)
                .addParams("payPass", psw)

                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(String mData, Response response, int id)
                            throws Exception {
                        if (PayChannel.equals("alipay")) {
                            JSONObject object = new JSONObject(mData);
                            return object.getString("payParam");
                        }
                        return mData;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if ("数据为空".equals(e.getMessage())) {
                            linstener.onSuccess(0, e.getMessage());
                        } else {
                            linstener.onFail(FailCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {
                        if (response == null) {
                            linstener.onFail(FailCode, "response is null!");
                            return;
                        }
                        KLog.i("response:%s", response.toString());

                        linstener.onSuccess(0, response);
                    }
                });
    }

    /**
     * 商家录单付款
     *
     * @param MemberId
     * @param ConsumeAmount
     * @param HandleFee
     * @param FeeId
     * @param payPass
     * @param PayChannel
     * @param PlaceImgFile
     * @param ObjImgFile
     * @param RcptImgFile
     * @param linstener
     */
    public static void PayShopRecord(String MemberId, String ConsumeAmount, String HandleFee, String FeeId, String payPass, final String PayChannel, String PlaceImgFile, String ObjImgFile, String RcptImgFile, final onConnectionFinishLinstener linstener) {

        PostFormBuilder mPost = OkHttpUtils.post();
        mPost.url(Common_HEADER + POST_SHOP_RECORD_SEND)
                .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                .addParams(USERID, Constant.LOGIN_USERID)
                .addParams("memberId", MemberId)
                .addParams("consumeAmount", ConsumeAmount)
                .addParams("handleFee", HandleFee)
                .addParams("feeId", FeeId)
                .addParams("payPass", payPass)
                .addParams("payChannel", PayChannel)
                .addFile("placeImgFile", "placeImgFile.png", new File(PlaceImgFile))// 消费场所
                .addFile("objImgFile", "objImgFile.png", new File(ObjImgFile))// 消费实物
                .addFile("rcptImgFile", "rcptImgFile.png", new File(RcptImgFile))// 消费发票
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(String mData, Response response, int id)
                            throws Exception {
                        if (PayChannel.equals("alipay")) {
                            JSONObject object = new JSONObject(mData);
                            return object.getString("payParam");
                        }
                        return mData;
                    }


                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if ("数据为空".equals(e.getMessage())) {
                            linstener.onSuccess(0, e.getMessage());
                        } else {
                            linstener.onFail(FailCode, e.getMessage());
                        }
                    }


                    @Override
                    public void onResponse(Object response, int id) {
                        if (response == null) {
                            linstener.onFail(FailCode, "response is null!");
                            return;
                        }
                        KLog.i("response:%s", response.toString());

                        linstener.onSuccess(0, response);
                    }
                });

    }


    /**
     * 为我买单
     *
     * @param payPass       支付密码
     * @param merchantName  商户名称
     * @param merchantAddr  商户地址
     * @param goodsName     商品名称
     * @param consumeAmount 消费金额
     * @param handleFee     手续费（系统参数--策划推广比例接口返回的比例*消费金额）
     * @param payChannel    支付渠道（wx：微信，alipay：支付宝，balance：余额，offline：线下转账）
     * @param placeImgFile  消费场所图
     * @param objImgFile    消费实物图
     * @param rcptImgFile   消费发票图
     * @param payProofFile  转账凭证（线下支付时必传）
     * @param linstener
     */
    public static void payForMe(String payPass, String merchantName, String merchantAddr, String goodsName, String consumeAmount, String handleFee, final String payChannel,
                                String placeImgFile, String objImgFile, String rcptImgFile, String payProofFile, final onConnectionFinishLinstener linstener) {

        PostFormBuilder post = OkHttpUtils.post();
        if (!TextUtils.isEmpty(payProofFile)) {
            post.addFile("payProofFile", "payProofFile.png", new File(payProofFile));// 转账凭证（线下支付时必传）
        }
        post
                .url(Urls.PAY_FOR_ME)
                .addParams("payPass", payPass)
                .addParams("merchantName", merchantName)
                .addParams("merchantAddr", merchantAddr)
                .addParams("goodsName", goodsName)
                .addParams("consumeAmount", consumeAmount)
                .addParams("handleFee", handleFee)

                .addParams("payChannel", payChannel)
                .addFile("placeImgFile", "placeImgFile.png", new File(placeImgFile))// 消费场所
                .addFile("objImgFile", "objImgFile.png", new File(objImgFile))// 消费实物
                .addFile("rcptImgFile", "rcptImgFile.png", new File(rcptImgFile))// 消费发票
                .addParams("accessToken", Constant.ACCESS_TOKEN)
                .addParams("userId", Constant.LOGIN_USERID)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(String mData, Response response, int id) throws Exception {
                        if (payChannel.equals("alipay")) {
                            JSONObject object = new JSONObject(mData);
                            return object.getString("payParam");
                        } else if (payChannel.equals("wx")) {

                        }
                        return mData;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        KLog.e(e);
                        if ("数据为空".equals(e.getMessage())) {
                            linstener.onSuccess(0, e.getMessage());
                        } else {
                            linstener.onFail(FailCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                        if (response == null) {
                            linstener.onFail(FailCode, "response is null!");
                            return;
                        }
                        KLog.i("response:%s", response.toString());

                        linstener.onSuccess(0, response);
                    }
                });
    }

    /**
     * 为我买单
     *
     * @param merchantName 商户名称
     * @param merchantAddr 商户地址
     * @param goodsName    商品名称
     * @param placeImgFile 消费场所图
     * @param objImgFile   消费实物图
     * @param rcptImgFile  消费发票图
     * @param linstener
     */
    public static void updateOrder(String orderNo, String merchantName, String merchantAddr, String goodsName,
                                   String placeImgFile, String objImgFile, String rcptImgFile, final onConnectionFinishLinstener linstener) {
        OkHttpUtils
                .post()
                .url(Urls.UPDATE_ORDER)
                .addParams("orderNo", orderNo)
                .addParams("merchantName", merchantName)
                .addParams("merchantAddr", merchantAddr)
                .addParams("goodsName", goodsName)
                .addFile("placeImgFile", "placeImgFile.png", new File(placeImgFile))// 消费场所
                .addFile("objImgFile", "objImgFile.png", new File(objImgFile))// 消费实物
                .addFile("rcptImgFile", "rcptImgFile.png", new File(rcptImgFile))// 消费发票
                .addParams("accessToken", Constant.ACCESS_TOKEN)
                .addParams("userId", Constant.LOGIN_USERID)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(String mData, Response response, int id) throws Exception {
                        Type listType = new TypeToken<ArrayList<BankCardEntity>>() {
                        }.getType();
                        return new Gson().fromJson(mData, listType);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        KLog.e(e);
                        if ("数据为空".equals(e.getMessage())) {
                            linstener.onSuccess(0, e.getMessage());
                        } else {
                            linstener.onFail(FailCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                        if (response == null) {
                            linstener.onFail(FailCode, "response is null!");
                            return;
                        }
                        KLog.i("response:%s", response.toString());

                        linstener.onSuccess(0, response);
                    }
                });
    }


    /**
     * 为我买单记录
     *
     * @param status    审核状态（0：审核中，1：审核通过，-1：拒绝）
     * @param pageIndex 页数
     * @param pageRows  每页行数
     * @param linstener
     */
    public static void payForMeRecord(String status, String pageIndex, String pageRows, final onConnectionFinishLinstener linstener) {

        OkHttpUtils
                .post()
                .url(Urls.PAY_FOR_ME_RECORD)
                .addParams("status", status)
                .addParams("pageIndex", pageIndex)
                .addParams("pageRows", pageRows)
                .addParams("accessToken", Constant.ACCESS_TOKEN)
                .addParams("userId", Constant.LOGIN_USERID)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(String mData, Response response, int id) throws Exception {

                        return new Gson().fromJson(mData, PayForMeEntity.class);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        KLog.e(e);
                        if ("数据为空".equals(e.getMessage())) {
                            linstener.onSuccess(0, e.getMessage());
                        } else {
                            linstener.onFail(FailCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                        if (response == null) {
                            linstener.onFail(FailCode, "response is null!");
                            return;
                        }
                        KLog.i("response:%s", response.toString());

                        linstener.onSuccess(0, response);
                    }
                });
    }

    /**
     * 查询默认策划推广比例
     */
    public static void getDefaultProportion(final onConnectionFinishLinstener linstener) {

        OkHttpUtils
                .post()
                .url(Urls.DEFAULT_PROPORTION)
                .addParams("accessToken", Constant.ACCESS_TOKEN)
                .addParams("userId", Constant.LOGIN_USERID)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(String mData, Response response, int id) throws Exception {
                        return new Gson().fromJson(mData, DefaultRate.class);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        KLog.e(e);
                        if ("数据为空".equals(e.getMessage())) {
                            linstener.onSuccess(0, e.getMessage());
                        } else {
                            linstener.onFail(FailCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                        if (response == null) {
                            linstener.onFail(FailCode, "response is null!");
                            return;
                        }
                        KLog.i("response:%s", response.toString());

                        linstener.onSuccess(0, response);
                    }
                });
    }

    /**
     * 兑换
     *
     * @param payPass
     * @param amount
     * @param bankCardId 银行卡id
     * @param type       类型（1：余额兑换，2：商户钱包兑换）
     * @param linstener
     */
    public static void withdraw(String payPass, String amount, String bankCardId, String type, final onConnectionFinishLinstener linstener) {

        OkHttpUtils
                .post()
                .url(Urls.WITHDRAW)
                .addParams("payPass", payPass)
                .addParams("amount", amount)
                .addParams("bankCardId", bankCardId)
                .addParams("type", type)
                .addParams("accessToken", Constant.ACCESS_TOKEN)
                .addParams("userId", Constant.LOGIN_USERID)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(String mData, Response response, int id) throws Exception {
                        return new Gson().fromJson(mData, DefaultRate.class);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        KLog.e(e);
                        if ("数据为空".equals(e.getMessage())) {
                            linstener.onSuccess(0, e.getMessage());
                        } else {
                            linstener.onFail(FailCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                        if (response == null) {
                            linstener.onFail(FailCode, "response is null!");
                            return;
                        }
                        KLog.i("response:%s", response.toString());

                        linstener.onSuccess(0, response);
                    }
                });
    }


    /**
     * 兑换手续费
     *
     * @param type      type=1表示余额，type=2表示钱包
     * @param linstener {"code":0,"data":{"rate":10}}
     */
    public static void withdrawFeeRate(String type, final onConnectionFinishLinstener linstener) {

        OkHttpUtils
                .post()
                .url(Urls.WITHDRAW_FEE_RATE)
                .addParams("type", type)
                .addParams("accessToken", Constant.ACCESS_TOKEN)
                .addParams("userId", Constant.LOGIN_USERID)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(String mData, Response response, int id) throws Exception {
                        return new Gson().fromJson(mData, WithdrawFeeRateEntity.class);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        KLog.e(e);
                        if ("数据为空".equals(e.getMessage())) {
                            linstener.onSuccess(0, e.getMessage());
                        } else {
                            linstener.onFail(FailCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                        if (response == null) {
                            linstener.onFail(FailCode, "response is null!");
                            return;
                        }
                        KLog.i("response:%s", response.toString());

                        linstener.onSuccess(0, response);
                    }
                });
    }

    /**
     * 获取黄花梨详情
     *
     * @param linstener
     */
    public static void getHuanghualiInfo(final onConnectionFinishLinstener linstener) {

        OkHttpUtils
                .post()
                .url(Urls.HUANGHUALI_WEBVIEW)
                .addParams("accessToken", Constant.ACCESS_TOKEN)
                .addParams("userId", Constant.LOGIN_USERID)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(String mData, Response response, int id) throws Exception {
                        return new Gson().fromJson(mData, HuangHuaLiHMLEntity.class);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        KLog.e(e);
                        if ("数据为空".equals(e.getMessage())) {
                            linstener.onSuccess(0, e.getMessage());
                        } else {
                            linstener.onFail(FailCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                        if (response == null) {
                            linstener.onFail(FailCode, "response is null!");
                            return;
                        }
                        KLog.i("response:%s", response.toString());

                        linstener.onSuccess(0, response);
                    }
                });
    }

    /**
     * 购买黄花梨
     *
     * @param linstener
     */
    public static void buyHuangHuaLi(String payProofFile, final onConnectionFinishLinstener linstener) {

        OkHttpUtils
                .post()
                .url(Urls.HUANGHUALI_BUY)
                .addParams("accessToken", Constant.ACCESS_TOKEN)
                .addParams("userId", Constant.LOGIN_USERID)
                .addFile("payProofFile", "payProofFile.png", new File(payProofFile))// 转账凭证（线下支付时必传）
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
                            linstener.onSuccess(0, e.getMessage());
                        } else {
                            linstener.onFail(FailCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                        if (response == null) {
                            linstener.onFail(FailCode, "response is null!");
                            return;
                        }
                        KLog.i("response:%s", response.toString());

                        linstener.onSuccess(0, response);
                    }
                });
    }

    /**
     * 黄花梨购买记录
     *
     * @param linstener
     */
    public static void getHuangHuaLiRecord(final onConnectionFinishLinstener linstener) {

        OkHttpUtils
                .post()
                .url(Urls.HUANGHUALI_LIST)
                .addParams("accessToken", Constant.ACCESS_TOKEN)
                .addParams("userId", Constant.LOGIN_USERID)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(String mData, Response response, int id) throws Exception {
                        Type listType = new TypeToken<ArrayList<HuangHuaLiRecordEntity>>() {
                        }.getType();
                        return new Gson().fromJson(mData, listType);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        KLog.e(e);
                        if ("数据为空".equals(e.getMessage())) {
                            linstener.onSuccess(0, e.getMessage());
                        } else {
                            linstener.onFail(FailCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                        if (response == null) {
                            linstener.onFail(FailCode, "response is null!");
                            return;
                        }
                        KLog.i("response:%s", response.toString());

                        linstener.onSuccess(0, response);
                    }
                });
    }


    public static void getNotices(String pageIndex, String pageRows, final onConnectionFinishLinstener linstener) {

        OkHttpUtils
                .post()
                .url(Urls.NOTICE_LIST)
                .addParams("pageIndex", pageIndex)
                .addParams("pageRows", pageRows)
                .addParams("accessToken", Constant.ACCESS_TOKEN)
                .addParams("userId", Constant.LOGIN_USERID)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(String mData, Response response, int id) throws Exception {

                        return new Gson().fromJson(mData, NoticeEntity.class);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        KLog.e(e);
                        if ("数据为空".equals(e.getMessage())) {
                            linstener.onSuccess(0, e.getMessage());
                        } else {
                            linstener.onFail(FailCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                        if (response == null) {
                            linstener.onFail(FailCode, "response is null!");
                            return;
                        }
                        KLog.i("response:%s", response.toString());

                        linstener.onSuccess(0, response);
                    }
                });
    }


}