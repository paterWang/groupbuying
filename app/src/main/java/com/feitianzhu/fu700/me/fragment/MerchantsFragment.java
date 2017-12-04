package com.feitianzhu.fu700.me.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.Constant;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.me.adapter.MerchantsAdapter;
import com.feitianzhu.fu700.me.base.BaseFragment;
import com.feitianzhu.fu700.model.MineCollectionMerchantsModel;
import com.feitianzhu.fu700.shop.ShopDao;
import com.feitianzhu.fu700.shop.ui.ShopsActivity;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

import static com.feitianzhu.fu700.common.Constant.ACCESSTOKEN;
import static com.feitianzhu.fu700.common.Constant.Common_HEADER;
import static com.feitianzhu.fu700.common.Constant.ISADMIN;
import static com.feitianzhu.fu700.common.Constant.MERCHANTID;
import static com.feitianzhu.fu700.common.Constant.POST_MINE_COLLECTION;
import static com.feitianzhu.fu700.common.Constant.USERID;

/**
 * Created by Vya on 2017/9/5 0005.
 * 我的收藏-商户界面
 */

public class MerchantsFragment extends BaseFragment implements SwipeMenuItemClickListener, SwipeItemClickListener, SwipeMenuRecyclerView.LoadMoreListener {
    @BindView(R.id.recyclerview)
    SwipeMenuRecyclerView mRecyclerView;
    @BindView(R.id.fl_emptyview)
    FrameLayout mEmptyViewContainer;


    private MerchantsAdapter mAdapter;
    private List<MineCollectionMerchantsModel.ListBean> mList;

    private int pageRows=6;
    private final static int LOAD_NORMAL = 0;
    private final static int LOAD_MORE = 1;
    private int currPage = 0;
    private int totalPage = 0;
    private boolean hasNextPage = false;
    private List<MineCollectionMerchantsModel.ListBean> mTemp;

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mList = new ArrayList<>();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new MerchantsAdapter(mList);
//        mAdapter.setEmptyView(mEmptyView);
        mEmptyViewContainer.addView(mEmptyView);
//        mRecyclerView.setItemViewSwipeEnabled(true);
        mRecyclerView.setSwipeMenuItemClickListener(this);
        mRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
        mRecyclerView.setSwipeItemClickListener(this);
        mRecyclerView.setLoadMoreListener(this);
        mRecyclerView.setAdapter(mAdapter);

        requestData(LOAD_NORMAL);
    }

    private void requestData(final int state) {
        OkHttpUtils.post()//
                .url(Common_HEADER + POST_MINE_COLLECTION)
                .addParams(ACCESSTOKEN, Constant.ACCESS_TOKEN)//
                .addParams(USERID, Constant.LOGIN_USERID)
                .addParams("type", "1")// 0代表收藏商户
                .addParams("pageIndex", currPage+"")//
                .addParams("pageRows", pageRows+"")//
                .build()
                .execute(new Callback<MineCollectionMerchantsModel>() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("wangyan","onError---->"+e.getMessage());
                        if(mRecyclerView!=null){
                            mRecyclerView.setVisibility(View.GONE);
                        }
                        if(mEmptyViewContainer!=null){
                            mEmptyViewContainer.setVisibility(View.VISIBLE);
                        }


                    }

                    @Override
                    public void onResponse(MineCollectionMerchantsModel response, int id) {
                        mTemp = response.getList();
                        if(mTemp == null || mTemp.size()<=0){

                            if(mRecyclerView!=null){
                                mRecyclerView.setVisibility(View.GONE);
                            }
                            if(mEmptyViewContainer!=null){
                                mEmptyViewContainer.setVisibility(View.VISIBLE);
                            }
                            return;
                        }
                        if(response!=null&&response.getPager()!=null){
                            hasNextPage = response.getPager().isHasNextPage();
                        }
                        if(response.getList() == null){
                            mAdapter.notifyDataSetChanged();
                            return;
                        }
                        switch (state){
                            case LOAD_NORMAL:
                                mList.addAll(response.getList());
                                mAdapter.notifyDataSetChanged();
                                break;
                            case LOAD_MORE:

                                break;
                        }
                        if(hasNextPage){
                            currPage++;
                            mAdapter.loadMoreComplete();
                        }else{
                            mAdapter.loadMoreEnd(true);
                        }
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.merchants_recyclerview;
    }

    private SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {

        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_100);
            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            {
                SwipeMenuItem addItem = new SwipeMenuItem(getContext())
                        .setBackground(R.drawable.buybutton_shape_unbind_red)
                        .setWidth(width)
                        .setHeight(height)
                        .setTextColor(getResources().getColor(R.color.white))
                        .setText("解除绑定");
                rightMenu.addMenuItem(addItem); // 添加一个按钮到左侧菜单。
            }

        }
    };



    @Override
    public void onItemClick(SwipeMenuBridge menuBridge) {
        menuBridge.closeMenu();
        int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
        final int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
        int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

        if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
            deleteShops(mList.get(adapterPosition).getCollectId(),adapterPosition);


        }else{
            ToastUtils.showShortToast("点击了左边");
        }
    }

    private void deleteShops(int collectId, final int position) {
        ShopDao.DeleteCollect(collectId+"", new onConnectionFinishLinstener() {
            @Override
            public void onSuccess(int code, Object result) {
                ToastUtils.showShortToast("取消收藏成功!");
                mList.remove(position);
                mAdapter.notifyItemRemoved(position);
            }

            @Override
            public void onFail(int code, String result) {
                ToastUtils.showShortToast(result);
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getContext(), ShopsActivity.class);
        intent.putExtra(ISADMIN,false);
        intent.putExtra(MERCHANTID,mTemp.get(position).getId()+"");
        startActivity(intent);
    }

    @Override
    public void onLoadMore() {
        if(hasNextPage){
            requestData(LOAD_MORE);
        }else{
            mAdapter.loadMoreEnd(true);
        }
    }
}
