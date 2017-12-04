package com.feitianzhu.fu700.me.ui.totalScore;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.dao.NetworkDao;
import com.feitianzhu.fu700.me.base.BaseTakePhotoActivity;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.me.ui.ShopRecordDetailActivity;
import com.feitianzhu.fu700.model.OfflineModel;
import com.feitianzhu.fu700.model.SelectPayNeedModel;
import com.feitianzhu.fu700.shop.ShopDao;
import com.feitianzhu.fu700.shop.ShopHelp;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.jph.takephoto.model.TResult;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.feitianzhu.fu700.common.Constant.ACCESSTOKEN;
import static com.feitianzhu.fu700.common.Constant.Common_HEADER;
import static com.feitianzhu.fu700.common.Constant.POST_SHOP_RECORD_SEND;
import static com.feitianzhu.fu700.common.Constant.USERID;

/**
 * Created by Vya on 2017/9/5 0005.
 * 转账并上传凭证界面
 */

public class TransferVoucherActivity extends BaseTakePhotoActivity {
    @BindView(R.id.iv_selectIcon)
    ImageView mImageView;
    @BindView(R.id.tv_top)
    TextView mTvTop;
    @BindView(R.id.tv_middle)
    TextView mTvMiddle;
    @BindView(R.id.tv_bottom)
    TextView mTvBottom;
    @BindView(R.id.iv_icon)
    ImageView mIcon;

    private SelectPayNeedModel mModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transfer_voucher;
    }

    @Override
    protected void initTitle() {
        defaultNavigationBar = new DefaultNavigationBar
                .Builder(TransferVoucherActivity.this, (ViewGroup) findViewById(R.id.Rl_titleContainer))
                .setTitle("转账并上传凭证")
                .setStatusHeight(TransferVoucherActivity.this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }

    @Override
    protected void initView() {
        ShopDao.loadUserAuthImpl();
        Intent intent = getIntent();
        mModel = (SelectPayNeedModel) intent.getSerializableExtra("transferNeedModel");
    }

    @Override
    protected void initData() {
        requestData();
    }

    private void requestData() {
        showloadDialog("正在加载...");
        NetworkDao.getOfflineInfo(new onConnectionFinishLinstener() {
            @Override
            public void onSuccess(int code, Object result) {
                goneloadDialog();
                if(result != null){
                    OfflineModel mData = (OfflineModel)result;
                    if(mData != null){
                        mTvTop.setText(mData.getOpenBank()+" "+mData.getOpenSubbranch());
                        mTvMiddle.setText(mData.getAccName());
                        mTvBottom.setText(mData.getAccNo());
                        Glide.with(TransferVoucherActivity.this).load(mData.getBankLogo())
                                .placeholder(R.drawable.pic_fuwutujiazaishibai).into(mIcon);
                    }
                }
            }

            @Override
            public void onFail(int code, String result) {
                goneloadDialog();
            }
        });
    }

    @OnClick({R.id.iv_selectIcon, R.id.bt_send})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_selectIcon:
                showDialog();
                break;
            case R.id.bt_send:
                sendRequest();
                break;
        }
    }

    /**
     * 线下支付
     */
    private void sendRequest() {
        if (mModel == null) {
            ToastUtils.showShortToast("获取的参数异常，请重试!");
            return;
        }
        if (TextUtils.isEmpty(mModel.getPayProofFile())) {
            ToastUtils.showShortToast("线下支付必须要上传凭证，请重试!");
            return;
        }

        ShopHelp.veriPassword(this, new onConnectionFinishLinstener() {
            @Override
            public void onSuccess(int code, Object result) {

                showloadDialog("支付中...");

                if (mModel.getType() == SelectPayNeedModel.TYPE_SHOP_RECORD) {
                    PostFormBuilder mPost = OkHttpUtils.post();
                    mPost.url(Common_HEADER + POST_SHOP_RECORD_SEND)
                            .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                            .addParams(USERID, Constant.LOGIN_USERID)
                            .addParams("memberId", mModel.getMemberId())
                            .addParams("consumeAmount", mModel.getConsumeAmount() + "")
                            .addParams("handleFee", mModel.getHandleFee() + "")
                            .addParams("feeId", mModel.getFeeId())
                            .addParams("payPass", result.toString())  //二级密码 线下支付可以不传
                            .addParams("payChannel", mModel.getPayChannel())
                            .addFile("payProofFile", "payProofFile.png", new File(mModel.getPayProofFile()))  //线下支付，必须要传
                            .addFile("placeImgFile", "placeImgFile.png", new File(mModel.getPlaceImgFile()))// 消费场所
                            .addFile("objImgFile", "objImgFile.png", new File(mModel.getObjImgFile()))// 消费实物
                            .addFile("rcptImgFile", "rcptImgFile.png", new File(mModel.getRcptImgFile()))// 消费发票
                            .build()
                            .execute(new Callback() {
                                @Override
                                public Object parseNetworkResponse(String mData, Response response, int id)
                                        throws Exception {
                                    return mData;
                                }

                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    goneloadDialog();
                                    if ("数据为空".equals(e.getMessage())) {
                                        ToastUtils.showShortToast("支付成功");
                                        Intent Recordintent = new Intent(TransferVoucherActivity.this, ShopRecordDetailActivity.class);
                                        startActivity(Recordintent);
                                        finish();
                                    } else {
                                        ToastUtils.showShortToast("支付失败");
                                    }
                                }

                                @Override
                                public void onResponse(Object response, int id) {
                                    goneloadDialog();
                                }
                            });
                } else if (mModel.getType() == SelectPayNeedModel.TYPE_PAY_FOR_ME) {

                    NetworkDao.payForMe(result.toString(), mModel.getMerchantName(), mModel.getMerchantAddr(), mModel.getGoodsName()
                            , mModel.getConsumeAmount() + "", mModel.getHandleFee() + "", mModel.getPayChannel(),
                            mModel.getPlaceImgFile(), mModel.getObjImgFile(), mModel.getRcptImgFile(), mModel.getPayProofFile(), new onConnectionFinishLinstener() {
                                @Override
                                public void onSuccess(int code, Object result) {
                                    goneloadDialog();
                                    ToastUtils.showShortToast("支付成功");
                                    finish();
                                }

                                @Override
                                public void onFail(int code, String result) {
                                    goneloadDialog();
                                    ToastUtils.showShortToast(result);
                                }
                            });
                } else if (mModel.getType() == SelectPayNeedModel.TYPE_UNION_LEVEL) {
                    String gradeid = "";
                    if(mModel!=null&&mModel.gradeId!=null){
                        gradeid = mModel.gradeId;
                    }else {
                        gradeid = "";
                    }
                    PostFormBuilder mPost = OkHttpUtils.post();
                    mPost.url(Common_HEADER + Constant.POST_UNION_LEVEL_PAY)
                            .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                            .addParams(USERID, Constant.LOGIN_USERID)
                            .addParams("gradeId",gradeid)
                            .addParams("payPass", result.toString())//二级密码 线下支付可以不传
                            .addParams("payChannel", mModel.getPayChannel())
                            .addFile("payProofFile", "payLevelProofFile.png", new File(mModel.getPayProofFile()))  //线下支付，必须要传
                            .build()
                            .execute(new Callback() {
                                @Override
                                public Object parseNetworkResponse(String mData, Response response, int id)
                                        throws Exception {
                                    return mData;
                                }

                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    goneloadDialog();
                                    if ("数据为空".equals(e.getMessage())) {
                                        ToastUtils.showShortToast("支付成功");
                                        finish();
                                    } else {
                                        ToastUtils.showShortToast("支付失败");
                                    }
                                }

                                @Override
                                public void onResponse(Object response, int id) {
                                    goneloadDialog();
                                }
                            });
                } else if (mModel.getType() == SelectPayNeedModel.TYPE_HUANGHUALI) {
                    
                    NetworkDao.buyHuangHuaLi(mModel.getPayProofFile(), new onConnectionFinishLinstener() {
                        @Override
                        public void onSuccess(int code, Object result) {
                            goneloadDialog();
                            ToastUtils.showShortToast("支付成功");
                            finish();
                        }

                        @Override
                        public void onFail(int code, String result) {
                            goneloadDialog();
                            ToastUtils.showShortToast(result);
                        }
                    });
                }
            }

            @Override
            public void onFail(int code, String result) {
                ToastUtils.showShortToast(result);
            }
        });
    }

    @Override
    public void takeSuccess(TResult result) {
        String compressPath = result.getImage().getCompressPath();
        Glide.with(this).load(compressPath).into(mImageView);
        if (!TextUtils.isEmpty(compressPath)) {
            mModel.setPayProofFile(compressPath);
        }
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }
}
