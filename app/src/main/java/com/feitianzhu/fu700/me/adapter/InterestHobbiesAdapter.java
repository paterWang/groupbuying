package com.feitianzhu.fu700.me.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.me.helper.DividerGridItemDecoration;
import com.feitianzhu.fu700.model.InterestOutModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/28 0028.
 */

public class InterestHobbiesAdapter extends BaseQuickAdapter<InterestOutModel, BaseViewHolder> {
    private RecyclerView itemRecycler;
    private List<InterestOutModel.ChildrenListBean> mTempData;
  public InterestHobbiesAdapter(@Nullable List<InterestOutModel> data) {
    super(R.layout.interest_hobies_item, data);
      mTempData = new ArrayList<>();
  }

  @Override protected void convert(BaseViewHolder holder, InterestOutModel item) {
      mTempData.clear();
      int size =item.getChildrenList().size();
      if(size>0){
          for(int i=0;i<size;i++){
              if(item.getChildrenList().get(i).getInterestName().equals("其他")){
                  mTempData.add(item.getChildrenList().get(i));
                    break;
              }else{
                  Log.e("wangyan","i===>"+i);
                  mTempData.add(item.getChildrenList().get(i));
              }
          }
      }else{
          mTempData.addAll(item.getChildrenList());
      }




      holder.setText(R.id.tv_title,item.getInterestName());
      itemRecycler = holder.getView(R.id.recyclerview_item);


      itemRecycler.setLayoutManager(new GridLayoutManager(mContext,4));
      itemRecycler.addItemDecoration(new DividerGridItemDecoration(mContext));
      InterestHobbiesItemAdapter adapter = new InterestHobbiesItemAdapter(mTempData);
      adapter.SetOnItemClickListener((new InterestHobbiesItemAdapter.OnItemClickListener(){

          @Override
          public void onItemClick(int position, View v) {
              if(listener != null){
                  listener.onPassClick(position,v);
              }
          }

          @Override
          public void onOtherButtonClick() {
              if(listener != null){
                  listener.onPassOtherButtonClick();
              }
          }
      }));
      itemRecycler.setAdapter(adapter);
//      SpacesItemDecoration decoration=new SpacesItemDecoration(16);
//      itemRecycler.addItemDecoration(decoration);
  }


    public interface OnPassClickListener{
        void onPassClick(int position,View v);
        void onPassOtherButtonClick();
    }

    private OnPassClickListener listener;
    public void SetOnPassClickListner(OnPassClickListener listener){
        this.listener = listener;
    }

  private List<String> getTestData(){
    List<String> list = new ArrayList<>();
    for(int i=0;i<12;i++){
      list.add("测试"+i);
    }
    return list;
  }
}
