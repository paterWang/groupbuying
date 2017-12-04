package com.feitianzhu.fu700.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.feitianzhu.fu700.App;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.dao.NetworkDao;
import com.feitianzhu.fu700.events.NotifyEvent;
import com.feitianzhu.fu700.home.adapter.HomeRecommendAdapter;
import com.feitianzhu.fu700.home.entity.HomeEntity;
import com.feitianzhu.fu700.huanghuali.HuangHuaLiHTMLActivity;
import com.feitianzhu.fu700.me.ui.ScannerActivity;
import com.feitianzhu.fu700.me.ui.ServiceDetailActivity;
import com.feitianzhu.fu700.me.ui.totalScore.HotServiceActivity;
import com.feitianzhu.fu700.shop.ui.HotShopActivity;
import com.feitianzhu.fu700.shop.ui.ShopSearchActivity;
import com.feitianzhu.fu700.shop.ui.ShopsActivity;
import com.feitianzhu.fu700.utils.GlideImageLoader;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.feitianzhu.fu700.utils.Urls;
import com.google.gson.Gson;
import com.socks.library.KLog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import okhttp3.Call;
import okhttp3.Response;

import static com.feitianzhu.fu700.common.Constant.ISADMIN;
import static com.feitianzhu.fu700.common.Constant.MERCHANTID;

public class HomeFragment extends Fragment implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener, OnBannerListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.search)
    LinearLayout mSearchLayout;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeLayout;
    Unbinder unbinder;

    private HomeRecommendAdapter mAdapter;
    private Banner mBanner;
    private View mHeader;
    private View mFooter;
    private List<HomeEntity.BannerListBean> mBanners;
    private List<String> mBannerUrls;
    private List<HomeEntity.RecommendListBean> mRecommends = new ArrayList<>();
    private ImageView mImageView1;
    private ImageView mImageView2;
    private ImageView mImageView3;
    private ImageView mImageView4;
    private ImageView mImageView5;
    private TextView mTitle1;
    private TextView mTitle2;
    private TextView mDetail1;
    private TextView mDetail2;
    private TextView mFanDian1;
    private TextView mFanDian2;
    private TextView mFanDian3;
    private TextView mName1;
    private TextView mName2;
    private TextView mName3;
    private TextView mPrice1;
    private TextView mPrice2;
    private TextView mPrice3;
    private int serviceId1, serviceId2, serviceId3, serviceId4, serviceId5;
    private HomeEntity mHomeEntity;
    private ImageView mNotifyImageView;

    public HomeFragment() {

    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);

        mNotifyImageView = (ImageView) view.findViewById(R.id.iv_home_nv_left);
        mNotifyImageView.setOnClickListener(this);
        view.findViewById(R.id.iv_home_nv_right).setOnClickListener(this);
        view.findViewById(R.id.search).setOnClickListener(this);

        mHeader = LayoutInflater.from(getActivity()).inflate(R.layout.home_header, null);
        mFooter = LayoutInflater.from(getActivity()).inflate(R.layout.home_footer, null);

        mBanner = (Banner) mHeader.findViewById(R.id.banner);
        mHeader.findViewById(R.id.iv_more).setOnClickListener(this);

        mFooter.findViewById(R.id.rl_image1_1).setOnClickListener(this);
        mFooter.findViewById(R.id.rl_image1_2).setOnClickListener(this);
        mFooter.findViewById(R.id.ll_image2_1).setOnClickListener(this);
        mFooter.findViewById(R.id.ll_image2_2).setOnClickListener(this);
        mFooter.findViewById(R.id.ll_image2_3).setOnClickListener(this);
        mFooter.findViewById(R.id.tv_more).setOnClickListener(this);
        mFooter.findViewById(R.id.iv_duobao).setOnClickListener(this);
        mFooter.findViewById(R.id.iv_suyuan).setOnClickListener(this);
        mFooter.findViewById(R.id.iv_hunaghuali).setOnClickListener(this);

        mImageView1 = (ImageView) mFooter.findViewById(R.id.imageview1);
        mImageView2 = (ImageView) mFooter.findViewById(R.id.imageview2);
        mImageView3 = (ImageView) mFooter.findViewById(R.id.imageview3);
        mImageView4 = (ImageView) mFooter.findViewById(R.id.imageview4);
        mImageView5 = (ImageView) mFooter.findViewById(R.id.imageview5);

        mTitle1 = (TextView) mFooter.findViewById(R.id.tv_title1);
        mTitle2 = (TextView) mFooter.findViewById(R.id.tv_title2);

        mDetail1 = (TextView) mFooter.findViewById(R.id.tv_detail1);
        mDetail2 = (TextView) mFooter.findViewById(R.id.tv_detail2);

        mFanDian1 = (TextView) mFooter.findViewById(R.id.tv_fandian1);
        mFanDian2 = (TextView) mFooter.findViewById(R.id.tv_fandian2);
        mFanDian3 = (TextView) mFooter.findViewById(R.id.tv_fandian3);

        mName1 = (TextView) mFooter.findViewById(R.id.tv_name1);
        mName2 = (TextView) mFooter.findViewById(R.id.tv_name2);
        mName3 = (TextView) mFooter.findViewById(R.id.tv_name3);

        mPrice1 = (TextView) mFooter.findViewById(R.id.tv_price1);
        mPrice2 = (TextView) mFooter.findViewById(R.id.tv_price2);
        mPrice3 = (TextView) mFooter.findViewById(R.id.tv_price3);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        mRecyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 5));


        mAdapter = new HomeRecommendAdapter(mRecommends);
        mRecyclerview.setAdapter(mAdapter);

        mAdapter.addHeaderView(mHeader);
        mAdapter.addFooterView(mFooter);
        mAdapter.setOnItemClickListener(this);

        mBanner.setOnBannerListener(this);

        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeColors(getActivity().getResources().getColor(R.color.sf_blue));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mBanners = new ArrayList<>();
        mBannerUrls = new ArrayList<>();


//        NetworkDao.getNotices(1 + "", 5 + "", new onConnectionFinishLinstener() {
//            @Override
//            public void onSuccess(int code, Object result) {
//
//                NoticeEntity entity = (NoticeEntity) result;
//
//                if (null == entity.list || 0 == entity.list.size()) {
//                    mNotifyImageView.setImageResource(R.mipmap.icon_xiaoxi);
//                } else {
//                    mNotifyImageView.setImageResource(R.mipmap.icon_notify_has);
//                }
//
//            }
//
//            @Override
//            public void onFail(int code, String result) {
//                mNotifyImageView.setImageResource(R.mipmap.icon_xiaoxi);
//            }
//        });

        getData();
    }

    private void getData() {

        NetworkDao.getHuanghualiInfo(new onConnectionFinishLinstener() {
            @Override
            public void onSuccess(int code, Object result) {

            }

            @Override
            public void onFail(int code, String result) {

            }
        });

        OkHttpUtils
                .post()
                .url(Urls.GET_INDEX)
                .addParams("accessToken", Constant.ACCESS_TOKEN)
                .addParams("userId", Constant.LOGIN_USERID)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(String mData, Response response, int id) throws Exception {
                        return new Gson().fromJson(mData, HomeEntity.class);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (mSwipeLayout != null) {
                            mSwipeLayout.setRefreshing(false);
                        }
                        Toast.makeText(getActivity(), TextUtils.isEmpty(e.getMessage()) ? "加载失败，请重试" : e.getMessage(), Toast.LENGTH_SHORT).show();
                        KLog.e(e);
                    }

                    @Override
                    public void onResponse(Object response, int id) {
                        if (mSwipeLayout != null) {
                            mSwipeLayout.setRefreshing(false);
                        }

                        KLog.i("response:%s", response);

                        mHomeEntity = (HomeEntity) response;

                        if (mHomeEntity == null) {
                            return;
                        }

                        //1.set banner
                        if (mHomeEntity.bannerList != null && !mHomeEntity.bannerList.isEmpty()) {
                            mBanners.clear();
                            mBannerUrls.clear();
                            for (HomeEntity.BannerListBean bean : mHomeEntity.bannerList) {
                                mBannerUrls.add(bean.imagUrl);
                            }
                            mBanners.addAll(mHomeEntity.bannerList);
                            mBanner.setImageLoader(new GlideImageLoader());
                            //设置图片集合
                            mBanner.setImages(mBannerUrls);
                            mBanner.setDelayTime(3000);
                            //banner设置方法全部调用完毕时最后调用
                            mBanner.start();

                        }

                        //2. 人气商户
                        if (mHomeEntity.recommendList != null && !mHomeEntity.recommendList.isEmpty()) {
                            mRecommends.clear();
                            mRecommends.addAll(mHomeEntity.recommendList);
                            mAdapter.notifyDataSetChanged();
                        }

                        // 3. 热门服务

                        if (mHomeEntity.serviceRecommendList != null && !mHomeEntity.serviceRecommendList.isEmpty()) {

                            List<HomeEntity.ServiceRecommendListBean> serviceRecommendList = mHomeEntity.serviceRecommendList;
                            if (serviceRecommendList.get(0).serviceId > 0) {
                                serviceId1 = serviceRecommendList.get(0).serviceId;
                            }
                            setServerData(serviceRecommendList.get(0), mImageView1, mTitle1, mDetail1, null, null);

                            if (serviceRecommendList.size() >= 2) {
                                if (serviceRecommendList.get(1).serviceId > 0) {
                                    serviceId2 = serviceRecommendList.get(1).serviceId;
                                }
                                setServerData(serviceRecommendList.get(1), mImageView2, mTitle2, mDetail2, null, null);

                                if (serviceRecommendList.size() >= 3) {
                                    if (serviceRecommendList.get(2).serviceId > 0) {
                                        serviceId3 = serviceRecommendList.get(2).serviceId;
                                    }
                                    setServerData(serviceRecommendList.get(2), mImageView3, mName1, null, mFanDian1, mPrice1);

                                    if (serviceRecommendList.size() >= 4) {
                                        if (serviceRecommendList.get(3).serviceId > 0) {
                                            serviceId4 = serviceRecommendList.get(3).serviceId;
                                        }
                                        setServerData(serviceRecommendList.get(3), mImageView4, mName2, null, mFanDian2, mPrice2);

                                        if (serviceRecommendList.size() >= 5) {
                                            if (serviceRecommendList.get(4).serviceId > 0) {
                                                serviceId5 = serviceRecommendList.get(4).serviceId;
                                            }
                                            setServerData(serviceRecommendList.get(4), mImageView5, mName3, null, mFanDian3, mPrice3);

                                        }

                                    }

                                }
                            }

                        }


                    }

                    private void setServerData(HomeEntity.ServiceRecommendListBean data, ImageView imageView,
                                               TextView titleView, TextView addressView, TextView fandianView, TextView priceView) {

                        Glide.with(getActivity()).load(data.adImg).placeholder(R.mipmap.pic_fuwutujiazaishibai).into(imageView);
                        titleView.setText(data.serviceName);

                        if (addressView != null) {
                            addressView.setText(data.serviceAddr);
                        }

                        if (fandianView != null) {
                            fandianView.setText(String.format(getString(R.string.fandian), data.rebate + ""));
                        }
                        if (priceView != null) {
                            //priceView.setText(String.format(getString(R.string.money), data.price + ""));
                            priceView.setText(data.price + "");
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_more:
                startActivity(new Intent(getActivity(), HotShopActivity.class));
                break;
            case R.id.iv_home_nv_left:
                //Toast.makeText(getActivity(), "iv_home_nv_left", Toast.LENGTH_SHORT).show();
                JumpActivity(getContext(), NoticeActivity.class);
                break;
            case R.id.iv_home_nv_right:
                // Toast.makeText(getActivity(), "iv_home_nv_right", Toast.LENGTH_SHORT).show();
                requestPermission();
                break;
            case R.id.search:
//                Toast.makeText(getActivity(), "search", Toast.LENGTH_SHORT).show();
                JumpActivity(getContext(), ShopSearchActivity.class);
                break;
            case R.id.rl_image1_1:
                if (mHomeEntity.serviceRecommendList != null && !mHomeEntity.serviceRecommendList.isEmpty()) {
                    JumpActivity(getContext(), ServiceDetailActivity.class, serviceId1);
                }else{
                    ToastUtils.showShortToast("暂无商品");
                }
                //Toast.makeText(getActivity(), "rl_image1_1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_image1_2:
                if (mHomeEntity.serviceRecommendList != null && !mHomeEntity.serviceRecommendList.isEmpty()
                        && mHomeEntity.serviceRecommendList.size()>=2) {

                    JumpActivity(getContext(), ServiceDetailActivity.class, serviceId2);
                }else{
                    ToastUtils.showShortToast("暂无商品");
                }

                // Toast.makeText(getActivity(), "rl_image1_2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_image2_1:
                if (mHomeEntity.serviceRecommendList != null && !mHomeEntity.serviceRecommendList.isEmpty()
                        && mHomeEntity.serviceRecommendList.size()>=3) {
                    JumpActivity(getContext(), ServiceDetailActivity.class, serviceId3);
                }else{
                    ToastUtils.showShortToast("暂无商品");
                }

                // Toast.makeText(getActivity(), "ll_image2_1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_image2_2:
                if (mHomeEntity.serviceRecommendList != null && !mHomeEntity.serviceRecommendList.isEmpty()
                        && mHomeEntity.serviceRecommendList.size()>=4) {
                    JumpActivity(getContext(), ServiceDetailActivity.class, serviceId4);
                }else{
                    ToastUtils.showShortToast("暂无商品");
                }

                // Toast.makeText(getActivity(), "ll_image2_2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_image2_3:
                if (mHomeEntity.serviceRecommendList != null && !mHomeEntity.serviceRecommendList.isEmpty()
                        && mHomeEntity.serviceRecommendList.size()>=5) {
                    JumpActivity(getContext(), ServiceDetailActivity.class, serviceId5);
                }else{
                    ToastUtils.showShortToast("暂无商品");
                }

                // Toast.makeText(getActivity(), "ll_image2_3", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_more:
                // Toast.makeText(getActivity(), "tv_more", Toast.LENGTH_SHORT).show();
                JumpActivity(getContext(), HotServiceActivity.class);
                break;
            case R.id.iv_duobao:
                Toast.makeText(getActivity(), "此功能待开发", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_suyuan:
                Toast.makeText(getActivity(), "此功能待开发", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_hunaghuali:
                startActivity(new Intent(getActivity(), HuangHuaLiHTMLActivity.class));
                break;

        }
    }

    private void JumpActivity(Context context, Class<ServiceDetailActivity> clazz, int mId) {
        Intent intent = new Intent(context, clazz);
        intent.putExtra("serviceid", mId + "");
        context.startActivity(intent);
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        if (mHomeEntity != null && mHomeEntity.recommendList != null && i < mHomeEntity.recommendList.size()) {
            startShopsActivity(mHomeEntity.recommendList.get(i).merchantId);
        }
    }

    private void showShare() {

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.app_name));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("www.qq.com");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("Text文本内容 www.qq.com");
        // imagePath是图片地址，Linked-In以外的平台都支持此参数
        // 如果不用本地图片，千万不要调用这个方法！！！
//        oks.setImagePath("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("www.qq.com");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("www.qq.com");
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                KLog.i("share onComplete...");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                KLog.i("share onError..." + throwable);
            }

            @Override
            public void onCancel(Platform platform, int i) {
                KLog.i("share onCancel...");
            }
        });
        // 启动分享GUI
        oks.show(getActivity());
    }

    @Override
    public void OnBannerClick(int i) {

        if (mHomeEntity == null || mHomeEntity.bannerList == null || mHomeEntity.bannerList.isEmpty()) {
            ToastUtils.showShortToast("数据加载失败，请重新获取");
            return;
        }
        //链接类型（1：商户，2：服务，3：文章，4：外部链接）
        switch (mHomeEntity.bannerList.get(i).linkType) {
            case 1:
                startShopsActivity(mHomeEntity.bannerList.get(i).idValue);
                break;
            case 2:
                JumpActivity(getContext(), ServiceDetailActivity.class, mHomeEntity.bannerList.get(i).idValue);
                break;
            case 3:
                WebViewActivity.startActivity(getActivity(), mHomeEntity.bannerList.get(i).outUrl, "");
                break;
            case 4:
                WebViewActivity.startActivity(getActivity(), mHomeEntity.bannerList.get(i).outUrl, "");
                break;

        }
    }

    private void startShopsActivity(int id) {
        Intent intent = new Intent(getActivity(), ShopsActivity.class);
        intent.putExtra(ISADMIN, false);
        intent.putExtra(MERCHANTID, id + "");
        startActivity(intent);
    }

    private void JumpActivity(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }

    private void requestPermission() {
        AndPermission.with(this)
                .requestCode(200)
                .permission(
                        // 多个权限，以数组的形式传入。
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.CAMERA
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
                JumpActivity(getContext(), ScannerActivity.class);
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

    @Override
    public void onRefresh() {
        getData();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(NotifyEvent event) {
        switch (event) {
            case HAS_NOTIFY:
                KLog.i("收到推送");
                mNotifyImageView.setImageResource(R.mipmap.icon_notify_has);
                break;
            case NO_NOTIFY:
                KLog.i("清除推送");
                mNotifyImageView.setImageResource(R.mipmap.icon_xiaoxi);
                break;
            default:
                KLog.i("NotifyEvent : " + event);
                break;
        }
    }

}
