package com.feitianzhu.fu700.me.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import com.feitianzhu.fu700.common.impl.onNetFinishLinstenerT;
import com.feitianzhu.fu700.model.Province;
import com.feitianzhu.fu700.model.ShopVeriModel;
import com.feitianzhu.fu700.model.UserAuth;
import com.feitianzhu.fu700.model.UserVeriModel;
import com.feitianzhu.fu700.shop.ShopDao;
import com.feitianzhu.fu700.shop.ShopHelp;
import com.feitianzhu.fu700.shop.ui.dialog.ProvinceCallBack;
import com.feitianzhu.fu700.shop.ui.dialog.ProvincehDialog;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.jph.takephoto.model.TResult;
import com.socks.library.KLog;
import java.util.ArrayList;

/**
 * description: 认证界面
 * autour: dicallc
 */
public class MyVerificationActivity extends SelectPhotoActivity {

  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.txt_one) TextView mTxtOne;
  @BindView(R.id.take_photo_one) LinearLayout mTakePhotoOne;
  @BindView(R.id.take_photo_two) LinearLayout mTakePhotoTwo;
  @BindView(R.id.take_photo_three) LinearLayout mTakePhotoThree;
  @BindView(R.id.edit_name) EditText mEditName;
  @BindView(R.id.ly_type) LinearLayout mLyType;
  @BindView(R.id.edt_id_num) EditText mEdtIdNum;
  @BindView(R.id.ly_huji) LinearLayout mLyHuji;
  @BindView(R.id.btn_submit) TextView mBtnSubmit;
  @BindView(R.id.img_veri_one) ImageView mImgVeriOne;
  @BindView(R.id.img_veri_two) ImageView mImgVeriTwo;
  @BindView(R.id.img_veri_three) ImageView mImgVeriThree;
  @BindView(R.id.ly_shiming) LinearLayout mLyShiming;
  @BindView(R.id.ly_wait) LinearLayout mLyWait;
  @BindView(R.id.txt_person_name) TextView mTxtPersonName;
  @BindView(R.id.txt_person_idnum) TextView mTxtPersonIdnum;
  @BindView(R.id.btn_to_shops) TextView mBtnToShops;
  @BindView(R.id.ly_shiming_success) LinearLayout mLyShimingSuccess;
  @BindView(R.id.img_update_yinye_card) ImageView mImgUpdateYinyeCard;
  @BindView(R.id.edit_yinye_card) EditText mEditYinyeCard;
  @BindView(R.id.edt_register_num) EditText mEdtRegisterNum;
  @BindView(R.id.edt_faren) EditText mEdtFaren;
  @BindView(R.id.ly_shops_type) LinearLayout mLyShopsType;
  @BindView(R.id.btn_shops_submit) TextView mBtnShopsSubmit;
  @BindView(R.id.ly_veri_shop) LinearLayout mLyVeriShop;
  @BindView(R.id.line_two) ImageView mLineTwo;
  @BindView(R.id.img_veri_shop) TextView mImgVeriShop;
  @BindView(R.id.line_three) ImageView mLineThree;
  @BindView(R.id.txt_faren_name) TextView mTxtFarenName;
  @BindView(R.id.txt_xingzhi_name) TextView mTxtXingzhiName;
  @BindView(R.id.txt_yinye_name) TextView mTxtYinyeName;
  @BindView(R.id.txt_zhuce_num) TextView mTxtZhuceNum;
  @BindView(R.id.ly_shops_success) LinearLayout mLyShopsSuccess;
  @BindView(R.id.txt_shops_type) TextView txtShopsType;
  @BindView(R.id.txt_idcard_type) TextView mTxtIdcardType;
  @BindView(R.id.txt_address_type) TextView mTxtAddressType;
  @BindView(R.id.ly_user_veri) TextView mLyUserVeri;
  private String mPath;
  /**
   * 0 代表对着招 1代表正面照 2代表背面照 3是商户照片
   */
  private int photo_type = 0;
  private String photo_file_one = "";
  private String photo_file_two = "";
  private String photo_file_three = "";
  private String photo_file_four = "";
  private Province mOnSelectProvince;
  private boolean mVeri_shops;

  @Override public void takeSuccess(TResult result) {
    super.takeSuccess(result);
    switch (photo_type) {
      case 0:
        photo_file_one = result.getImage().getCompressPath();
        Glide.with(MyVerificationActivity.this).load(photo_file_one).into(mImgVeriOne);
        break;
      case 1:
        photo_file_two = result.getImage().getCompressPath();
        Glide.with(MyVerificationActivity.this).load(photo_file_two).into(mImgVeriTwo);
        break;
      case 2:
        photo_file_three = result.getImage().getCompressPath();
        Glide.with(MyVerificationActivity.this).load(photo_file_three).into(mImgVeriThree);
        break;
      case 3:
        photo_file_four = result.getImage().getCompressPath();
        Glide.with(MyVerificationActivity.this).load(photo_file_four).into(mImgUpdateYinyeCard);
        break;
    }
    KLog.i("takeSuccess：" + result.getImage().getCompressPath() + "photo_type" + photo_type);
  }

  @Override protected void onWheelSelect(int num, ArrayList<String> mList) {
    try {
      if (mVeri_shops) {
        txtShopsType.setText(mList.get(num - 1));
      } else {
        mTxtIdcardType.setText(mList.get(num - 1));
      }
    }catch (Exception mE){

    }

  }

  @Override public void onCreate(Bundle savedInstanceState) {
    getTakePhoto().onCreate(savedInstanceState);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_verification);
    ButterKnife.bind(this);
    mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        finish();
      }
    });
    initData();
  }

  private void initData() {
    isCorp=false;
    showloadDialog("获取验证信息");
    ShopDao.loadUserAuth(new onNetFinishLinstenerT<UserAuth>() {
      @Override public void onSuccess(int code, UserAuth result) {
        if (null != result) {
          //商铺验证没验证走下面
          if (0 == result.isMerchantAuth) {
            mImgVeriShop.setEnabled(false);
            mLyUserVeri.setEnabled(false);
            IFNoVeriShop(result);
          } else if (-1 == result.isMerchantAuth) {
            goneloadDialog();
            mImgVeriShop.setEnabled(false);
            mLyUserVeri.setEnabled(false);
            ToastUtils.showLongToast("商铺验证被拒绝,原因是:" + result.merchantAuthRefuseReason + ",请重新认证");
            showShopsVeriView();
          } else if (2 == result.isMerchantAuth) {
            //商铺验证中
            mImgVeriShop.setEnabled(false);
            mLyUserVeri.setEnabled(false);
            showShopsVeriLoadingView();
            goneloadDialog();
          } else {
            //商铺验证通过
            showVeriShopsCompleteView();
            veriShop();
          }
        } else {
          onFail(11, "服务器错误");
          goneloadDialog();
        }
      }

      @Override public void onFail(int code, String result) {
        goneloadDialog();
      }
    });
  }

  /**
   * 没有验证过商户
   */
  private void IFNoVeriShop(UserAuth result) {
    if (0 == result.isRnAuth) {
      showVeriUserView();
    } else if (-1 == result.isRnAuth) {
      ToastUtils.showLongToast("未通过实名认证，原因是: " + result.rnAuthRefuseReason + "请重新认证");
      showVeriUserView();
    } else if (result.isRnAuth == 2) {
      //审核中
      ShowVeriUserLoadingView();
      goneloadDialog();
    } else if (result.isRnAuth == 1) {
      ShowVeriUserCompleteView();
      veriUser();
    }
  }

  private void showVeriUserView() {
    mLyShiming.setVisibility(View.VISIBLE);
    mLyVeriShop.setVisibility(View.GONE);
    goneloadDialog();
  }

  /**
   * 显示验证用户成功
   */
  private void ShowVeriUserCompleteView() {
    mLyShopsSuccess.setVisibility(View.GONE);
    mLineTwo.setVisibility(View.VISIBLE);
    mLineTwo.setImageResource(R.mipmap.xuxianlanse);
    mLineThree.setImageResource(R.mipmap.xuxianhuise);
    mLyShiming.setVisibility(View.GONE);
    mLyWait.setVisibility(View.GONE);
    mLyShimingSuccess.setVisibility(View.VISIBLE);
  }

  /**
   * 正在验证用户
   */
  private void ShowVeriUserLoadingView() {
    mLineTwo.setVisibility(View.VISIBLE);
    mLineTwo.setImageResource(R.mipmap.xuxianlanse);
    mLyShiming.setVisibility(View.GONE);
    mLyWait.setVisibility(View.VISIBLE);
  }

  /**
   * 显示商户验证
   */
  private void showShopsVeriView() {
    mVeri_shops = true;
    mLineTwo.setVisibility(View.VISIBLE);
    mLineTwo.setImageResource(R.mipmap.xuxianlanse);
    mLyShiming.setVisibility(View.GONE);
    mLyWait.setVisibility(View.GONE);
    mLyVeriShop.setVisibility(View.VISIBLE);
  }

  /**
   * 正在商户验证
   */
  private void showShopsVeriLoadingView() {
    mLineTwo.setVisibility(View.VISIBLE);
    mLineTwo.setImageResource(R.mipmap.xuxianlanse);
    mLyShiming.setVisibility(View.GONE);
    mLyWait.setVisibility(View.VISIBLE);
    mLyVeriShop.setVisibility(View.GONE);
  }

  /**
   * 商户验证成功
   */
  private void showVeriShopsCompleteView() {
    mImgVeriShop.setSelected(true);
    mLineTwo.setVisibility(View.VISIBLE);
    mLineThree.setVisibility(View.VISIBLE);
    mLineTwo.setImageResource(R.mipmap.xuxianlanse);
    mLineThree.setImageResource(R.mipmap.xuxianlanse);
    mLyWait.setVisibility(View.GONE);
    mLyVeriShop.setVisibility(View.GONE);
    mLyShimingSuccess.setVisibility(View.GONE);
    mLyShopsSuccess.setVisibility(View.VISIBLE);
  }

  private void veriShop() {
    ShopDao.loadShopsVeriInfo(new onConnectionFinishLinstener() {
      @Override public void onSuccess(int code, Object result) {
        ShopVeriModel mShopVeriModel = (ShopVeriModel) result;
        mTxtFarenName.setText("法人代表：" + mShopVeriModel.legalPerson);
        String xingzhi = "";
        switch (mShopVeriModel.busiNature) {
          case "1":
            xingzhi = "个体工商户";
            break;
          case "2":
            xingzhi = "民营企业";
            break;
          case "3":
            xingzhi = "国营企业";
            break;
          case "4":
            xingzhi = "外企";
            break;
          case "5":
            xingzhi = "合资企业";
            break;
          case "10":
            xingzhi = "其他";
            break;
        }
        mTxtXingzhiName.setText("商户性质：" + xingzhi);
        mTxtYinyeName.setText("营业执照名称：" + mShopVeriModel.busiLicenseName);
        mTxtZhuceNum.setText("注册号：" + mShopVeriModel.registeNo);
        goneloadDialog();
      }

      @Override public void onFail(int code, String result) {
        KLog.e(result);
        ToastUtils.showShortToast(result);
        goneloadDialog();
      }
    });
  }

  private void veriUser() {
    ShopDao.loadUserVeriInfo(new onConnectionFinishLinstener() {
      @Override public void onSuccess(int code, Object result) {
        UserVeriModel mUserVeriModel = (UserVeriModel) result;
        mTxtPersonName.setText("真实名字: " + mUserVeriModel.realName);
        mTxtPersonIdnum.setText("证件号: " + mUserVeriModel.certifNo);
        goneloadDialog();
      }

      @Override public void onFail(int code, String result) {
        goneloadDialog();
        ToastUtils.showShortToast(result);
      }
    });
  }

  @OnClick({
      R.id.edit_name, R.id.ly_type, R.id.edt_id_num, R.id.ly_huji, R.id.take_photo_one,
      R.id.take_photo_two, R.id.take_photo_three, R.id.btn_submit, R.id.img_update_yinye_card,
      R.id.btn_shops_submit, R.id.ly_shops_type, R.id.btn_to_shops,R.id.img_veri_shop,R.id.ly_user_veri
  }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.btn_to_shops:
        mLyShimingSuccess.setVisibility(View.GONE);
        showShopsVeriView();
        break;
      case R.id.edit_name:
        break;
      case R.id.ly_user_veri:
        showloadDialog("");
        mBtnToShops.setVisibility(View.GONE);
        veriUser();
        ShowVeriUserCompleteView();
        mLineThree.setImageResource(R.mipmap.xuxianlanse);
        break;
      case R.id.img_veri_shop:

        showVeriShopsCompleteView();
        break;
      case R.id.ly_type:
        ArrayList mList = new ArrayList();
        mList.add("身份证");
        mList.add("护照");
        mList.add("其他");
        showTypeDialog(mList);
        break;
      case R.id.edt_id_num:
        break;
      case R.id.ly_huji:
        final ProvincehDialog branchDialog = ProvincehDialog.newInstance(this);
        branchDialog.setAddress("北京市", "北京市");
        branchDialog.setSelectOnListener(new ProvinceCallBack() {
          @Override public void onWhellFinish(Province province, Province.CityListBean city,
              Province.AreaListBean mAreaListBean) {
            final String mProvince_name = province.name;
            final String mCity_name = city.name;
            final String mProvince_id = province.id;
            final String mCity_id = city.id;
//            if(mProvince_name.contains("TH")){
//              ShopHelp.showVeringPassword2(MyVerificationActivity.this, new onConnectionFinishLinstener() {
//                @Override
//                public void onSuccess(int code, Object result) {
//                  mOnSelectProvince = new Province(mProvince_name, mCity_name, mCity_id, mProvince_id);
//                  mTxtAddressType.setText(mProvince_name + mCity_name + "");
//                  KLog.e("mProvince" + mProvince_name + "mCity_name" + mCity_name);
//                }
//
//                @Override
//                public void onFail(int code, String result) {
//                  //branchDialog.show(getSupportFragmentManager());
//                  ToastUtils.showShortToast(result);
//                }
//              });
//            }else{
              mOnSelectProvince = new Province(mProvince_name, mCity_name, mCity_id, mProvince_id);
              mTxtAddressType.setText(mProvince_name + mCity_name + "");
              KLog.e("mProvince" + mProvince_name + "mCity_name" + mCity_name);
            //}
          }
        });
        branchDialog.show(getSupportFragmentManager());
        break;
      case R.id.take_photo_one:
        photo_type = 0;
        showDialog();
        break;
      case R.id.take_photo_two:
        photo_type = 1;
        showDialog();
        break;
      case R.id.take_photo_three:
        photo_type = 2;
        showDialog();
        break;
      case R.id.btn_submit:
        showUserWait();
        break;
      case R.id.img_update_yinye_card:
        photo_type = 3;
        showDialog();
        break;
      case R.id.ly_shops_type:
        ArrayList mLists = new ArrayList();
        mLists.add("个体工商户");
        mLists.add("民营企业");
        mLists.add("国营企业");
        mLists.add("外企");
        mLists.add("合资企业");
        mLists.add("其他");
        showTypeDialog(mLists);
        break;
      case R.id.btn_shops_submit:
        showShopsWait();
        break;
    }
  }

  private void showShopsWait() {
    String YinyeCard = mEditYinyeCard.getText().toString().trim();
    String RegisterNum = mEdtRegisterNum.getText().toString().trim();
    String Faren = mEdtFaren.getText().toString().trim();
    if (TextUtils.isEmpty(photo_file_four)) {
      ToastUtils.showShortToast("还没有选择营业执照");
      return;
    }
    if (TextUtils.isEmpty(YinyeCard)) {
      ToastUtils.showShortToast("还没有填写营业执照名称");
      return;
    }
    if (TextUtils.isEmpty(RegisterNum)) {
      ToastUtils.showShortToast("还没有填写注册号");
      return;
    }
    if (TextUtils.isEmpty(Faren)) {
      ToastUtils.showShortToast("还没有填写法人代表");
      return;
    }
    if (selectIndex == 0) {
      ToastUtils.showShortToast("还没有选择商户性质");
      return;
    }
    showloadDialog("提交中");
    ShopDao.PostDataToVeriShop(photo_file_four, YinyeCard, RegisterNum, Faren, selectIndex,
        new onConnectionFinishLinstener() {
          @Override public void onSuccess(int code, Object result) {
            goneloadDialog();
            ToastUtils.showShortToast(result.toString());
            finish();
          }

          @Override public void onFail(int code, String result) {
            goneloadDialog();
            ToastUtils.showShortToast(result);
          }
        });
  }

  private void showUserWait() {
    if (TextUtils.isEmpty(photo_file_one)) {
      ToastUtils.showShortToast("还没有选择手持证件照");
      return;
    }
    if (TextUtils.isEmpty(photo_file_two)) {
      ToastUtils.showShortToast("还没有选择证件正面照");
      return;
    }
    if (TextUtils.isEmpty(photo_file_three)) {
      ToastUtils.showShortToast("还没有选择证件背面照");
      return;
    }
    if (selectIndex == 0) {
      ToastUtils.showShortToast("还没有选择证件类型");
      return;
    }
    String name = mEditName.getText().toString().trim();
    if (TextUtils.isEmpty(name)) {
      ToastUtils.showShortToast("还没有填写真实姓名");
      return;
    }
    String id_num = mEdtIdNum.getText().toString().trim();
    if (TextUtils.isEmpty(id_num)) {
      ToastUtils.showShortToast("还没有填写证件号码");
      return;
    }
    String address_num = mTxtAddressType.getText().toString().trim();
    if (TextUtils.isEmpty(address_num)||null==mOnSelectProvince) {
      ToastUtils.showShortToast("还没有选择地区");
      return;
    }
    if (18<id_num.length()){
      ToastUtils.showShortToast("证件号码格式错误");
      return;
    }

    showloadDialog("提交中");
    ShopDao.PostDataToVeriUser(photo_file_one, photo_file_two, photo_file_three, name, id_num,
        selectIndex, mOnSelectProvince, new onConnectionFinishLinstener() {
          @Override public void onSuccess(int code, Object result) {
            goneloadDialog();
            ToastUtils.showShortToast("提交成功，请等待验证");
            finish();
          }

          @Override public void onFail(int code, String result) {
            goneloadDialog();
            ToastUtils.showShortToast(result);
          }
        });
  }
}
