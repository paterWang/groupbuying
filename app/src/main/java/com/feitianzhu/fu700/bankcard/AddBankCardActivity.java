package com.feitianzhu.fu700.bankcard;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.bankcard.entity.BankCardEntity;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.dao.NetworkDao;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.helper.DialogHelper;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.shop.ShopDao;
import com.feitianzhu.fu700.shop.ShopHelp;
import com.feitianzhu.fu700.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddBankCardActivity extends BaseActivity {

    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_zhihang)
    EditText mEtZhihang;
    @BindView(R.id.et_number)
    EditText mEtNumber;
    @BindView(R.id.button)
    Button mButton;
    @BindView(R.id.tv_bankname)
    TextView mTvBankName;
    private List<String> mBankNames;
    private List<BankCardEntity> mBankList;
    private int mDialogSelectedPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_bank_card;
    }

    @Override
    protected void initView() {

        defaultNavigationBar = new DefaultNavigationBar
                .Builder(this, (ViewGroup) findViewById(R.id.Rl_titleContainer))
                .setTitle("添加银行卡")
                .setStatusHeight(AddBankCardActivity.this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }

    @Override
    protected void initData() {
        mBankNames = new ArrayList<>();
        mBankList = new ArrayList<>();
        ShopDao.loadUserAuthImpl();
    }

    @OnClick({R.id.button, R.id.tv_bankname})
    public void onClick(final View view) {

        switch (view.getId()) {

            case R.id.button:

                if (checkEditText(mEtName, "请输入姓名")) return;
                if (checkEditText(mEtZhihang, "请输入开户支行")) return;
                if (checkEditText(mEtNumber, "请输入银行卡号")) return;
                if (mTvBankName.getText().equals("请选择银行")) {
                    ToastUtils.showShortToast("请选择银行");
                    return;
                }
                if (mEtNumber.getText().toString().trim().length() < 16) {
                    ToastUtils.showShortToast("请输入正确的银行卡号");
                    return;
                }

                ShopHelp.veriPassword(this, new onConnectionFinishLinstener() {
                    @Override
                    public void onSuccess(int code, Object result) {
                        NetworkDao.addBankCard(mEtName.getText().toString().trim(), mBankList.get(mDialogSelectedPos).bankId + "", mEtZhihang.getText().toString().trim(), mEtNumber.getText().toString().trim(), result.toString(), new onConnectionFinishLinstener() {
                            @Override
                            public void onSuccess(int code, Object result) {
                                ToastUtils.showShortToast("添加成功");
                                finish();
                                EventBus.getDefault().postSticky(BankCardEvent.ADD_BANKCARD);
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
            case R.id.tv_bankname:

                if (mBankNames.isEmpty()) {

                    NetworkDao.getBankCardList(new onConnectionFinishLinstener() {
                        @Override
                        public void onSuccess(int code, Object result) {

                            List<BankCardEntity> list = (List<BankCardEntity>) result;

                            if (list == null || list.isEmpty()) {
                                ToastUtils.showShortToast("获取银行卡列表失败，请重试");
                                return;
                            }

                            mBankList.clear();
                            mBankList.addAll(list);
                            for (BankCardEntity entity : mBankList) {
                                mBankNames.add(entity.bankName);
                            }

                            new DialogHelper(AddBankCardActivity.this).init(DialogHelper.DIALOG_ONE, view).buildDialog(mBankNames, new DialogHelper.OnButtonClickPosListener<String>() {
                                @Override
                                public void onButtonClick(int position, String result, View clickView) {
                                    mDialogSelectedPos = position;
                                    mTvBankName.setText(result);
                                }
                            });
                        }

                        @Override
                        public void onFail(int code, String result) {
                            ToastUtils.showShortToast(result);
                        }
                    });
                } else {
                    new DialogHelper(this).init(DialogHelper.DIALOG_ONE, view).buildDialog(mBankNames, new DialogHelper.OnButtonClickPosListener<String>() {
                        @Override
                        public void onButtonClick(int position, String result, View clickView) {
                            mDialogSelectedPos = position;
                            mTvBankName.setText(result);
                        }
                    });
                }
                break;
        }
    }

}
