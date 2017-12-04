package com.feitianzhu.fu700.me.ui.totalScore;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.me.adapter.ReleaseTotalDetailAdapter;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.model.ReleaseTotalDetailModel;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

import static com.feitianzhu.fu700.common.Constant.ACCESSTOKEN;
import static com.feitianzhu.fu700.common.Constant.Common_HEADER;
import static com.feitianzhu.fu700.common.Constant.POST_RELEASE_SCORE_DETAIL;
import static com.feitianzhu.fu700.common.Constant.USERID;

/**
 * Created by Vya on 2017/11/12.
 */

public class ReleaseTotalScoreActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.text_big)
    TextView mTextBig;
    @BindView(R.id.recyclerview_release)
    RecyclerView mRecyclerView;

    private ReleaseTotalDetailAdapter mAdapter;
    private String PageRows = "6";
    private final static int LOAD_NORMAL = 0;
    private final static int LOAD_MORE = 1;
    private boolean isHasNextPage = false;
    private View mEmptyView;
    private int currPage = 1;
    private   String flag; //传过来的标记
    private String totalScore; //传过来的总积分
    private List<ReleaseTotalDetailModel.ListBean> mDatas;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_release_view;
    }

    @Override
    protected void initTitle() {
        defaultNavigationBar = new DefaultNavigationBar
                .Builder(ReleaseTotalScoreActivity.this, (ViewGroup) findViewById(R.id.Rl_titleContainer))
                .setTitle("释放积分明细")
                .setStatusHeight(ReleaseTotalScoreActivity.this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        mDatas = new ArrayList<>();
        flag = intent.getStringExtra("releaseFlag");
        totalScore = intent.getStringExtra("releaseTotalScore");
        if(TextUtils.isEmpty(flag)){
            flag = "-1";
        }
        if(!TextUtils.isEmpty(totalScore)){
            mTextBig.setText(totalScore);
        }else{
            mTextBig.setText("0");
        }
        mEmptyView = View.inflate(this, R.layout.view_common_nodata, null);
    }

    private void requestData(final int state) {
        showloadDialog("正在加载");
        OkHttpUtils.post()//
                .url(Common_HEADER + POST_RELEASE_SCORE_DETAIL)
                .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                .addParams(USERID, Constant.LOGIN_USERID)
                .addParams("type",flag)
                .addParams("pageIndex",currPage+"")
                .addParams("pageRows",PageRows)
                .build().execute(new Callback<ReleaseTotalDetailModel>() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e("Test", "--Error-->" + e.getMessage());
                goneloadDialog();
                mAdapter.loadMoreFail();
                ToastUtils.showShortToast(e.getMessage());
            }

            @Override
            public void onResponse(ReleaseTotalDetailModel response, int id) {
                goneloadDialog();
                if(response == null){
                    return;
                }
                if(response!=null && response.getPager()!=null){
                    isHasNextPage = response.getPager().isHasNextPage();
                }


                if (null == response.getList() || 0 == response.getList().size()) {
                    mAdapter.setEmptyView(mEmptyView);
                    mAdapter.getEmptyView().setVisibility(View.VISIBLE);
                    return;
                }
                if (mAdapter.getEmptyView() != null) {
                    mAdapter.getEmptyView().setVisibility(View.GONE);
                }

                switch (state) {
                    case LOAD_NORMAL:
                        mDatas.clear();
                        mDatas.addAll(response.getList());
                        mAdapter.notifyDataSetChanged();
                        break;
                    case LOAD_MORE:
                        mDatas.addAll(response.getList());
                        mAdapter.notifyDataSetChanged();
                        break;
                }
                if (isHasNextPage) {
                    currPage++;
                    mAdapter.loadMoreComplete();
                } else{
                    mAdapter.loadMoreEnd();
                }



            }
        });
    }

    @Override
    protected void initData() {
        mAdapter = new ReleaseTotalDetailAdapter(mDatas);
        mAdapter.setOnLoadMoreListener(this,mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        requestData(LOAD_NORMAL);
    }

    @Override
    public void onLoadMoreRequested() {
        if (!isHasNextPage){
            mAdapter.loadMoreEnd();
        }else{
            requestData(LOAD_MORE);
        }
    }
}
