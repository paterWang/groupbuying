package com.feitianzhu.fu700.me.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.base.SFFragment;
import com.feitianzhu.fu700.me.adapter.MyMoneyAdapter;
import com.feitianzhu.fu700.model.WalletModel;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MyMoneyFragment extends SFFragment{
  private static final String ARG_PARAM1 = "param1";
  private static final String ARG_PARAM2 = "param2";
  @BindView(R.id.list) RecyclerView mList;
  Unbinder unbinder;

  private String mParam1;
  private String mParam2;
  private List<WalletModel.WallentRecordListEntity> lists=new ArrayList<>();
  private TextView mNum;
  private MyMoneyAdapter mAdapter;

  public MyMoneyFragment() {
  }

  public static MyMoneyFragment newInstance(String param1, String param2) {
    MyMoneyFragment fragment = new MyMoneyFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    args.putString(ARG_PARAM2, param2);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EventBus.getDefault().register(this);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
      mParam2 = getArguments().getString(ARG_PARAM2);
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_my_money, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mAdapter = new MyMoneyAdapter(lists);
    View mView = View.inflate(mContext, R.layout.mywallet_header_view, null);
    mNum = (TextView) mView.findViewById(R.id.num);
    mAdapter.setHeaderView(mView);
    mList.setAdapter(mAdapter);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    EventBus.getDefault().unregister(this);
    unbinder.unbind();
  }
  @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
  public void onDataSynEvent(WalletModel event) {
    mNum.setText(event.balance+"");
    mAdapter.getData().clear();
    mAdapter.addData(event.balanceRecordList);
  }
}
