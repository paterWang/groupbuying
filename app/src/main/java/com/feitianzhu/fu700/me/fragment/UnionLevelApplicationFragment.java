package com.feitianzhu.fu700.me.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.me.adapter.UnionLevelAdapter;
import com.feitianzhu.fu700.me.base.BaseFragment;
import com.feitianzhu.fu700.me.ui.totalScore.SelectPayActivity;
import com.feitianzhu.fu700.model.SelectPayNeedModel;
import com.feitianzhu.fu700.model.UnionLevelModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.feitianzhu.fu700.common.Constant.ACCESSTOKEN;
import static com.feitianzhu.fu700.common.Constant.Common_HEADER;
import static com.feitianzhu.fu700.common.Constant.POST_UNION_LEVEL;
import static com.feitianzhu.fu700.common.Constant.USERID;

/**
 * Created by Vya on 2017/8/29 0029.
 */

public class UnionLevelApplicationFragment extends BaseFragment {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_LevelTxt)
    TextView mCurrentLevel;
    @BindView(R.id.tv_returnNum)
    TextView mTvReturnNum;
    private List<UnionLevelModel> mList;
    private UnionLevelAdapter mAdapter;
    private  SelectPayNeedModel model;
    private String agentName;
    private String mRate;
    @Override
    protected void initView() {
        Bundle arguments = getArguments();
        agentName = arguments.getString("AgentName");
        mRate = arguments.getString("Rate");
        if(agentName !=null){
            mCurrentLevel.setText(agentName);
        }
        if(!TextUtils.isEmpty(mRate)){
            mTvReturnNum.setText(mRate);
        }
        model = new SelectPayNeedModel();
        model.setType(SelectPayNeedModel.TYPE_UNION_LEVEL);
    }

    @Override
    protected void initData() {
       mList = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new UnionLevelAdapter(mList);
        mAdapter.setOnItemImageClickListener(new UnionLevelAdapter.OnItemImageClickListener() {
            @Override
            public void onItemImageClick(int position) {
//                ToastUtils.showShortToast("点击----》"+position);
                //进入付款页面
                model.gradeId = mList.get(position).getGradeId()+"";
                model.setHandleFee(mList.get(position).getPoints());
                model.agentName = mList.get(position).getName();
                model.agentType = mList.get(position).getAgentType();
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        requestData();
    }

    private void requestData() {
        OkHttpUtils.post()//
                .url(Common_HEADER + POST_UNION_LEVEL)
                .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                .addParams(USERID, Constant.LOGIN_USERID)
                .build().execute(new Callback< List<UnionLevelModel>>() {
            @Override
            public List<UnionLevelModel> parseNetworkResponse(String mData, Response response, int id)
                    throws Exception {
                Type type = new TypeToken<List<UnionLevelModel>>() {
                }.getType();
                List<UnionLevelModel> bean = new Gson().fromJson(mData, type);
                return bean;
            }
            @Override public void onError(Call call, Exception e, int id) {
                Log.e("wangyan", "onError---->" + e.getMessage());
            }

            @Override public void onResponse( List<UnionLevelModel> response, int id) {
                //setShowData(response);
                mList.addAll(response);
                mAdapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_union_level;
    }
    @OnClick(R.id.bt_send)
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.bt_send:
                Log.e("Test","------>SelectPayActivity");
                Intent intent = new Intent(getContext(), SelectPayActivity.class);
                intent.putExtra(Constant.INTENT_SELECTET_PAY_MODEL,model);
                startActivity(intent);
                break;
        }
    }
}
