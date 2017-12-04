package com.feitianzhu.fu700.me.ui;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.login.LoginEvent;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.helper.CityModel;
import com.feitianzhu.fu700.me.helper.DateModel;
import com.feitianzhu.fu700.me.helper.DialogHelper;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.model.MineInfoModel;
import com.feitianzhu.fu700.shop.ShopDao;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

import static com.feitianzhu.fu700.common.Constant.ACCESSTOKEN;
import static com.feitianzhu.fu700.common.Constant.Common_HEADER;
import static com.feitianzhu.fu700.common.Constant.EDIT_MINE_INFO;
import static com.feitianzhu.fu700.common.Constant.POST_MINE_INFO;
import static com.feitianzhu.fu700.common.Constant.USERID;

/**
 * Created by Vya on 2017/9/1 0001.
 * 编辑名片
 */

public class EditCardActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.et_companyName)
    EditText mCompanyName;
    @BindView(R.id.et_jobName)
    EditText mJobName;
    @BindView(R.id.et_nickName)
    EditText mNickName;
    @BindView(R.id.radiogroup)
    RadioGroup mRadioGroup;
    @BindView(R.id.tv_time_pick1)
    TextView mTimePick1;
    @BindView(R.id.tv_time_pick2)
    TextView mTimePick2;
    @BindView(R.id.tv_city_pick)
    TextView mCityPick;
    @BindView(R.id.tv_signNameContainer)
    TextView mSignName;
    @BindView(R.id.tv_habit_Container)
    TextView mHabitTxt;
    @BindView(R.id.tv_tuijianId)
    TextView tv_tuijianId;
    @BindView(R.id.tv_phoneNum)
    TextView tv_phoneNum;
    @BindView(R.id.tv_homeTown)
    TextView mHomeTown;
    @BindView(R.id.rb_boy)
    RadioButton rb_boy;
    @BindView(R.id.rb_girl)
    RadioButton rb_girl;

    private String sexTxt;

    private  AlertDialog alertDialog;
    private String birthday;

    private String industryId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_card;
    }

    @Override
    protected void initTitle() {
        defaultNavigationBar = new DefaultNavigationBar
                .Builder(EditCardActivity.this, (ViewGroup)findViewById(R.id.Rl_titleContainer))
                .setTitle("编辑名片")
                .setStatusHeight(EditCardActivity.this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .setRightText("保存", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendSaveRequest();
                    }
                })
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }

    private void sendSaveRequest() {
        if(TextUtils.isEmpty(birthday)){
            Toast.makeText(EditCardActivity.this,"必须要填写生日!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(mCityModel == null){
            mCityModel = new CityModel();
        }
        if(mHomeModel == null){
            mHomeModel = new CityModel();
        }
        Log.e("Test","打印----sex---->"+sexTxt);
        OkHttpUtils.post()//

                .url(Common_HEADER + EDIT_MINE_INFO)
                .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                .addParams(USERID, Constant.LOGIN_USERID)
                .addParams("nickName", TextUtils.isEmpty(mNickName.getText().toString())?"":mNickName.getText().toString())
                .addParams("sex", sexTxt==null?"":sexTxt)
                .addParams("birthday", birthday==null?"":birthday)
                .addParams("industry", industryId==null?"":industryId)
                .addParams("company",mCompanyName.getText().toString()==null?"":mCompanyName.getText().toString())
                .addParams("job",mJobName.getText().toString()==null?"":mJobName.getText().toString())
                .addParams("liveProvinceld",mCityModel.getAreaId()==null?"":mCityModel.getAreaId())
                .addParams("liveProvinceName",mCityModel.getArea()==null?"":mCityModel.getArea())
                .addParams("liveCityId",mCityModel.getCityId()==null?"":mCityModel.getCityId())
                .addParams("liveCityName",mCityModel.getCity()==null?"":mCityModel.getCity())
                .addParams("homeProvinceld",mHomeModel.getAreaId()==null?"":mHomeModel.getAreaId())
                .addParams("homeProvinceName",mHomeModel.getArea()==null?"":mHomeModel.getArea())
                .addParams("homeCityId",mHomeModel.getCityId()==null?"":mHomeModel.getCityId())
                .addParams("homeCityName",mHomeModel.getCity()==null?"":mHomeModel.getCity())
                .addParams("personSign",signTxt==null?"":signTxt)
                .addParams("interest",hobbiesTxt==null?"":hobbiesTxt)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(String mData, Response response, int id) throws Exception {
                        return mData;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("wangyan","onError---->"+e.getMessage());
                        if ("数据为空".equals(e.getMessage())) {
                            Toast.makeText(mContext, "修改成功", Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().post(LoginEvent.EDITORINFO);
                            finish();

                        } else {
                            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onResponse(Object response, int id) {
                        Log.e("wangyan","onResponse---->"+response);
                    }
                });
    }

    @Override
    protected void initView() {
       Intent intent =  getIntent();
        String parentId = intent.getStringExtra("parentId");
        String phoneNum = intent.getStringExtra("phoneNum");
        if(parentId != null){
            tv_tuijianId.setText("("+parentId+")");
        }
        if(phoneNum != null){
            tv_phoneNum.setText("("+phoneNum+")");
        }
        requestData();
    }

    private void requestData() {
        OkHttpUtils.post()//
                .url(Common_HEADER + POST_MINE_INFO)
                .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                .addParams(USERID, Constant.LOGIN_USERID)
                .build()
                .execute(new Callback<MineInfoModel>() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("wangyan","onError---->"+e.getMessage());
                        ToastUtils.showShortToast(e.getMessage());
                    }

                    @Override
                    public void onResponse(MineInfoModel response, int id) {
                        setShowData(response);
                    }
                });
    }
    private String longToData(long time){
        Log.e("Test","----------time------>"+time);
        time = Math.abs(time);
        if(time<=0){
            return "";
        }
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd");
//前面的lSysTime是秒数，先乘1000得到毫秒数，再转为java.util.Date类型
        java.util.Date dt = new Date(time);
        String sDateTime = sdf.format(dt);  //得到精确到秒的表示：08/31/2006 21:08:00
        return sDateTime;
    }
    private void setShowData(MineInfoModel response) {
        if(response==null){
            return;
        }
        if(response.getSex()==0){
            rb_girl.setChecked(true);
        }else if(response.getSex()==1){
            rb_boy.setChecked(true);
        }
        mCompanyName.setText(response.getCompany()==null?"":response.getCompany());
        mJobName.setText(response.getJob()==null?"":response.getJob());
        mNickName.setText(response.getNickName()==null?"":response.getNickName());
        birthday = response.getBirthday()==null?"":response.getBirthday();
        mTimePick1.setText(birthday);
        String label2 = "";
        if(response.getIndustryLabel() != null && response.getIndustryLabel().size()>0){
            label2 = response.getIndustryLabel().get(0).toString();
        }else{
            label2 = "";
        }
        mTimePick2.setText(label2);
        mCityPick.setText(response.getLiveAdress()==null?"":response.getLiveAdress());
        mHomeTown.setText(response.getHomeAdress()==null?"":response.getHomeAdress());
        mSignName.setText(response.getPersonSign()==null?"":response.getPersonSign());
        mHabitTxt.setText(response.getInterest()==null?"":response.getInterest());

    }


    @Override
    protected void initData() {

        ShopDao.loadUserAuthImpl();

        mTimePick1.setOnClickListener(this);
        mTimePick2.setOnClickListener(this);
        mCityPick.setOnClickListener(this);
        mSignName.setOnClickListener(this);
        mHabitTxt.setOnClickListener(this);
        mHomeTown.setOnClickListener(this);

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton radioButton = (RadioButton) findViewById(group.getCheckedRadioButtonId());
                sexTxt = (radioButton.getText().toString().equals("男")?"1":"0");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_time_pick1:
                showDialog(v);
                break;
            case R.id.tv_time_pick2: //选择行业方向
                Intent intent = new Intent(EditCardActivity.this,ChooseIndustryDirectionActivity.class);
                startActivityForResult(intent,1003);
                break;
            case R.id.tv_city_pick:
                showCityDialog(v);
                break;
            case R.id.tv_signNameContainer:
                Intent signItent = new Intent(EditCardActivity.this,EditPersonSignActivity.class);
                startActivityForResult(signItent,1001);
                break;
            case R.id.tv_habit_Container:
                Intent habitItent = new Intent(EditCardActivity.this,InterestHobbiesActivity.class);
                startActivityForResult(habitItent,1002);
                break;
            case R.id.tv_homeTown:
                showCityDialog(v);
                break;
        }
    }
    private CityModel mCityModel,mHomeModel;
    private void showCityDialog(View v) {
        //这个是城市选择器
      new DialogHelper(EditCardActivity.this).init(DialogHelper.DIALOG_TWO,v).buildDialog(new DialogHelper.OnButtonClickListener<CityModel>() {

            @Override
            public void onButtonClick(View v, CityModel result, View clickView) {
                switch (clickView.getId()){
                    case R.id.tv_city_pick:
                        mCityModel = result;
                        mCityPick.setText(result.getCity()+"  "+result.getArea());
                        break;
                    case R.id.tv_homeTown:
                        mHomeModel = result;
                        mHomeTown.setText(result.getCity()+"  "+result.getArea());
                        break;
                }

            }
        });

    }

    private void showDialog(View v) {
        //这个是 时间选择
        new DialogHelper(EditCardActivity.this).init(DialogHelper.DIALOG_THREE,v).buildDialog(new DialogHelper.OnButtonClickListener<DateModel>() {

            @Override
            public void onButtonClick(View v, DateModel result, View clickView) {
                birthday = result.year+"/"+result.month+"/"+result.day;
                mTimePick1.setText(birthday);

            }
        });
    }

    private String signTxt;
    private String hobbiesTxt;
    private String industryTxt;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data == null){
            return;
        }
        switch (requestCode){
            case 1001:
                 signTxt = data.getStringExtra("signTxt");
                if(!TextUtils.isEmpty(signTxt)){
                    mSignName.setText(signTxt);
                }
                break;
            case 1002:
                hobbiesTxt = data.getStringExtra("hobbiesTxt");
                if(!TextUtils.isEmpty(hobbiesTxt)){
                    mHabitTxt.setText(hobbiesTxt);
                }
                break;
            case 1003:
                 industryTxt = data.getStringExtra("industryTxt");
                industryId = data.getStringExtra("industryId");
                if(!TextUtils.isEmpty(industryTxt)){
                    mTimePick2.setText(industryTxt);
                }
                break;

        }
    }
}
