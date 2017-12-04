package com.feitianzhu.fu700.bankcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.bankcard.adapter.BankCardAdapter;
import com.feitianzhu.fu700.bankcard.entity.UserBankCardEntity;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.dao.NetworkDao;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.shop.ShopDao;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyBankCardActivity extends BaseActivity implements SwipeItemClickListener {

    @BindView(R.id.button)
    Button mButton;
    @BindView(R.id.ll_no_card)
    LinearLayout mNoCardView;
    @BindView(R.id.recyclerview)
    SwipeMenuRecyclerView mRecyclerView;
    private BankCardAdapter mAdapter;
    private List<UserBankCardEntity> mDatas;

    private static final int REQUEST_CODE = 101;

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
    protected int getLayoutId() {
        return R.layout.activity_my_bank_card;
    }

    @Override
    protected void initView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setOnItemMoveListener(mItemMoveListener);// 监听拖拽，更新UI。
        mRecyclerView.setSwipeMenuItemClickListener(mMenuItemClickListener); // Item的Menu点击。
        mRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator); //设置侧滑菜单

        defaultNavigationBar = new DefaultNavigationBar
                .Builder(this, (ViewGroup) findViewById(R.id.Rl_titleContainer))
                .setTitle("我的银行卡")
                .setStatusHeight(MyBankCardActivity.this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);
    }

    @OnClick({R.id.button})
    public void onClick() {
        startActivityForResult(new Intent(this, AddBankCardActivity.class), REQUEST_CODE);
    }

    @Override
    protected void initData() {

        mDatas = new ArrayList<>();

        mAdapter = new BankCardAdapter(mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setSwipeItemClickListener(this);
        ShopDao.loadUserAuthImpl();

        View footerView = LayoutInflater.from(this).inflate(R.layout.footer_button, null);
        footerView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MyBankCardActivity.this, AddBankCardActivity.class), REQUEST_CODE);
            }
        });
        mAdapter.addFooterView(footerView);

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
                onNoData();
            }
        });
    }

    private void onNoData() {
        mRecyclerView.setVisibility(View.GONE);
        mNoCardView.setVisibility(View.VISIBLE);
    }

    OnItemMoveListener mItemMoveListener = new OnItemMoveListener() {
        @Override
        public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
            int fromPosition = srcHolder.getAdapterPosition();
            int toPosition = targetHolder.getAdapterPosition();
            // Item被拖拽时，交换数据，并更新adapter。
            Collections.swap(mDatas, fromPosition, toPosition);
            mAdapter.notifyItemMoved(fromPosition, toPosition);
            return true;
        }

        @Override
        public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {
            int position = srcHolder.getAdapterPosition();
            // Item被侧滑删除时，删除数据，并更新adapter。
            mDatas.remove(position);
            mAdapter.notifyItemRemoved(position);
        }
    };

    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {

            menuBridge.closeMenu();
            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            final int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                new MaterialDialog.Builder(MyBankCardActivity.this).title("温馨提示")
                        .content("确定要解绑该银行卡吗？")
                        .positiveText("确认")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog mMaterialDialog,
                                                @NonNull DialogAction mDialogAction) {
                                NetworkDao.deleteBankCard(mDatas.get(adapterPosition).bankCardId + "", new onConnectionFinishLinstener() {
                                    @Override
                                    public void onSuccess(int code, Object result) {
                                        ToastUtils.showShortToast("解绑成功");
                                        mDatas.remove(adapterPosition);
                                        mAdapter.notifyItemRemoved(adapterPosition);
                                    }

                                    @Override
                                    public void onFail(int code, String result) {
                                        ToastUtils.showShortToast(result);
                                    }
                                });
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog mMaterialDialog,
                                                @NonNull DialogAction mDialogAction) {
                                mMaterialDialog.dismiss();
                            }
                        })
                        .negativeText("取消")
                        .show();
            }
        }
    };

    private SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeMenu, SwipeMenu swipeMenu1, int i) {

            int width = getResources().getDimensionPixelSize(R.dimen.dp_100);
            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = getResources().getDimensionPixelSize(R.dimen.bankcard_content_height);
            {
                SwipeMenuItem addItem = new SwipeMenuItem(MyBankCardActivity.this)
                        .setBackground(R.drawable.buybutton_shape_unbind_red)
                        .setWidth(width)
                        .setHeight(height)
                        .setTextColor(getResources().getColor(R.color.white))
                        .setText("解除绑定");
                swipeMenu1.addMenuItem(addItem); // 添加一个按钮到左侧菜单。
            }
        }
    };

    @Override
    public void onItemClick(View view, int i) {
//        startActivity(new Intent(this, SelectBankCardActivity.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BankCardEvent event) {
        switch (event) {
            case ADD_BANKCARD:
                getData();
                break;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getData();
    }
}
