package com.feitianzhu.fu700.me.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.model.MineInfoModel;
import com.feitianzhu.fu700.utils.ToastUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28 0028.
 */

public class PersonInfoAdapter extends BaseQuickAdapter<MineInfoModel, BaseViewHolder> {
  public PersonInfoAdapter(@Nullable List<MineInfoModel> data) {
    super(R.layout.person_info_fragment, data);
  }

  @Override protected void convert(BaseViewHolder holder, MineInfoModel item) {
      RelativeLayout rlGuanzhu = holder.getView(R.id.rl_guanzhu);
      RelativeLayout rlZhuanfa = holder.getView(R.id.rl_zhuanfa);
      rlGuanzhu.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              ToastUtils.showShortToast("敬请期待……");
          }
      });
      rlZhuanfa.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              ToastUtils.showShortToast("敬请期待……");
          }
      });

      String sex = "";
      if(item.getSex()==0){
        sex = "女";
      }else{
        sex = "男";
      }
    holder.setText(R.id.tv_username,item.getNickName()==null?"":item.getNickName().toString())
            .setText(R.id.tv_userId,item.getUserId()==0?"0":item.getUserId()+"")
            .setText(R.id.tv_userGender,sex==null?"":sex)
            .setText(R.id.tv_userStart,item.getConstellation()==null?"":item.getConstellation().toString())
            .setText(R.id.tv_userLocal,item.getLiveAdress()==null?"":item.getLiveAdress().toString())
            .setText(R.id.tv_userHome,item.getHomeAdress()==null?"":item.getHomeAdress().toString())
            .setText(R.id.tv_userJob,item.getJob()==null?"":item.getJob().toString())
            .setText(R.id.tv_userIndustry,item.getIndustry()==null?"":item.getIndustry().toString())
            .setText(R.id.tv_companyName,item.getCompany()==null?"":item.getCompany().toString())
            .setText(R.id.tv_phoneNum,item.getPhone()==null?"":item.getPhone().toString())
            .setText(R.id.tv_personSign,item.getPersonSign()==null?"":item.getPersonSign().toString())
            .setText(R.id.tv_InterestsHobbies,item.getInterest()==null?"":item.getInterest().toString());


  }
}
