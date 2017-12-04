package com.feitianzhu.fu700.me.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.SelectPhotoActivity;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.model.Province;
import com.feitianzhu.fu700.model.ShopsInfo;
import com.feitianzhu.fu700.model.ShopsType;
import com.feitianzhu.fu700.shop.ShopDao;
import com.feitianzhu.fu700.shop.ShopHelp;
import com.feitianzhu.fu700.shop.ui.dialog.ProvinceCallBack;
import com.feitianzhu.fu700.shop.ui.dialog.ProvincehAreaDialog;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.jph.takephoto.model.TResult;
import com.socks.library.KLog;
import java.util.ArrayList;
import java.util.List;

/**
 * description: 编辑商铺
 * autour: dicallc
 */
public class ShopsEditActivity extends SelectPhotoActivity {

  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.txt_shop_name) EditText mTxtShopName;
  @BindView(R.id.txt_select_address) TextView mTxtSelectAddress;
  @BindView(R.id.ly_huji) LinearLayout mLyHuji;
  @BindView(R.id.txt_address) EditText mTxtAddress;
  @BindView(R.id.txt_phone) EditText mTxtPhone;
  @BindView(R.id.txt_shop_types) TextView mTxtShopTypes;
  @BindView(R.id.ly_shop_type) LinearLayout mLyShopType;
  @BindView(R.id.btn_icon) ImageView mBtnIcon;
  @BindView(R.id.btn_cover) ImageView mBtnCover;
  @BindView(R.id.edit_intro) EditText mEditIntro;
  @BindView(R.id.btn_create) TextView mBtnCreate;
  private Province mOnSelectProvince;
  private List<ShopsType> mList;
  /**
   * 商铺类型
   */
  ArrayList<String> mShopsTypes = new ArrayList<>();
  private int photo_type;
  private String photo_file_one;
  private String photo_file_two;
  private ShopsInfo mShopsInfo;
  private String thPass;


  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_shops_edit);
    ButterKnife.bind(this);
    mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
    initData();
  }

  @Override protected void onWheelSelect(int num, ArrayList<String> mList) {
    mTxtShopTypes.setText(mShopsTypes.get(num - 1));
  }

  private void initData() {
    loadShopsType(false);
    ShopDao.loadShopsInfo("",new onConnectionFinishLinstener() {
      @Override public void onSuccess(int code, Object result) {
        mShopsInfo = (ShopsInfo) result;
        mTxtShopName.setText(mShopsInfo.merchantName + "");
        mTxtSelectAddress.setText(mShopsInfo.provinceName + "" + mShopsInfo.cityName);
        mTxtAddress.setText(mShopsInfo.dtlAddr + "");
        mTxtPhone.setText(mShopsInfo.servicePhone + "");
        mTxtShopTypes.setText(mShopsInfo.clsName + "");
        //photo_file_one= mShopsInfo.adImgs+"";
        //photo_file_two= mShopsInfo.merchantHeadImg+"";
        Glide.with(mContext)
            .load(mShopsInfo.merchantHeadImg)
            .placeholder(R.mipmap.pic_fuwutujiazaishibai)
            .into(mBtnIcon);
        Glide.with(mContext)
            .load(mShopsInfo.adImgs.replace(",",""))
            .placeholder(R.mipmap.pic_fuwutujiazaishibai)
            .into(mBtnCover);
        mEditIntro.setText(mShopsInfo.introduce + "");
      }

      @Override public void onFail(int code, String result) {
        ToastUtils.showShortToast(result);
      }
    });
  }

  @OnClick({ R.id.ly_huji, R.id.ly_shop_type, R.id.btn_create, R.id.btn_icon, R.id.btn_cover })
  public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.ly_huji:
        ProvincehAreaDialog branchDialog = ProvincehAreaDialog.newInstance(this);
        branchDialog.setAddress("北京市", "北京市");
        branchDialog.setSelectOnListener(new ProvinceCallBack() {
          @Override public void onWhellFinish(Province province, Province.CityListBean city,
                                              final Province.AreaListBean mAreaListBean) {
            final String mProvince_name = province.name;
            final String mCity_name = city.name;
            final String mProvince_id = province.id;
            final String mCity_id = city.id;
            if(mProvince_name.contains("TH")){
              ShopHelp.showVeringPassword2(ShopsEditActivity.this, new onConnectionFinishLinstener() {
                @Override
                public void onSuccess(int code, Object result) {
                  mOnSelectProvince = new Province(mProvince_name, mCity_name, mCity_id, mProvince_id, mAreaListBean.name, mAreaListBean.id);
                  KLog.e("mProvince" + mProvince_name + "mCity_name" + mCity_name);
                  mTxtSelectAddress.setText(mProvince_name + " " + mCity_name + " " + mAreaListBean.name);
                  thPass = result.toString();
                }

                @Override
                public void onFail(int code, String result) {

                  ToastUtils.showShortToast(result);
                }
              });
            }else {
              mOnSelectProvince = new Province(mProvince_name, mCity_name, mCity_id, mProvince_id, mAreaListBean.name, mAreaListBean.id);
              KLog.e("mProvince" + mProvince_name + "mCity_name" + mCity_name);
              mTxtSelectAddress.setText(mProvince_name + " " + mCity_name + " " + mAreaListBean.name);
              thPass = "";
            }



          }
        });


        branchDialog.show(getSupportFragmentManager());
        break;
      case R.id.ly_shop_type:
        //如果获取失败就在这里重新获取
        if (mShopsTypes.size() == 0) {
          loadShopsType(true);
        } else {
          showTypeDialog(mShopsTypes);
        }
        break;
      case R.id.btn_icon:
        photo_type = 0;
        showDialog();
        break;
      case R.id.btn_cover:
        photo_type = 1;
        showDialog();
        break;
      case R.id.btn_create:
        final String shopname = mTxtShopName.getText().toString().trim();
        if (TextUtils.isEmpty(shopname)) {
          ToastUtils.showShortToast("还没有填写商铺名称");
          return;
        }
        final String shopaddress = mTxtAddress.getText().toString().trim();
        if (TextUtils.isEmpty(shopname)) {
          ToastUtils.showShortToast("还没有填写商铺详细地址");
          return;
        }
        final String shopPhone = mTxtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(shopPhone)) {
          ToastUtils.showShortToast("还没有填写商铺电话");
          return;
        }
        final String shopIntro = mEditIntro.getText().toString().trim();
        if (TextUtils.isEmpty(shopIntro)) {
          ToastUtils.showShortToast("还没有填写介绍");
          return;
        }
        if ("请选择地区".equals(mTxtSelectAddress.getText().toString().trim())) {
          ToastUtils.showShortToast("还没有选择商铺地区");
          return;
        }
        if ("请输入类型".equals(mTxtShopTypes.getText().toString().trim())) {
          ToastUtils.showShortToast("还没有选择商铺类型");
          return;
        }
       if(null==mOnSelectProvince){
         ToastUtils.showShortToast("还没有选择地区");
         return;
       }

        ShopDao.updateShopsInfo(new onConnectionFinishLinstener() {
                                  @Override public void onSuccess(int code, Object result) {
                                    finish();
                                  }

                                  @Override public void onFail(int code, String result) {
                                    ToastUtils.showShortToast(result);
                                  }
                                }, photo_file_one, photo_file_two, shopname, shopaddress, shopPhone, mOnSelectProvince,
            selectIndex, shopIntro,mList,mShopsInfo.merchantId,thPass);

        break;
    }
  }

  private void loadShopsType(final boolean mB) {
    if (mB) showloadDialog("");

    ShopDao.loadShopsType(new onConnectionFinishLinstener() {
      @Override public void onSuccess(int code, Object result) {
        mList = (List<ShopsType>) result;
        for (ShopsType item : mList) {
          mShopsTypes.add(item.clsName);
        }
        goneloadDialog();
        if (mB) showTypeDialog(mShopsTypes);
        KLog.e("成功");
      }

      @Override public void onFail(int code, String result) {
        KLog.e("失败");
      }
    });
  }

  @Override public void takeSuccess(TResult result) {
    super.takeSuccess(result);
    switch (photo_type) {
      case 0:
        photo_file_one = result.getImage().getCompressPath();
        Glide.with(mContext).load(photo_file_one).into(mBtnIcon);
        break;
      case 1:
        photo_file_two = result.getImage().getCompressPath();
        Glide.with(mContext).load(photo_file_two).into(mBtnCover);
        break;
    }
    KLog.i("takeSuccess：" + result.getImage().getCompressPath() + "photo_type" + photo_type);
  }
}
