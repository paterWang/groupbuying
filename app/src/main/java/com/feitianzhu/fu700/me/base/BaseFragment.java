package com.feitianzhu.fu700.me.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.feitianzhu.fu700.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2016/3/29.
 */
public abstract class BaseFragment extends Fragment {
    protected Context mContext;

    protected View rootView;
    private Context contextThemeWrapper;
    private LayoutInflater localInflater;
    private MaterialDialog mDialog;

    private Unbinder unbinder;
    protected View mEmptyView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.mContext = getActivity();
        rootView = View.inflate(mContext,getLayoutId(),null);
        if(getStyleId()>0){
            contextThemeWrapper = new ContextThemeWrapper(getActivity(), getStyleId());
            localInflater = inflater.cloneInContext(contextThemeWrapper);
        }else{
            localInflater = LayoutInflater.from(getActivity());
        }

        rootView = localInflater.inflate(getLayoutId(), null);
        // 加入注解
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected int getStyleId() {
        return 0;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mEmptyView =  View.inflate(getContext(), R.layout.view_common_nodata, null);
        initView();
        initData();
    }

    protected void showloadDialog(String title) {
        mDialog= new MaterialDialog.Builder(getContext())
                .content("加载中,请稍等")
                .progress(true, 0)
                .progressIndeterminateStyle(false)
                .show();
    }

    protected void goneloadDialog() {
        if (null != mDialog&&mDialog.isShowing()) if (mDialog.isShowing()) mDialog.dismiss();
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract int getLayoutId();
}
