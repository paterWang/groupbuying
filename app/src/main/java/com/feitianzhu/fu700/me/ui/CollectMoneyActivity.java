package com.feitianzhu.fu700.me.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.model.MineCollectionMoneyModel;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.feitianzhu.fu700.view.CircleImageView;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import okhttp3.Call;

import static com.feitianzhu.fu700.common.Constant.ACCESSTOKEN;
import static com.feitianzhu.fu700.common.Constant.Common_HEADER;
import static com.feitianzhu.fu700.common.Constant.USERID;

/**
 * Created by Vya on 2017/8/31 0031.
 */

public class CollectMoneyActivity extends BaseActivity {
    @BindView(R.id.iv_qrcode)
    ImageView mQrcode;
    @BindView(R.id.tv_localName)
    TextView mLocalName;
    @BindView(R.id.civ_pic)
    CircleImageView mCivPic;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect_money;
    }

    @Override
    protected void initTitle() {
        defaultNavigationBar = new DefaultNavigationBar
                .Builder(CollectMoneyActivity.this, (ViewGroup)findViewById(R.id.Rl_titleContainer))
                .setTitle("我要收款")
                .setStatusHeight(CollectMoneyActivity.this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }
    //{"qrCodeUrl":"http://118.190.156.13/merchantqrcode/7.png","expire":30}
    @Override
    protected void initView() {
        requestData();
    }

    private void requestData() {
        OkHttpUtils.post()//
                .url(Common_HEADER + Constant.POST_MINE_COLLECTION_MONEY)
                .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                .addParams(USERID, Constant.LOGIN_USERID)
                .build()
                .execute(new Callback<MineCollectionMoneyModel>() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("wangyan","onError---->"+e.getMessage());
                        ToastUtils.showShortToast(e.getMessage());
                    }

                    @Override
                    public void onResponse(MineCollectionMoneyModel response, int id) {
                        setShowData(response);
                    }

                });
    }

    private void setShowData(MineCollectionMoneyModel response) {
        //mQrcode
        //Constant.USER_INFO.headImg;
        String qrUrl = response.getQrCodeUrl();
        if(TextUtils.isEmpty(qrUrl)){
            qrUrl = "http://www.fu700.cn/?id=7";
        }
       Bitmap bitmap = CodeUtils.createImage(qrUrl,400,400, BitmapFactory.decodeResource(getResources(), R.mipmap.icon_fu));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes=baos.toByteArray();
        Glide.with(mContext).load(bytes).placeholder(R.drawable.pic_fuwutujiazaishibai).into(mQrcode);
        Glide.with(mContext).load(response.getMerchantHeadImg()).dontAnimate().placeholder(R.drawable.pic_fuwutujiazaishibai)
                .into(mCivPic);
        mLocalName.setText(response.getMerchantName());
    }

    @Override
    protected void initData() {

    }
}
