package com.feitianzhu.fu700.me.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.fragment.PersonInfoFragment;
import com.feitianzhu.fu700.model.MineInfoModel;
import com.feitianzhu.fu700.shop.adapter.MyPagerAdapter;
import com.feitianzhu.fu700.shop.ui.ShopReportActivity;
import com.feitianzhu.fu700.shop.ui.ShopsActivity;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.feitianzhu.fu700.view.CircleImageView;
import com.feitianzhu.fu700.view.CustomPopWindow;
import com.flyco.tablayout.SlidingTabLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static com.feitianzhu.fu700.common.Constant.ACCESSTOKEN;
import static com.feitianzhu.fu700.common.Constant.Common_HEADER;
import static com.feitianzhu.fu700.common.Constant.MERCHANTID;
import static com.feitianzhu.fu700.common.Constant.POST_MINE_INFO;
import static com.feitianzhu.fu700.common.Constant.USERID;

/**
 * Created by Vya on 2017/9/1 0001.
 */

public class ShopDetailActivity extends BaseActivity {
    @BindView(R.id.tl_2)
    SlidingTabLayout mTl_2;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.profile_image)
    CircleImageView mCircleImage;
    @BindView(R.id.circleImageView)
    CircleImageView mParentIcon;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_local)
    TextView mTvLocal;
    @BindView(R.id.tv_position)
    TextView mTvPosition;


    private String[] mTitles = { "资料"};
    private List<Fragment> mFragments;
    private CustomPopWindow mCustomPopWindow;

    private  PersonInfoFragment fragment;
    private MyPagerAdapter mAdapter;
    private MineInfoModel mTempData;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_detail;
    }

    @Override
    protected void initTitle() {
//        defaultNavigationBar = new DefaultNavigationBar
//                .Builder(ShopDetailActivity.this, (ViewGroup)findViewById(R.id.Rl_titleContainer))
//               // .setStatusHeight()
//                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
//                .showRightPic(R.drawable.icon_gengduo, AbsNavigationbar.PIC_THREE,new View.OnClickListener(){
//
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(ShopDetailActivity.this,"AAAAAAAAAA",Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .builder();
//        defaultNavigationBar.setImmersion(R.color.transparent);
    }

    @Override
    protected void initView() {
        String otherUserId = getIntent().getStringExtra("otherId");
       // otherUserId = "13";
        mFragments = new ArrayList<>();
        fragment = new PersonInfoFragment();
        requestData(otherUserId);

       // mFragments.add(new testFragment());
        //mTl_2.setDividerWidth(0);
    }

    @Override
    protected void initData() {

    }
    @OnClick({R.id.right_menu,R.id.iv_backIcon,R.id.bt_shop})
    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.right_menu:
                showPopMenu(v);
                break;
            case R.id.iv_backIcon:
                finish();
                break;
            case R.id.bt_shop:
                if(mTempData != null && mTempData.getUserId()>0){
                    Intent intent = new Intent(mContext, ShopsActivity.class);
                    intent.putExtra(MERCHANTID,mTempData.getUserId()+"");
                    startActivity(intent);
                }

                break;
        }

    }

    private void requestData(String otherUserId) {
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
                    }

                    @Override
                    public void onResponse(MineInfoModel response, int id) {
                        mTempData = response;
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("shopDetailBean",response);//这里的values就是我们要传的值
                        fragment.setArguments(bundle);
                        mFragments.add(fragment);
                        mAdapter =  new MyPagerAdapter(getSupportFragmentManager(),mTitles,mFragments);
                        mViewPager.setAdapter(mAdapter);
                        mTl_2.setViewPager(mViewPager,mTitles);
                            setShowData(response);
                    }
                });
    }

    private void setShowData(MineInfoModel response) {
        if(mCircleImage != null){
            Glide.with(this).load(response.getHeadImg()).dontAnimate().placeholder(R.drawable.pic_fuwutujiazaishibai)
                    .into(mCircleImage);
        }
        if(mParentIcon != null){
            if(response.getSex() == 0){ //女
                mParentIcon.setImageResource(R.drawable.icon_nv);
            }else{
                mParentIcon.setImageResource(R.drawable.icon_nan);
            }
        }

        mTvName.setText(response.getNickName()==null?"":response.getNickName().toString());
        mTvLocal.setText(response.getCompany()==null?"":response.getCompany().toString());
        mTvPosition.setText(response.getJob()==null?"":response.getJob().toString());
    }

    protected void showPopMenu(View v){
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_menu_shopdetail,null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        //创建并显示popWindow
        mCustomPopWindow= new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .create();
        int width = mCustomPopWindow.getWidth();
        mCustomPopWindow.showAsDropDown(v, -width+v.getWidth(),0);


    }
    private void handleLogic(View contentView) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCustomPopWindow!=null){
                    mCustomPopWindow.dissmiss();
                }

                switch (v.getId()){
                    case R.id.menu1:
                        ToastUtils.showShortToast("此功能待开发");
                       // onMenuOneClick();
                        break;
                    case R.id.menu2:
                      //  onMenuTwoClick();
                        Intent mIntent=new Intent(ShopDetailActivity.this,ShopReportActivity.class);
                        mIntent.putExtra(MERCHANTID,mTempData.getUserId()+"");
                        mIntent.putExtra("type","1");
                        startActivity(mIntent);
                        break;
                }

            }
        };
        contentView.findViewById(R.id.menu1).setOnClickListener(listener);
        contentView.findViewById(R.id.menu2).setOnClickListener(listener);
    }




}
