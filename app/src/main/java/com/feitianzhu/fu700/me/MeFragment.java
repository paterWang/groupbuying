package com.feitianzhu.fu700.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.bankcard.MyBankCardActivity;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.login.LoginEvent;
import com.feitianzhu.fu700.me.ui.MyFriendActivity;
import com.feitianzhu.fu700.me.ui.MyVerificationActivity;
import com.feitianzhu.fu700.me.ui.MyWalletActivity;
import com.feitianzhu.fu700.me.ui.PersonalCenterActivity;
import com.feitianzhu.fu700.me.ui.TotalScoreActivity;
import com.feitianzhu.fu700.me.ui.consumeralliance.UnionlevelActivity;
import com.feitianzhu.fu700.me.ui.totalScore.MineCollectionActivity;
import com.feitianzhu.fu700.me.ui.totalScore.MineQrcodeActivity;
import com.feitianzhu.fu700.model.MineInfoModel;
import com.feitianzhu.fu700.settings.SettingsActivity;
import com.feitianzhu.fu700.shop.ShopDao;
import com.feitianzhu.fu700.shop.ShopHelp;
import com.feitianzhu.fu700.shop.ui.MyOrderActivity;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.feitianzhu.fu700.view.CircleImageView;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

import static com.feitianzhu.fu700.common.Constant.ACCESSTOKEN;
import static com.feitianzhu.fu700.common.Constant.Common_HEADER;
import static com.feitianzhu.fu700.common.Constant.POST_MINE_INFO;
import static com.feitianzhu.fu700.common.Constant.USERID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    @BindView(R.id.picture)
    CircleImageView mImageView;
    @BindView(R.id.tv_name)
    TextView mNameTxt;
    @BindView(R.id.textView2)
    TextView mCompanyName;
    @BindView(R.id.tv_jobName)
    TextView mJobName;
    Unbinder unbinder;
    private String mParam1;
    private String mParam2;
    private String agentName;
    private String rate;
    private MineInfoModel mTempData = null;

    public MeFragment() {
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(mTempData == null){
            Log.e("Test","onHiddenChanged---->");
            requestData();
        }
    }

    public static MeFragment newInstance(String param1, String param2) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine_view, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestData();
    }

    private void requestData() {
        OkHttpUtils.post()//
                .url(Common_HEADER + POST_MINE_INFO)
                .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                .addParams(USERID, Constant.LOGIN_USERID)
                .build().execute(new Callback<MineInfoModel>() {

            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("wangyan", "onError---->" + e.getMessage());
                ToastUtils.showShortToast(e.getMessage());
            }

            @Override
            public void onResponse(MineInfoModel response, int id) {
                if (response == null) {
                    return;
                }
                mTempData = response;
                if (response.getAgentName() == null) {
                    agentName = "";
                } else {
                    agentName = response.getAgentName().toString();
                }
                if (response.getRate() <= 0) {
                    rate = "0%";
                } else {
                    rate = response.getRate() + "%";
                }
                setShowData(response);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        ShopDao.loadUserAuthImpl();
    }

    private void setShowData(MineInfoModel response) {
        if (response == null) {
            return;
        }
        if (getActivity() == null) {
            return;
        }
        Glide.with(getActivity())
                .load(response.getHeadImg())
                .dontAnimate()
                .placeholder(R.drawable.pic_fuwutujiazaishibai)
                .into(mImageView);
        mNameTxt.setText(response.getNickName() == null ? "" : response.getNickName());
        mCompanyName.setText(response.getCompany() == null ? "" : response.getCompany());
        mJobName.setText(response.getJob() == null ? "" : response.getJob());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_setting, R.id.iv_qrcode, R.id.picture, R.id.tv_yu_e,
            R.id.tv_bankcard, R.id.tv_zhanghu, R.id.tv_totalScore,
            R.id.tv_volenteer, R.id.tv_lianmeng, R.id.rl_myfriend,
            R.id.rl_Shopmanagement, R.id.rl_myCollection, R.id.rl_MyOrder})

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_setting: //设置按钮
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                break;
            case R.id.iv_qrcode: //我的二维码
                JumpActivity(getContext(), MineQrcodeActivity.class);
                break;
            case R.id.picture: //头像点击
                JumpActivity(getContext(), PersonalCenterActivity.class);
                break;
            case R.id.tv_yu_e: //余额
                JumpActivity(getContext(), MyWalletActivity.class);
                break;
            case R.id.tv_bankcard: //银行卡
                if (!Constant.loadUserAuth) {
                    ToastUtils.showShortToast("正在获取授权信息，稍候进入");
                    ShopDao.loadUserAuthImpl();
                    return;
                } else {
                    ShopHelp.veriUserAndPayPwdJumpActivity(getActivity(), new onConnectionFinishLinstener() {
                        @Override
                        public void onSuccess(int code, Object result) {
                            startActivity(new Intent(getActivity(), MyBankCardActivity.class));
                        }

                        @Override
                        public void onFail(int code, String result) {
                            ToastUtils.showShortToast(result);
                        }
                    });
                }
                break;
            case R.id.tv_zhanghu: //账户
                startActivity(new Intent(getActivity(), MyVerificationActivity.class));
                break;
            case R.id.tv_totalScore: //总积分
                JumpActivity(getContext(), TotalScoreActivity.class);
                break;
          /*  case R.id.tv_volenteer: //志愿者
                JumpActivity(getContext(), NewBecomeVolunteerActivity.class);
                break;*/
            case R.id.tv_lianmeng: //联盟
                Intent intent = new Intent(getContext(), UnionlevelActivity.class);
                intent.putExtra("AgentName", agentName);
                intent.putExtra("Rate", rate);
                startActivity(intent);
                break;
            case R.id.rl_myfriend: //我的福友
                JumpActivity(getContext(), MyFriendActivity.class);

                break;
            case R.id.rl_MyOrder: //我的订单
                JumpActivity(getContext(), MyOrderActivity.class);
                break;

            case R.id.rl_Shopmanagement: //商铺管理
                if (!Constant.loadUserAuth) {
                    ToastUtils.showShortToast("正在获取授权信息，稍候进入");
                    ShopDao.loadUserAuthImpl();
                    return;
                } else {
                    ShopHelp.veriJumpActivity(getActivity());
                }
                break;
            case R.id.rl_myCollection: //我的收藏
                JumpActivity(getContext(), MineCollectionActivity.class);
                break;

        }

    }

    private void JumpActivity(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(LoginEvent event) {
        switch (event) {
            case LOGIN_SUCCESS:
                KLog.i("登录成功");
                requestData();
                break;
            case LOGIN_FAILURE:
                KLog.i("登录失败");
                break;
            case LOGOUT:
                KLog.i("登出");
                ClearData();
                break;
            case TAKEPHOTO:
            case EDITORINFO:
                requestData();
                break;
        }
    }

    private void ClearData() {
        Glide.with(this)
                .load("")
                .dontAnimate()
                .placeholder(R.drawable.pic_fuwutujiazaishibai)
                .into(mImageView);
        mNameTxt.setText("");
        mCompanyName.setText("");
        mJobName.setText("");
    }
}
