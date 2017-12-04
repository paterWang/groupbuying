package com.feitianzhu.fu700.me.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.login.LoginEvent;
import com.feitianzhu.fu700.me.base.BaseTakePhotoActivity;
import com.feitianzhu.fu700.model.MineInfoModel;
import com.feitianzhu.fu700.model.SharedInfoModel;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.feitianzhu.fu700.view.CircleImageView;
import com.feitianzhu.fu700.view.CustomPopWindow;
import com.jph.takephoto.model.TResult;
import com.socks.library.KLog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import okhttp3.Call;
import okhttp3.Response;

import static com.feitianzhu.fu700.common.Constant.ACCESSTOKEN;
import static com.feitianzhu.fu700.common.Constant.Common_HEADER;
import static com.feitianzhu.fu700.common.Constant.POST_MINE_INFO;
import static com.feitianzhu.fu700.common.Constant.POST_UPLOAD_PIC;
import static com.feitianzhu.fu700.common.Constant.USERID;

/**
 * Created by Vya on 2017/9/4 0004.
 */

public class PersonalCenterActivity extends BaseTakePhotoActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.profile_image)
    CircleImageView mCircleImage;
    @BindView(R.id.tv_name)
    TextView mTextViewName;
    @BindView(R.id.circleImageView)
    CircleImageView mSexIcon;
    @BindView(R.id.tv_local)
    TextView mTvLocal;
    @BindView(R.id.tv_position)
    TextView mTvPostion;
    @BindView(R.id.tv_detail_name)
    TextView mDetailName;
    @BindView(R.id.tv_detail_Id)
    TextView mDetailId;
    @BindView(R.id.tv_detail_sex)
    TextView mDetailSex;
    @BindView(R.id.tv_detail_age)
    TextView mDetailAge;
    @BindView(R.id.tv_detail_constellation)
    TextView mDetailConstellation;
    @BindView(R.id.tv_detail_location)
    TextView mDetailLocal;
    @BindView(R.id.tv_detail_hometown)
    TextView mDetailHomeTown;
    @BindView(R.id.tv_job)
    TextView mJobName;
    @BindView(R.id.tv_partner)
    TextView tv_partner;
    @BindView(R.id.tv_payLevel)
    TextView mPayLevel;
    @BindView(R.id.upgrade)
    TextView mUpgrade;

    @BindView(R.id.tv_industry)
    TextView mIndustry;
    @BindView(R.id.tv_companyName)
    TextView mCompanyName;
    @BindView(R.id.tv_phoneNum)
    TextView mPhoneNum;
    @BindView(R.id.tv_personSign)
    TextView mPersonSign;
    @BindView(R.id.tv_InterestsHobbies)
    TextView mInterestsHobbies;
    @BindView(R.id.parentId)
    TextView mParentId;
    @BindView(R.id.parentIcon)
    ImageView mParentIcon;
    @BindView(R.id.parentName)
    TextView mParentName;
    @BindView(R.id.parentPosition)
    TextView mParentPosition;
    @BindView(R.id.parentCompany)
    TextView mParentCompany;
    private MineInfoModel mData;
    private SharedInfoModel mSharedInfo;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.ViewLine)
    View mViewLine;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_center;
    }

    @Override
    protected void initView() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mSwipeRefreshLayout.setOnRefreshListener(this);
        getSharedInfo();
        requestData();
    }


    /**
     * 获取分享的信息
     */
    private void getSharedInfo() {
        OkHttpUtils.post()//
                .url(Common_HEADER + Constant.GET_SHARED_INFO)
                .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                .addParams(USERID, Constant.LOGIN_USERID)
                .build()
                .execute(new Callback<SharedInfoModel>() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("wangyan", "onError---->" + e.getMessage());
                    }

                    @Override
                    public void onResponse(SharedInfoModel response, int id) {
                        mSharedInfo = response;
                    }

                });
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(LoginEvent event) {
        switch (event) {
            case TAKEPHOTO:
            case EDITORINFO:
                requestData();
                break;
        }
    }


    private void requestData() {
        OkHttpUtils.post()//
                .url(Common_HEADER + POST_MINE_INFO)
                .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                .addParams(USERID, Constant.LOGIN_USERID)
                .build()
                .execute(new Callback<MineInfoModel>() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("wangyan", "onError---->" + e.getMessage());
                        ToastUtils.showShortToast(e.getMessage());
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onResponse(MineInfoModel response, int id) {
                        mData = response;
                        setShowData(response);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    private void setShowData(MineInfoModel response) {
        String sex = "";
        String headImg = "";
        String parentImg = "";
        if(mCircleImage !=null ){
            if(TextUtils.isEmpty(response.getHeadImg())){
                headImg = "";
            }else{
                headImg = response.getHeadImg();
            }
            Glide.with(this).load(headImg).dontAnimate().placeholder(R.drawable.pic_fuwutujiazaishibai)
                    .into(mCircleImage);
        }
        if(mParentIcon !=null){
            if(TextUtils.isEmpty(response.getParentHeadImg())){
                parentImg = "";
            }else{
                parentImg = response.getParentHeadImg();
            }
            Glide.with(this).load(parentImg).dontAnimate().placeholder(R.drawable.pic_fuwutujiazaishibai)
                    .into(mParentIcon);
        }

        if (response.getSex() == 0) { //女
            mSexIcon.setImageResource(R.drawable.icon_nv);
            sex = "女";
        } else {
            mSexIcon.setImageResource(R.drawable.icon_nan);
            sex = "男";
        }

        mTextViewName.setText(response.getNickName() == null ? "" : response.getNickName().toString());
        mTvLocal.setText(response.getCompany() == null ? "" : response.getCompany().toString());
        mTvPostion.setText(response.getJob() == null ? "" : response.getJob().toString());
        mDetailName.setText(response.getNickName() == null ? "" : response.getNickName().toString());

        mDetailId.setText(response.getUserId() == 0 ? "0" : response.getUserId() + "");
        mDetailSex.setText(sex == null ? "" : sex);
        mDetailAge.setText(response.getAge() == 0 ? "0" : response.getAge() + "");
        mDetailConstellation.setText(response.getConstellation() == null ? "" : response.getConstellation().toString());
        mDetailLocal.setText(response.getLiveAdress() == null ? "" : response.getLiveAdress().toString());

        mDetailHomeTown.setText(response.getHomeAdress() == null ? "" : response.getHomeAdress().toString());
        mPayLevel.setText(response.getGradeName() == null ? "" : response.getGradeName().toString());
        tv_partner.setText(response.getAgentName() == null ? "" : response.getAgentName().toString());

        mUpgrade.setText(response.getTotalPoints() == 0 ? "0" : response.getTotalPoints() + "");

        mJobName.setText(response.getJob() == null ? "" : response.getJob().toString());
        mIndustry.setText(response.getIndustryLabel() == null ? "" : response.getIndustryLabel().size() <= 0 ? "" : response.getIndustryLabel().get(0).toString());
        mCompanyName.setText(response.getCompany() == null ? "" : response.getCompany().toString());
        mPhoneNum.setText(response.getPhone() == null ? "" : response.getPhone().toString());
        mPersonSign.setText(response.getPersonSign() == null ? "" : response.getPersonSign().toString());

        mInterestsHobbies.setText(response.getInterest() == null ? "" : response.getInterest().toString());
        mParentId.setText(response.getParentId() == 0 ? "0" : response.getParentId() + "");

        mParentName.setText(response.getParentNickName() == null ? "" : response.getParentNickName().toString());
        mParentCompany.setText(response.getParentCompany() == null ? "" : response.getParentCompany().toString());
        mParentPosition.setText(response.getParentJob() == null ? "" : response.getParentJob().toString());
        if(TextUtils.isEmpty(response.getParentCompany())){
            mViewLine.setVisibility(View.INVISIBLE);
        }

    }

    @OnClick({R.id.iv_right_menu, R.id.iv_backIcon, R.id.profile_image})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_right_menu:
                showPopMenu(v);
                break;
            case R.id.iv_backIcon:
                finish();
                break;
            case R.id.profile_image:
                showDialog();
                break;
        }
    }


    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeSuccess(TResult result) {
        String compressPath = result.getImage().getCompressPath();
        Glide.with(mContext).load(compressPath).into(mCircleImage);
        uploadPic(compressPath);
    }

    private void uploadPic(String compressPath) {
        OkHttpUtils.post()//
                .url(Common_HEADER + POST_UPLOAD_PIC)
                .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                .addParams(USERID, Constant.LOGIN_USERID)
                .addFile("avatar", "touxiang.png", new File(compressPath))
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(String mData, Response response, int id) throws Exception {
                        return mData;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if ("数据为空".equals(e.getMessage())) {
                            ToastUtils.showShortToast("上传成功!");
                            EventBus.getDefault().post(LoginEvent.TAKEPHOTO);
                        } else {
                            ToastUtils.showShortToast(e.getMessage());
                        }

                    }

                    @Override
                    public void onResponse(Object response, int id) {
                        Log.e("wangyan", "response====" + response);

                    }
                });
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    protected void showPopMenu(View v) {
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_menu_personview, null);
        //处理popWindow 显示内容
        handleLogic(contentView);
        //创建并显示popWindow
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .create();
        int width = mCustomPopWindow.getWidth();
        mCustomPopWindow.showAsDropDown(v, -width + v.getWidth(), 0);


    }

    private void handleLogic(View contentView) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCustomPopWindow != null) {
                    mCustomPopWindow.dissmiss();
                }

                switch (v.getId()) {
                    case R.id.menu1:
                        Intent intent = new Intent(PersonalCenterActivity.this, EditCardActivity.class);
                        if (mData != null) {
                            intent.putExtra("parentId", mData.getParentId() == 0 ? "" : mData.getParentId() + "");
                            intent.putExtra("phoneNum", mData.getPhone() == null ? "" : mData.getPhone() + "");
                        }
                        startActivity(intent);
                        break;
                    case R.id.menu2:
                        showShare();
                        break;
                }

            }
        };
        contentView.findViewById(R.id.menu1).setOnClickListener(listener);
        contentView.findViewById(R.id.menu2).setOnClickListener(listener);
    }


    private void showShare() {

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        if (mSharedInfo == null) {
            ToastUtils.showShortToast("分享的资料信息未完善，无法分享");
            return;
        }
        if (TextUtils.isEmpty(mSharedInfo.getNickName()) || TextUtils.isEmpty(mSharedInfo.getLink()) || TextUtils.isEmpty(mSharedInfo.getCompany())) {
            ToastUtils.showShortToast("分享的资料信息未完善，无法分享");
            return;
        }
        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(mSharedInfo.getNickName());
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(mSharedInfo.getLink());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(mSharedInfo.getCompany());
        // imagePath是图片地址，Linked-In以外的平台都支持此参数
        // 如果不用本地图片，千万不要调用这个方法！！！
//        oks.setImagePath("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        oks.setImageUrl(mSharedInfo.getHeadImg());
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(mSharedInfo.getLink());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(mSharedInfo.getJob());
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(mSharedInfo.getLink());
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
        oks.show(PersonalCenterActivity.this);
    }


    private void jumpActivity(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        startActivity(intent);
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onRefresh() {
        requestData();
    }
}
