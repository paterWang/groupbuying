package com.feitianzhu.fu700.huanghuali;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.dao.NetworkDao;
import com.feitianzhu.fu700.huanghuali.entity.HuangHuaLiHMLEntity;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.me.ui.totalScore.TransferVoucherActivity;
import com.feitianzhu.fu700.model.SelectPayNeedModel;
import com.feitianzhu.fu700.utils.FileUtils;
import com.feitianzhu.fu700.utils.ToastUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class HuangHuaLiHTMLActivity extends BaseActivity {


    @BindView(R.id.webview)
    WebView mWebView;
    private static final String FILE_NAME = "huanghuali.html";
    @BindView(R.id.button)
    Button mButton;
    @BindView(R.id.tv_money)
    TextView mTvMoney;
    private HuangHuaLiHMLEntity mEntity;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_huang_hua_li;
    }

    @Override
    protected void initView() {

        WebSettings settings = mWebView.getSettings();
        settings.setLoadWithOverviewMode(true);
        //支持屏幕缩放
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        //不显示webview缩放按钮
        settings.setDisplayZoomControls(false);
        settings.setJavaScriptEnabled(true);
        showloadDialog("加载中...");

    }

    @Override
    protected void initTitle() {

        defaultNavigationBar = new DefaultNavigationBar
                .Builder(this, (ViewGroup) findViewById(R.id.Rl_titleContainer))
                .setTitle("黄花梨")
                .setStatusHeight(this)
                .setRightText("记录", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(mContext, HuangHuaLiRecordActivity.class));
                    }
                })
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }

    @Override
    protected void initData() {

        NetworkDao.getHuanghualiInfo(new onConnectionFinishLinstener() {
            @Override
            public void onSuccess(int code, Object result) {

                mEntity = (HuangHuaLiHMLEntity) result;
           //     mTvMoney.setText(String.format(getString(R.string.money), mEntity.price + ""));
                mTvMoney.setText( mEntity.price + "");
                if (mEntity != null) {
                    String introduce = mEntity.introduce;
                    introduce = introduce.replace("<img", "<img style='max-width:100%;height:auto;'");
                    FileUtils.writeFileFromString(new File(getCacheDir(), FILE_NAME), introduce, false);
                    loadWebView("file://" + new File(getCacheDir(), FILE_NAME).getAbsolutePath());
                } else {
                    onFail(0, "数据为空");
                }

            }

            @Override
            public void onFail(int code, String result) {
                ToastUtils.showShortToast(result);

            }
        });
    }

    private void loadWebView(String mShopUrl) {
        mWebView.loadUrl(mShopUrl);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    goneloadDialog();
                    // 网页加载完成
                }

            }
        });
    }

    @OnClick(R.id.button)
    public void onClick() {

        SelectPayNeedModel selectPayNeedModel = new SelectPayNeedModel();
        selectPayNeedModel.setType(SelectPayNeedModel.TYPE_HUANGHUALI);

        Intent intent = new Intent(this, TransferVoucherActivity.class);
        intent.putExtra("transferNeedModel", selectPayNeedModel);
        startActivity(intent);
    }
}
