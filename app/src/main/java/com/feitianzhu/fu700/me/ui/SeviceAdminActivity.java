package com.feitianzhu.fu700.me.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.common.base.SFActivity;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.common.impl.onNetFinishLinstenerT;
import com.feitianzhu.fu700.me.adapter.MyServiceAdminAdapter;
import com.feitianzhu.fu700.model.ShopsService;
import com.feitianzhu.fu700.shop.ShopDao;
import com.feitianzhu.fu700.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * description: 服务管理
 * autour: dicallc
 */
public class SeviceAdminActivity extends SFActivity
    implements BaseQuickAdapter.RequestLoadMoreListener {

  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.list) RecyclerView list;

  List<ShopsService.ListEntity> lists = new ArrayList<>();
  private MyServiceAdminAdapter mAdapter;
  private String mMerchantId;
  private int page = 1;
  private boolean hasNextPage = true;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sevice_admin);
    ButterKnife.bind(this);
    mMerchantId = getIntent().getStringExtra(Constant.MERCHANTID);
    initView();

    mAdapter = new MyServiceAdminAdapter(lists);
    View mEmptyView = View.inflate(this, R.layout.view_common_nodata, null);
    ImageView img_empty = (ImageView) mEmptyView.findViewById(R.id.img_empty);
    img_empty.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        page = 1;
        initData(false);
      }
    });
    mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
      @Override public void onItemClick(BaseQuickAdapter mBaseQuickAdapter, View mView, int mI) {
        Intent mIntent = new Intent(SeviceAdminActivity.this, ServiceDetailActivity.class);
        ShopsService.ListEntity mEntity = lists.get(mI);
        mIntent.putExtra("serviceid",mEntity.serviceId+"");

        startActivity(mIntent);
      }
    });
    mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
      @Override
      public void onItemChildClick(final BaseQuickAdapter mBaseQuickAdapter, View mView, final int mI) {
        if (mView.getId() == R.id.btn_edit) {
          Intent mIntent = new Intent(SeviceAdminActivity.this, EditServiceActivity.class);
          mIntent.putExtra("serviceAdmin", lists.get(mI));
          startActivity(mIntent);
        }else{
          int mServiceId = lists.get(mI).serviceId;
          showloadDialog("");
          ShopDao.deleteServe(mServiceId, new onConnectionFinishLinstener() {
            @Override public void onSuccess(int code, Object result) {
              goneloadDialog();
              ToastUtils.showShortToast("删除成功");
              mBaseQuickAdapter.remove(mI);
            }

            @Override public void onFail(int code, String result) {
              goneloadDialog();
              ToastUtils.showShortToast(result);
            }
          });
        }
      }
    });
    mAdapter.setEmptyView(mEmptyView);
    mAdapter.setOnLoadMoreListener(this, list);
    list.setAdapter(mAdapter);
  }

  @Override protected void onStart() {
    super.onStart();
    initData(false);
  }

  private void initView() {
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
  }

  private void initData(final boolean isLoadMore) {
    if (!isLoadMore) {
      page=1;
      showloadDialog("加载数据中");
    }
    ShopDao.loadShopsServiceInfo(page, "", new onNetFinishLinstenerT<ShopsService>() {
      @Override public void onSuccess(int code, ShopsService result) {
        hasNextPage = result.pager.hasNextPage;
        List<ShopsService.ListEntity> mList = result.list;


        try {
          for (ShopsService.ListEntity mEntity : mList) {
            String[] arr = mEntity.photos.split("\\,");
            mEntity.one_photo = arr[0];
          }
        } catch (Exception e) {

        }
        if (isLoadMore) mAdapter.loadMoreComplete();
        mAdapter.getData().clear();
        mAdapter.addData(mList);
        goneloadDialog();
      }

      @Override public void onFail(int code, String result) {
        if (isLoadMore) mAdapter.loadMoreFail();
        goneloadDialog();
        ToastUtils.showShortToast(result);
      }
    });
  }

  @OnClick({ R.id.tv_push }) public void onClick(View v) {
    switch (v.getId()) {
      case R.id.tv_push:

        Intent mPushIntent = new Intent(SeviceAdminActivity.this, PushServiceActivity.class);
        startActivity(mPushIntent);
        break;
    }
  }

  @Override public void onLoadMoreRequested() {
    if (!hasNextPage) {
      mAdapter.loadMoreEnd();
    } else {
      page += 1;
      initData(true);
    }
  }
}
