package com.feitianzhu.fu700.me.ui;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.base.LazyBaseActivity;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.model.ShopOrderModel;
import com.feitianzhu.fu700.shop.ShopDao;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.socks.library.KLog;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class OrdeRevaluateActivity extends LazyBaseActivity {

  @BindView(R.id.item_icon) ImageView mItemIcon;
  @BindView(R.id.item_name) TextView mItemName;
  @BindView(R.id.item_juli) TextView mItemJuli;
  @BindView(R.id.item_address) TextView mItemAddress;
  @BindView(R.id.item_msg) TextView mItemMsg;
  @BindView(R.id.shop_money) TextView mShopMoney;
  @BindView(R.id.item_service_ratting) MaterialRatingBar mItemServiceRatting;
  @BindView(R.id.item_ratting) MaterialRatingBar mItemRatting;
  @BindView(R.id.edit_px) EditText mEditPx;
  @BindView(R.id.rl_bottomContainer) Button mRlBottomContainer;
  private ShopOrderModel.ListEntity mOrderModel;

  @Override protected int setView() {
    return R.layout.activity_orde_revaluate;
  }

  @Override protected void initView() {
    ShopOrderModel.ListEntity.MerchantEntity mMerchant = mOrderModel.merchant;
    setTitleName("评价");
    if (null != mMerchant) {
      Glide.with(this)
          .load(mMerchant.merchantHeadImg)
          .placeholder(R.mipmap.pic_fuwutujiazaishibai)
          .into(mItemIcon);
      mItemName.setText(mMerchant.merchantName);
      mItemAddress.setText(mMerchant.provinceName
          + mMerchant.cityName
          + mMerchant.areaName
          + mMerchant.dtlAddr
          + "");
      mShopMoney.setText(mOrderModel.consumeAmount + "");
    }
  }

  @Override protected void initLocal() {
    mOrderModel = getIntent().getParcelableExtra("model");
    KLog.e(mOrderModel);
  }

  @Override protected void initData() {

  }

  @OnClick(R.id.rl_bottomContainer) public void onViewClicked() {
    String content = mEditPx.getText().toString().trim();
    if (TextUtils.isEmpty(content)) {
      ToastUtils.showShortToast("评论不能为空");
      return;
    }
    showloadDialog("");
    float mRating = mItemRatting.getRating();
    ShopDao.postShopEva(mOrderModel.orderNo, content, mRating + "",
        new onConnectionFinishLinstener() {
          @Override public void onSuccess(int code, Object result) {
            goneloadDialog();
            ToastUtils.showShortToast("评论成功");
            finish();
          }

          @Override public void onFail(int code, String result) {
            goneloadDialog();
            ToastUtils.showShortToast(result);
          }
        });
  }
}
