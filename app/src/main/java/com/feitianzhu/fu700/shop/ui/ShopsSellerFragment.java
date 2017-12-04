package com.feitianzhu.fu700.shop.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.afollestad.materialdialogs.MaterialDialog;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.impl.DataCallBack;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.model.ShopsInfo;
import com.feitianzhu.fu700.shop.ShopDao;
import com.feitianzhu.fu700.utils.MapUtils;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.socks.library.KLog;

import static com.feitianzhu.fu700.common.Constant.FailCode;

/**
 * description: 商鋪信息展示頁面
 * autour: dicallc
 */
public class ShopsSellerFragment extends Fragment {
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  @BindView(R.id.txt_address) TextView mTxtAddress;
  @BindView(R.id.txt_shop_type) TextView mTxtShopType;
  @BindView(R.id.txt_shops_des) TextView mTxtShopsDes;
  Unbinder unbinder;

  private String merchantid;
  private String mParam2;
  private DataCallBack callBack;
  private ShopsInfo mShopsInfo;
  private LinearLayout gaode;
  private LinearLayout baidu;
  private MaterialDialog mDialog;

  public String getCollectId() {
    return mCollectId;
  }

  public void setmCollectId(String mCollectId) {
    this.mCollectId = mCollectId;
  }

  private String mCollectId;

  public int getMerchantId() {
    return mMerchantId;
  }

  private int mMerchantId;

  public ShopsSellerFragment() {
  }

  public void setDataCallBack(DataCallBack mDataCallBack) {
    callBack = mDataCallBack;
  }

  public static ShopsSellerFragment newInstance(String param1, String param2) {
    ShopsSellerFragment fragment = new ShopsSellerFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      merchantid = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_shops_seller, container, false);
    unbinder = ButterKnife.bind(this, view);

    return view;
  }

  //@Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
  //  super.onViewCreated(view, savedInstanceState);
  //
  //}

  @Override public void onStart() {
    super.onStart();
    initData();
  }

  private void initData() {
    ShopDao.loadShopsInfo(merchantid, new onConnectionFinishLinstener() {
      @Override public void onSuccess(int code, Object result) {
        if (null == result) {
          onFail(FailCode, "商铺信息获取失败");
          callBack.CallBack(null);
          return;
        }
        mShopsInfo = (ShopsInfo) result;
        try {
          String[] mSplit = mShopsInfo.adImgs.split(",");
          String str = mSplit[0];
          mShopsInfo.adImgs=str;
          mTxtShopsDes.setText(mShopsInfo.introduce);
          mTxtShopType.setText(mShopsInfo.clsName + "");
          mTxtAddress.setText(Html.fromHtml("<u>" + mShopsInfo.dtlAddr+"" + "</u>"));

        }catch (Exception mE){
          mShopsInfo.adImgs="";
          mTxtAddress.setText("");
        }

        mCollectId = mShopsInfo.collectId;
        KLog.e("mCollectId" + mCollectId);
        callBack.CallBack(mShopsInfo);

        mMerchantId = mShopsInfo.merchantId;

      }

      @Override public void onFail(int code, String result) {
        ToastUtils.showShortToast(result);
      }
    });
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }

  @OnClick(R.id.txt_address) public void onViewClicked() {
    View mView = View.inflate(getActivity(), R.layout.dialog_map, null);
    initMapView(mView);
    mDialog = new MaterialDialog.Builder(getActivity()).title("导航").customView(mView, false).show();
    //Intent mIntent = new Intent(getActivity(), ShopMapActivity.class);
    //mIntent.putExtra(Constant.LATITUDE, mShopsInfo.latitude);
    //mIntent.putExtra(Constant.LONGITUDE, mShopsInfo.longitude);
    //mIntent.putExtra(Constant.ADDRESS, mShopsInfo.provinceName + mShopsInfo.cityName);
    //mIntent.putExtra(Constant.DTLADDR, mShopsInfo.dtlAddr);
    //mIntent.putExtra(Constant.AVATAR, mShopsInfo.merchantHeadImg);
    //startActivity(mIntent);
  }


  private void initMapView(View view) {
    gaode = (LinearLayout) view.findViewById(R.id.gaode);
    baidu = (LinearLayout) view.findViewById(R.id.baidu);
    gaode.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent;
        if (MapUtils.isAvilible(getActivity(), "com.autonavi.minimap")) {
          try {
            intent = Intent.getIntent("androidamap://navi?sourceApplication="
                + mShopsInfo.merchantName
                + "&poiname="
                + mShopsInfo.provinceName
                + mShopsInfo.cityName
                + mShopsInfo.dtlAddr
                + "&lat="
                + mShopsInfo.latitude
                + "&lon="
                + mShopsInfo.longitude
                + "&dev=0");
            startActivity(intent);
          } catch (Exception e) {
            ToastUtils.showShortToast("您尚未安装高德地图");
//            e.printStackTrace();
          }
        } else {
          try {
            ToastUtils.showShortToast("您尚未安装高德地图");
            Uri uri = Uri.parse("market://details?id=com.autonavi.minimap");
            intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
          }catch (Exception e){

          }

        }
        mDialog.dismiss();
      }
    });
    baidu.setOnClickListener(new View.OnClickListener() {
      public Intent intent;

      @Override public void onClick(View v) {
        if (MapUtils.isAvilible(getActivity(), "com.baidu.BaiduMap")) {//传入指定应用包名

          try {
            //                          intent = Intent.getIntent("intent://map/direction?origin=latlng:34.264642646862,108.95108518068|name:我家&destination=大雁塔&mode=driving®ion=西安&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
            intent = Intent.getIntent("intent://map/direction?"
                +
                //"origin=latlng:"+"34.264642646862,108.95108518068&" +   //起点  此处不传值默认选择当前位置
                "destination=latlng:"
                + mShopsInfo.latitude
                + ","
                + mShopsInfo.longitude
                + "|name:我的目的地"
                +
                //终点
                "&mode=driving&"
                +
                //导航路线方式
                "region="
                + mShopsInfo.cityName
                +
                //
                "&src="
                + mShopsInfo.merchantName
                + "#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
            getActivity().startActivity(intent); //启动调用
          } catch (Exception e) {
            ToastUtils.showShortToast("您尚未安装百度地图");
            Log.e("intent", e.getMessage());
          }
        } else {//未安装
          //market为路径，id为包名
          //显示手机上所有的market商店
          try {
            ToastUtils.showShortToast("您尚未安装百度地图");
            Uri uri = Uri.parse("market://details?id=com.baidu.BaiduMap");
            intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
          }catch (Exception e){

          }

        }
        mDialog.dismiss();
      }
    });
  }
}
