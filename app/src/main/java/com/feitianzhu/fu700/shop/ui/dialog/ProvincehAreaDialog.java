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

public class ProvincehAreaDialog extends BaseDialog {
    @BindView(R.id.wv_province)
    WheelView mWvProvince;
    @BindView(R.id.wv_city)
    WheelView mWvCity;
    @BindView(R.id.wv_area)
    WheelView mWvArea;

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
    private ArrayList<Province.AreaListBean> arrAreas = new ArrayList<>();
    private AddressTextAdapter provinceAdapter;
    private CityAddressTextAdapter cityAdapter;
    private String strProvince = "北京市";
    private String strCity = "北京市";
    private String strArea = "东城区";
    private int selColor;
    private int unSelColor;
    private  ProvinceCallBack mListener;
    /**
     * 省份列表
     */
    private List<Province> provinceCityLists;
    /**
     * 城市列表
     */
    private List<Province.CityListBean> mCitys;
    private AreaAddressTextAdapter mAreaAddressTextAdapter;

    public static ProvincehAreaDialog newInstance(Context mShopFragment) {
        ProvincehAreaDialog dialog = new ProvincehAreaDialog();
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
        Province.AreaListBean mAreaListBean = mCityListBean.areaList.get(mWvArea.getCurrentItem());
        mListener.onWhellFinish(mProvince, mCityListBean,mAreaListBean);
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

    private void initProvinceData() {
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
        initProvinceView();
        initCityView();
        initAreaView();

    }


    private void initAreaView() {
        initArea(strArea);
        mAreaAddressTextAdapter =
            new AreaAddressTextAdapter(mContext, arrAreas, getAreaItem(strArea), maxsize, minsize);
        mWvArea.setVisibleItems(5);
        mWvArea.setViewAdapter(mAreaAddressTextAdapter);
        mWvArea.setCurrentItem(getAreaItem(strArea));
        mWvArea.setShadowColor(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT);
        mWvArea.setWheelBackground(R.drawable.province_wheel_bg);
        mWvArea.setWheelForeground(R.drawable.province_wheel_val);
        mWvArea.post(new Runnable() {

            @Override
            public void run() {
                String currentText = (String) mAreaAddressTextAdapter.getItemText(mWvArea.getCurrentItem());
                setAreaTextviewSize(currentText, mAreaAddressTextAdapter);
            }
        });
        mWvArea.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) mAreaAddressTextAdapter.getItemText(wheel.getCurrentItem());
                strCity = currentText;
                setAreaTextviewSize(currentText, mAreaAddressTextAdapter);
            }
        });
        mWvArea.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {
            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                String currentText = (String) mAreaAddressTextAdapter.getItemText(wheel.getCurrentItem());
                setAreaTextviewSize(currentText, mAreaAddressTextAdapter);
            }
        });

    }
    private void initCityView() {
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

        mWvCity.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                String currentText = (String) cityAdapter.getItemText(wheel.getCurrentItem());
                strCity = currentText;
                setCityTextviewSize(currentText, cityAdapter);
                initArea(currentText);
                mAreaAddressTextAdapter=new AreaAddressTextAdapter(mContext,arrAreas,0,maxsize,minsize);
                mWvArea.setVisibleItems(5);
                mWvArea.setCurrentItem(0);
                String currentCityText = (String) mAreaAddressTextAdapter.getItemText(mWvArea.getCurrentItem());
                setAreaTextviewSize(currentCityText, mAreaAddressTextAdapter);
                mWvArea.setViewAdapter(mAreaAddressTextAdapter);
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

    private void initProvinceView() {
        initProvinceData();
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
                //String currentAreaText = (String) cityAdapter.getItemText(mWvCity.getCurrentItem());
                initArea(currentCityText);
                mAreaAddressTextAdapter=new AreaAddressTextAdapter(mContext,arrAreas,0,maxsize,minsize);
                mWvArea.setVisibleItems(5);
                mWvArea.setCurrentItem(0);
                String currentAreaText = (String) mAreaAddressTextAdapter.getItemText(mWvArea.getCurrentItem());
                setAreaTextviewSize(currentAreaText, mAreaAddressTextAdapter);
                mWvArea.setViewAdapter(mAreaAddressTextAdapter);
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
    }

    @Override
    protected int onGetDialogViewId() {
        return R.layout.dialog_province_area;
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
    private class AreaAddressTextAdapter extends AbstractWheelTextAdapter {
        List<Province.AreaListBean> list;

        protected AreaAddressTextAdapter(Context context, List<Province.AreaListBean> list, int currentItem, int maxsize, int minsize) {
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
            if (0==list.size()||null==list.get(index))
                return "";
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
    public void setAreaTextviewSize(String curriteItemText, AreaAddressTextAdapter adapter) {
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
                mCitys = provinceCity.getCitys();
                if (mCitys != null && !mCitys.isEmpty()) {
                    for (Province.CityListBean string : mCitys) {
                        arrCitys.add(string);
                    }
                }
            }
        }
    }

    /**
     * 根据市找到区
     * @param area
     */
    private void initArea(String area) {
        arrAreas.clear();
        for (Province.CityListBean mBean : mCitys) {
            if (mBean.name.equals(area)) {
                List<Province.AreaListBean> citys = mBean.areaList;
                if (citys != null && !citys.isEmpty()) {
                    for (Province.AreaListBean string : citys) {
                        arrAreas.add(string);
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
    public int getAreaItem(String area) {
        int size = arrAreas.size();
        int areaIndex = 0;
        for (int i = 0; i < size; i++) {
            if (area.equals(arrAreas.get(i))) {
                return areaIndex;
            } else {
                areaIndex++;
            }
        }
        return areaIndex;
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
