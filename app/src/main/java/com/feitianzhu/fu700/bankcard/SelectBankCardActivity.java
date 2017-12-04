package com.feitianzhu.fu700.bankcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.bankcard.adapter.BankCardAdapter;
import com.feitianzhu.fu700.bankcard.entity.UserBankCardEntity;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.dao.NetworkDao;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SelectBankCardActivity extends BaseActivity implements SwipeItemClickListener {

    @BindView(R.id.button)
    Button mButton;
    @BindView(R.id.ll_no_card)
    LinearLayout mNoCardView;
    @BindView(R.id.recyclerview)
    SwipeMenuRecyclerView mRecyclerView;

    private BankCardAdapter mAdapter;
    private List<UserBankCardEntity> mDatas;
    private View mFooterView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_bank_card;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mFooterView = LayoutInflater.from(this).inflate(R.layout.footer_select_bank_card, null);
        mFooterView.findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectBankCardActivity.this, AddBankCardActivity.class));
            }
        });

        defaultNavigationBar = new DefaultNavigationBar
                .Builder(this, (ViewGroup) findViewById(R.id.Rl_titleContainer))
                .setTitle("选择银行卡")
                .setStatusHeight(SelectBankCardActivity.this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }

    @Override
    protected void initData() {

        mDatas = new ArrayList<>();

        mAdapter = new BankCardAdapter(mDatas);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.addFooterView(mFooterView);

        mRecyclerView.setSwipeItemClickListener(this);

        getData();
    }

    @Override
    public void onItemClick(View view, int i) {
        if (mDatas.isEmpty() || i > mDatas.size() - 1) return;

        Intent intent = new Intent();
        intent.putExtra(Constant.INTENT_SELECTET_BANKCARD, mDatas.get(i));
        setResult(RESULT_OK, intent);
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BankCardEvent event) {
        getData();
    }

    private void getData() {

        NetworkDao.getUserBankCardList(new onConnectionFinishLinstener() {
            @Override
            public void onSuccess(int code, Object result) {

                List<UserBankCardEntity> list = (List<UserBankCardEntity>) result;

                if (list.isEmpty()) {
                    onNoData();
                } else {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mNoCardView.setVisibility(View.GONE);

                    mDatas.clear();
                    mDatas.addAll(list);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFail(int code, String result) {
                ToastUtils.showShortToast(result);
            }
        });
    }

    private void onNoData() {
        mRecyclerView.setVisibility(View.GONE);
        mNoCardView.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.button})
    public void onClick() {
        startActivity(new Intent(SelectBankCardActivity.this, AddBankCardActivity.class));
    }
}
