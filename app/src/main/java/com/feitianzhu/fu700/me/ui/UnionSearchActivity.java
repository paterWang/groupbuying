package com.feitianzhu.fu700.me.ui;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.feitianzhu.fu700.App;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.me.adapter.UnionSearchAdapter;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.helper.DialogHelper;
import com.feitianzhu.fu700.model.FuFriendModel;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.feitianzhu.fu700.common.Constant.ACCESSTOKEN;
import static com.feitianzhu.fu700.common.Constant.Common_HEADER;
import static com.feitianzhu.fu700.common.Constant.LOAD_FU_FRIEND;
import static com.feitianzhu.fu700.common.Constant.PAGEINDEX;
import static com.feitianzhu.fu700.common.Constant.PAGEROWS;
import static com.feitianzhu.fu700.common.Constant.USERID;

/**
 * Created by Vya on 2017/9/24.
 */

public class UnionSearchActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.et_input)
    EditText mEditText;


    private UnionSearchAdapter mAdapter;
    private int index = 0;
    private List<FuFriendModel.ListEntity> mLists=new ArrayList<>();
    private boolean hasNextPage=true;
    private View mEmptyView;


    private String telNum="";

    @Override
    protected int getLayoutId() {
        return R.layout.union_search_view;
    }

    @Override
    protected void initView() {
        mEmptyView = View.inflate(this, R.layout.view_common_nodata, null);
        mEditText.addTextChangedListener(new MyTextWatch());
    }

    @Override
    protected void useNavigationBar() {

    }

    @Override
    protected void initData() {
        mAdapter = new UnionSearchAdapter(mLists);
        mAdapter.setEmptyView(mEmptyView);
        mAdapter.setOnLoadMoreListener(this,mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnPhoneClickListener(new UnionSearchAdapter.OnPhoneButtonClickListener() {
            @Override
            public void onPhoneClick(int position, String number, View v) {
                new DialogHelper(UnionSearchActivity.this).init(DialogHelper.DIALOG_ONE,v).buildDialog(number,"呼叫", new DialogHelper.OnButtonClickListener<String>() {
                    @Override
                    public void onButtonClick(View v, String result, View clickView) {
                        telNum = result;
                        requestPermission();
                    }
                });
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent mIntent = new Intent(UnionSearchActivity.this, ShopDetailActivity.class);
                mIntent.putExtra("otherId",mLists.get(i).userId+"");
                startActivity(mIntent);
            }
        });

    }


    private void requestPermission() {
        AndPermission.with(this)
                .requestCode(200)
                .permission(
                        // 多个权限，以数组的形式传入。
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .callback(
                        new RationaleListener() {
                            @Override
                            public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                                // 此对话框可以自定义，调用rationale.resume()就可以继续申请。
                                AndPermission.rationaleDialog(App.getAppContext(), rationale).show();
                            }
                        }
                )
                .callback(listener)
                .start();
    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。

            // 这里的requestCode就是申请时设置的requestCode。
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
            if (requestCode == 200) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+telNum));
                startActivity(intent);
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == 200) {
                Toast.makeText(UnionSearchActivity.this, "请求权限失败!", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void requestData(final boolean isLoadMore,String keyword) {
                if(TextUtils.isEmpty(keyword)){
                    return;
                }
        OkHttpUtils.post()//
                .url(Common_HEADER + LOAD_FU_FRIEND)
                .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
//                .addParams(ACCESSTOKEN, "1be1a0caf9b74ea1a0020ac0faef9727")//
                .addParams(USERID, Constant.LOGIN_USERID)
//                .addParams(USERID, "2")
                .addParams(PAGEINDEX, index+"")//
                .addParams(PAGEROWS, 10 + "")//
                .addParams(PAGEROWS, 10 + "")//
                .addParams("keyword", keyword)//
                .build().execute(new Callback<FuFriendModel>() {


            @Override
            public void onError(Call call, Exception e, int id) {
                if (isLoadMore)
                    mAdapter.loadMoreFail();
                ToastUtils.showShortToast(e.getMessage());
            }

            @Override
            public void onResponse(FuFriendModel response, int id) {
                hasNextPage=response.pager.hasNextPage;
                mAdapter.addData(response.list);
                if (isLoadMore)mAdapter.loadMoreComplete();
            }
        });

    }

    @Override
    public void onLoadMoreRequested() {
        if (!hasNextPage){
            mAdapter.loadMoreEnd();
        }else{
            index+=1;

            requestData(true,TextUtils.isEmpty(mEditText.getText().toString())?"":mEditText.getText().toString());
        }
    }


    private class MyTextWatch implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String txt = s.toString();
            if(TextUtils.isEmpty(txt)){
                if(TextUtils.isEmpty(mEditText.getText().toString())){
                    txt = "";
                }else {
                    txt = mEditText.getText().toString();
                }
            }
            requestData(false,txt);
        }
    }

    @OnClick(R.id.tv_back)
    public void onClick(View v){
        switch (v.getId()){
            case R.id.tv_back:
                finish();
                break;
        }
    }

}
