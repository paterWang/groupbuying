package com.feitianzhu.fu700.me.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.base.SFActivity;
import com.feitianzhu.fu700.common.impl.onNetFinishLinstenerT;
import com.feitianzhu.fu700.me.adapter.MyFriendAdapter;
import com.feitianzhu.fu700.model.FuFriendModel;
import com.feitianzhu.fu700.shop.ShopDao;
import com.feitianzhu.fu700.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * description: 我的福友
 * autour: dicallc
*/
public class MyFriendActivity extends SFActivity
    implements BaseQuickAdapter.RequestLoadMoreListener {

  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.list) RecyclerView mList;
  private MyFriendAdapter mAdapter;
  private List<FuFriendModel.ListEntity> mLists=new ArrayList<>();
  private int index=1;
  private boolean hasNextPage=true;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my_friend);
    ButterKnife.bind(this);
    mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
    mAdapter = new MyFriendAdapter(mLists);
    mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        Intent mIntent = new Intent(MyFriendActivity.this, ShopDetailActivity.class);
        mIntent.putExtra("otherId",mLists.get(i).userId+"");
        startActivity(mIntent);
      }
    });
    View mEmptyView = View.inflate(this, R.layout.view_common_nodata, null);
    ImageView img_empty = (ImageView) mEmptyView.findViewById(R.id.img_empty);
    img_empty.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        index=1;
        initData(false);
      }
    });
    mAdapter.setEmptyView(mEmptyView);
    mAdapter.setOnLoadMoreListener(this,mList);
    mList.setAdapter(mAdapter);
    initData(false);
  }

  private void initData(final boolean isLoadMore) {
    if (!isLoadMore){
        showloadDialog("");
    }
    ShopDao.loadFUFriend(index+"", new onNetFinishLinstenerT<FuFriendModel>() {
      @Override public void onSuccess(int code, FuFriendModel result) {
        hasNextPage=result.pager.hasNextPage;
        mAdapter.addData(result.list);
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

  @Override public void onLoadMoreRequested() {
    if (!hasNextPage){
      mAdapter.loadMoreEnd();
    }else{
      index+=1;
      initData(true);
    }
  }
}
