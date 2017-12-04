package com.feitianzhu.fu700.me.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.me.helper.DividerGridItemDecoration;
import com.feitianzhu.fu700.model.IndustryOutModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/28 0028.
 */

public class ChooseIndustryDirectionAdapter extends BaseQuickAdapter<IndustryOutModel, BaseViewHolder> {
    private RecyclerView itemRecycler;
    private List<IndustryOutModel.ChildrenListBean> mTempData;
  public ChooseIndustryDirectionAdapter(@Nullable List<IndustryOutModel> data,Context context) {
    super(R.layout.chooseindustry_direction_item, data);
      mTempData = new ArrayList<>();
  }

  @Override protected void convert(BaseViewHolder holder, IndustryOutModel item) {

      mTempData.clear();
      if(item.getChildrenList().size()>0){
          for(int i=0; i<5 ; i++){
              if(item.getChildrenList().size()>i){
                  mTempData.add(item.getChildrenList().get(i));
              }
          }
          IndustryOutModel.ChildrenListBean endBean = new IndustryOutModel.ChildrenListBean();
          endBean.setIndustryName("更多");
          mTempData.add(endBean);
      }else{
          mTempData.clear();
          mTempData.addAll(item.getChildrenList());
      }


      holder.setText(R.id.tv_title,item.getIndustryName());
      itemRecycler = holder.getView(R.id.recyclerview_item);


      itemRecycler.setLayoutManager(new GridLayoutManager(mContext,3));
      itemRecycler.addItemDecoration(new DividerGridItemDecoration(mContext));
      ChooseIndustryItemAdapter adapter = new ChooseIndustryItemAdapter(mTempData);
      adapter.SetOnItemClickListener(new ChooseIndustryItemAdapter.OnItemClickListener() {
          @Override
          public void onItemClick(String id, View v) {
             // Toast.makeText(mContext,"----"+((Button)v).getText()+"----"+position,Toast.LENGTH_SHORT).show();
                if(listener != null){
                        listener.onPassClick(id,v);
                }
          }

          @Override
          public void onMoreButtonClick() {
                if(listener != null){
                    listener.onPassMoreButtonClick();
                }
          }
      });
      itemRecycler.setAdapter(adapter);
//      SpacesItemDecoration decoration=new SpacesItemDecoration(16);
//      itemRecycler.addItemDecoration(decoration);
  }


  public interface OnPassClickListener{
     void onPassClick(String id,View v);
      void onPassMoreButtonClick();
  }

  private OnPassClickListener listener;
    public void SetOnPassClickListner(OnPassClickListener listener){
        this.listener = listener;
    }

  private List<String> getTestData(){
    List<String> list = new ArrayList<>();
    for(int i=0;i<6;i++){
      list.add("测试"+i);
    }
    return list;
  }
}
