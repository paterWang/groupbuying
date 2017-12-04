package com.feitianzhu.fu700.shop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.common.base.SFActivity;
import com.feitianzhu.fu700.common.impl.onNetFinishLinstenerT;
import com.feitianzhu.fu700.model.RecommndShopModel;
import com.feitianzhu.fu700.shop.ShopDao;
import com.feitianzhu.fu700.shop.adapter.ShopRecommendAdapter;
import com.feitianzhu.fu700.shop.adapter.ShopSearchAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopSearchActivity extends SFActivity {

    @BindView(R.id.toolbar)
    LinearLayout toolbar;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.et_keyword)
    EditText etKeyword;
    @BindView(R.id.btn_search)
    TextView btnSearch;
    private ShopSearchAdapter mAdapter;
    private List<RecommndShopModel.ListEntity> Models = new ArrayList<>();
    private int page;
    private String keyword;
    private boolean hasNextPage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_search);
        ButterKnife.bind(this);
        initView();
//        mAdapter.setOnLoadMoreListener(this,list);
    }

    private void initData() {
        showloadDialog("");
        ShopDao.LoadShopsSearchData(page, keyword, new onNetFinishLinstenerT<RecommndShopModel>() {
            @Override
            public void onSuccess(int code, RecommndShopModel result) {
//                hasNextPage=result.pager.hasNextPage;
                if (null == result.list || 0 == result.list.size()) {
                    mAdapter.getEmptyView().setVisibility(View.VISIBLE);
                    return;
                } else {
                    mAdapter.getEmptyView().setVisibility(View.GONE);
                    mAdapter.getData().clear();
                    mAdapter.getData().addAll(result.list);
                }
                mAdapter.getData();
                goneloadDialog();
            }

            @Override
            public void onFail(int code, String result) {
                goneloadDialog();
                mAdapter.getEmptyView().setVisibility(View.VISIBLE);
            }
        });
    }

    private void initView() {
        mAdapter = new ShopSearchAdapter(Models);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                RecommndShopModel.ListEntity listEntity = mAdapter.getData().get(i);
                Intent mIntent = new Intent(ShopSearchActivity.this, ShopsActivity.class);
                mIntent.putExtra(Constant.ISADMIN, false);
                mIntent.putExtra(Constant.MERCHANTID, listEntity.merchantId+"");
                startActivity(mIntent);
            }
        });
        mAdapter.setEmptyView(View.inflate(sfContext, R.layout.view_common_nodata, null));
        list.setAdapter(mAdapter);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.btn_search)
    public void onViewClicked() {
        keyword = etKeyword.getText().toString().trim();
        initData();
    }

//    @Override
//    public void onLoadMoreRequested() {
//        if (hasNextPage){
//            page+=1;
//        }
//    }
}
