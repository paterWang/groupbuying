package com.feitianzhu.fu700.common;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import com.afollestad.materialdialogs.MaterialDialog;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.view.pickview.LoopView;
import com.feitianzhu.fu700.view.pickview.OnItemSelectedListener;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.socks.library.KLog;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by dicallc on 2017/9/4 0004.
 */

public abstract class SelectPhotoActivity extends AppCompatActivity
    implements TakePhoto.TakeResultListener, InvokeListener {
  protected InvokeParam invokeParam;
  protected Context mContext;

  @Override public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
    PermissionManager.TPermissionType type =
        PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
    if (PermissionManager.TPermissionType.WAIT.equals(type)) {
      this.invokeParam = invokeParam;
    }
    return type;
  }

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    getTakePhoto().onCreate(savedInstanceState);
    super.onCreate(savedInstanceState);
    mContext = SelectPhotoActivity.this;
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    getTakePhoto().onSaveInstanceState(outState);
    super.onSaveInstanceState(outState);
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    getTakePhoto().onActivityResult(requestCode, resultCode, data);
    super.onActivityResult(requestCode, resultCode, data);
  }

  @Override public void takeSuccess(TResult result) {
    KLog.i("takeSuccess：" + result.getImage().getCompressPath());
  }

  @Override public void takeFail(TResult result, String msg) {
    KLog.e("takeFail:" + msg);
  }

  @Override public void takeCancel() {
    KLog.e("takeCancel:");
  }

  protected TakePhoto takePhoto;

  protected boolean isCorp=true;

  /**
   * 获取TakePhoto实例
   */
  protected TakePhoto getTakePhoto() {
    if (takePhoto == null) {
      takePhoto =
          (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
    }
    return takePhoto;
  }

  protected void TakePhoto() {
    Uri imageUri = CommonPhoto();
    //是否剪辑
    if (isCorp)
    takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
    else{
      takePhoto.onPickFromGallery();
    }
  }

  protected Uri CommonPhoto() {
    File file = new File(Environment.getExternalStorageDirectory(),
        "/temp/" + System.currentTimeMillis() + ".jpg");
    if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
    Uri imageUri = Uri.fromFile(file);

    //压缩
    configCompress();
    //选择图片配置
    configTakePhotoOption();
    return imageUri;
  }

  protected CropOptions getCropOptions() {
    CropOptions.Builder builder = new CropOptions.Builder();
    builder.setOutputX(800).setOutputY(800);
    //是否使用自带工具
    builder.setWithOwnCrop(true);
    return builder.create();
  }

  protected void configTakePhotoOption() {
    //是否使用take相册
    TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
    //builder.setWithOwnGallery(true);
    //纠正拍照的照片旋转角度
    builder.setCorrectImage(true);
    takePhoto.setTakePhotoOptions(builder.create());
  }

  protected void configCompress() {
    CompressConfig config;
    LubanOptions option =
        new LubanOptions.Builder().setMaxHeight(800).setMaxWidth(800).setMaxSize(102400).create();
    config = CompressConfig.ofLuban(option);
    config.enableReserveRaw(true);
    takePhoto.onEnableCompress(config, true);
  }

  protected void TakeCamera() {
    Uri imageUri = CommonPhoto();
    if (isCorp)
    takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
    else{
      takePhoto.onPickFromCapture(imageUri);
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, String[] permissions,
      int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    PermissionManager.TPermissionType type =
        PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    PermissionManager.handlePermissionsResult(this, type, invokeParam, this);
  }

  protected Dialog mCameraDialog;

  protected void showDialog() {
    mCameraDialog = new Dialog(this, R.style.BottomDialog);
    LinearLayout root =
        (LinearLayout) LayoutInflater.from(this).inflate(R.layout.dialog_view_takephoto, null);
    //初始化视图
    root.findViewById(R.id.btn_choose_img).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        TakePhoto();
        mCameraDialog.dismiss();
      }
    });
    root.findViewById(R.id.btn_open_camera).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        TakeCamera();
        mCameraDialog.dismiss();
      }
    });
    root.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        mCameraDialog.dismiss();
      }
    });
    mCameraDialog.setContentView(root);
    Window dialogWindow = mCameraDialog.getWindow();
    dialogWindow.setGravity(Gravity.BOTTOM);
    //        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
    WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
    lp.x = 0; // 新位置X坐标
    lp.y = 0; // 新位置Y坐标
    lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
    root.measure(0, 0);
    lp.height = root.getMeasuredHeight();

    lp.alpha = 9f; // 透明度
    dialogWindow.setAttributes(lp);
    mCameraDialog.show();
  }

  private MaterialDialog mDialog;

  protected void showloadDialog(String title) {
    mDialog = new MaterialDialog.Builder(this).title(title)
        .content("加载中,请稍等")
        .progress(true, 0)
        .progressIndeterminateStyle(true)
        .show();
  }

  protected void goneloadDialog() {
    if (null != mDialog && mDialog.isShowing()) if (mDialog.isShowing()) mDialog.dismiss();
  }
  protected int selectIndex = 0;
  protected AlertDialog alertDialog;
  protected abstract void onWheelSelect(int num, ArrayList<String> mList);
  protected void showTypeDialog(final ArrayList<String> list) {
    selectIndex = 0;
    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
    LayoutInflater inflater = this.getLayoutInflater();
    View dialogView = inflater.inflate(R.layout.dialog_view, null);
    dialogBuilder.setView(dialogView);
    LoopView loopView = (LoopView) dialogView.findViewById(R.id.loopView);
    loopView.setDividerColor(getResources().getColor(R.color.dialog_rect_bg));
    Button bt_sure = (Button) dialogView.findViewById(R.id.bt_sure);
    Button bt_cancel = (Button) dialogView.findViewById(R.id.bt_cancel);

    loopView.setNotLoop();
    // 滚动监听
    loopView.setListener(new OnItemSelectedListener() {
      @Override public void onItemSelected(int index) {
        KLog.e(selectIndex);
        selectIndex = index;
      }
    });
    // 设置原始数据
    loopView.setItems(list);
    alertDialog = dialogBuilder.create();
    alertDialog.show();
    bt_sure.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        selectIndex+=1;
        //Toast.makeText(VerificationActivity.this, "点击：" + list.get(selectIndex)+"selectIndex"+selectIndex, Toast.LENGTH_SHORT)
        //    .show();
        onWheelSelect(selectIndex,list);
        alertDialog.dismiss();
      }
    });
    bt_cancel.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        alertDialog.dismiss();
      }
    });
  }
}
