package com.feitianzhu.fu700.me.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.feitianzhu.fu700.App;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.impl.onNetFinishLinstenerT;
import com.feitianzhu.fu700.me.adapter.UnionNumberAdapter;
import com.feitianzhu.fu700.me.base.BaseFragment;
import com.feitianzhu.fu700.me.helper.DialogHelper;
import com.feitianzhu.fu700.me.ui.ShopDetailActivity;
import com.feitianzhu.fu700.me.ui.UnionSearchActivity;
import com.feitianzhu.fu700.model.FuFriendModel;
import com.feitianzhu.fu700.shop.ShopDao;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Vya on 2017/8/29 0029.
 */

public class UnionNumberFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private UnionNumberAdapter mAdapter;
    private List<FuFriendModel.ListEntity> mLists=new ArrayList<>();
    private int index=1;
    private boolean hasNextPage=true;
    private View mEmptyView;
    @Override
    protected void initView() {

        mEmptyView = View.inflate(getContext(), R.layout.view_common_nodata, null);
    }

    @Override
    protected void initData() {
        mAdapter = new UnionNumberAdapter(mLists);
        mAdapter.setEmptyView(mEmptyView);
        mAdapter.setOnLoadMoreListener(this,mRecyclerView);
        mAdapter.setOnPhoneClickListener(new UnionNumberAdapter.OnPhoneButtonClickListener() {
            @Override
            public void onPhoneClick(int position, String number,View v) {
                new DialogHelper(getActivity()).init(DialogHelper.DIALOG_ONE,v).buildDialog(number,"呼叫", new DialogHelper.OnButtonClickListener<String>() {
                    @Override
                    public void onButtonClick(View v, String result, View clickView) {
                        telNum = result;
                        requestPermission();
                    }
                });
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        requestData(false);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent mIntent = new Intent(getContext(), ShopDetailActivity.class);
                mIntent.putExtra("otherId",mLists.get(i).userId+"");
                startActivity(mIntent);
            }
        });
    }


    private String telNum="";
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
                Toast.makeText(getContext(), "请求权限失败!", Toast.LENGTH_SHORT).show();
            }
        }
    };


    private void requestData(final boolean isLoadMore) {
        if (!isLoadMore){
            showloadDialog("");
        }
        ShopDao.loadFUFriend(index+"", new onNetFinishLinstenerT<FuFriendModel>() {
            @Override public void onSuccess(int code, FuFriendModel result) {
                if(result!=null && result.pager != null){
                    hasNextPage=result.pager.hasNextPage;
                }
                if(result!=null && result.list!=null){
                    mAdapter.addData(result.list);
                }
                if (isLoadMore)mAdapter.loadMoreComplete();
                goneloadDialog();
            }

            @Override public void onFail(int code, String result) {
                if (isLoadMore)
                    mAdapter.loadMoreFail();
                goneloadDialog();
                ToastUtils.showShortToast(result);
            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_union_number;
    }

    @Override
    public void onLoadMoreRequested() {
        if (!hasNextPage){
            mAdapter.loadMoreEnd();
        }else{
            index+=1;
            requestData(true);
        }
    }

    @OnClick(R.id.rl_search)
    public void onClick(View v){
        switch (v.getId()){
            case R.id.rl_search:
                Intent intent = new Intent(getContext(), UnionSearchActivity.class);
                startActivity(intent);
                break;
        }
    }
}
