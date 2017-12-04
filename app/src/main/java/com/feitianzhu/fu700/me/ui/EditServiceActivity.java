package com.feitianzhu.fu700.me.ui;

import android.content.Intent;
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
import com.feitianzhu.fu700.model.ShopsService;
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
import static com.feitianzhu.fu700.common.Constant.POST_PUSH_EDITSERVICE;
import static com.feitianzhu.fu700.common.Constant.USERID;

public class EditServiceActivity extends BaseTakePhotoActivity {
    @BindView(R.id.et_TradeName)
    EditText mTradeName;
    @BindView(R.id.et_TradePrice)
    EditText mTradePrice;
    @BindView(R.id.et_TradeBenefit)
    EditText mTradeBenefit;
    @BindView(R.id.ll_addCover)
    LinearLayout mAddCover;
    @BindView(R.id.ll_addPic1)
    LinearLayout mAddPic;
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
    private  ShopsService.ListEntity mData;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_service;
    }

    @Override
    protected void initTitle() {
        defaultNavigationBar = new DefaultNavigationBar
                .Builder(EditServiceActivity.this, (ViewGroup)findViewById(R.id.Rl_titleContainer))
                .setTitle("发布服务")
                .setStatusHeight(EditServiceActivity.this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .setRightText("删除", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(EditServiceActivity.this,"点击删除",Toast.LENGTH_SHORT).show();
                    }
                })
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }

    @Override
    protected void initView() {
        mPicList = new HashMap<>();
        Intent mIntent = getIntent();

        mData = (ShopsService.ListEntity) mIntent.getSerializableExtra("serviceAdmin");
        if(mData!= null){
            setReShowData();
        }
    }
    /**
     * 回显数据
     * */
    private void setReShowData() {

        mTradeName.setText(mData.serviceName);
        mTradePrice.setText(mData.price);
        mTradeBenefit.setText(mData.rebate);
        mContact.setText(mData.contactPerson==null?"":mData.contactPerson.toString());
        mContactNum.setText(mData.contactTel==null?"":mData.contactTel+"");
        mContactAddress.setText(mData.serviceAddr);
        mTradeDesc.setText(mData.serviceDesc);
    }

    @Override
    protected void initData() {

    }

    private  int ClickType = -1;
    private String avatar;
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


    /**
     * 编辑服务
     */
    private void requestData() {

        PostFormBuilder mPost = OkHttpUtils.post();
        File mAvatar = new File(avatar);
        if(mAvatar!=null){
            if(!mAvatar.exists()){
                ToastUtils.showShortToast("请上传图片");
                return;
            }
        }else{
            ToastUtils.showShortToast("图片不存在，请重新上传图片");
            return;
        }

        if(mPicList.size()<1){
            ToastUtils.showShortToast("请上传图片");
            return;
        }
        mPost.addFile("cover", "avatarAAA.png",mAvatar )//
                .addFiles("goodsPic",mPicList)
                .url(Common_HEADER + POST_PUSH_EDITSERVICE)
                .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                .addParams(USERID, Constant.LOGIN_USERID)
                .addParams("serviceId",mData.serviceId+"")
                .addParams("serviceName",mTradeName.getText().toString())
                .addParams("price",mTradePrice.getText().toString())
                .addParams("rebate",mTradeBenefit.getText().toString())
                .addParams("contactTel",mContactNum.getText().toString())
                .addParams("contactPerson",mContact.getText().toString())
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
                        } else {
                            ToastUtils.showShortToast("发布失败");
                        }

                    }

                    @Override
                    public void onResponse(Object response, int id) {

                    }
                });
    }

    @Override
    public void takeSuccess(TResult result) {
        String compressPath = result.getImage().getCompressPath();
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
        Toast.makeText(EditServiceActivity.this,"takeFail",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void takeCancel() {
        Toast.makeText(EditServiceActivity.this,"takeCancel",Toast.LENGTH_SHORT).show();
    }

}
