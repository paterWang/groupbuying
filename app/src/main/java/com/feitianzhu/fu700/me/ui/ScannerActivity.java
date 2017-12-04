package com.feitianzhu.fu700.me.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.common.base.LazyWebActivity;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.helper.ImageUtil;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.me.ui.totalScore.MineQrcodeActivity;
import com.feitianzhu.fu700.shop.ui.ShopsPayActivity;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Vya on 2017/8/31 0031.
 */

public class ScannerActivity extends BaseActivity {

    @BindView(R.id.textview)
    TextView mTextview;
    private static final int REQUEST_IMAGE = 0x00011;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scanner;
    }

    @Override
    protected void initTitle() {
        defaultNavigationBar = new DefaultNavigationBar
                .Builder(ScannerActivity.this, (ViewGroup) findViewById(R.id.Rl_titleContainer))
                .setTitle("扫一扫")
                .setStatusHeight(ScannerActivity.this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .setRightText("相册", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        TakePhoto();
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        intent.setType("image/*");
                        startActivityForResult(intent, REQUEST_IMAGE);
                    }
                })
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }

    @Override
    protected void initView() {

        /**
         * 执行扫面Fragment的初始化操作
         */
        CaptureFragment captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.scanner_view);

        captureFragment.setAnalyzeCallback(analyzeCallback);
        /**
         * 替换我们的扫描控件
         */
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {

//            1.判断是否是当前服务器地址的前缀
//            a 是的话判断type 1 就去商户付款页面  2就去用户个人资料
//            b 不是的话 判断是否是Url 是 直接跳 web就提示无法识别
            Log.e("Test", "-------Url----->" + result);
            checkUrl(result);

           /* Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            resultIntent.putExtras(bundle);
            ScannerActivity.this.setResult(RESULT_OK, resultIntent);
            ScannerActivity.this.finish();*/
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            ScannerActivity.this.setResult(RESULT_OK, resultIntent);
            ScannerActivity.this.finish();
        }
    };


    private void checkUrl(String result) {
        Intent intent = null;
        if (result.contains(Constant.CHECK_URL_HEAD)) {  //.判断是否是当前服务器地址的前缀
            if (result.contains("type=1")) { //商户
                intent = new Intent(ScannerActivity.this, ShopsPayActivity.class);
                String merchantId = getStringByUrl(result, "merchantId");
                intent.putExtra("merchantId", merchantId);
                startActivity(intent);
                finish();
            } else if (result.contains("type=2")) { //跳转进入到商户详情页
                intent = new Intent(ScannerActivity.this, ShopDetailActivity.class);
                String otherId = getStringByUrl(result, "userId");
                intent.putExtra("otherId", otherId);
                startActivity(intent);
                finish();
            }
        } else {
            if (isURL(result)) {
                //跳转到webViewnull
                intent = new Intent(ScannerActivity.this, LazyWebActivity.class);
                intent.putExtra(Constant.URL, result);
                intent.putExtra(Constant.H5_TITLE, "扫描二维码");
                startActivity(intent);
                finish();
            } else {
                ToastUtils.showShortToast("无法识别此二维码!!");
            }
        }
    }

    private boolean isURL(String result) {
        String regex = "^((https|http|ftp|rtsp|mms)?://)"
                + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
                + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
                + "|" // 允许IP和DOMAIN（域名）
                + "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.
                + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
                + "[a-z]{2,6})" // first level domain- .com or .museum
                + "(:[0-9]{1,4})?" // 端口- :80
                + "((/?)|" // a slash isn't required if there is no file name
                + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";

        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(result);
        return matcher.find();
    }

    @Override
    protected void initData() {


    }

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
*/

    @OnClick(R.id.textview)
    public void onClick(View v) {
        Intent intent = new Intent(ScannerActivity.this, MineQrcodeActivity.class);
        startActivity(intent);
    }

    public String getStringByUrl(String url, String typeName) {
        String[] arr = url.split("[?]");
        String temp = arr[1];
        String[] arr2 = temp.split("&");
        for (String item : arr2) {
            if (item.contains(typeName)) {
                String[] str = item.split("=");
                return str[1];
            }
        }
        return "";
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        /**
         * 选择系统图片并解析
         */
         if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(this, uri), new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                            checkUrl(result);
                        }

                        @Override
                        public void onAnalyzeFailed() {
                            Toast.makeText(ScannerActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
