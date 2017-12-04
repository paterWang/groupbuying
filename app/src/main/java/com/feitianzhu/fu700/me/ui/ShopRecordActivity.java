package com.feitianzhu.fu700.me.ui;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.me.base.BaseTakePhotoActivity;
import com.feitianzhu.fu700.me.helper.DialogHelper;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.me.ui.totalScore.SelectPayActivity;
import com.feitianzhu.fu700.model.PromotionPercentModel;
import com.feitianzhu.fu700.model.SelectPayNeedModel;
import com.feitianzhu.fu700.utils.MathUtils;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jph.takephoto.model.TResult;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.feitianzhu.fu700.common.Constant.ACCESSTOKEN;
import static com.feitianzhu.fu700.common.Constant.ACCESS_TOKEN;
import static com.feitianzhu.fu700.common.Constant.Common_HEADER;
import static com.feitianzhu.fu700.common.Constant.POST_SEARCH_PERCENT;
import static com.feitianzhu.fu700.common.Constant.POST_SHOP_RECORED_RETRY;
import static com.feitianzhu.fu700.common.Constant.USERID;

/**
 * Created by Vya on 2017/8/30 0030.
 */

public class ShopRecordActivity extends BaseTakePhotoActivity {

    @BindView(R.id.et_VipId)
    EditText mVipId;
    @BindView(R.id.et_ConsumptionAmount)
    EditText mConsumptionAmount;
    @BindView(R.id.tv_percent)
    TextView mPercent;

    @BindView(R.id.tv_poundage)
    TextView mPoundage; //手续费
    @BindView(R.id.tv_showPayTxt)
    TextView mShowPayTxt; //应付金额

    @BindView(R.id.iv_add_pic1)
    ImageView mIvPic1;
    @BindView(R.id.iv_add_pic2)
    ImageView mIvPic2;
    @BindView(R.id.iv_add_pic3)
    ImageView mIvPic3;


    private List<String> mData;
    private double currentPercent;
    private int clickType = -1;

    private List<String> mPicList;
    private double showTxt = 0;

    private List<PromotionPercentModel> mList;
    private int feedId = 0;
    private String OrderNo,RetryMemberId;
    private String ConsumeAmount;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_record;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        OrderNo = intent.getStringExtra("OrderNo");
        RetryMemberId = intent.getStringExtra("RetryMemberId");
        ConsumeAmount = intent.getStringExtra("ConsumeAmount");
        if(RetryMemberId != null){
            mVipId.setText(RetryMemberId);
        }
        if(ConsumeAmount != null){
            mConsumptionAmount.setText(ConsumeAmount);
        }
        mData = new ArrayList<>();
        mPicList = new ArrayList<>();
        mList = new ArrayList<>();
        requestPercentData();
    }

    /**
     * 请求比例
     */
    private void requestPercentData() {
        OkHttpUtils.post()
                .url(Common_HEADER + POST_SEARCH_PERCENT)
                .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                .addParams(USERID, Constant.LOGIN_USERID)
                .build()
                .execute(new Callback<List<PromotionPercentModel>>() {
                    @Override
                    public List<PromotionPercentModel> parseNetworkResponse(String mData, Response response, int id)
                            throws Exception {
                        Type type = new TypeToken<List<PromotionPercentModel>>() {
                        }.getType();
                        List<PromotionPercentModel> bean = new Gson().fromJson(mData, type);
                        return bean;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(List<PromotionPercentModel> response, int id) {
                        if(response == null){
                            return;
                        }
                        mList.addAll(response);
                        for (PromotionPercentModel item : response) {
                            mData.add(item.getRate() + "%");
                        }
                        if(mList.size()>0){
                            feedId = mList.get(0).getFeeId();
                            mPercent.setText(mList.get(0).getRate()+"%");
                            currentPercent = mList.get(0).getRate();

                        }

                    }
                });

    }

    @Override
    protected void initData() {
        //消费金额*策划推广比例=商家应付金额=应付手续费
       /* mPercent.setText("20%");
        currentPercent = 20;*/
        mConsumptionAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("Test","打印------》str==="+s.toString()+"-----currentPercent++++"+currentPercent);
                if (!TextUtils.isEmpty(s)) {
                    inputNum = Double.valueOf(s.toString());
                    if (inputNum > 0) {
                        showTxt = MathUtils.divide(MathUtils.multiply(inputNum, currentPercent), 100);
                        mShowPayTxt.setText(showTxt + "");
                        mPoundage.setText(showTxt + "");
                    } else {
                        mShowPayTxt.setText("0");
                        mPoundage.setText("0");
                    }


                }
            }
        });
    }

    private double inputNum;
    private int dialogClickPos = 1;

    @OnClick({R.id.tv_percent, R.id.ll_addPic1, R.id.ll_addPic2, R.id.ll_addPic3, R.id.rl_SureButton})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_percent:
                new DialogHelper(ShopRecordActivity.this).init(DialogHelper.DIALOG_ONE, v).buildDialog(mData, new DialogHelper.OnButtonClickPosListener<String>() {
                    @Override
                    public void onButtonClick(int position, String result, View clickView) {
                        dialogClickPos = position;
                        feedId = mList.get(position).getFeeId();
                        mPercent.setText(result);
                        if (result.contains("%")) {
                            String temp = result.replace("%", "");
                            currentPercent = Double.valueOf(temp);
                            showTxt = MathUtils.divide(MathUtils.multiply(inputNum, currentPercent), 100);
                            mShowPayTxt.setText(showTxt + "");
                            mPoundage.setText(showTxt + "");
                            Log.e("wangyan", "currentPercent====" + currentPercent);
                        }


                    }
                });
                break;
            case R.id.ll_addPic1:
                clickType = 1;
                showDialog();
                break;

            case R.id.ll_addPic2:
                clickType = 2;
                showDialog();
                break;

            case R.id.ll_addPic3:
                clickType = 3;
                showDialog();
                break;
            case R.id.rl_SureButton:
                if(!TextUtils.isEmpty(OrderNo)&&!TextUtils.isEmpty(RetryMemberId)){
                    //重新提交订单
                    showloadDialog("正在提交...");
                    RetryUpdateOrder();
                    return;
                }



                if (mPicList.size() <3) {
                    ToastUtils.showShortToast("请上传完整图片");
                    return;
                }
                Intent mintent = new Intent(ShopRecordActivity.this, SelectPayActivity.class);
                //通过SelectPayNeedModel封装参数传递过去
                SelectPayNeedModel selectPayNeedModel = new SelectPayNeedModel();
                Log.e("wangyan","showText----"+showTxt);
                if(mPicList.size()<3){
                    ToastUtils.showLongToast("必须上传完整的图片信息!");
                    return;
                }
                if(TextUtils.isEmpty(mVipId.getText())||inputNum<=0||showTxt<=0){
                    ToastUtils.showLongToast("会员编号和金额必须填写!");
                    return;
                }
                selectPayNeedModel.setAccessToken(ACCESS_TOKEN)
                        .setUserId(USERID)
                        .setMemberId(mVipId.getText().toString())
                        .setConsumeAmount(inputNum)
                        .setHandleFee(showTxt)
                        .setFeeId(feedId + "")
                        .setType(SelectPayNeedModel.TYPE_SHOP_RECORD)
                        .setPlaceImgFile(mPicList.get(0))
                        .setObjImgFile(mPicList.get(1))
                        .setRcptImgFile(mPicList.get(2)).setPayChannel("");
                mintent.putExtra(Constant.INTENT_SELECTET_PAY_MODEL, selectPayNeedModel);

                startActivity(mintent);
                finish();

                break;

        }
    }

    private void RetryUpdateOrder() {
        PostFormBuilder mPost = OkHttpUtils.post();
        mPost.setMultipart(true);
        if(!TextUtils.isEmpty(pic1)||!TextUtils.isEmpty(pic2)||!TextUtils.isEmpty(pic3)){
            mPost.addFile("placeImgFile", "placeImgFile.png", new File(pic1))// 消费场所
                    .addFile("objImgFile", "objImgFile.png", new File(pic2))// 消费实物
                    .addFile("rcptImgFile", "rcptImgFile.png", new File(pic3));// 消费发票
        }else{
            ToastUtils.showLongToast("必须要上传图片");
            return;
        }

        mPost.url(Common_HEADER + POST_SHOP_RECORED_RETRY)
                .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                .addParams(USERID, Constant.LOGIN_USERID)
                .addParams("orderNo", OrderNo)
                .addParams("memberId", RetryMemberId)
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

                            ToastUtils.showShortToast("提交成功!");
                            Intent intent = new Intent(ShopRecordActivity.this,ShopRecordDetailActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            ToastUtils.showShortToast(e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {
                        goneloadDialog();
                        if (response == null) {
                            ToastUtils.showShortToast("提交失败!!");
                            return;
                        }
                        ToastUtils.showShortToast("提交成功!");
                    }
                });

    }


    @Override
    protected void initTitle() {
        defaultNavigationBar = new DefaultNavigationBar
                .Builder(ShopRecordActivity.this, (ViewGroup) findViewById(R.id.Rl_titleContainer))
                .setTitle("商家录单")
                .setStatusHeight(ShopRecordActivity.this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .setRightText("记录", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ShopRecordActivity.this, ShopRecordDetailActivity.class);
                        startActivity(intent);
                    }
                })
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);

    }
    private String pic1,pic2,pic3;
    @Override
    public void takeSuccess(TResult result) {
        String compressPath = result.getImage().getCompressPath();
        switch (clickType) {
            case 1:
                pic1 = compressPath;
                mPicList.add(compressPath);
                Glide.with(mContext).load(compressPath).into(mIvPic1);
                break;
            case 2:
                pic2 = compressPath;
                mPicList.add(compressPath);
                Glide.with(mContext).load(compressPath).into(mIvPic2);
                break;
            case 3:
                pic3 = compressPath;
                mPicList.add(compressPath);
                Glide.with(mContext).load(compressPath).into(mIvPic3);
                break;
        }
    }

    @Override
    public void takeCancel() {
        Toast.makeText(ShopRecordActivity.this, "takeCancel", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        Toast.makeText(ShopRecordActivity.this, "takeFail", Toast.LENGTH_SHORT).show();
    }
}
