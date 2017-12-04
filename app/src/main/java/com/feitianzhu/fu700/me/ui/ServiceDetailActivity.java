package com.feitianzhu.fu700.me.ui;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.feitianzhu.fu700.App;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.common.impl.onNetFinishLinstenerT;
import com.feitianzhu.fu700.me.adapter.PurchaseInfoAdapter;
import com.feitianzhu.fu700.me.adapter.ServiceDetailRecyclerAdapter;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.helper.DialogHelper;
import com.feitianzhu.fu700.me.navigationbar.UIUtil;
import com.feitianzhu.fu700.model.BuyServiceNeedModel;
import com.feitianzhu.fu700.model.PurchaseInfoModel;
import com.feitianzhu.fu700.model.ServiceDetailModel;
import com.feitianzhu.fu700.shop.ShopDao;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.feitianzhu.fu700.view.CircleImageView;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.feitianzhu.fu700.common.Constant.ACCESSTOKEN;
import static com.feitianzhu.fu700.common.Constant.Common_HEADER;
import static com.feitianzhu.fu700.common.Constant.USERID;

/**
 * Created by Vya on 2017/9/1.
 */

public class ServiceDetailActivity extends BaseActivity {
    @BindView(R.id.purchaseInfo_recycler)
    RecyclerView purchaseInfo_recycler;

    @BindView(R.id.profile_image)
    CircleImageView mCircleImageView;
    @BindView(R.id.tv_shopName)
    TextView mShopName;
    @BindView(R.id.tv_TradeName)
    TextView mTradeName;
/*    @BindView(R.id.tv_TradeParams)
    TextView mTradeParams;
    @BindView(R.id.tv_TradeColor)
    TextView mTradeColor;*/
    @BindView(R.id.tv_TradePrice)
    TextView mTradePrice;
    @BindView(R.id.tv_TradePreferential)
    TextView getmTradePreferential;
    @BindView(R.id.tv_ContactName)
    TextView mContactName;
    @BindView(R.id.tv_ContactNum)
    TextView mContactNum;
    @BindView(R.id.tv_ContactAddress)
    TextView mContactAdress;
    @BindView(R.id.tv_TradeDesc)
    TextView mTradeDesc;
    @BindView(R.id.recyclerview_img)
    RecyclerView mTradeImage; //底部相册
    @BindView(R.id.iv_bj)
    ImageView mIvPic;//背景
    @BindView(R.id.ib_collection)
    ImageView mCollectionView;
    private ServiceDetailModel model;
    private String serviceId;
    @BindView(R.id.bottom_view)
    View bottom_view;
    @BindView(R.id.rl_titleBar)
    RelativeLayout rl_titleBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_service_detail;
    }

    @Override
    protected void initView() {
        setTitleView();
        setTitleBarMargin();
        List<PurchaseInfoModel> mList = new ArrayList<>();
        mList.add(new PurchaseInfoModel("订单有效期为30天，请在30天内联系服务站享受该服务，30天后订单过期，支付费用不予退还;"));
        mList.add(new PurchaseInfoModel("该服务由发布者通过xx平台发布，用户需联系发布者进行服务履约，xx仅提供平台支持。"));
        purchaseInfo_recycler.setNestedScrollingEnabled(false);
        purchaseInfo_recycler.setLayoutManager(new LinearLayoutManager(this));
        purchaseInfo_recycler.setAdapter(new PurchaseInfoAdapter(mList));
    }

    private void setTitleBarMargin() {
        int marginHeight = UIUtil.dip2px(ServiceDetailActivity.this,UIUtil.getStatusBarHeight(ServiceDetailActivity.this));
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rl_titleBar.getLayoutParams();
        lp.setMargins(0, marginHeight, 0, 0);
        rl_titleBar.setLayoutParams(lp);
    }

    private void setTitleView() {
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        int mheight = getNavigationBarHeight();
        if(isNavigationBarShow()){
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, mheight);
            bottom_view.setLayoutParams(params);
        }
    }

    @Override
    protected void useNavigationBar() {
        //去掉默认
    }
    /**
     * 获取屏幕尺寸，但是不包括虚拟功能高度
     *
     * @return
     */
    public int getNoHasVirtualKey() {
        int height = getWindowManager().getDefaultDisplay().getHeight();
        return height;
    }
    /**
     * 通过反射，获取包含虚拟键的整体屏幕高度
     *
     * @return
     */
    private int getHasVirtualKey() {
        int dpi = 0;
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            dpi = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }

    //获取虚拟按键的高度
    public  int getNavigationBarHeight() {
        return getHasVirtualKey() - getNoHasVirtualKey();
    }
    @Override
    protected void initData() {
        mTradeImage.setNestedScrollingEnabled(false);

        serviceId = getIntent().getStringExtra("serviceid");
        if(TextUtils.isEmpty(serviceId)){
            serviceId="";
        }
        requestShowData(serviceId);
    }

    private void requestShowData(String serviceId) {
        Log.e("wangyan","----serviceId----"+serviceId);
        OkHttpUtils.post()//
                .url(Common_HEADER + Constant.POST_SERVICE_DETAIL_INFO)
                .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                .addParams(USERID, Constant.LOGIN_USERID)
                .addParams("serviceId",serviceId)
                .build()
                .execute(new Callback<ServiceDetailModel>() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("wangyan","onError---->"+e.getMessage());
                        ToastUtils.showShortToast(e.getMessage());
                    }

                    @Override
                    public void onResponse(ServiceDetailModel response, int id) {
                        model = response;
                        setShowData(response);
                    }

                });
    }

    private void setShowData(ServiceDetailModel response) {
        String phoneStr = response.getContactTel();
        if(TextUtils.isEmpty(phoneStr)){
            phoneStr = "";
        }
        if(response.getCollectId()<=0){
            mCollectionView.setSelected(false);
        }else{
            mCollectionView.setSelected(true);
        }



        SpannableString msp = new SpannableString(phoneStr);
        msp.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.txt_blue)), 0, phoneStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        msp.setSpan(new UnderlineSpan(), 0, phoneStr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        Glide.with(this).load(response.getHeadImg()).dontAnimate().placeholder(R.drawable.pic_fuwutujiazaishibai)
                .into(mCircleImageView);
        //这个是相册

        Glide.with(this).load(response.getAdImg()).dontAnimate().placeholder(R.drawable.pic_fuwutujiazaishibai)
                .into(mIvPic);

        mShopName.setText(response.getMerchantName());
        mTradeName.setText(response.getServiceName());
        //mTradePrice.setText("¥"+response.getPrice()+"");
        mTradePrice.setText(response.getPrice()+"");
        getmTradePreferential.setText(response.getRebate()+"PV");
        mContactName.setText(response.getContactPerson());

        mContactNum.setText(msp);
        mContactAdress.setText(response.getServiceAddr());
        mTradeDesc.setText(response.getServiceDesc());
        List<String> mList;
        String[] photos = new String[10];
        if(response.getPhotos() != null){
            photos = response.getPhotos().split(",");
        }

        if(photos!=null && photos.length >0){
            mList = Arrays.asList(photos);
        }else{
            mList = new ArrayList<>();
            mList.add("error");
        }

        mTradeImage.setLayoutManager(new LinearLayoutManager(ServiceDetailActivity.this));
        mTradeImage.setAdapter(new ServiceDetailRecyclerAdapter(mList));
    }

    @OnClick({R.id.rl_ContactMerchant,R.id.rl_BuyService,R.id.tv_ContactNum,R.id.ib_collection,R.id.iv_back})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_ContactMerchant:
                //单独单轮对话框
                String phoneNum = "";
                if(model != null && TextUtils.isEmpty(model.getContactTel())){
                    phoneNum = model.getContactTel();
                }else{
                    ToastUtils.showShortToast("获取信息失败，请检查网络!");
                   return;
                }
                new DialogHelper(ServiceDetailActivity.this).init(DialogHelper.DIALOG_ONE,v).buildDialog(phoneNum,"呼叫", new DialogHelper.OnButtonClickListener<String>() {
                    @Override
                    public void onButtonClick(View v, String result, View clickView) {
                            //通过result去拨打电话
                        telNum = result;
                        requestPermission();
                    }
                });
                break;
            case R.id.rl_BuyService:
                //跳转到购买服务界面
                if(model == null){
                    ToastUtils.showShortToast("获取服务器数据失败!");
                    return;
                }
                Intent intent = new Intent(ServiceDetailActivity.this,BuyServiceActivity.class);
                //带参数，直接把Bean带过去，因为参数多
                BuyServiceNeedModel mModel = new BuyServiceNeedModel(model.getServiceId(),model.getServiceName(),model.getPrice(),model.getRebate(),model.getUserId(),model.getHeadImg(),model.getContactPerson(),model.getContactTel(),model.getServiceAddr());
                mModel.merchantId = model.getMerchantId();
                mModel.type = BuyServiceNeedModel.SERVICE_DETAIL_BEAN;
                intent.putExtra("serviceDetailBean",mModel);
                startActivity(intent);
                break;

            case R.id.tv_ContactNum:  //电话号码
                //单独单轮对话框
                new DialogHelper(ServiceDetailActivity.this).init(DialogHelper.DIALOG_ONE,v).buildDialog(model.getContactTel(),"呼叫", new DialogHelper.OnButtonClickListener<String>() {
                    @Override
                    public void onButtonClick(View v, String result, View clickView) {
                        //通过result去拨打电话
                        telNum = result;
                        requestPermission();
                    }
                });
                break;
            case R.id.ib_collection: //收藏
                Log.e("Test","mCollectionView.isSelected()-->"+mCollectionView.isSelected());
                if (mCollectionView.isSelected()) { //如果已经是收藏就删除收藏
                    deleteShops();
                } else {  //收藏
                    doCelectShops();
                }
                break;

          /*  case R.id.iv_zhuanfa: //分享
                //showShare();
                break;*/
            case R.id.iv_back:
                finish();
                break;

        }
    }


    private void deleteShops() {
        ShopDao.DeleteCollect(model.getCollectId()+"", new onConnectionFinishLinstener() {
            @Override
            public void onSuccess(int code, Object result) {
                mCollectionView.setSelected(false);
            }

            @Override
            public void onFail(int code, String result) {
                ToastUtils.showShortToast(result);
            }
        });
    }

    private void doCelectShops() {
        ShopDao.PostCollect(2, model.getServiceId(), new onNetFinishLinstenerT() {
            @Override
            public void onSuccess(int code, Object result) {
                model.setCollectId(Integer.parseInt(result.toString()));
                ToastUtils.showShortToast("收藏成功");
                mCollectionView.setSelected(true);
            }

            @Override
            public void onFail(int code, String result) {
                ToastUtils.showShortToast(result);
            }
        });
    }







    private String telNum="";
    private void requestPermission() {
        AndPermission.with(this)
                .requestCode(200)
                .permission(
                        // 多个权限，以数组的形式传入。
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .callback(
                        new RationaleListener() {
                            @Override
                            public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                                // 此对话框可以自定义，调用rationale.resume()就可以继续申请。
                                AndPermission.rationaleDialog(App.getAppContext(), rationale).show();
                            }
                        }
                )
                .callback(listener)
                .start();
    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。

            // 这里的requestCode就是申请时设置的requestCode。
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
            if (requestCode == 200) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+telNum));
                startActivity(intent);
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == 200) {
                Toast.makeText(ServiceDetailActivity.this, "请求权限失败!", Toast.LENGTH_SHORT).show();
            }
        }
    };

}
