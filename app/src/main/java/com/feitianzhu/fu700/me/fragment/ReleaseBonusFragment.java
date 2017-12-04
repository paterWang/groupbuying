package com.feitianzhu.fu700.me.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.common.base.LazyFragment;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.me.adapter.ReleaseBonusAdapter;
import com.feitianzhu.fu700.me.ui.GetMoneyActivity;
import com.feitianzhu.fu700.me.ui.totalScore.ReleaseTotalScoreActivity;
import com.feitianzhu.fu700.model.GetMoneyModel;
import com.feitianzhu.fu700.model.TotalScoreModel;
import com.feitianzhu.fu700.shop.ShopHelp;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static com.feitianzhu.fu700.common.Constant.ACCESSTOKEN;
import static com.feitianzhu.fu700.common.Constant.Common_HEADER;
import static com.feitianzhu.fu700.common.Constant.POST_TOTALSCORE;
import static com.feitianzhu.fu700.common.Constant.USERID;

/**
 * Created by Vya on 2017/9/4 0004.
 * 释放红利界面
 */

public class ReleaseBonusFragment extends LazyFragment {
    @BindView(R.id.recyclerview_volunteers)
    RecyclerView mVoluntRecycler;

    @BindView(R.id.bt_volenteer)
    Button bt_xiaofei;
    @BindView(R.id.bt_partner)
    Button bt_huilian;
    @BindView(R.id.bt_huanghuali)
    Button bt_huanghuali;
    @BindView(R.id.textView13)
    TextView textView13;
    @BindView(R.id.tv_name_num)
    TextView mNameNum;
    private TotalScoreModel response;

    private int currentType = 2;
    private int totalScore=0;
    private List<TotalScoreModel.RebateRecordsBean.RebateItemBean> mList;
    private ReleaseBonusAdapter mAdapter;
    private int defaultIndex = 1;
    private View mEmptyView;

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_release_bonus, null);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    protected void initData() {
        mEmptyView = View.inflate(getActivity(), R.layout.view_common_nodata, null);
        mClick = new ArrayList<>();
        mList = new ArrayList<>();
        mAdapter = new ReleaseBonusAdapter(mList);
        mVoluntRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mVoluntRecycler.setAdapter(mAdapter);
        mAdapter.setEmptyView(mEmptyView);
        Bundle arguments = getArguments();
        response = (TotalScoreModel) arguments.getSerializable("model");
        if(response!=null){
            setShowData(defaultIndex);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        initBgColor();
        switch (defaultIndex){
            case 1:
                bt_xiaofei.setBackgroundResource(R.drawable.release_bonus_button_click);
                break;
            case 2:
                bt_huilian.setBackgroundResource(R.drawable.release_bonus_button_click);
                break;
            case 3:
                bt_huanghuali.setBackgroundResource(R.drawable.release_bonus_button_click);
                break;
        }
    }

    protected MaterialDialog mDialog;
    protected void showloadDialog(String title) {
        mDialog = new MaterialDialog.Builder(getActivity()).title(title)
                .content("加载中,请稍候...")
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show();
    }

    protected void goneloadDialog() {
        if (null != mDialog && mDialog.isShowing()){
            if (mDialog.isShowing()){
                mDialog.dismiss();
            }
        }
    }

    @Override
    protected void setDefaultFragmentTitle(String title) {

    }

    private void requestData(final int ItemIndex) {
        showloadDialog("正在加载");
        OkHttpUtils.post()//
                .url(Common_HEADER + POST_TOTALSCORE)
                .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                .addParams(USERID, Constant.LOGIN_USERID)
                .build().execute(new Callback<TotalScoreModel>() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("Test", "--Error-->" + e.getMessage());
                goneloadDialog();
                ToastUtils.showShortToast(e.getMessage());
            }

            @Override
            public void onResponse(TotalScoreModel response, int id) {
                setShowData(ItemIndex);
                goneloadDialog();
            }
        });
    }

    private void setShowData(int ItemIndex) {
        String ConsumePoints = "";
        String MerchantPoints = "";
        String YellowPearPoints = "";
        String ConsumePointsNum = "";
        String MerchantPointsNum = "";
        String YellowPearPointsNum = "";

        Log.e("Test", "------setShowData------" + ItemIndex);
        mList.clear();
        if(response == null || response.getRebateRecords()==null ){
            return;
        }
        switch (ItemIndex) {
            case 1: //消费积分
                if(response==null||response.getRebateRecords()==null||response.getRebateRecords().getConsumePointsRecord() == null){
                    ToastUtils.showShortToast("获取服务数据失败!");
                    return;
                }
                mList.addAll(response.getRebateRecords().getConsumePointsRecord());
                if (response != null && response.getUserPoints() != null) {
                    ConsumePoints = response.getUserPoints().getConsumePoints() + "";
                    ConsumePointsNum = response.getUserPoints().getConsumePointsBalance() +"";
                    if (TextUtils.isEmpty(ConsumePoints)) {
                        ConsumePoints = "0";
                    }
                    if(TextUtils.isEmpty(ConsumePointsNum)){
                        ConsumePointsNum = "0";
                    }
                } else {
                    ConsumePoints = "0";
                    ConsumePointsNum = "0";
                }
                mNameNum.setText(ConsumePointsNum);
                textView13.setText(ConsumePoints);
                break;
            case 2: //汇联积分
                if(response==null||response.getRebateRecords()==null||response.getRebateRecords().getMerchantPointRecord() == null){
                    ToastUtils.showShortToast("获取服务数据失败!");
                    return;
                }
                mList.addAll(response.getRebateRecords().getMerchantPointRecord());
                if (response != null && response.getUserPoints() != null) {
                    MerchantPoints = response.getUserPoints().getMerchantPoints() + "";
                    MerchantPointsNum = response.getUserPoints().getMerchantPointsBalance()+"";
                    if (TextUtils.isEmpty(MerchantPoints)) {
                        MerchantPoints = "0";
                    }
                    if(TextUtils.isEmpty(MerchantPointsNum)){
                        MerchantPointsNum = "0";
                    }
                } else {
                    MerchantPoints = "0";
                    MerchantPointsNum = "0";
                }
                mNameNum.setText(MerchantPointsNum);
                textView13.setText(MerchantPoints);
                break;
            case 3: //黄花梨
                if(response==null||response.getRebateRecords()==null||response.getRebateRecords().getYellowPearPointsRecord() == null){
                    ToastUtils.showShortToast("获取服务数据失败!");
                    return;
                }
                mList.addAll(response.getRebateRecords().getYellowPearPointsRecord());
                if (response != null && response.getUserPoints() != null) {
                    YellowPearPoints = response.getUserPoints().getYellowPearPoints() + "";
                    YellowPearPointsNum = response.getUserPoints().getYellowPearPointsBalance()+"";
                    if (TextUtils.isEmpty(YellowPearPoints)) {
                        YellowPearPoints = "0";
                    }
                    if (TextUtils.isEmpty(YellowPearPointsNum)) {
                        YellowPearPointsNum = "0";
                    }
                } else {
                    YellowPearPoints = "0";
                    YellowPearPointsNum = "0";
                }
                mNameNum.setText(YellowPearPointsNum);
                textView13.setText(YellowPearPoints);
                break;
        }
        mAdapter.notifyDataSetChanged();

    }

    private List<Button> mClick;

    //    @BindView(R.id.rl_bottom_container)
    @OnClick({R.id.bt_partner, R.id.bt_volenteer, R.id.bt_huanghuali, R.id.rl_bottom_container,R.id.rl_release_totaldetail})
    public void onClick(View v) {
        initBgColor();


        switch (v.getId()) {
            case R.id.bt_volenteer: //消费  1:推广积分，2：消费积分，3：汇联积分，4：志愿者积分，5：合伙人积分，6：分红积分，7：共享红利，8：黄花梨积分
                Button mBtn = (Button) v;
                mBtn.setBackgroundResource(R.drawable.release_bonus_button_click);
                defaultIndex = 1;
                currentType = 2;
              //  requestData(defaultIndex);
                break;
            case R.id.bt_partner: //汇联
                Button mBtn2 = (Button) v;
                mBtn2.setBackgroundResource(R.drawable.release_bonus_button_click);
                defaultIndex = 2;
                currentType = 3;
               // requestData(defaultIndex);
                break;
            case R.id.bt_huanghuali:  //点击黄花梨
                Button mBtn3 = (Button) v;
                mBtn3.setBackgroundResource(R.drawable.release_bonus_button_click);
                defaultIndex = 3;
                currentType = 8;
              //  requestData(defaultIndex);
                break;
            case R.id.rl_bottom_container: //转入余额
                getMoneyFun();
                break;
            case R.id.rl_release_totaldetail: //释放积分详情页
                Intent intent = new Intent(getContext(), ReleaseTotalScoreActivity.class);
                intent.putExtra("releaseFlag",currentType+"");
                intent.putExtra("releaseTotalScore",mNameNum.getText().toString());
                startActivity(intent);
                break;
        }
        setShowData(defaultIndex);

    }

    private void getMoneyFun() {

        ShopHelp.veriPassword(getActivity(), new onConnectionFinishLinstener() {

            @Override
            public void onSuccess(int code, Object result) {
                String mResult = "";
                if(TextUtils.isEmpty(result.toString())){
                    mResult = "";
                }else{
                    mResult = result.toString();
                }
                GetMoneyModel model = new GetMoneyModel(mResult);
                switch (defaultIndex) {
                    case 1: //设置消费余额
                        model.setType("2");
                        if (response == null||response.getExchangeRates() == null || response.getExchangeRates().isEmpty()||response.getExchangeRates().size()<=1) {
                            model.rate = 0;
                            model.points = 0;
                        } else {
                            model.rate = response.getExchangeRates().get(1).getRate(); //集合角标从0开始
                            model.points = response.getUserPoints().getConsumePoints();
                        }
                        break;
                    case 2://设置汇联余额
                        model.setType("3");
                        if (response == null||response.getExchangeRates() == null ||response.getExchangeRates().isEmpty()|| response.getExchangeRates().size() <= 2) {
                            model.rate = 0;
                            model.points = 0;
                        } else {
                            model.rate = response.getExchangeRates().get(2).getRate();
                            model.points = response.getUserPoints().getMerchantPoints();
                        }
                        break;
                    case 3://设置黄花梨余额
                        model.setType("8");
                        if (response == null||response.getExchangeRates() == null || response.getExchangeRates().isEmpty()||response.getExchangeRates().size() <= 7) {
                            model.rate = 0;
                            model.points = 0;
                        } else {
                            model.rate = response.getExchangeRates().get(7).getRate();
                            model.points = response.getUserPoints().getYellowPearPoints();
                        }
                        break;
                }
                Intent intent = new Intent(getContext(), GetMoneyActivity.class);
                intent.putExtra("getMoneyBean", model);
                startActivity(intent);
            }

            @Override
            public void onFail(int code, String result) {
                ToastUtils.showShortToast(result);
            }
        });
    }


    private void initBgColor() {
        bt_xiaofei.setBackgroundResource(R.drawable.release_bonus_button_normal);
        bt_huilian.setBackgroundResource(R.drawable.release_bonus_middle_normal);
        bt_huanghuali.setBackgroundResource(R.drawable.huanghuali_button_normal);
    }

    @Override
    public void onPause() {
        super.onPause();
        if ((mDialog != null) && mDialog.isShowing())
            mDialog.dismiss();
        mDialog = null;
    }
}
