package com.feitianzhu.fu700.me.ui;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.me.base.BaseTakePhotoActivity;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TResult;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.feitianzhu.fu700.common.Constant.ACCESSTOKEN;
import static com.feitianzhu.fu700.common.Constant.Common_HEADER;
import static com.feitianzhu.fu700.common.Constant.POST_PUSH_SHOPSERVICE;
import static com.feitianzhu.fu700.common.Constant.USERID;

/**
 * Created by Vya on 2017/8/31 0031.
 */

public class PushServiceActivity extends BaseTakePhotoActivity{
    @BindView(R.id.et_TradeName)
    EditText mTradeName;
    @BindView(R.id.et_TradePrice)
    EditText mTradePrice;
    @BindView(R.id.et_TradeBenefit)
    EditText mTradeBenefit;
    @BindView(R.id.ll_addCover)
    LinearLayout mAddCover;
    @BindView(R.id.et_contact)
    EditText mContact;
    @BindView(R.id.et_contactNum)
    EditText mContactNum;
    @BindView(R.id.et_contactAddress)
    EditText mContactAddress;
    @BindView(R.id.et_TradeDesc)
    EditText mTradeDesc;
    @BindView(R.id.iv_add_cover)
    ImageView mIvCover;
    @BindView(R.id.iv_addPic1)
    ImageView mIvPic1;
    @BindView(R.id.iv_addPic2)
    ImageView mIvPic2;
    @BindView(R.id.iv_addPic3)
    ImageView mIvPic3;
    @BindView(R.id.iv_addPic4)
    ImageView mIvPic4;
    @BindView(R.id.iv_addPic5)
    ImageView mIvPic5;

    @BindView(R.id.ll_addPic2)
    LinearLayout ll_addPic2;
    @BindView(R.id.ll_addPic3)
    LinearLayout ll_addPic3;
    @BindView(R.id.ll_addPic4)
    LinearLayout ll_addPic4;
    @BindView(R.id.ll_addPic5)
    LinearLayout ll_addPic5;



    protected InvokeParam invokeParam;
    private  int ClickType = -1;
    private String avatar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_push_service;
    }

    @Override
    protected void initTitle() {
        defaultNavigationBar = new DefaultNavigationBar
                .Builder(PushServiceActivity.this, (ViewGroup)findViewById(R.id.Rl_titleContainer))
                .setTitle("发布服务")
                .setStatusHeight(PushServiceActivity.this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }

    @Override
    protected void initView() {

    }


    @Override
    protected void initData() {
        mPicList = new HashMap<>();
    }
    private Map<String,File> mPicList;
    @OnClick({R.id.ll_addCover,R.id.ll_addPic1,R.id.ll_addPic2,
            R.id.ll_addPic3,R.id.ll_addPic4,R.id.ll_addPic5,
            R.id.bt_send})
    public void onClick(View v){
        switch (v.getId()){

            case R.id.ll_addCover:
                ClickType = 111;
                showDialog();
                break;

            case R.id.ll_addPic1:
                ClickType = 1;
                showDialog();
                break;
            case R.id.ll_addPic2:
                ClickType = 2;
                showDialog();
                break;
            case R.id.ll_addPic3:
                ClickType = 3;
                showDialog();
                break;
            case R.id.ll_addPic4:
                ClickType = 4;
                showDialog();
                break;
            case R.id.ll_addPic5:
                ClickType = 5;
                showDialog();
                break;

            case R.id.bt_send:
                requestData();
                break;
        }
    }


        /*
        * accessToken 是 String 登录凭证
          userId 是 number 用户id
          serviceName 是 string 名称
          price 是 double 价格
          rebate 是 double 让利（PV）
          contactTel 是 string 联系电话
          contactPerson 是 string 联系人
          serviceAddr 是 string 联系地址
          serviceDesc 是 string 描述
          cover 否 File 封面
          goodsPic 否 File[] 图片
          */
    /**
     * 发布服务
     */
    private void requestData() {
        if(avatar == null){
            avatar= "";
        }
        if(TextUtils.isEmpty(mTradeName.getText())||TextUtils.isEmpty(mTradePrice.getText())
                || TextUtils.isEmpty(mTradeBenefit.getText())||TextUtils.isEmpty(mContact.getText())
                || TextUtils.isEmpty(mContactNum.getText())||TextUtils.isEmpty(mContactAddress.getText())
                || TextUtils.isEmpty(mTradeDesc.getText())){
            ToastUtils.showShortToast("有内容未填写，必须填写后才能发布!");
            return;
        }
        showloadDialog("正在发布...");
        File mCoverFile =  new File(avatar);
        if(!mCoverFile.exists()){
            ToastUtils.showShortToast("必须要上传封面图片");
            return;
        }
        PostFormBuilder mPost = OkHttpUtils.post();
        mPost.addFile("cover", "avatarAAA.png", mCoverFile)//
              //  .addFile("goodsPic", "filesBBB.png", new File(files))//
                .addFiles("goodsPic",mPicList)
                .url(Common_HEADER + POST_PUSH_SHOPSERVICE)
                .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                .addParams(USERID, Constant.LOGIN_USERID)
                .addParams("serviceName",mTradeName.getText().toString())
                .addParams("price",mTradePrice.getText().toString())
                .addParams("rebate",mTradeBenefit.getText().toString())
                .addParams("contactPerson",mContact.getText().toString())
                .addParams("contactTel",mContactNum.getText().toString())
                .addParams("serviceAddr",mContactAddress.getText().toString())
                .addParams("serviceDesc",mTradeDesc.getText().toString())
                .build()
                .execute(new Callback() {
                    @Override public Object parseNetworkResponse(String mData, Response response, int id)
                            throws Exception {
                        return mData;
                    }
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if ("数据为空".equals(e.getMessage())) {
                            ToastUtils.showShortToast("发布成功");
                            goneloadDialog();
                            finish();
                        } else {
                            ToastUtils.showShortToast(e.getMessage());
                            goneloadDialog();
                        }

                    }

                    @Override
                    public void onResponse(Object response, int id) {
                       Log.e("Test","--------response------>"+response.toString());
                        goneloadDialog();
                    }
                });
    }


    @Override
    public void takeSuccess(TResult result) {
        String compressPath = result.getImage().getCompressPath();
        if(compressPath == null){
            compressPath = "";
        }
        switch (ClickType){
            case 111:  //添加封面
                avatar = compressPath;
                Glide.with(mContext).load(compressPath).into(mIvCover);
                break;
            case 1:  //添加图片
                mPicList.put("Picture"+ClickType+".png",new File(compressPath));
                Glide.with(mContext).load(compressPath).into(mIvPic1);
                showNextButton(ClickType);
                break;
            case 2:  //添加图片
                mPicList.put("Picture"+ClickType+".png",new File(compressPath));
                Glide.with(mContext).load(compressPath).into(mIvPic2);
                showNextButton(ClickType);
                break;
            case 3:  //添加图片
                mPicList.put("Picture"+ClickType+".png",new File(compressPath));
                Glide.with(mContext).load(compressPath).into(mIvPic3);
                showNextButton(ClickType);
                break;
            case 4:  //添加图片
                mPicList.put("Picture"+ClickType+".png",new File(compressPath));
                Glide.with(mContext).load(compressPath).into(mIvPic4);
                showNextButton(ClickType);
                break;
            case 5:  //添加图片
                mPicList.put("Picture"+ClickType+".png",new File(compressPath));
                Glide.with(mContext).load(compressPath).into(mIvPic5);
                break;
        }

    }

    private void showNextButton(int clickType) {
        switch (clickType){
            case 1:
                ll_addPic2.setVisibility(View.VISIBLE);
                ll_addPic2.setClickable(true);
                ll_addPic2.setFocusable(true);
                break;
            case 2:
                ll_addPic3.setVisibility(View.VISIBLE);
                ll_addPic3.setClickable(true);
                ll_addPic3.setFocusable(true);
                break;
            case 3:
                ll_addPic4.setVisibility(View.VISIBLE);
                ll_addPic4.setClickable(true);
                ll_addPic4.setFocusable(true);
                break;
            case 4:
                ll_addPic5.setVisibility(View.VISIBLE);
                ll_addPic5.setClickable(true);
                ll_addPic5.setFocusable(true);
                break;

        }
    }

    @Override
    public void takeFail(TResult result, String msg) {
        Toast.makeText(PushServiceActivity.this,"takeFail",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void takeCancel() {
        Toast.makeText(PushServiceActivity.this,"takeCancel",Toast.LENGTH_SHORT).show();
    }

}
