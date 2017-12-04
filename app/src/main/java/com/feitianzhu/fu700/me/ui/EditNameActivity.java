package com.feitianzhu.fu700.me.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Vya on 2017/9/19 0019.
 */

public class EditNameActivity extends BaseActivity {
    @BindView(R.id.et_inputName)
    EditText mInputName;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_name;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
    @OnClick({R.id.bt_send})
    public void onClick(View v)
    {
        String text = mInputName.getText().toString();
        if(TextUtils.isEmpty(text)){
            ToastUtils.showShortToast("内容不能为空!");
            return;
        }
        sendData(text);
    }

    private void sendData(String text) {
      /*  OkHttpUtils.post()//
                .url(Common_HEADER + Constant.POST_SERVICE_DETAIL_INFO)
                .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                .addParams(USERID, Constant.LOGIN_USERID)
                .addParams("serviceId",serviceId)
                .build()
                .execute(new Callback<ServiceDetailModel>() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("wangyan","onError---->"+e.getMessage());
                    }

                    @Override
                    public void onResponse(ServiceDetailModel response, int id) {
                        model = response;
                        setShowData(response);
                    }

                });*/
    }
}
