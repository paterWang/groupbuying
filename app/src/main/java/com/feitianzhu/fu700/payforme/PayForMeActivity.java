package com.feitianzhu.fu700.payforme;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.common.entity.DefaultRate;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.dao.NetworkDao;
import com.feitianzhu.fu700.me.base.BaseTakePhotoActivity;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.me.ui.totalScore.SelectPayActivity;
import com.feitianzhu.fu700.model.SelectPayNeedModel;
import com.feitianzhu.fu700.utils.MathUtils;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.jph.takephoto.model.TResult;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Vya on 2017/8/31 0031.
 */

public class PayForMeActivity extends BaseTakePhotoActivity {

    @BindView(R.id.et_businessmen_name)
    EditText mEtBusinessmenName;
    @BindView(R.id.et_businessmen_address)
    EditText mEtBusinessmenAddress;
    @BindView(R.id.et_goods_name)
    EditText mEtGoodsName;
    @BindView(R.id.tv_Contact)
    TextView mTvContact;
    @BindView(R.id.et_money)
    EditText mEtMoney;
    @BindView(R.id.button)
    Button mButton;
    @BindView(R.id.tv_fee)
    TextView mTvFee;
    @BindView(R.id.tv_tips)
    TextView mTvTips;
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
    private double mInputMoney = 0.00;
    private double mFee = 0.00;
    private double mRate;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_for_me;
    }

    @Override
    protected void initTitle() {
        defaultNavigationBar = new DefaultNavigationBar
                .Builder(PayForMeActivity.this, (ViewGroup) findViewById(R.id.Rl_titleContainer))
                .setTitle("为我买单")
                .setStatusHeight(PayForMeActivity.this)
                .setRightText("记录", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(PayForMeActivity.this, PayForMeRecordActivity.class));
                    }
                })
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }

    @Override
    protected void initView() {

        mTvFee.setText(String.format(getString(R.string.money), mFee + ""));

        mEtMoney.addTextChangedListener(mTextWatcher);
    }

    @Override
    protected void initData() {

        EventBus.getDefault().register(this);
        mSparseArray = new SparseArray<>();

        NetworkDao.getDefaultProportion(new onConnectionFinishLinstener() {
            @Override
            public void onSuccess(int code, Object result) {
                DefaultRate defaultRate = (DefaultRate) result;
                mRate = defaultRate.rate;
                mTvTips.setText(String.format(getString(R.string.helpful_hints), mRate + "%"));
                KLog.i("mRate: " + mRate);
            }

            @Override
            public void onFail(int code, String result) {
                mRate = 0.0;
                ToastUtils.showShortToast("手续费获取失败，请重试");
                mButton.setEnabled(false);
                mButton.setBackgroundResource(R.color.sf_hint_color);
                mTvTips.setText(String.format(getString(R.string.helpful_hints), mRate + "%"));
            }
        });
    }

    @OnClick({R.id.button, R.id.ll_addPic1, R.id.ll_addPic2, R.id.ll_addPic3})
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button:

                if (checkEditText(mEtBusinessmenName, "商户名称不能为空")) return;
                if (checkEditText(mEtBusinessmenAddress, "商户名称不能为空")) return;
                if (checkEditText(mEtGoodsName, "商品名称不能为空")) return;
                if (checkEditText(mEtMoney, "消费金额不能为空")) return;

                KLog.i("mSparseArray: " + mSparseArray.toString());

                if (mSparseArray.size() < 3) {
                    ToastUtils.showShortToast("请上传完整凭证");
                    return;
                }
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
                Intent intent = new Intent(this, SelectPayActivity.class);
                SelectPayNeedModel selectPayNeedModel = new SelectPayNeedModel();
                selectPayNeedModel.setMerchantName(mEtBusinessmenName.getText().toString().trim())
                        .setMerchantAddr(mEtBusinessmenAddress.getText().toString().trim())
                        .setGoodsName(mEtGoodsName.getText().toString().trim())
                        .setConsumeAmount(mInputMoney)
                        .setHandleFee(mFee)
                        .setPayChannel("")
                        .setType(SelectPayNeedModel.TYPE_PAY_FOR_ME)
                        .setPlaceImgFile(mSparseArray.get(0))
                        .setObjImgFile(mSparseArray.get(1))
                        .setRcptImgFile(mSparseArray.get(2));

                intent.putExtra(Constant.INTENT_SELECTET_PAY_MODEL, selectPayNeedModel);
                startActivity(intent);
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
        Toast.makeText(this, "takeCancel", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        Toast.makeText(this, "takeFail", Toast.LENGTH_SHORT).show();
    }


    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            if (TextUtils.isEmpty(s)) {
                mFee = 0.00;
                mTvFee.setText(String.format(getString(R.string.money), mFee + ""));
            } else {
                try {
                    mInputMoney = Double.parseDouble(s.toString());
                    if (mInputMoney > 0) {
                        mFee = MathUtils.divide(MathUtils.multiply(mInputMoney, mRate), 100);
                    } else {
                        mFee = 0.00;
                    }
                    mTvFee.setText(String.format(getString(R.string.money), mFee + ""));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    mFee = 0.00;
                    mInputMoney = 0.00;
                    mTvFee.setText(String.format(getString(R.string.money), mFee + ""));
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayEvent(PayForMeEvent event) {
        switch (event) {
            case PAY_SUCCESS:
                KLog.i("为我买单成功");
                finish();
                break;
            case PAY_FAILURE:
                KLog.i("为我买单失败");
                break;
        }
    }
}
