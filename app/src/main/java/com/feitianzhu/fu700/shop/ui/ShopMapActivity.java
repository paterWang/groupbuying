//package com.feitianzhu.fu700.shop.ui;
//
//import android.content.Intent;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//import butterknife.BindView;
//import com.baidu.mapapi.map.BaiduMap;
//import com.baidu.mapapi.map.BitmapDescriptor;
//import com.baidu.mapapi.map.BitmapDescriptorFactory;
//import com.baidu.mapapi.map.MapStatus;
//import com.baidu.mapapi.map.MapStatusUpdate;
//import com.baidu.mapapi.map.MapStatusUpdateFactory;
//import com.baidu.mapapi.map.MapView;
//import com.baidu.mapapi.map.MarkerOptions;
//import com.baidu.mapapi.map.OverlayOptions;
//import com.baidu.mapapi.model.LatLng;
//import com.bumptech.glide.Glide;
//import com.feitianzhu.fu700.R;
//import com.feitianzhu.fu700.common.Constant;
//import com.feitianzhu.fu700.common.base.LazyBaseActivity;
//import com.socks.library.KLog;
//
//public class ShopMapActivity extends LazyBaseActivity {
//  @BindView(R.id.mapview) MapView mMapView;
//  @BindView(R.id.item_icon) ImageView mItemIcon;
//  @BindView(R.id.txt_address) TextView mTxtAddress;
//  @BindView(R.id.txt_xiangxi_address) TextView mTxtXiangxiAddress;
//  private BaiduMap mBaiduMap;
//  private double la;
//  private double lo;
//  private String mAddress;
//  private String DTLADDR;
//  private String AVATAR;
//
//  @Override protected int setView() {
//    return R.layout.activity_shop_map;
//  }
//
//  @Override protected void initView() {
//    setTitleName("位置");
//    initMapView();
//    Glide.with(this).load(AVATAR).placeholder(R.mipmap.pic_fuwutujiazaishibai).into(mItemIcon);
//    mTxtAddress.setText(mAddress+"");
//    mTxtXiangxiAddress.setText(DTLADDR+"");
//
//  }
//
//  private void initMapView() {
//    Intent mIntent = getIntent();
//    String mX = mIntent.getStringExtra(Constant.LATITUDE);
//    mAddress = mIntent.getStringExtra(Constant.ADDRESS);
//    DTLADDR = mIntent.getStringExtra(Constant.DTLADDR);
//    AVATAR = mIntent.getStringExtra(Constant.AVATAR);
//    la = Double.parseDouble(mX);
//    String mY = mIntent.getStringExtra(Constant.LONGITUDE);
//    lo = Double.parseDouble(mY);
//  }
//
//  @Override protected void initLocal() {
//
//  }
//
//  @Override protected void initData() {
//    mBaiduMap = mMapView.getMap();
//    //普通地图
//    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
//    // 开启定位图层
//    mBaiduMap.setMyLocationEnabled(true);
//    initMarker();
//  }
//
//  private void initMarker() {
//    if (null == Constant.mPoint) {
//      Toast.makeText(this, "我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！", Toast.LENGTH_SHORT)
//          .show();
//    } else {
//      KLog.e("坐标是lo"+lo+"la"+la);
//      //定义Maker坐标点
//      LatLng point = new LatLng(la, lo);
//      //构建Marker图标
//      BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.icon_shop_weizhi);
//      //构建MarkerOption，用于在地图上添加Marker
//      OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
//      //在地图上添加Marker，并显示
//      mBaiduMap.addOverlay(option);
//      MapStatus mMapStatus = new MapStatus.Builder().target(new LatLng(la,lo)).zoom(16).build();
//      MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
//
//      //改变地图状态
//      mBaiduMap.setMapStatus(mMapStatusUpdate);
//    }
//  }
//
//  @Override protected void onDestroy() {
//    super.onDestroy();
//    //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
//    mMapView.onDestroy();
//  }
//}
