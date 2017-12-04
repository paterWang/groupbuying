package com.feitianzhu.fu700.me.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.model.GetMoneyModel;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.Callback;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.feitianzhu.fu700.common.Constant.ACCESSTOKEN;
import static com.feitianzhu.fu700.common.Constant.Common_HEADER;
import static com.feitianzhu.fu700.common.Constant.POST_SCORE_GET;
import static com.feitianzhu.fu700.common.Constant.USERID;

/**
 * Created by Vya on 2017/9/24.
 * 转入余额界面
 */

public class GetMoneyActivity extends BaseActivity {
    private GetMoneyModel mData;
    @BindView(R.id.et_inputMoney)
    EditText mEditText;
    @BindView(R.id.tv_TotalMoney)
    TextView mLeftName;
    @BindView(R.id.tv_formatTxt)
    TextView mFormatTxt;
    private DecimalFormat mFormatter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_get_money;
    }

    @Override
    protected void initTitle() {
        defaultNavigationBar = new DefaultNavigationBar
                .Builder(GetMoneyActivity.this, (ViewGroup) findViewById(R.id.Rl_titleContainer))
                .setTitle("转入余额")
                .setStatusHeight(GetMoneyActivity.this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }

    @Override
    protected void initView() {
        mFormatter = new DecimalFormat();
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mData = (GetMoneyModel) intent.getSerializableExtra("getMoneyBean");
        mLeftName.setText(mData.points + "");
      //  double rate = 0;
    /*    try {
            Log.e("Test","Point==="+Double.valueOf(mData.points));
//            rate = mData.points * mData.rate / 100;
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        mFormatTxt.setText(String.format(getString(R.string.withdraw_tips), mFormatter.format(mData.points), mData.rate + "%"));
    }

    @OnClick(R.id.bt_sure)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_sure:
                if (TextUtils.isEmpty(mEditText.getText().toString())) {
                    ToastUtils.showShortToast("请输入转出金额");
                    return;
                }
                sendParams();
                break;
        }
    }

    /**
     * 提交
     */
    private void sendParams() {
        if("0.0".equals(mEditText.getText().toString())||"".equals(mEditText.getText().toString())||"0".equals(mEditText.getText().toString())){
            ToastUtils.showShortToast("请输入有效的金额");
            return;
        }
        PostFormBuilder mPost = OkHttpUtils.post();
        mPost.url(Common_HEADER + POST_SCORE_GET)
                .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                .addParams(USERID, Constant.LOGIN_USERID)
                .addParams("type", mData.getType()) //申请兑换的积分类型（1:推广积分，2：消费积分，3：汇联积分，4：志愿者积分，5：合伙人积分，6：分红积分，7：共享红利，8：黄花梨积分）
                .addParams("payPass", mData.getPayPass())  //二级密码 线下支付可以不传
                .addParams("points", mEditText.getText().toString() + "") //转出积分
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(String mData, Response response, int id)
                            throws Exception {
                        return mData;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if ("数据为空".equals(e.getMessage())) {
                            ToastUtils.showShortToast("支付成功");
                            finish();
                        } else {
                            ToastUtils.showShortToast(e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {

                    }
                });
    }

}
