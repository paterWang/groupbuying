package com.feitianzhu.fu700.me.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
 */

public class PartnerBonusFragment extends LazyFragment {
    @BindView(R.id.recyclerview_volunteers)
    RecyclerView mVoluntRecycler;

    @BindView(R.id.bt_volenteer)
    Button bt_volenteer;
    @BindView(R.id.bt_partner)
    Button bt_partner;
    @BindView(R.id.textView13)
    TextView textView13;
    @BindView(R.id.tv_name_num)
    TextView mNameNum;

    private TotalScoreModel response;

    private List<TotalScoreModel.RebateRecordsBean.RebateItemBean> mList;
    private ReleaseBonusAdapter mAdapter;

    private int defaultIndex = 1;

    private View mEmptyView;
    private int currentType = 4;

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_partner_bonus, null);
        ButterKnife.bind(this, v);
        return v;
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
    protected void initData() {
        mEmptyView = View.inflate(getActivity(), R.layout.view_common_nodata, null);
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
    protected void setDefaultFragmentTitle(String title) {

    }


    @OnClick({R.id.bt_partner, R.id.bt_volenteer, R.id.rl_bottom_container,R.id.rl_release_totaldetail})
    public void onClick(View v) {
        initBgColor();

        switch (v.getId()) {
            case R.id.bt_partner: //合伙人
                Button mBtn = (Button) v;
                mBtn.setBackgroundResource(R.drawable.release_bonus_button_click);
                defaultIndex = 2;
                currentType = 5;
                //requestData(defaultIndex);
                break;
            case R.id.bt_volenteer: //志愿者
                Button mBtn2 = (Button) v;
                mBtn2.setBackgroundResource(R.drawable.release_bonus_button_click);
                defaultIndex = 1;
                currentType = 4;
               // requestData(defaultIndex);

                break;
            case R.id.rl_bottom_container:
                getMoneyFun();
                break;
            case R.id.rl_release_totaldetail:
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
                GetMoneyModel model = new GetMoneyModel(mResult);// 1:推广积分，2：消费积分，3：汇联积分，4：志愿者积分，5：合伙人积分，6：分红积分，7：共享红利，8：黄花梨积分
                switch (defaultIndex) {
                    case 2: //设置合伙人余额
                        model.setType("5");
                        if (response == null||response.getExchangeRates() == null ||response.getExchangeRates().size() <= 4) {
                            model.rate = 0;
                            model.points = 0;
                        } else {
                            model.rate = response.getExchangeRates().get(4).getRate();
                            model.points = response.getUserPoints().getPartnerPoints();
                        }
                        break;
                    case 1://设置志愿者余额
                        model.setType("4");
                        if (response == null||response.getExchangeRates() == null || response.getExchangeRates().size() <= 3) {
                            model.rate = 0;
                            model.points = 0;
                        } else {
                            model.rate = response.getExchangeRates().get(3).getRate();
                            model.points = response.getUserPoints().getVolunteerPoints();
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

    private void requestData(final int ItemIndex) {
        showloadDialog("正在加载...");
        OkHttpUtils.post()//
                .url(Common_HEADER + POST_TOTALSCORE)
                .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                .addParams(USERID, Constant.LOGIN_USERID)
                .build().execute(new Callback<TotalScoreModel>() {
            @Override
            public void onError(Call call, Exception e, int id) {
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

    private void setShowData( int ItemIndex) {
        mList.clear();
        String PartnerPoints = "";
        String VolunteerPoints = "";
        String PartnerPointsNum = "";
        String VolunteerPointsNum = "";
        switch (ItemIndex) {
            case 2: //合伙人
                if(response==null||response.getRebateRecords()==null||response.getRebateRecords().getPartnerPointRecord()==null){
                    ToastUtils.showShortToast("获取服务数据失败!");
                    return;
                }
                mList.addAll(response.getRebateRecords().getPartnerPointRecord());
                if (response != null && response.getUserPoints() != null) {
                    PartnerPoints = response.getUserPoints().getPartnerPoints() + "";
                    PartnerPointsNum = response.getUserPoints().getPartnerPointsBalance()+"";
                    if (TextUtils.isEmpty(PartnerPoints)) {
                        PartnerPoints = "0";
                    }
                    if (TextUtils.isEmpty(PartnerPointsNum)) {
                        PartnerPointsNum = "0";
                    }
                } else {
                    PartnerPointsNum = "0";
                    PartnerPoints = "0";
                }
                mNameNum.setText(PartnerPointsNum);
                textView13.setText(PartnerPoints);
                break;
            case 1: //志愿者
                if(response==null||response.getRebateRecords()==null||response.getRebateRecords().getVolunteerPointRecord()==null){
                    ToastUtils.showShortToast("获取服务数据失败!");
                    return;
                }
                mList.addAll(response.getRebateRecords().getVolunteerPointRecord());
                if (response != null && response.getUserPoints() != null) {
                    VolunteerPoints = response.getUserPoints().getVolunteerPoints() + "";
                    VolunteerPointsNum = response.getUserPoints().getVolunteerPointsBalance()+"";
                    if (TextUtils.isEmpty(VolunteerPoints)) {
                        VolunteerPoints = "0";
                    }
                    if (TextUtils.isEmpty(VolunteerPointsNum)) {
                        VolunteerPointsNum = "0";
                    }
                } else {
                    VolunteerPoints = "0";
                    VolunteerPointsNum = "0";
                }
                mNameNum.setText(VolunteerPointsNum);
                textView13.setText(VolunteerPoints);
                break;
        }
        mAdapter.notifyDataSetChanged();
    }

    private void initBgColor() {
        bt_volenteer.setBackgroundResource(R.drawable.release_bonus_button_normal);
        bt_partner.setBackgroundResource(R.drawable.release_bonus_middle_normal);

    }
}
