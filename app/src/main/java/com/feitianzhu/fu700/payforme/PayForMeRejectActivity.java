package com.feitianzhu.fu700.payforme;

import android.content.Intent;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.feitianzhu.fu700.MainActivity;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.dao.NetworkDao;
import com.feitianzhu.fu700.me.base.BaseTakePhotoActivity;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.payforme.entity.PayForMeEntity;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.jph.takephoto.model.TResult;
import com.socks.library.KLog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Vya on 2017/8/31 0031.
 */

public class PayForMeRejectActivity extends BaseTakePhotoActivity {

    @BindView(R.id.et_businessmen_name)
    EditText mEtBusinessmenName;
    @BindView(R.id.et_businessmen_address)
    EditText mEtBusinessmenAddress;
    @BindView(R.id.et_goods_name)
    EditText mEtGoodsName;
    @BindView(R.id.button)
    Button mButton;

    @BindView(R.id.iv_add_pic1)
    ImageView mIvAddPic1;
    @BindView(R.id.ll_addPic1)
    LinearLayout mLlAddPic1;
    @BindView(R.id.iv_add_pic2)
    ImageView mIvAddPic2;
    @BindView(R.id.ll_addPic2)
    LinearLayout mLlAddPic2;
    @BindView(R.id.iv_add_pic3)
    ImageView mIvAddPic3;
    @BindView(R.id.ll_addPic3)
    LinearLayout mLlAddPic3;

    private int mClickType;

    private SparseArray<String> mSparseArray;
    private PayForMeEntity.ListBean mEntity;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_for_me_reject;
    }

    @Override
    protected void initTitle() {
        defaultNavigationBar = new DefaultNavigationBar
                .Builder(PayForMeRejectActivity.this, (ViewGroup) findViewById(R.id.Rl_titleContainer))
                .setTitle("为我买单")
                .setStatusHeight(PayForMeRejectActivity.this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

        mSparseArray = new SparseArray<>();
        Intent intent = getIntent();
        if (intent != null) {
            mEntity = intent.getParcelableExtra(Constant.INTENT_REJECT_RECORD);
            KLog.i("mEntity: %s", mEntity.toString());

//            Glide.with(mContext).load(mEntity.consumePlaceImg).placeholder(R.mipmap.pic_fuwutujiazaishibai).into(mIvAddPic1);
//            Glide.with(mContext).load(mEntity.consumeObjImg).placeholder(R.mipmap.pic_fuwutujiazaishibai).into(mIvAddPic2);
//            Glide.with(mContext).load(mEntity.consumeRcptImg).placeholder(R.mipmap.pic_fuwutujiazaishibai).into(mIvAddPic3);

            mEtBusinessmenName.setText(mEntity.merchantName);
            mEtBusinessmenAddress.setText(mEntity.merchantAddr);
            mEtGoodsName.setText(mEntity.goodsName);

//            mSparseArray.put(0,mEntity.consumePlaceImg);
//            mSparseArray.put(1,mEntity.consumeObjImg);
//            mSparseArray.put(2,mEntity.consumeRcptImg);
        }


    }

    @OnClick({R.id.button, R.id.ll_addPic1, R.id.ll_addPic2, R.id.ll_addPic3})
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button:

                if (checkEditText(mEtBusinessmenName, "商户名称不能为空")) return;
                if (checkEditText(mEtBusinessmenAddress, "商户名称不能为空")) return;
                if (checkEditText(mEtGoodsName, "商品名称不能为空")) return;

                KLog.i("mSparseArray: " + mSparseArray.toString());

                if (mSparseArray.size() < 3) {
                    ToastUtils.showShortToast("请上传完整凭证");
                    return;
                }
                showloadDialog("正在提交...");
                NetworkDao.updateOrder(mEntity.orderNo, mEtBusinessmenName.getText().toString().trim(), mEtBusinessmenAddress.getText().toString().trim(), mEtGoodsName.getText().toString().trim(),
                        mSparseArray.get(0), mSparseArray.get(1), mSparseArray.get(2), new onConnectionFinishLinstener() {
                            @Override
                            public void onSuccess(int code, Object result) {
                                ToastUtils.showShortToast("提交成功");
                                goneloadDialog();
                                finish();
                                startActivity(new Intent(mContext, MainActivity.class));
                            }

                            @Override
                            public void onFail(int code, String result) {
                                goneloadDialog();
                                ToastUtils.showShortToast(result);
                            }
                        });


//                merchantName	是	string	商户名称
//                merchantAddr	是	string	商户地址
//                goodsName	是	string	商品名称
//                consumeAmount	是	double	消费金额
//                handleFee	是	double	手续费（系统参数--策划推广比例接口返回的比例*消费金额）
//                payChannel	是	string	支付渠道（wx：微信，alipay：支付宝，balance：余额，offline：线下转账）
//                placeImgFile	是	file	消费场所图
//                objImgFile	是	file	消费实物图
//                rcptImgFile	是	file	消费发票图
//                payProofFile	可选	file	转账凭证（线下支付时必传）
//                Intent intent = new Intent(this, SelectPayActivity.class);
//                SelectPayNeedModel selectPayNeedModel = new SelectPayNeedModel();
//                selectPayNeedModel.setMerchantName(mEtBusinessmenName.getText().toString().trim())
//                        .setMerchantAddr(mEtBusinessmenAddress.getText().toString().trim())
//                        .setGoodsName(mEtGoodsName.getText().toString().trim())
//                        .setConsumeAmount(mInputMoney)
//                        .setHandleFee(mFee)
//                        .setPayChannel("")
//                        .setType(SelectPayNeedModel.TYPE_PAY_FOR_ME)
//                        .setPlaceImgFile(mSparseArray.get(0))
//                        .setObjImgFile(mSparseArray.get(1))
//                        .setRcptImgFile(mSparseArray.get(2));
//
//                intent.putExtra(Constant.INTENT_SELECTET_PAY_MODEL, selectPayNeedModel);
//                startActivity(intent);
                break;

            case R.id.ll_addPic1:
                mClickType = 1;
                showDialog();
                break;

            case R.id.ll_addPic2:
                mClickType = 2;
                showDialog();
                break;

            case R.id.ll_addPic3:
                mClickType = 3;
                showDialog();
                break;
        }
    }

    @Override
    public void takeSuccess(TResult result) {
        String compressPath = result.getImage().getCompressPath();
        switch (mClickType) {
            case 1:
                mSparseArray.put(0, compressPath);
                Glide.with(mContext).load(compressPath).into(mIvAddPic1);
                break;
            case 2:
                mSparseArray.put(1, compressPath);
                Glide.with(mContext).load(compressPath).into(mIvAddPic2);
                break;
            case 3:
                mSparseArray.put(2, compressPath);
                Glide.with(mContext).load(compressPath).into(mIvAddPic3);
                break;
        }
    }

    @Override
    public void takeCancel() {

    }

    @Override
    public void takeFail(TResult result, String msg) {
        Toast.makeText(this, "takeFail", Toast.LENGTH_SHORT).show();
    }

}
