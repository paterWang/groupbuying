package com.feitianzhu.fu700.me.ui;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;

import butterknife.BindView;

/**
 * Created by Vya on 2017/9/4 0004.
 */

public class EditPersonSignActivity extends BaseActivity {
    @BindView(R.id.et_signTxt)
    EditText mSignTxt;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_person_sign;
    }

    @Override
    protected void initTitle() {
        defaultNavigationBar = new DefaultNavigationBar
                .Builder(EditPersonSignActivity.this, (ViewGroup)findViewById(R.id.ll_Container))
                .setTitle("修改个性签名")
                .setStatusHeight(EditPersonSignActivity.this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .setRightText("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(EditPersonSignActivity.this,"点击完成",Toast.LENGTH_SHORT).show();
                        Intent mIntent = new Intent();
                        mIntent.putExtra("signTxt", mSignTxt.getText().toString());
                        setResult(1001, mIntent);
                        finish();
                    }
                })
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
