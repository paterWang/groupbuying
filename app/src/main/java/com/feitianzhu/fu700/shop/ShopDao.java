package com.feitianzhu.fu700.shop;

import android.text.TextUtils;

import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.common.impl.AliPayCallBack;
import com.feitianzhu.fu700.common.impl.BaseCallBackObject;
import com.feitianzhu.fu700.common.impl.BaseCallBackT;
import com.feitianzhu.fu700.common.impl.MyInfomationCallback;
import com.feitianzhu.fu700.common.impl.NoDataCallBack;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.common.impl.onNetFinishLinstenerT;
import com.feitianzhu.fu700.model.FuFriendModel;
import com.feitianzhu.fu700.model.MyPoint;
import com.feitianzhu.fu700.model.Province;
import com.feitianzhu.fu700.model.RecommndShopModel;
import com.feitianzhu.fu700.model.ServeOrderModel;
import com.feitianzhu.fu700.model.ShopOrderModel;
import com.feitianzhu.fu700.model.ShopType;
import com.feitianzhu.fu700.model.ShopVeriModel;
import com.feitianzhu.fu700.model.ShopsEvali;
import com.feitianzhu.fu700.model.ShopsIndex;
import com.feitianzhu.fu700.model.ShopsInfo;
import com.feitianzhu.fu700.model.ShopsNearby;
import com.feitianzhu.fu700.model.ShopsService;
import com.feitianzhu.fu700.model.ShopsType;
import com.feitianzhu.fu700.model.UpdateAppModel;
import com.feitianzhu.fu700.model.UserAuth;
import com.feitianzhu.fu700.model.UserInformation;
import com.feitianzhu.fu700.model.UserVeriModel;
import com.feitianzhu.fu700.model.WXModel;
import com.feitianzhu.fu700.model.WalletModel;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.feitianzhu.fu700.common.Constant.ACCESSTOKEN;
import static com.feitianzhu.fu700.common.Constant.ACCESS_TOKEN;
import static com.feitianzhu.fu700.common.Constant.AMOUNT;
import static com.feitianzhu.fu700.common.Constant.AREAID;
import static com.feitianzhu.fu700.common.Constant.AREANAME;
import static com.feitianzhu.fu700.common.Constant.AVATAR;
import static com.feitianzhu.fu700.common.Constant.BUSILICENSENAME;
import static com.feitianzhu.fu700.common.Constant.BUSINATURE;
import static com.feitianzhu.fu700.common.Constant.CERTIFNO;
import static com.feitianzhu.fu700.common.Constant.CERTIFTYPE;
import static com.feitianzhu.fu700.common.Constant.CHANNEL;
import static com.feitianzhu.fu700.common.Constant.CITYID;
import static com.feitianzhu.fu700.common.Constant.CITYNAME;
import static com.feitianzhu.fu700.common.Constant.CLSID;
import static com.feitianzhu.fu700.common.Constant.COLLECTID;
import static com.feitianzhu.fu700.common.Constant.CONSUMEAMOUNT;
import static com.feitianzhu.fu700.common.Constant.CONTENT;
import static com.feitianzhu.fu700.common.Constant.Common_HEADER;
import static com.feitianzhu.fu700.common.Constant.DELETE_COLLECT;
import static com.feitianzhu.fu700.common.Constant.DELETE_SERVICE;
import static com.feitianzhu.fu700.common.Constant.DTLADDR;
import static com.feitianzhu.fu700.common.Constant.EDIT_SHOPS_INFO;
import static com.feitianzhu.fu700.common.Constant.FILES;
import static com.feitianzhu.fu700.common.Constant.FailCode;
import static com.feitianzhu.fu700.common.Constant.IDVALUE;
import static com.feitianzhu.fu700.common.Constant.INTRODUCE;
import static com.feitianzhu.fu700.common.Constant.JUBAO;
import static com.feitianzhu.fu700.common.Constant.LATITUDE;
import static com.feitianzhu.fu700.common.Constant.LEGALPERSON;
import static com.feitianzhu.fu700.common.Constant.LOADER_USERINFO;
import static com.feitianzhu.fu700.common.Constant.LOADER_VERI_SHOPS_INFO;
import static com.feitianzhu.fu700.common.Constant.LOADER_VERI_USER_INFO;
import static com.feitianzhu.fu700.common.Constant.LOAD_FU_FRIEND;
import static com.feitianzhu.fu700.common.Constant.LOAD_NEARBY_SHOPS;
import static com.feitianzhu.fu700.common.Constant.LOAD_RECMOOND_SHOP_LIST;
import static com.feitianzhu.fu700.common.Constant.LOAD_SHOPS_EVALUTE;
import static com.feitianzhu.fu700.common.Constant.LOAD_SHOPS_INDEX;
import static com.feitianzhu.fu700.common.Constant.LOAD_SHOPS_INFO;
import static com.feitianzhu.fu700.common.Constant.LOAD_SHOPS_SEARCH_DATA;
import static com.feitianzhu.fu700.common.Constant.LOAD_SHOPS_SERVICES;
import static com.feitianzhu.fu700.common.Constant.LOAD_SHOPS_TYPE;
import static com.feitianzhu.fu700.common.Constant.LOAD_SHOPS_WALLET;
import static com.feitianzhu.fu700.common.Constant.LOAD_USER_AUTH;
import static com.feitianzhu.fu700.common.Constant.LOGIN_USERID;
import static com.feitianzhu.fu700.common.Constant.LONGITUDE;
import static com.feitianzhu.fu700.common.Constant.MERCHANTID;
import static com.feitianzhu.fu700.common.Constant.MERCHANTNAME;
import static com.feitianzhu.fu700.common.Constant.ORDERNO;
import static com.feitianzhu.fu700.common.Constant.PAGEINDEX;
import static com.feitianzhu.fu700.common.Constant.PAGEROWS;
import static com.feitianzhu.fu700.common.Constant.PAYCHANNEL;
import static com.feitianzhu.fu700.common.Constant.PAYPASS;
import static com.feitianzhu.fu700.common.Constant.POST_COLLECT;
import static com.feitianzhu.fu700.common.Constant.POST_REALAUTH;
import static com.feitianzhu.fu700.common.Constant.POST_SHOPS_INFO;
import static com.feitianzhu.fu700.common.Constant.POST_SHOPS_TYPE;
import static com.feitianzhu.fu700.common.Constant.POST_SHOPS_VERI;
import static com.feitianzhu.fu700.common.Constant.POST_SHOP_PAY;
import static com.feitianzhu.fu700.common.Constant.PROVINCEID;
import static com.feitianzhu.fu700.common.Constant.PROVINCENAME;
import static com.feitianzhu.fu700.common.Constant.REALNAME;
import static com.feitianzhu.fu700.common.Constant.REGISTENO;
import static com.feitianzhu.fu700.common.Constant.REPORTCONTENT;
import static com.feitianzhu.fu700.common.Constant.SERVICEID;
import static com.feitianzhu.fu700.common.Constant.SERVICEPHONE;
import static com.feitianzhu.fu700.common.Constant.SERVICE_ORDERS;
import static com.feitianzhu.fu700.common.Constant.SHOP_EVA;
import static com.feitianzhu.fu700.common.Constant.SHOP_ORDERS;
import static com.feitianzhu.fu700.common.Constant.STAR;
import static com.feitianzhu.fu700.common.Constant.SuccessCode;
import static com.feitianzhu.fu700.common.Constant.THPASS;
import static com.feitianzhu.fu700.common.Constant.TYPE;
import static com.feitianzhu.fu700.common.Constant.UAPDATE;
import static com.feitianzhu.fu700.common.Constant.USERID;
import static com.feitianzhu.fu700.common.Constant.VALUEID;
import static com.feitianzhu.fu700.common.Constant.WX_PAY_URL;

/**
 * Created by dicallc on 2017/9/4 0004.
 */

public class ShopDao {
    /**
     * 提交商户验证数据
     */
    public static void PostDataToVeriShop(String mPhoto_file_four, String mYinyeCard,
                                          String mRegisterNum, String Faren, int mSelectIndex,
                                          final onConnectionFinishLinstener mLinstener) {
        String BUSINATUREs;
        if (mSelectIndex > 5) {
            BUSINATUREs = "10";
        } else {
            BUSINATUREs = mSelectIndex + "";
        }
        OkHttpUtils.post()//
                .addFile("certifFile", "01.png", new File(mPhoto_file_four))//
                .url(Common_HEADER + POST_SHOPS_VERI).addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                .addParams(USERID, LOGIN_USERID)//
                .addParams(BUSILICENSENAME, mYinyeCard)//
                .addParams(REGISTENO, mRegisterNum)//
                .addParams(LEGALPERSON, Faren)//
                .addParams(BUSINATURE, BUSINATUREs)//
                .build()//
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(String mData, Response response, int id)
                            throws Exception {
                        KLog.e(mData);
                        return null;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if ("数据为空".equals(e.getMessage())) {
                            mLinstener.onSuccess(1, "成功");
                            ToastUtils.showShortToast("商户验证成功");
                        } else {
                            mLinstener.onFail(FailCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                    }
                });
    }

    /**
     * 提交个人验证
     */
    public static void PostDataToVeriUser(String mPhoto_file_one, String mPhoto_file_two, String file,
                                          String mName, String mId_num, int mSelectIndex, Province mOnSelectProvince,
                                          final onConnectionFinishLinstener mLinstener) {
        String BUSINATUREs;
        if (mSelectIndex > 2) {
            BUSINATUREs = "10";
        } else {
            BUSINATUREs = mSelectIndex + "";
        }
        OkHttpUtils.post()//
                .addFile("certifFile", "01.png", new File(mPhoto_file_one))//
                .addFile("certifFile", "02.png", new File(mPhoto_file_two))//
                .addFile("certifFile", "03.png", new File(file))//
                .url(Common_HEADER + POST_REALAUTH).addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                .addParams(USERID, LOGIN_USERID)//
                .addParams(REALNAME, mName)//
                .addParams(CERTIFTYPE, BUSINATUREs)//
                .addParams(CERTIFNO, mId_num)//
                .addParams(PROVINCEID, mOnSelectProvince.id)//
                .addParams(PROVINCENAME, mOnSelectProvince.name)//
                .addParams(CITYID, mOnSelectProvince.pid)//
                .addParams(CITYNAME, mOnSelectProvince.city_name)//
                .build()//
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(String mData, Response response, int id)
                            throws Exception {
                        KLog.e(mData);
                        return null;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if ("数据为空".equals(e.getMessage())) {
                            mLinstener.onSuccess(1, "成功");
                        } else {
                            mLinstener.onFail(FailCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                    }
                });
    }

    /**
     * 获取个人信息
     */
    public static void loadUserInfo(final onConnectionFinishLinstener mLinstener) {
        OkHttpUtils.get()
                .addParams(ACCESSTOKEN, ACCESS_TOKEN)
                .addParams(USERID, LOGIN_USERID)
                .url(Common_HEADER + LOADER_USERINFO)
                .build()
                .execute(new MyInfomationCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mLinstener.onFail(1, e.getMessage());
                    }

                    @Override
                    public void onResponse(UserInformation response, int id) {
                        mLinstener.onSuccess(1, response);
                    }
                });
    }

    public static void loadUserVeriInfo(final onConnectionFinishLinstener mLinstener) {
        //UserVeriModel mUserVeriModel = new UserVeriModel(-1);
        //mLinstener.onSuccess(SuccessCode, mUserVeriModel);
        OkHttpUtils.get()
                .addParams(ACCESSTOKEN, ACCESS_TOKEN)
                .addParams(USERID, LOGIN_USERID)
                .url(Common_HEADER + LOADER_VERI_USER_INFO)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(String mData, Response response, int id)
                            throws Exception {
                        UserVeriModel user = new Gson().fromJson(mData, UserVeriModel.class);
                        return user;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mLinstener.onFail(FailCode, e.getMessage());
                    }

                    @Override
                    public void onResponse(Object response, int id) {
                        mLinstener.onSuccess(SuccessCode, response);
                    }
                });
    }

    public static void loadShopsVeriInfo(final onConnectionFinishLinstener mLinstener) {
        //ShopVeriModel mUserVeriModel = new ShopVeriModel(-1);
        //mLinstener.onSuccess(SuccessCode, mUserVeriModel);
        OkHttpUtils.get()
                .addParams(ACCESSTOKEN, ACCESS_TOKEN)
                .addParams(USERID, LOGIN_USERID)
                .url(Common_HEADER + LOADER_VERI_SHOPS_INFO)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(String mData, Response response, int id)
                            throws Exception {
                        ShopVeriModel user = new Gson().fromJson(mData, ShopVeriModel.class);
                        return user;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mLinstener.onFail(FailCode, e.getMessage());
                    }

                    @Override
                    public void onResponse(Object response, int id) {
                        mLinstener.onSuccess(SuccessCode, response);
                    }
                });
    }

    public static void postShopsInfo(final onConnectionFinishLinstener mLinstener, String avatar,
                                     String files, String merchantname, String dtladdr, String servicephone,
                                     Province mOnSelectProvince, String clsid, String introduce, MyPoint mPoint,String thPass) {
        OkHttpUtils.post()//
                .addFile(AVATAR, "01.png", new File(avatar))//
                .addFile(FILES, "02.png", new File(files))//
                .url(Common_HEADER + POST_SHOPS_INFO).addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                .addParams(USERID, LOGIN_USERID)//
                .addParams(MERCHANTNAME, merchantname)//
                .addParams(DTLADDR, dtladdr)//
                .addParams(SERVICEPHONE, servicephone)//
                .addParams(PROVINCEID, mOnSelectProvince.id)//
                .addParams(PROVINCENAME, mOnSelectProvince.name)//
                .addParams(CITYID, mOnSelectProvince.pid)//
                .addParams(CITYNAME, mOnSelectProvince.city_name)//
                .addParams(CLSID, clsid)//
                .addParams(AREAID, mOnSelectProvince.areaId)//
                .addParams(THPASS, thPass)//
                .addParams(AREANAME, mOnSelectProvince.areaName)//
                .addParams(INTRODUCE, introduce)//
                .addParams(LONGITUDE, mPoint.longitude + "")//
                .addParams(LATITUDE, mPoint.latitude + "")//
                .build()//
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(String mData, Response response, int id)
                            throws Exception {
                        return null;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if ("数据为空".equals(e.getMessage())) {
                            mLinstener.onSuccess(SuccessCode, "商铺创建成功");
                        } else {
                            mLinstener.onFail(FailCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {
                        KLog.e(response.toString());
                    }
                });
    }

    /**
     * 编辑商铺信息
     */
    public static void updateShopsInfo(final onConnectionFinishLinstener mLinstener, String avatar,
                                       String files, String merchantname, String dtladdr, String servicephone,
                                       Province mOnSelectProvince, int clsid, String introduce, List<ShopsType> mList,
                                       int mMerchantId,String thPass ) {
        PostFormBuilder mPost = OkHttpUtils.post();
        if (!TextUtils.isEmpty(avatar)) {
            mPost.addFile(AVATAR, "01.png", new File(avatar));
        }
        if (!TextUtils.isEmpty(files)) {
            mPost.addFile(FILES, "02.png", new File(files));
        }
        if (clsid != 0) {
            mPost.addParams(CLSID, mList.get(clsid - 1).clsId + "");
        }
        if (null != mOnSelectProvince) {
            mPost.addParams(PROVINCEID, mOnSelectProvince.id)//
                    .addParams(PROVINCENAME, mOnSelectProvince.name)//
                    .addParams(CITYID, mOnSelectProvince.pid)//
                    .addParams(CITYNAME, mOnSelectProvince.city_name)
                    .addParams(AREAID, mOnSelectProvince.areaId)//
                    .addParams(AREANAME, mOnSelectProvince.areaName);
        }
        mPost.setMultipart(true)
                .url(Common_HEADER + EDIT_SHOPS_INFO)
                .addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                .addParams(USERID, LOGIN_USERID)//
                .addParams(MERCHANTNAME, merchantname)//
                .addParams(MERCHANTID, mMerchantId + "")//
                .addParams(DTLADDR, dtladdr)//
                .addParams(THPASS, thPass)//
                .addParams(SERVICEPHONE, servicephone)//
                .addParams(INTRODUCE, introduce)//
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(String mData, Response response, int id)
                            throws Exception {
                        return null;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if ("数据为空".equals(e.getMessage())) {
                            mLinstener.onSuccess(SuccessCode, "成功");
                            ToastUtils.showShortToast("商铺编辑成功");
                        } else {
                            mLinstener.onFail(FailCode, e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {
                        KLog.e(response.toString());
                    }
                });
    }

    public static void loadShopsType(final onConnectionFinishLinstener mLinstener) {
        OkHttpUtils.get()
                .addParams(ACCESSTOKEN, ACCESS_TOKEN)
                .addParams(USERID, LOGIN_USERID)
                .url(Common_HEADER + POST_SHOPS_TYPE)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(String mData, Response response, int id)
                            throws Exception {
                        Type type = new TypeToken<List<ShopsType>>() {
                        }.getType();
                        List<ShopsType> list = new Gson().fromJson(mData, type);
                        return list;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mLinstener.onFail(FailCode, e.getMessage());
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                        mLinstener.onSuccess(SuccessCode, response);
                    }
                });
    }

    public static void loadShopsInfo(String merchantid,
                                     final onConnectionFinishLinstener mLinstener) {
        PostFormBuilder builder = OkHttpUtils.post()//
                .url(Common_HEADER + LOAD_SHOPS_INFO);
        if (!TextUtils.isEmpty(merchantid)) {
            builder.addParams(MERCHANTID, merchantid);
        }
        builder.addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                .addParams(USERID, LOGIN_USERID)//
                .build().execute(new Callback() {
            @Override
            public Object parseNetworkResponse(String mData, Response response, int id)
                    throws Exception {
                ShopsInfo user = new Gson().fromJson(mData, ShopsInfo.class);
                return user;
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                mLinstener.onFail(FailCode, e.getMessage());
            }

            @Override
            public void onResponse(Object response, int id) {
                mLinstener.onSuccess(SuccessCode, response);
            }
        });//
    }

    public static void PostCollect(int type, int id, final onNetFinishLinstenerT mLinstener) {
        OkHttpUtils.post()//
                .url(Common_HEADER + POST_COLLECT).addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                .addParams(USERID, LOGIN_USERID)//
                .addParams(TYPE, type + "")//
                .addParams(IDVALUE, id + "")//
                .build().execute(new BaseCallBackT(mLinstener) {
            @Override
            public Object parseNetworkResponse(String mData, Response response, int id) throws Exception {
                return mData;
            }
        });
    }

    public static void DeleteCollect(String id, final onConnectionFinishLinstener mLinstener) {
        OkHttpUtils.get()//
                .url(Common_HEADER + DELETE_COLLECT).addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                .addParams(USERID, LOGIN_USERID)//
                .addParams(COLLECTID, id + "")//
                .build().execute(new NoDataCallBack(mLinstener));
    }

    public static void LoadShopType(final onNetFinishLinstenerT mLinstener) {
        OkHttpUtils.get()//
                .url(Common_HEADER + LOAD_SHOPS_TYPE).addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                .addParams(USERID, LOGIN_USERID)//
                .build().execute(new BaseCallBackT(mLinstener) {
            @Override
            public List<ShopType> parseNetworkResponse(String mData, Response response, int id)
                    throws Exception {
                Type type = new TypeToken<List<ShopType>>() {
                }.getType();
                List<ShopType> list = new Gson().fromJson(mData, type);
                return list;
            }
        });
    }

    public static void LoadReCommondShop(String type, int index, String pagerows,
                                         final onNetFinishLinstenerT mLinstener) {
        OkHttpUtils.post()//
                .url(Common_HEADER + LOAD_RECMOOND_SHOP_LIST).addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                .addParams(USERID, LOGIN_USERID)//
                .addParams(TYPE, type)//
                .addParams(PAGEINDEX, index + "")//
                .addParams(PAGEROWS, pagerows)//
                .build().execute(new BaseCallBackT(mLinstener) {
            @Override
            public RecommndShopModel parseNetworkResponse(String mData, Response response, int id)
                    throws Exception {
                RecommndShopModel user = new Gson().fromJson(mData, RecommndShopModel.class);
                return user;
            }
        });
    }

    public static void LoadShopsIndex(String provinceId, String cityId,
                                      final onNetFinishLinstenerT<ShopsIndex> mLinstener) {
        GetBuilder mBuilder = OkHttpUtils.get()//
                .url(Common_HEADER + LOAD_SHOPS_INDEX);
        if (!TextUtils.isEmpty(provinceId)) {
            mBuilder.addParams(PROVINCEID, provinceId).addParams(CITYID, cityId);
        }
        mBuilder.addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                .addParams(USERID, LOGIN_USERID)//
                .addParams(LONGITUDE, Constant.mPoint.longitude + "")//
                .addParams(LATITUDE, Constant.mPoint.latitude + "")//
                .build().execute(new BaseCallBackObject(mLinstener, ShopsIndex.class) {
        });
    }

    public static void LoadNearbyShops(int page, String provinceId, String cityId, String clsId,
                                       final onNetFinishLinstenerT<ShopsNearby> mLinstener) {
        GetBuilder builder = OkHttpUtils.get()//
                .url(Common_HEADER + LOAD_NEARBY_SHOPS);
        if (!TextUtils.isEmpty(provinceId)) {
            builder.addParams(PROVINCEID, provinceId)//
                    .addParams(CITYID, cityId);
        }
        if (!TextUtils.isEmpty(clsId)) {
            builder.addParams(CLSID, clsId);
        }
        builder.addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                .addParams(USERID, LOGIN_USERID)//
                .addParams(LONGITUDE, Constant.mPoint.longitude + "")//
                .addParams(LATITUDE, Constant.mPoint.latitude + "")//
                .addParams(PAGEINDEX, page + "")//
                .addParams(PAGEROWS, 10 + "")//
                .build().execute(new BaseCallBackObject(mLinstener, ShopsNearby.class) {
        });
    }

    public static void LoadShopsSearchData(int page, String result,
                                           final onNetFinishLinstenerT<RecommndShopModel> mLinstener) {
        OkHttpUtils.get()//
                .url(Common_HEADER + LOAD_SHOPS_SEARCH_DATA).addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                .addParams(USERID, LOGIN_USERID)//
                .addParams(LONGITUDE, Constant.mPoint.longitude + "")//
                .addParams(LATITUDE, Constant.mPoint.latitude + "")//
                .addParams(PAGEINDEX, page + "")//
                .addParams(PAGEROWS, 10 + "")//
                .addParams(MERCHANTNAME, result)//
                .build().execute(new BaseCallBackObject(mLinstener, RecommndShopModel.class) {
        });
    }

    public static void loadShopsServiceEvalu(int page, String mMerchantId, final onNetFinishLinstenerT<ShopsEvali> mLinstener) {
        PostFormBuilder mBuilder = OkHttpUtils.post()//
                .url(Common_HEADER + LOAD_SHOPS_EVALUTE);
        if (!TextUtils.isEmpty(mMerchantId))
            mBuilder.addParams(MERCHANTID, mMerchantId);
        mBuilder.addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                .addParams(USERID, LOGIN_USERID)//
                .addParams(PAGEINDEX, "1")//
                .addParams(PAGEROWS, "50")//
                .build().execute(new BaseCallBackObject(mLinstener, ShopsEvali.class) {
        });
    }

    public static void loadShopsServiceInfo(int page, String merchantId,
                                            final onNetFinishLinstenerT<ShopsService> mLinstener) {
        PostFormBuilder mBuilder = OkHttpUtils.post()//
                .url(Common_HEADER + LOAD_SHOPS_SERVICES);
        if (!TextUtils.isEmpty(merchantId))
            mBuilder.addParams(MERCHANTID, merchantId);

        mBuilder.addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                .addParams(USERID, LOGIN_USERID)//
                .addParams(PAGEINDEX, page + "")//
                .addParams(PAGEROWS, "10")//
                .build().execute(new BaseCallBackObject(mLinstener, ShopsService.class) {
        });
    }

    public static void loadMyWalletInfo(final onNetFinishLinstenerT<WalletModel> mLinstener) {
        OkHttpUtils.post()//
                .url(Common_HEADER + LOAD_SHOPS_WALLET).addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                .addParams(USERID, LOGIN_USERID)//
                .build().execute(new BaseCallBackObject(mLinstener, WalletModel.class) {
        });
    }

    public static void loadUserAuth(final onNetFinishLinstenerT<UserAuth> mLinstener) {
        OkHttpUtils.post()//
                .url(Common_HEADER + LOAD_USER_AUTH).addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                .addParams(USERID, LOGIN_USERID)//
                .build().execute(new BaseCallBackObject(mLinstener, UserAuth.class) {
        });
    }

    /**
     * 获取用户授权信息
     */
    public static void loadUserAuthImpl() {
        Constant.loadUserAuth = false;
        Constant.mUserAuth = null;
        loadUserAuth(new onNetFinishLinstenerT<UserAuth>() {
            @Override
            public void onSuccess(int code, UserAuth result) {
              Constant.mUserAuth = result;
              Constant.loadUserAuth = true;
            }

            @Override
            public void onFail(int code, String result) {
                Constant.loadUserAuth = false;
            }
        });
    }

    public static void loadFUFriend(String index,
                                    final onNetFinishLinstenerT<FuFriendModel> mLinstener) {
        OkHttpUtils.post()//
                .url(Common_HEADER + LOAD_FU_FRIEND).addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                .addParams(USERID, Constant.LOGIN_USERID)//
                .addParams(PAGEINDEX, index)//
                .addParams(PAGEROWS, 10 + "")//
                .addParams(PAGEROWS, 10 + "")//
                .build().execute(new BaseCallBackObject(mLinstener, FuFriendModel.class) {
        });
    }

    public static void postShopPay(String merchantid, String consumeamount, String payChannel,
                                   String mPassowrd, final onConnectionFinishLinstener mLinstener) {
        if ("alipay".equals(payChannel)) {
            OkHttpUtils.post()//
                    .url(Common_HEADER + POST_SHOP_PAY).addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                    .addParams(USERID, LOGIN_USERID)//
                    .addParams(MERCHANTID, merchantid)//
                    .addParams(CONSUMEAMOUNT, consumeamount)//
                    .addParams(PAYCHANNEL, payChannel)//
                    .addParams(PAYPASS, mPassowrd)//
                    .build().execute(new Callback() {
                @Override
                public Object parseNetworkResponse(String mData, Response response, int id) throws Exception {
                    JSONObject object = new JSONObject(mData);
                    String payParam = object.getString("payParam");

                    return payParam;
                }

                @Override
                public void onError(Call call, Exception e, int id) {
                    mLinstener.onFail(FailCode,e.getMessage());
                }

                @Override
                public void onResponse(Object response, int id) {
                    mLinstener.onSuccess(SuccessCode,response.toString());
                }
            });
        } else {
            OkHttpUtils.post()//
                    .url(Common_HEADER + POST_SHOP_PAY).addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                    .addParams(USERID, LOGIN_USERID)//
                    .addParams(MERCHANTID, merchantid)//
                    .addParams(CONSUMEAMOUNT, consumeamount)//
                    .addParams(PAYCHANNEL, payChannel)//
                    .addParams(PAYPASS, mPassowrd)//
                    .build().execute(new NoDataCallBack(mLinstener));
        }

    }

  public static void postShopWxPay(String merchantid, String consumeamount, String payChannel,
      String mPassowrd, final onNetFinishLinstenerT mLinstener) {
      OkHttpUtils.post()//
          .url(Common_HEADER + POST_SHOP_PAY).addParams(ACCESSTOKEN, ACCESS_TOKEN)//
          .addParams(USERID, LOGIN_USERID)//
          .addParams(MERCHANTID, merchantid)//
          .addParams(CONSUMEAMOUNT, consumeamount)//
          .addParams(PAYCHANNEL, payChannel)//
          .addParams(PAYPASS, mPassowrd)//
          .build().execute(new BaseCallBackObject(mLinstener, WXModel.class) {});

  }

  public static void postShopReport(String id, String type, String reportContent,
                                      final onConnectionFinishLinstener mLinstener) {
        OkHttpUtils.post()//
                .url(Common_HEADER + JUBAO).addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                .addParams(USERID, LOGIN_USERID)//
                .addParams(VALUEID, id)//
                .addParams(TYPE, type)//
                .addParams(REPORTCONTENT, reportContent)//
                .build().execute(new NoDataCallBack(mLinstener));
    }

    /**
     * 应用升级
     */
    public static void updateApp(final onNetFinishLinstenerT<UpdateAppModel> mLinstener) {
        OkHttpUtils.get()//
                .url(Common_HEADER + UAPDATE).addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                .addParams(USERID, LOGIN_USERID)//
                .addParams(TYPE, "1")//
                .build().execute(new BaseCallBackObject(mLinstener, UpdateAppModel.class) {
        });
    }

    public static void loadShopOrderList(int page,
                                         final onNetFinishLinstenerT<ShopOrderModel> mLinstener) {
        OkHttpUtils.get()//
                .url(Common_HEADER + SHOP_ORDERS).addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                .addParams(USERID, LOGIN_USERID)//
                .addParams(PAGEINDEX, page + "")//
                .addParams(PAGEROWS, "10")//
                .build().execute(new BaseCallBackObject(mLinstener, ShopOrderModel.class) {
        });
    }

    public static void loadServeOrderList(int page,
                                          final onNetFinishLinstenerT<ServeOrderModel> mLinstener) {
        OkHttpUtils.get()//
                .url(Common_HEADER + SERVICE_ORDERS).addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                .addParams(USERID, LOGIN_USERID)//
                .addParams(PAGEINDEX, page + "")//
                .addParams(PAGEROWS, "10")//
                .build().execute(new BaseCallBackObject(mLinstener, ServeOrderModel.class) {
        });
    }

    public static void deleteServe(int id, final onConnectionFinishLinstener mLinstener) {
        OkHttpUtils.get()//
                .url(Common_HEADER + DELETE_SERVICE).addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                .addParams(USERID, LOGIN_USERID)//
                .addParams(SERVICEID, id + "")//
                .build().execute(new NoDataCallBack(mLinstener) {
        });
    }

    public static void postWxPay(String money,String chanel, final onNetFinishLinstenerT<WXModel> mLinstener) {
        OkHttpUtils.get()//
                .url(Common_HEADER + WX_PAY_URL).addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                .addParams(USERID, LOGIN_USERID)//
                .addParams(AMOUNT, money + "")//
                .addParams(CHANNEL, chanel)//
                .build().execute(new BaseCallBackObject(mLinstener, WXModel.class) {
        });
    }
    public static void postAliPayPay(String money, final onConnectionFinishLinstener mLinstener) {
        OkHttpUtils.get()//
                .url(Common_HEADER + WX_PAY_URL).addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                .addParams(USERID, LOGIN_USERID)//
                .addParams(AMOUNT, money + "")//
                .addParams(CHANNEL, "alipay")//
                .build().execute(new AliPayCallBack(mLinstener));
    }
    public static void postShopEva(String orderNo,String content,String star, final onConnectionFinishLinstener mLinstener) {
        OkHttpUtils.get()//
                .url(Common_HEADER + SHOP_EVA).addParams(ACCESSTOKEN, ACCESS_TOKEN)//
                .addParams(USERID, LOGIN_USERID)//
                .addParams(ORDERNO, orderNo + "")//
                .addParams(CONTENT, content+"")//
                .addParams(STAR, star+"")//
                .build().execute(new NoDataCallBack(mLinstener));
    }
}
