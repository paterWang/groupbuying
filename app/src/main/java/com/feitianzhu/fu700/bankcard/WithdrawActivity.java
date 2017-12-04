package com.feitianzhu.fu700.bankcard;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.bankcard.entity.UserBankCardEntity;
import com.feitianzhu.fu700.bankcard.entity.WithdrawFeeRateEntity;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.dao.NetworkDao;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.shop.ShopDao;
import com.feitianzhu.fu700.shop.ShopHelp;
import com.feitianzhu.fu700.utils.MathUtils;
import com.feitianzhu.fu700.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.feitianzhu.fu700.common.Constant.INTENT_SELECTET_BANKCARD;

public class WithdrawActivity extends BaseActivity {

    @BindView(R.id.tv_name)
    TextView mTvBankName;
    @BindView(R.id.tv_detail)
    TextView mTvDetail;
    @BindView(R.id.tv_number)
    TextView mTvBankNo;
    @BindView(R.id.rl_card)
    RelativeLayout mRlCard;
    @BindView(R.id.et_money)
    EditText mEtMoney;
    @BindView(R.id.tv_tips)
    TextView mTvTips;
    @BindView(R.id.button)
    Button mButton;
    private static final int REQUESTCODE = 100;
    private UserBankCardEntity mUserBankCardEntity;
    private double mBalance;
    private int mType;
    private int mRate;
    private NumberFormat mFormatter;

    private double mFee;
    private double mInputMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdraw;
    }

    @Override
    protected void initView() {

        defaultNavigationBar = new DefaultNavigationBar
                .Builder(this, (ViewGroup) findViewById(R.id.Rl_titleContainer))
                .setTitle("兑换")
                .setStatusHeight(WithdrawActivity.this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);

        mEtMoney.addTextChangedListener(mTextWatcher);
    }

    @Override
    protected void initData() {

        ShopDao.loadUserAuthImpl();
        mFormatter = new DecimalFormat();
        Intent intent = getIntent();
        if (intent != null) {
            mBalance = intent.getDoubleExtra(Constant.INTENT_BALANCE, 0);
            mType = intent.getIntExtra(Constant.INTENT_WITHDRAW_TYPE, 1);
        }

        NetworkDao.getUserBankCardList(new onConnectionFinishLinstener() {
            @Override
            public void onSuccess(int code, Object result) {

                List<UserBankCardEntity> list = (List<UserBankCardEntity>) result;

                if (list == null || list.isEmpty()) {
                    return;
                }
                mUserBankCardEntity = list.get(0);
                mTvBankName.setText(mUserBankCardEntity.bankName);
                mTvBankNo.setText(mUserBankCardEntity.bankCardNo);
            }

            @Override
            public void onFail(int code, String result) {
                ToastUtils.showShortToast(result);
            }
        });


        NetworkDao.withdrawFeeRate(mType + "", new onConnectionFinishLinstener() {
            @Override
            public void onSuccess(int code, Object result) {
                WithdrawFeeRateEntity entity = (WithdrawFeeRateEntity) result;
                mRate = entity.rate;
                mFormatter.format(mBalance);
                mTvTips.setText(String.format(getString(R.string.withdraw_tips), mFormatter.format(mBalance), mFee + ""));
            }

            @Override
            public void onFail(int code, String result) {
                ToastUtils.showShortToast(result);
                mTvTips.setText(String.format(getString(R.string.withdraw_tips), mBalance + "", mFee + ""));
            }
        });
    }

    @OnClick({R.id.rl_card, R.id.button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_card:
                startActivityForResult(new Intent(mContext, SelectBankCardActivity.class), REQUESTCODE);
                break;
            case R.id.button:

                if (mUserBankCardEntity == null) {
                    ToastUtils.showShortToast("请选择银行卡");
                    return;
                }
                if (checkTextView(mTvBankName, "请选择兑换银行卡")) return;
                if (checkTextView(mTvBankNo, "请选择兑换银行卡")) return;
                if (checkEditText(mEtMoney, "请输入兑换金额")) return;

                ShopHelp.veriPassword(this, new onConnectionFinishLinstener() {
                    @Override
                    public void onSuccess(int code, Object result) {

                        NetworkDao.withdraw(result.toString(), mInputMoney + "", mUserBankCardEntity.bankCardId + "", mType + "", new onConnectionFinishLinstener() {
                            @Override
                            public void onSuccess(int code, Object result) {
                                ToastUtils.showShortToast("兑换成功");
                                EventBus.getDefault().post(BankCardEvent.WITHDRAW_SUCCESS);
                                startActivity(new Intent(mContext, WithdrawResultActivity.class));
                                finish();
                            }

                            @Override
                            public void onFail(int code, String result) {
                                ToastUtils.showShortToast(result);
                            }
                        });
                    }

                    @Override
                    public void onFail(int code, String result) {
                        ToastUtils.showShortToast(result);
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUESTCODE) {
            if (resultCode == RESULT_OK) {

                mUserBankCardEntity = data.getParcelableExtra(INTENT_SELECTET_BANKCARD);

                if (mUserBankCardEntity == null) return;

                mTvBankName.setText(mUserBankCardEntity.bankName);
                mTvBankNo.setText(mUserBankCardEntity.bankCardNo);
            }
        }
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
                mTvTips.setText(String.format(getString(R.string.withdraw_tips), mFormatter.format(mBalance), mFee + ""));
            } else {
                try {
                    mInputMoney = Double.parseDouble(s.toString());
                    if (mInputMoney > 0) {
                        mFee = MathUtils.divide(MathUtils.multiply(mInputMoney, mRate), 100);
                    } else {
                        mFee = 0.00;
                    }
                    mTvTips.setText(String.format(getString(R.string.withdraw_tips), mFormatter.format(mBalance), mFee + ""));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    mInputMoney = 0.00;
                    mFee = 0.00;
                    mTvTips.setText(String.format(getString(R.string.withdraw_tips), mFormatter.format(mBalance), mFee + ""));
                }
            }
        }
    };
}
