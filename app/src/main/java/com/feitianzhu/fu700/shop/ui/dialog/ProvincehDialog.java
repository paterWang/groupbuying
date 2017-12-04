package com.feitianzhu.fu700.shop.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.model.Province;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

public class ProvincehDialog extends BaseDialog {
    @BindView(R.id.wv_province)
    WheelView mWvProvince;
    @BindView(R.id.wv_city)
    WheelView mWvCity;

    private int maxsize = 24;
    private int minsize = 14;
    /**
     * 省份列表
     */
    private ArrayList<String> arrProvinces = new ArrayList<String>();
    /**
     * 城市列表
     */
    private ArrayList<Province.CityListBean> arrCitys = new ArrayList<>();
    private AddressTextAdapter provinceAdapter;
    private CityAddressTextAdapter cityAdapter;
    private String strProvince = "北京市";
    private String strCity = "北京市";
    private int selColor;
    private int unSelColor;
    private  ProvinceCallBack mListener;
    /**
     * 省份列表
     */
    private List<Province> provinceCityLists;

    public static ProvincehDialog newInstance(Context mShopFragment) {
        ProvincehDialog dialog = new ProvincehDialog();
        return dialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    protected int onGetStyle() {
        return R.style.transparent_dialog;
    }

    @OnClick(R.id.layout_wheel_top)
    public void topClick(View v) {
        dissmissDialog();
    }

    @OnClick(R.id.iv_wheel_ok)
    public void okClick(View v) {
        int mItem = mWvProvince.getCurrentItem();
        Province mProvince = provinceCityLists.get(mItem);
        strProvince = mProvince.getProvince();
        int mCurrentItem = mWvCity.getCurrentItem();
        Province.CityListBean mCityListBean = arrCitys.get(mCurrentItem);
        strCity = mCityListBean.name;
        mListener.onWhellFinish(mProvince, mCityListBean, null);
        dismiss();
    }
    @OnClick(R.id.iv_wheel_cancel)
    public void cancelClick(View v) {
        //mListener.onWhellFinish(strProvince, strCity);
        dismiss();
    }
    public void setSelectOnListener(ProvinceCallBack listener ){
        this.mListener=listener;
    }

    private void initData() {
        try {
            String json = readString(mContext.getAssets().open("region.json"));
            Type type = new TypeToken<List<Province>>() {}.getType();
            provinceCityLists = new Gson().fromJson(json, type);
            if (provinceCityLists != null && !provinceCityLists.isEmpty()) {
                for (Province provinceCity : provinceCityLists) {
                    arrProvinces.add(provinceCity.getProvince());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        getDialog().getWindow().setLayout(dm.widthPixels, getDialog().getWindow().getAttributes().height);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onDialogCreated(Dialog dialog) {
        selColor = getResources().getColor(R.color.color_wheel_sel);
        unSelColor = getResources().getColor(R.color.color_wheel_unsel);
        initData();
        provinceAdapter = new AddressTextAdapter(mContext, arrProvinces, getProvinceItem(strProvince), maxsize, minsize);
        mWvProvince.setVisibleItems(5);
        mWvProvince.setViewAdapter(provinceAdapter);
        mWvProvince.setCurrentItem(getProvinceItem(strProvince));
        mWvProvince.setShadowColor(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT);
        mWvProvince.setWheelBackground(R.drawable.province_wheel_bg);
        mWvProvince.setWheelForeground(R.drawable.province_wheel_val);
        mWvProvince.post(new Runnable() {

            @Override
            public void run() {
                String currentText = (String) provinceAdapter.getItemText(mWvProvince.getCurrentItem());
                setTextviewSize(currentText, provinceAdapter);
            }
        });

        initCitys(strProvince);
        cityAdapter = new CityAddressTextAdapter(mContext, arrCitys, getCityItem(strCity), maxsize, minsize);
        mWvCity.setVisibleItems(5);
        mWvCity.setViewAdapter(cityAdapter);
        mWvCity.setCurrentItem(getCityItem(strCity));
        mWvCity.setShadowColor(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT);
        mWvCity.setWheelBackground(R.drawable.province_wheel_bg);
        mWvCity.setWheelForeground(R.drawable.province_wheel_val);
        mWvCity.post(new Runnable() {

            @Override
            public void run() {
                String currentText = (String) cityAdapter.getItemText(mWvCity.getCurrentItem());
                setCityTextviewSize(currentText, cityAdapter);
            }
        });

        mWvProvince.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) provinceAdapter.getItemText(wheel.getCurrentItem());
                strProvince = currentText;
                setTextviewSize(currentText, provinceAdapter);
                initCitys(currentText);
                cityAdapter = new CityAddressTextAdapter(mContext, arrCitys, 0, maxsize, minsize);
                mWvCity.setVisibleItems(5);
                mWvCity.setCurrentItem(0);
                String currentCityText = (String) cityAdapter.getItemText(mWvCity.getCurrentItem());
                setCityTextviewSize(currentCityText, cityAdapter);
                mWvCity.setViewAdapter(cityAdapter);
            }
        });

        mWvProvince.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) provinceAdapter.getItemText(wheel.getCurrentItem());
                setTextviewSize(currentText, provinceAdapter);
            }
        });

        mWvCity.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) cityAdapter.getItemText(wheel.getCurrentItem());
                strCity = currentText;
                setCityTextviewSize(currentText, cityAdapter);
            }
        });

        mWvCity.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) cityAdapter.getItemText(wheel.getCurrentItem());
                setCityTextviewSize(currentText, cityAdapter);
            }
        });

    }

    @Override
    protected int onGetDialogViewId() {
        return R.layout.dialog_province;
    }


    private class AddressTextAdapter extends AbstractWheelTextAdapter {
        List<String> list;

        protected AddressTextAdapter(Context context, List<String> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.item_view, NO_RESOURCE, currentItem, maxsize, minsize, selColor, unSelColor);
            this.list = list;
            setItemTextResource(R.id.tempValue);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index) + "";
        }
    }
    private class CityAddressTextAdapter extends AbstractWheelTextAdapter {
        List<Province.CityListBean> list;

        protected CityAddressTextAdapter(Context context, List<Province.CityListBean> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.item_view, NO_RESOURCE, currentItem, maxsize, minsize, selColor, unSelColor);
            this.list = list;
            setItemTextResource(R.id.tempValue);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index).name + "";
        }
    }

    /**
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextviewSize(String curriteItemText, AddressTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(20);
                textvew.setTextColor(selColor);
            } else {
                textvew.setTextSize(14);
                textvew.setTextColor(unSelColor);
            }
        }
    }
    public void setCityTextviewSize(String curriteItemText, CityAddressTextAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(20);
                textvew.setTextColor(selColor);
            } else {
                textvew.setTextSize(14);
                textvew.setTextColor(unSelColor);
            }
        }
    }

    /**
     * 根据省找到市
     *
     * @param
     */
    private void initCitys(String province) {
        arrCitys.clear();
        for (Province provinceCity : provinceCityLists) {
            if (provinceCity.getProvince().equals(province)) {
                List<Province.CityListBean> citys = provinceCity.getCitys();
                if (citys != null && !citys.isEmpty()) {
                    for (Province.CityListBean string : citys) {
                        arrCitys.add(string);
                    }
                }
            }
        }
    }

    /**
     * 初始化地点
     *
     * @param province
     * @param city
     */
    public void setAddress(String province, String city) {
        if (!TextUtils.isEmpty(province)) {
            this.strProvince = province;
        }
        if (!TextUtils.isEmpty(city)) {
            this.strCity = city;
        }
    }

    /**
     * 返回省会索引
     *
     * @param province
     * @return
     */
    public int getProvinceItem(String province) {
        int size = arrProvinces.size();
        int provinceIndex = 0;
        for (int i = 0; i < size; i++) {
            if (province.equals(arrProvinces.get(i))) {
                return provinceIndex;
            } else {
                provinceIndex++;
            }
        }
        return provinceIndex;
    }

    /**
     * 得到城市索引
     *
     * @param city
     * @return
     */
    public int getCityItem(String city) {
        int size = arrCitys.size();
        int cityIndex = 0;
        for (int i = 0; i < size; i++) {
            if (city.equals(arrCitys.get(i))) {
                return cityIndex;
            } else {
                cityIndex++;
            }
        }
        return cityIndex;
    }

    private String readString(InputStream in) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringWriter sw = new StringWriter();
            String line;
            while ((line = br.readLine()) != null) {
                sw.write(line);
            }
            br.close();
            sw.close();
            return sw.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }
}
