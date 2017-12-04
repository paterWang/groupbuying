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
import com.feitianzhu.fu700.me.adapter.PromotionBonusAdapter;
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
 * 推广红利
 */

public class PromotionBonusFragment extends LazyFragment {
    @BindView(R.id.recyclerview_volunteers)
    RecyclerView mVoluntRecycler;

    @BindView(R.id.bt_partner)
    Button bt_partner;
    @BindView(R.id.bt_volenteer)
    Button bt_volenteer;
    @BindView(R.id.textView13)
    TextView textView13;
    @BindView(R.id.tv_name_num)
    TextView mNameNum;
    private TotalScoreModel response;
    private List<TotalScoreModel.RebateRecordsBean.RebateItemBean> mList;
    private PromotionBonusAdapter mAdapter;

    private int defaultIndex = 1;

    private View mEmptyView;
    private int currentType = 6;

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_promotion_bonus, null);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    protected void initData() {
        mEmptyView = View.inflate(getActivity(), R.layout.view_common_nodata, null);
        mList = new ArrayList<>();
        mAdapter = new PromotionBonusAdapter(mList);
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
                bt_volenteer.setBackgroundResource(R.drawable.release_bonus_button_click);
                break;
            case 2:
                bt_partner.setBackgroundResource(R.drawable.release_bonus_button_click);
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
        showloadDialog("正在加载...");
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
        mList.clear();
        String ExtendPoints = "";
        String BonusPoints = "";
        String ExtendPointsNum = "";
        String BonusPointsNum = "";
        switch (ItemIndex) {
            case 1: //推广
                if(response ==null||response.getRebateRecords()==null||response.getRebateRecords().getExtendPointRecord()==null){
                    ToastUtils.showShortToast("获取服务数据失败!");
                    return;
                }
                mList.addAll(response.getRebateRecords().getExtendPointRecord());
                if (response != null && response.getUserPoints() != null) {
                    ExtendPoints = response.getUserPoints().getExtendPoints() + "";
                    ExtendPointsNum = response.getUserPoints().getExtendPointsBalance()+"";
                    if (TextUtils.isEmpty(ExtendPoints)) {
                        ExtendPoints = "0";
                    }
                    if (TextUtils.isEmpty(ExtendPointsNum)) {
                        ExtendPointsNum = "0";
                    }
                } else {
                    ExtendPoints = "0";
                    ExtendPointsNum = "0";
                }
                mNameNum.setText(ExtendPointsNum);
                textView13.setText(ExtendPoints);
                break;
            case 2: //分红
                if(response ==null||response.getRebateRecords()==null||response.getRebateRecords().getBonusPointsRecord()==null){
                    ToastUtils.showShortToast("获取服务数据失败!");
                    return;
                }
                mList.addAll(response.getRebateRecords().getBonusPointsRecord());
                if (response != null && response.getUserPoints() != null) {
                    BonusPoints = response.getUserPoints().getBonusPoints() + "";
                    BonusPointsNum = response.getUserPoints().getBonusPointsBalance()+"";
                    if (TextUtils.isEmpty(BonusPoints)) {
                        BonusPoints = "0";
                    }
                    if (TextUtils.isEmpty(BonusPointsNum)) {
                        BonusPointsNum = "0";
                    }
                } else {
                    BonusPoints = "0";
                    BonusPointsNum = "0";
                }
                mNameNum.setText(BonusPointsNum);
                textView13.setText(BonusPoints);
                break;
        }
        mAdapter.notifyDataSetChanged();
    }


    @OnClick({R.id.bt_partner, R.id.bt_volenteer, R.id.rl_bottom_container,R.id.rl_release_totaldetail})
    public void onClick(View v) {
        initBgColor();
        switch (v.getId()) {
            case R.id.bt_partner:  //分红
                Button mBtn = (Button) v;
                mBtn.setBackgroundResource(R.drawable.release_bonus_button_click);
                defaultIndex = 2;
                currentType = 6;
               // requestData(defaultIndex);
                break;
            case R.id.bt_volenteer:  //推广
                Button mBtn2 = (Button) v;
                mBtn2.setBackgroundResource(R.drawable.release_bonus_button_click);
                defaultIndex = 1;
                currentType = 1;
              //  requestData(defaultIndex);
                break;
            case R.id.rl_bottom_container: //转入余额
                getMoneyFun();
                break;

            case R.id.rl_release_totaldetail: //释放积分详情
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
                GetMoneyModel model = new GetMoneyModel(mResult); // 1:推广积分，2：消费积分，3：汇联积分，4：志愿者积分，5：合伙人积分，6：分红积分，7：共享红利，8：黄花梨积分
                switch (defaultIndex) {
                    case 1: //设置推广余额
                        model.setType("1");
                        if (response==null||response.getExchangeRates() == null || response.getExchangeRates().isEmpty()||response.getExchangeRates().size()<=0) {
                            model.rate = 0;
                            model.points = 0;
                        } else {

                            model.rate = response.getExchangeRates().get(0).getRate();
                            model.points = response.getUserPoints().getExtendPoints();

                        }
                        break;
                    case 2://设置分红余额
                        model.setType("6");

                        if (response==null||response.getExchangeRates() == null || response.getExchangeRates().isEmpty()||response.getExchangeRates().size() <= 5) {
                            model.rate = 0;
                            model.points = 0;
                        } else {
                            model.rate = response.getExchangeRates().get(5).getRate();
                            model.points = response.getUserPoints().getBonusPoints();
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
        bt_volenteer.setBackgroundResource(R.drawable.release_bonus_button_normal);
        bt_partner.setBackgroundResource(R.drawable.release_bonus_middle_normal);
    }
}
