package com.feitianzhu.fu700.me.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.me.adapter.UnionApplyRecordAdapter;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.model.UnionApplyRecordModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

import static com.feitianzhu.fu700.common.Constant.ACCESSTOKEN;
import static com.feitianzhu.fu700.common.Constant.Common_HEADER;
import static com.feitianzhu.fu700.common.Constant.POST_UNION_APPLY_RECORD;
import static com.feitianzhu.fu700.common.Constant.USERID;

/**
 * Created by Vya on 2017/9/23.
 * 联盟申请记录
 */

public class UnionApplyRecordActivity extends BaseActivity {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private UnionApplyRecordAdapter mAdapter;
    private List<UnionApplyRecordModel> mList;
    private View mEmptyView;
    @Override
    protected int getLayoutId() {
        return R.layout.layout_union_level_record;
    }

    @Override
    protected void initTitle() {
        defaultNavigationBar = new DefaultNavigationBar
                .Builder(UnionApplyRecordActivity.this, (ViewGroup)findViewById(R.id.ll_Container))
                .setTitle("申请记录")
                .setStatusHeight(this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }

    @Override
    protected void initView() {
        mEmptyView = View.inflate(this, R.layout.view_common_nodata, null);
        mList = new ArrayList<>();
    }

    @Override
    protected void initData() {
        mAdapter = new UnionApplyRecordAdapter(mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setEmptyView(mEmptyView);
        requestData();
    }

    private void requestData() {
        OkHttpUtils.post()//
                .url(Common_HEADER + POST_UNION_APPLY_RECORD)
                .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                .addParams(USERID, Constant.LOGIN_USERID)
                .build()
                .execute(new Callback< List<UnionApplyRecordModel>>() {
                    @Override
                    public List<UnionApplyRecordModel> parseNetworkResponse(String mData, Response response, int id) throws Exception {
                        Type type = new TypeToken<List<UnionApplyRecordModel>>() {
                        }.getType();
                        List<UnionApplyRecordModel> list = new Gson().fromJson(mData, type);
                        return list;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(List<UnionApplyRecordModel> response, int id) {
                        mList.addAll(response);
                        mAdapter.notifyDataSetChanged();
                    }


                });
    }

}
