package com.feitianzhu.fu700.me.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.me.adapter.PersonInfoAdapter;
import com.feitianzhu.fu700.me.base.BaseFragment;
import com.feitianzhu.fu700.model.MineInfoModel;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

import static com.feitianzhu.fu700.common.Constant.ACCESSTOKEN;
import static com.feitianzhu.fu700.common.Constant.Common_HEADER;
import static com.feitianzhu.fu700.common.Constant.POST_MINE_INFO;
import static com.feitianzhu.fu700.common.Constant.USERID;

/**
 * Created by Vya on 2017/8/29 0029.
 */

public class PersonInfoFragment extends BaseFragment {

    @BindView(R.id.list)
    RecyclerView mRecyclerView;
    private String otherUserId;
    private List<MineInfoModel> mList;
    private PersonInfoAdapter mAdapter;
    @Override
    protected void initView() {
        mList = new ArrayList<>();
        Bundle arguments = getArguments();
//        otherUserId = arguments.getString("otherUserId");
//        otherUserId = "13";
        MineInfoModel model = (MineInfoModel) arguments.getSerializable("shopDetailBean");
        mList.add(model);
    }

    @Override
    protected void initData() {
       // mList = new ArrayList<>();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new PersonInfoAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);

       // requestData();

    }

    private void requestData() {
        OkHttpUtils.post()//
                .url(Common_HEADER + POST_MINE_INFO)
                .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                .addParams(USERID, Constant.LOGIN_USERID)
                .addParams("otherUserId", otherUserId)//
                .build()
                .execute(new Callback<MineInfoModel>() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("wangyan","onError---->"+e.getMessage());
                        ToastUtils.showShortToast(e.getMessage());
                    }

                    @Override
                    public void onResponse(MineInfoModel response, int id) {
                        if(response != null){
                            mList.add(response);
                            mAdapter.notifyDataSetChanged();
                        }
                        Log.e("wangyan","--->"+response.toString());
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personinfo_recycler;
    }
}
