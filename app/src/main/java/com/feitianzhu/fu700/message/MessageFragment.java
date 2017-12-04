package com.feitianzhu.fu700.message;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.me.base.BaseFragment;
import com.flyco.tablayout.SlidingTabLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageFragment extends BaseFragment {
  // TODO: Rename parameter arguments, choose names that match
  // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.tl_2)
    SlidingTabLayout mTabLayout;

  // TODO: Rename and change types of parameters
  private String mParam1;
  private String mParam2;
    private String[] mTitles = { "消息", "福友","组群" };



  public MessageFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param param1 Parameter 1.
   * @param param2 Parameter 2.
   * @return A new instance of fragment MessageFragment.
   */
  // TODO: Rename and change types and number of parameters
  public static MessageFragment newInstance(String param1, String param2) {
    MessageFragment fragment = new MessageFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }



  @Override
  protected void initView() {
    ViewPager viewPager = new ViewPager(getContext());
    viewPager.setAdapter(new PagerAdapter() {
      @Override
      public int getCount() {
        return mTitles.length;
      }

      @Override
      public boolean isViewFromObject(View view, Object object) {
        return view == object;
      }
    });


    mTabLayout.setViewPager((viewPager),mTitles);
  }

  @Override
  protected void initData() {

  }



  @Override
  protected int getLayoutId() {
    return R.layout.fragment_message;
  }


    @OnClick(R.id.bt_mask)
  public void onClick(View v){
      Toast.makeText(getContext(),"此功能正在开发中",Toast.LENGTH_SHORT).show();
  }
}
