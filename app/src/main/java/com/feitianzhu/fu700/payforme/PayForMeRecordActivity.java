package com.feitianzhu.fu700.payforme;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.common.impl.onConnectionFinishLinstener;
import com.feitianzhu.fu700.dao.NetworkDao;
import com.feitianzhu.fu700.me.base.BaseActivity;
import com.feitianzhu.fu700.me.navigationbar.DefaultNavigationBar;
import com.feitianzhu.fu700.payforme.entity.PayForMeEntity;
import com.feitianzhu.fu700.payforme.fragment.AuditComFragment;
import com.feitianzhu.fu700.payforme.fragment.AuditIngFragment;
import com.feitianzhu.fu700.payforme.fragment.AuditRejFragment;
import com.feitianzhu.fu700.shop.adapter.MyPagerAdapter;
import com.feitianzhu.fu700.utils.ToastUtils;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.feitianzhu.fu700.common.Constant.PAY_FOR_ME_PAGE_SIZE;
import static com.feitianzhu.fu700.common.Constant.PAY_FOR_ME_START_PAGE;
import static com.feitianzhu.fu700.common.Constant.PAY_FOR_ME_STATUS_COM;
import static com.feitianzhu.fu700.common.Constant.PAY_FOR_ME_STATUS_ING;
import static com.feitianzhu.fu700.common.Constant.PAY_FOR_ME_STATUS_REJ;

public class PayForMeRecordActivity extends BaseActivity {

    @BindView(R.id.tablayout)
    SlidingTabLayout mTablayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private String[] mTitles = {"审核中", "已通过", "已驳回"};
    private List<String> mTitleList;
    private List<Fragment> mFragments;
    private SparseArray<String> mSparseArray;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_for_me_record;
    }

    @Override
    protected void initView() {

        defaultNavigationBar = new DefaultNavigationBar
                .Builder(this, (ViewGroup) findViewById(R.id.Rl_titleContainer))
                .setTitle("为我买单")
                .setStatusHeight(PayForMeRecordActivity.this)
                .setLeftIcon(R.drawable.iconfont_fanhuijiantou)
                .builder();
        defaultNavigationBar.setImmersion(R.color.txt_blue);

    }

    @Override
    protected void initData() {

        mTitleList = new ArrayList<>(3);
        mSparseArray = new SparseArray<>();
        mFragments = new ArrayList<>();

        getIngData();

    }

    private void getIngData() {

        NetworkDao.payForMeRecord(PAY_FOR_ME_STATUS_ING + "", PAY_FOR_ME_START_PAGE, PAY_FOR_ME_PAGE_SIZE, new onConnectionFinishLinstener() {
            @Override
            public void onSuccess(int code, Object result) {

                PayForMeEntity payForMeEntity = (PayForMeEntity) result;
                List<PayForMeEntity.StatusCntsBean> statusList = payForMeEntity.statusCnts;
                if (statusList == null || statusList.isEmpty()) {
                    setDefaultTabs();
                    return;
                }
                for (PayForMeEntity.StatusCntsBean countBean : statusList) {
                    if (countBean.status == PAY_FOR_ME_STATUS_ING) {
                        mSparseArray.put(countBean.status, String.format(getString(R.string.pay_for_me_ing), countBean.cnt + ""));
                    }
                    else if (countBean.status == PAY_FOR_ME_STATUS_COM) {
                        mSparseArray.put(countBean.status, String.format(getString(R.string.pay_for_me_com), countBean.cnt + ""));
                    }
                    else if (countBean.status == PAY_FOR_ME_STATUS_REJ) {
                        mSparseArray.put(countBean.status, String.format(getString(R.string.pay_for_me_rej), countBean.cnt + ""));
                    }
                }
                String ingTitle = mSparseArray.get(PAY_FOR_ME_STATUS_ING);
                if (!TextUtils.isEmpty(ingTitle)) {
                    mTitleList.add(ingTitle);
                    mFragments.add(new AuditIngFragment());
                }
                String comTitle = mSparseArray.get(PAY_FOR_ME_STATUS_COM);
                if (!TextUtils.isEmpty(comTitle)) {
                    mTitleList.add(comTitle);
                    mFragments.add(new AuditComFragment());
                }
                String rejTitle = mSparseArray.get(PAY_FOR_ME_STATUS_REJ);
                if (!TextUtils.isEmpty(rejTitle)) {
                    mTitleList.add(rejTitle);
                    mFragments.add(new AuditRejFragment());
                }
                String[] titles = mTitleList.toArray(new String[mTitleList.size()]);
                mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), titles, mFragments));
                mViewPager.setOffscreenPageLimit(titles.length);
                mTablayout.setViewPager(mViewPager, titles);

            }

            @Override
            public void onFail(int code, String result) {
                ToastUtils.showShortToast(result);
                setDefaultTabs();
            }
        });
    }

    private void setDefaultTabs() {

        mFragments.add(new AuditIngFragment());
        mFragments.add(new AuditComFragment());
        mFragments.add(new AuditRejFragment());

        mViewPager.setOffscreenPageLimit(mTitles.length);

        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), mTitles, mFragments));
        mTablayout.setViewPager(mViewPager, mTitles);
    }


}
