package com.feitianzhu.fu700.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.common.base.LazyWebActivity;
import com.feitianzhu.fu700.common.base.SFFragment;
import com.feitianzhu.fu700.common.impl.onNetFinishLinstenerT;
import com.feitianzhu.fu700.model.Province;
import com.feitianzhu.fu700.model.RecommndShopModel;
import com.feitianzhu.fu700.model.ShopType;
import com.feitianzhu.fu700.model.ShopsIndex;
import com.feitianzhu.fu700.model.ShopsNearby;
import com.feitianzhu.fu700.shop.adapter.ShopHomeAdapter;
import com.feitianzhu.fu700.shop.adapter.ShopRecommendAdapter;
import com.feitianzhu.fu700.shop.adapter.ShopTypeAdapter;
import com.feitianzhu.fu700.shop.ui.ShopHeadView;
import com.feitianzhu.fu700.shop.ui.ShopSearchActivity;
import com.feitianzhu.fu700.shop.ui.ShopTypeActivity;
import com.feitianzhu.fu700.shop.ui.ShopsActivity;
import com.feitianzhu.fu700.shop.ui.dialog.ProvinceCallBack;
import com.feitianzhu.fu700.shop.ui.dialog.ProvincehDialog;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;

/**
 * description:  购物界面
 * autour: dicallc
 */
public class ShopFragment extends SFFragment
        implements SwipeRefreshLayout.OnRefreshListener, ProvinceCallBack,
        BaseQuickAdapter.RequestLoadMoreListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.txt_location)
    TextView mTxtLocation;
    @BindView(R.id.tl_4)
    SegmentTabLayout mTl4;
    @BindView(R.id.img_search)
    ImageView mImgSearch;
    @BindView(R.id.img_shop)
    ImageView mImgShop;
    Unbinder unbinder;
    @BindView(R.id.shop_list)
    RecyclerView mShopList;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeLayout;
    private String[] mTitles = {"商户", "商城"};
    private String mParam1;
    private String mParam2;
    private ShopHeadView mShopHeadView;
    private ShopHomeAdapter mAdapter;
    /**
     * 进行实名认证
     */
    private int Veri_User = 1;
    private Intent mIntent;
    private int recmoondIndex = 1;
    private ImageView refreshView;
    private String provinceId;
    private String cityId;
    private String h5_url;
    private  List<ShopType> clsList;
    public ShopFragment() {
    }

    public static ShopFragment newInstance(String param1, String param2) {
        ShopFragment fragment = new ShopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        initData();
    }

    @Override
    public void onStart() {
        super.onStart();
        KLog.e("onStart");
        ShopDao.loadUserAuthImpl();
    }

    @Override
    public void onResume() {
        super.onResume();
        KLog.e("onResume");

    }

    private void initData() {


        showloadDialog("");
        ShopDao.LoadShopsIndex(provinceId, cityId, new onNetFinishLinstenerT<ShopsIndex>() {
            @Override
            public void onSuccess(int code, ShopsIndex result) {
                goneloadDialog();
                try {
                    h5_url=result.mallUrl;
                    nearbyHasNextPage = result.nextPager.nearByMerchantHashNext;
                    recmoondHasNextPage = result.nextPager.recommendHashNext;

                    clsList = result.clsList;
                    ShopTypeAdapter shopTypeAdapter = mShopHeadView.getShopTypeAdapter();
                    shopTypeAdapter.getData().clear();
                    shopTypeAdapter.addData(clsList);

                    ShopRecommendAdapter shopRecommendAdapter = mShopHeadView.getShopRecommendAdapter();
                    shopRecommendAdapter.getData().clear();
                    shopRecommendAdapter.addData(result.recommendList);
                    mAdapter.getData().clear();
                    mAdapter.addData(result.nearByMerchantList);
                    mSwipeLayout.setRefreshing(false);
                    mAdapter.setEnableLoadMore(true);
                } catch (Exception mE) {

                }

            }

            @Override
            public void onFail(int code, String result) {
                KLog.e(result);
                goneloadDialog();
                mSwipeLayout.setRefreshing(false);
                mAdapter.setEnableLoadMore(true);
            }
        });
    }

    boolean nearbyHasNextPage = true;
    boolean recmoondHasNextPage = true;
    int index = 1;

    @Override
    public void onLoadMoreRequested() {
        if (nearbyHasNextPage) {
            ShopDao.LoadNearbyShops(index, provinceId, cityId, "", new onNetFinishLinstenerT<ShopsNearby>() {
                @Override
                public void onSuccess(int code, ShopsNearby result) {
                    KLog.e("onSuccess" + result);
                    if(null==result||null==result.pager){
                        onFail(404,"服务器出了点问题 请稍候再试");
                        return;
                    }
                    index += 1;
                    nearbyHasNextPage = result.pager.hasNextPage;
                    mAdapter.addData(result.list);
                    mAdapter.loadMoreComplete();
                }

                @Override
                public void onFail(int code, String result) {
                    KLog.e(result);
                    mAdapter.loadMoreFail();
                }
            });
        } else {
            mAdapter.loadMoreEnd();
        }
    }

    public void showRefreshAnimation() {
        //显示刷新动画
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.refresh);
        //设置重复模式  Defines what this animation should do when it reaches the end
        animation.setRepeatMode(Animation.RESTART);
        //设置重复次数
        animation.setRepeatCount(Animation.INFINITE);
        //使用ImageView 显示旋转动画
        refreshView.startAnimation(animation);
    }

    private void hideRefreshAnimation() {
        if (refreshView != null) {
            refreshView.clearAnimation();
        }
    }

    private void LoadRecmmondList(int index) {
        showRefreshAnimation();
        ShopDao.LoadReCommondShop("2", index, "4", new onNetFinishLinstenerT<RecommndShopModel>() {
            @Override
            public void onSuccess(int code, RecommndShopModel result) {
                recmoondHasNextPage = result.pager.hasNextPage;
                ShopRecommendAdapter mAdapter = mShopHeadView.getShopRecommendAdapter();
                mAdapter.getData().clear();
                mAdapter.addData(result.list);
                hideRefreshAnimation();
            }

            @Override
            public void onFail(int code, String result) {
                KLog.e(result);
                goneloadDialog();
                hideRefreshAnimation();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!TextUtils.isEmpty(Constant.mCity))mTxtLocation.setText(Constant.mCity);
        mSwipeLayout.setOnRefreshListener(this);
        mTl4.setTabData(mTitles);
        mTl4.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if (position==1){
                    Intent intent=new Intent(getActivity(), LazyWebActivity.class);
                    intent.putExtra(Constant.URL,h5_url);
                    intent.putExtra(Constant.H5_TITLE,"lalal");
                    startActivity(intent);
                    mTl4.setCurrentTab(0);
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mShopHeadView = new ShopHeadView(getActivity());
        refreshView = mShopHeadView.getmImgShuaxin();
        refreshView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recmoondHasNextPage) {
                    recmoondIndex += 1;
                } else {
                    recmoondIndex = 1;
                }
                LoadRecmmondList(recmoondIndex);
            }
        });
        mShopHeadView.getShopTypeAdapter()
                .setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter mBaseQuickAdapter, View mView, int mI) {
                        if(null==clsList||0==clsList.size()){
                            ToastUtils.showShortToast("数据加载失败，请稍候再试");
                            return;
                        }
                        Intent mIntent = new Intent(getActivity(), ShopTypeActivity.class);
                        String temp = clsList.get(mI).clsId+"";
                        mIntent.putExtra("putClsId",temp);
                        startActivity(mIntent);
                    }
                });
        mShopHeadView.getShopRecommendAdapter()
                .setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter mBaseQuickAdapter, View mView, int mI) {

                        RecommndShopModel.ListEntity listEntity =
                                (RecommndShopModel.ListEntity) mBaseQuickAdapter.getData().get(mI);
                        Intent mIntent = new Intent(getActivity(), ShopsActivity.class);
                        mIntent.putExtra(Constant.ISADMIN, false);
                        mIntent.putExtra(Constant.MERCHANTID, listEntity.merchantId + "");
                        startActivity(mIntent);
                    }
                });
        initLocalData();
        initList();
        veriAction();
    }

    private void veriAction() {
    }

    private void initList() {
        List<ShopsIndex.NearByMerchantListBean> mTypeModels = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            mTypeModels.add(new ShopsIndex.NearByMerchantListBean());
        }
        mShopList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ShopHomeAdapter(mTypeModels);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter mBaseQuickAdapter, View mView, int mI) {
                ShopsIndex.NearByMerchantListBean listEntity =
                        (ShopsIndex.NearByMerchantListBean) mBaseQuickAdapter.getData().get(mI);
                Intent mIntent = new Intent(getActivity(), ShopsActivity.class);
                mIntent.putExtra(Constant.ISADMIN, false);
                mIntent.putExtra(Constant.MERCHANTID, listEntity.merchantId + "");
                startActivity(mIntent);
            }
        });
        mAdapter.addHeaderView(mShopHeadView);
        mAdapter.setOnLoadMoreListener(this, mShopList);
        mShopList.setAdapter(mAdapter);
    }

    private void initLocalData() {
        List<ShopType> mTypeModels = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            mTypeModels.add(new ShopType());
        }
        mShopHeadView.setTypeModels(mTypeModels);

        List<RecommndShopModel.ListEntity> mRecmmondModels = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            mRecmmondModels.add(new RecommndShopModel.ListEntity());
        }
        mShopHeadView.setRecmmondModels(mRecmmondModels);
        mShopHeadView.getShopTypeAdapter().getData();
        mShopHeadView.getShopRecommendAdapter().getData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        index = 1;
        nearbyHasNextPage = true;
        recmoondHasNextPage = true;
        mAdapter.setEnableLoadMore(false);
        initData();
    }

    @OnClick(R.id.txt_location)
    public void onViewClicked() {
        ProvincehDialog branchDialog = ProvincehDialog.newInstance(getActivity());
        branchDialog.setAddress("北京市", "北京市");
        branchDialog.setSelectOnListener(this);
        branchDialog.show(getChildFragmentManager());
    }

    @Override
    public void onWhellFinish(Province province, Province.CityListBean city,
        Province.AreaListBean mAreaListBean) {
        Constant.provinceId = provinceId = province.id;
        Constant.cityId = cityId = city.id;
        mTxtLocation.setText(city.name);
        initData();
    }

    @OnClick(R.id.img_search)
    public void onSearchClicked() {
        Intent intent = new Intent(getActivity(), ShopSearchActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.img_shop)
    public void onShopsAdminClicked() {
        if (!Constant.loadUserAuth) {
            ToastUtils.showShortToast("正在获取授权信息，稍候进入");
            ShopDao.loadUserAuthImpl();
            return;
        } else {
            ShopHelp.veriJumpActivity(getActivity());
        }

    }


}
