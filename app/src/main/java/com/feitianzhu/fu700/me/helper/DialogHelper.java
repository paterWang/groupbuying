package com.feitianzhu.fu700.me.helper;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.feitianzhu.fu700.R;
import com.feitianzhu.fu700.model.Province;
import com.feitianzhu.fu700.view.pickview.LoopView;
import com.feitianzhu.fu700.view.pickview.OnItemSelectedListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Vya on 2017/9/7 0007.
 */

public class DialogHelper {
    public static final int DIALOG_ONE = 0x0001;
    public static final int DIALOG_TWO = 0x0002;
    public static final int DIALOG_THREE = 0x0003;
    public static final int DIALOG_CITY_THREE=0x0004;
    public static final int DIALOG_ONE_CITY = 0x0005;
    LoopView loopView1;
    LoopView loopView2;
    LoopView loopView3;

    LoopView cityView;
    LoopView areaView;
    LoopView localView;

    LoopView defaultView;

    Activity mContext;
    private ArrayList<String> oneList;
    private ArrayList<String> twoList;
    private ArrayList<String> threeList;

    private ArrayList<String> cityList;
    private ArrayList<String> areaList;
    private ArrayList<String> localList;

    /**
     * 省份列表
     */
    private List<Province> provinceCityLists;
    /**
     * 省份列表
     */
    private ArrayList<String> arrProvinces = new ArrayList<String>();
    /**
     * 城市列表
     */
    private ArrayList<Province.CityListBean> arrCitys = new ArrayList<>();
    private ArrayList<Province.AreaListBean> areaListBean = new ArrayList<>();

    public DialogHelper(Activity context){
        oneList = new ArrayList<>();
        twoList = new ArrayList<>();
        threeList = new ArrayList<>();
        cityList = new ArrayList<>();
        areaList = new ArrayList<>();
        localList = new ArrayList<>();
        mContext = context;
    }


    private int yearIndex = 0;
    private int monthIndex = 0;
    private int dayIndex = 0;
    private  AlertDialog alertDialog;
    private View clickView;
    private  View dialogView;
    private int mType = 0;
    private String json;
    private AlertDialog.Builder dialogBuilder;
    public  DialogHelper init(int Type,View clickView){
        initParams(Type);
        if(Type == DIALOG_TWO){
            initCityTwo();
        }
        if(Type == DIALOG_CITY_THREE){
            initCityThree();
        }
      if(Type == DIALOG_ONE_CITY){
          initOneCity();
      }

        this.clickView = clickView;
        return this;
    }


    private void initOneCity() {
        try {
            json = readString(mContext.getAssets().open("region.json"));
            //Log.e("wangyan","json==="+json);
            Type type = new TypeToken<List<Province>>() {}.getType();
            provinceCityLists = new Gson().fromJson(json, type);
            if (provinceCityLists != null && !provinceCityLists.isEmpty()) {
                for (Province provinceCity : provinceCityLists) {
                    arrProvinces.add(provinceCity.getProvince());
                    cityList.add(provinceCity.getProvince());
                }
            }
            defaultView.setItems(cityList);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initCityTwo() {
        try {
            json = readString(mContext.getAssets().open("region.json"));
            //Log.e("wangyan","json==="+json);
            Type type = new TypeToken<List<Province>>() {}.getType();
            provinceCityLists = new Gson().fromJson(json, type);
            if (provinceCityLists != null && !provinceCityLists.isEmpty()) {
                for (Province provinceCity : provinceCityLists) {
                    arrProvinces.add(provinceCity.getProvince());
                    cityList.add(provinceCity.getProvince());
                }
            }
            cityView.setItems(cityList);
            for(Province.CityListBean areaBean : provinceCityLists.get(0).cityList){
                areaList.add(areaBean.name);
            }
            areaView.setItems(areaList);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void initCityThree() {
        try {
            json = readString(mContext.getAssets().open("region.json"));
            //Log.e("wangyan","json==="+json);
            Type type = new TypeToken<List<Province>>() {}.getType();
            provinceCityLists = new Gson().fromJson(json, type);
            if (provinceCityLists != null && !provinceCityLists.isEmpty()) {
                for (Province provinceCity : provinceCityLists) {
                    arrProvinces.add(provinceCity.getProvince());
                    cityList.add(provinceCity.getProvince());
                }
            }
            cityView.setItems(cityList);
            for(Province.CityListBean areaBean : provinceCityLists.get(0).cityList){
                areaList.add(areaBean.name);
                for (Province.AreaListBean item : areaBean.areaList){
                    areaListBean.add(item);
                    localList.add(item.name);
                }
                localView.setItems(localList);
            }
            areaView.setItems(areaList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String subString(String str){

        if(str.length()>4){
          return str.substring(0,4);
        }
        return str;
    }

    private void clear(){
        oneList.clear();
        twoList.clear();
        threeList.clear();
        cityList.clear();
        areaList.clear();
    }
    private void initParams(int type,String ... buttonTxt) {
        mType=type;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        switch (type){
            case DIALOG_ONE:
                dialogView = inflater.inflate(R.layout.dialog_view, null,false);
                defaultView = (LoopView) dialogView.findViewById(R.id.loopView);
                defaultView.setDividerColor(mContext.getResources().getColor(R.color.dialog_rect_bg));
                defaultView.setNotLoop();
                break;

            case DIALOG_ONE_CITY:
                dialogView = inflater.inflate(R.layout.dialog_view, null,false);
                defaultView = (LoopView) dialogView.findViewById(R.id.loopView);
                defaultView.setDividerColor(mContext.getResources().getColor(R.color.dialog_rect_bg));
                defaultView.setNotLoop();
                break;

            case DIALOG_TWO:
                dialogView = inflater.inflate(R.layout.dialog_view_two, null,false);
                cityView = (LoopView) dialogView.findViewById(R.id.loopView1);
                areaView = (LoopView) dialogView.findViewById(R.id.loopView2);
                cityView.setDividerColor(mContext.getResources().getColor(R.color.dialog_rect_bg));
                cityView.setNotLoop();
                areaView.setDividerColor(mContext.getResources().getColor(R.color.dialog_rect_bg));
                areaView.setNotLoop();
                clear();
                break;
            case DIALOG_CITY_THREE:
                dialogView = inflater.inflate(R.layout.dialog_view_citythree, null,false);
                cityView = (LoopView) dialogView.findViewById(R.id.loopView1);
                areaView = (LoopView) dialogView.findViewById(R.id.loopView2);
                localView = (LoopView) dialogView.findViewById(R.id.loopView3);
                cityView.setDividerColor(mContext.getResources().getColor(R.color.dialog_rect_bg));
                cityView.setNotLoop();
                areaView.setDividerColor(mContext.getResources().getColor(R.color.dialog_rect_bg));
                areaView.setNotLoop();
                localView.setDividerColor(mContext.getResources().getColor(R.color.dialog_rect_bg));
                localView.setNotLoop();
                clear();
                break;
            case DIALOG_THREE:
                dialogView = inflater.inflate(R.layout.dialog_view_three, null,false);
                loopView1 = (LoopView) dialogView.findViewById(R.id.loopView1);
                loopView2 = (LoopView) dialogView.findViewById(R.id.loopView2);
                loopView3 = (LoopView) dialogView.findViewById(R.id.loopView3);
                loopView1.setDividerColor(mContext.getResources().getColor(R.color.dialog_rect_bg));
                loopView1.setNotLoop();
                loopView2.setDividerColor(mContext.getResources().getColor(R.color.dialog_rect_bg));
                loopView2.setNotLoop();
                loopView3.setDividerColor(mContext.getResources().getColor(R.color.dialog_rect_bg));
                loopView3.setNotLoop();

                clear();
                for (int i = 1900; i<= Calendar.getInstance().get(Calendar.YEAR); i++) {
                    oneList.add(i+"年");
                }
                for(int i=1;i<=12;i++){
                    twoList.add(i+"月");
                }
                for(int i=1;i<=30;i++){
                    threeList.add(i+"日");
                }
                // 设置原始数据
                loopView1.setItems(oneList);
                loopView2.setItems(twoList);
                loopView3.setItems(threeList);
                break;
            default:
                dialogView = inflater.inflate(R.layout.dialog_view, null,false);
                defaultView = (LoopView) dialogView.findViewById(R.id.loopView);
                break;
        }

        dialogBuilder = new AlertDialog.Builder(mContext);
      /*  LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_view_three, null,false);*/
        dialogBuilder.setView(dialogView);

        setClick(type);



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

    private void setClick(int type) {
        switch (type){
            case DIALOG_ONE:
                // 滚动监听
                defaultView.setListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int index) {
                        yearIndex = index;
                    }
                });
                break;
            case DIALOG_ONE_CITY:
                // 滚动监听
                defaultView.setListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int index) {
                        yearIndex = index;
                    }
                });
                break;
            case DIALOG_TWO:
                cityView.setListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int index) {
                        areaView.setCurrentPosition(0);
                        monthIndex = 0;
                        yearIndex = index;
                        areaList.clear();
                        Province province = provinceCityLists.get(index);
                        for(Province.CityListBean cityBean : province.getCitys()){
                            areaList.add(cityBean.name);
                        }
                        areaView.setItems(areaList);
                    }
                });
                areaView.setListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int index) {
                        monthIndex = index;
                    }
                });
                break;
            case DIALOG_CITY_THREE:
                cityView.setListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int index) {
                        areaView.setCurrentPosition(0);
                        monthIndex = 0;
                        dayIndex = 0;
                        yearIndex = index;
                        areaList.clear();
                        Province province = provinceCityLists.get(index);
                        for(Province.CityListBean cityBean : province.getCitys()){
                            areaList.add(subString(cityBean.name));
                        }
                        areaView.setItems(areaList);
                        //解决bug  当只滑动省的滚轮 区也要跟着变 他是市区List.get(monthIndex)
                        Log.e("Test","----index----"+index+"---monthIndex----"+monthIndex);
                        List<Province.AreaListBean> areaList = provinceCityLists.get(index)
                                .getCitys().get(monthIndex).areaList;
                        localList.clear();
                        for(Province.AreaListBean itemBean : areaList){
                            localList.add(subString(itemBean.name));
                        }

                        localView.setItems(localList);
                        localView.setCurrentPosition(0);

                    }

                });
                areaView.setListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int index) {
                        localView.setCurrentPosition(0);
                        dayIndex = 0;
                        monthIndex = index;
                        localList.clear();
                        Province.CityListBean cityListBean = provinceCityLists.get(yearIndex).cityList.get(index);
                        for(Province.AreaListBean itembean :cityListBean.areaList){
                            localList.add(itembean.name);
                        }
                        localView.setItems(localList);
                    }
                });
                localView.setListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int index) {
                        dayIndex = index;
                    }
                });

                break;


            case DIALOG_THREE:
                // 滚动监听
                loopView1.setListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int index) {
                        yearIndex = index;
                    }
                });
                loopView2.setListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int index) {
                        monthIndex = index;
                        int tempYear = Integer.parseInt(oneList.get(yearIndex).contains("年")?oneList.get(yearIndex).replace("年",""):oneList.get(yearIndex));
                        if((tempYear%4==0 && tempYear%100!=0||tempYear%400==0)&&twoList.get(monthIndex).equals("2")){ //闰年
                            threeList.clear();
                            for(int i=1;i<=29;i++){
                                threeList.add(i+"日");
                            }
                        }else if(twoList.get(monthIndex).equals("1月")||twoList.get(monthIndex).equals("3月")||twoList.get(monthIndex).equals("5月")||twoList.get(monthIndex).equals("7月")||twoList.get(monthIndex).equals("8月")||twoList.get(monthIndex).equals("10月")||twoList.get(monthIndex).equals("12月")){
                            threeList.clear();
                            for(int i=1;i<=31;i++){
                                threeList.add(i+"日");
                            }
                        }else if(twoList.get(monthIndex).equals("4月")||twoList.get(monthIndex).equals("6月")||twoList.get(monthIndex).equals("9月")||twoList.get(monthIndex).equals("11月")){
                            threeList.clear();
                            for(int i=1;i<=30;i++){
                                threeList.add(i+"日");
                            }
                        }else{
                            threeList.clear();
                            for(int i=1;i<=28;i++){
                                threeList.add(i+"日");
                            }
                        }
                        loopView3.setItems(threeList);
                    }
                });
                loopView3.setListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int index) {
                        dayIndex = index;


                    }
                });
                break;

        }

    }

    public DialogHelper buildDialog(final OnButtonClickListener Mistener){

        Button bt_sure = (Button) dialogView.findViewById(R.id.bt_sure) ;
        Button bt_cancel = (Button) dialogView.findViewById(R.id.bt_cancel) ;
        bt_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Mistener != null){
                    switch (mType){
                        case DIALOG_ONE:
                            break;
                        case DIALOG_ONE_CITY:
                            CityModel cityModel1 = new CityModel();
                            cityModel1.setCity(cityList.get(yearIndex));
                            cityModel1.setCityId(provinceCityLists.get(yearIndex).id);
                            Mistener.onButtonClick(v,cityModel1,clickView);
                            break;
                        case DIALOG_TWO:
                            CityModel cityModel = new CityModel();
                            cityModel.setCity(cityList.get(yearIndex));
                            cityModel.setCityId(provinceCityLists.get(yearIndex).id);
                            cityModel.setArea(provinceCityLists.get(yearIndex).getCitys().get(monthIndex).name);
                            cityModel.setAreaId(provinceCityLists.get(yearIndex).getCitys().get(monthIndex).id);
                            Mistener.onButtonClick(v,cityModel,clickView);
                            break;
                        case DIALOG_CITY_THREE:

                            CityModel cityModelThree = new CityModel();
                            cityModelThree.setCity(cityList.get(yearIndex));
                            cityModelThree.setCityId(provinceCityLists.get(yearIndex).id);
                            cityModelThree.setArea(provinceCityLists.get(yearIndex).getCitys().get(monthIndex).name);
                            cityModelThree.setAreaId(provinceCityLists.get(yearIndex).getCitys().get(monthIndex).id);
                            cityModelThree.setLocal(provinceCityLists.get(yearIndex).getCitys()
                                    .get(monthIndex).areaList.get(dayIndex).name);
                            cityModelThree.setLocalId(provinceCityLists.get(yearIndex).getCitys()
                                    .get(monthIndex).areaList.get(dayIndex).id);
                            Mistener.onButtonClick(v,cityModelThree,clickView);
                            break;

                        case DIALOG_THREE:
                            DateModel model = new DateModel(oneList.get(yearIndex).replace("年",""),twoList.get(monthIndex).replace("月",""),threeList.get(dayIndex).replace("日",""));
                            Mistener.onButtonClick(v,model,clickView);
                            break;
                    }

                }
                resetParam();
                alertDialog.dismiss();
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetParam();
                alertDialog.dismiss();

            }
        });
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        resetParam();
        return this;
    }
    public DialogHelper buildDialog(final List<String> oneListData, final OnButtonClickListener Mistener){
        defaultView.setItems(oneListData);
        Button bt_sure = (Button) dialogView.findViewById(R.id.bt_sure) ;
        Button bt_cancel = (Button) dialogView.findViewById(R.id.bt_cancel) ;
        bt_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Mistener != null){
                    switch (mType){
                        case DIALOG_ONE:
                            Mistener.onButtonClick(v,oneListData.get(yearIndex),clickView);
                            break;
                    }

                }
                resetParam();
                alertDialog.dismiss();
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetParam();
                alertDialog.dismiss();

            }
        });
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        resetParam();
        return this;
    }
    public DialogHelper buildDialog(final List<String> oneListData, final OnButtonClickPosListener Mistener){
        defaultView.setItems(oneListData);
        Button bt_sure = (Button) dialogView.findViewById(R.id.bt_sure) ;
        Button bt_cancel = (Button) dialogView.findViewById(R.id.bt_cancel) ;
        bt_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Mistener != null){
                    switch (mType){
                        case DIALOG_ONE:
                            if(oneListData.size()<=0){
                                oneListData.add("");
                            }
                            Mistener.onButtonClick(yearIndex,oneListData.get(yearIndex),clickView);
                            break;
                    }

                }
                resetParam();
                alertDialog.dismiss();
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetParam();
                alertDialog.dismiss();

            }
        });
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        resetParam();
        return this;
    }
    public DialogHelper buildDialog(final String text,final String ButtonTxt, final OnButtonClickListener Mistener){
        final List<String> mText = new ArrayList<>();
        if(TextUtils.isEmpty(text)){
            mText.add("NULL");
        }else{
            mText.add(text);
        }

        defaultView.setItems(mText);
        Button bt_sure = (Button) dialogView.findViewById(R.id.bt_sure) ;
        Button bt_cancel = (Button) dialogView.findViewById(R.id.bt_cancel) ;
        bt_sure.setText(ButtonTxt);
        bt_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Mistener != null){
                    switch (mType){
                        case DIALOG_ONE:
                            Mistener.onButtonClick(v,mText.get(yearIndex),clickView);
                            break;
                    }

                }
                resetParam();
                alertDialog.dismiss();
            }
        });
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetParam();
                alertDialog.dismiss();

            }
        });
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        resetParam();
        return this;
    }

    public interface OnButtonClickListener<T>{
        void onButtonClick(View v,T result,View clickView);
    }
    public interface OnButtonClickPosListener<T>{
        void onButtonClick(int position,T result,View clickView);
    }



    public void resetParam(){
        yearIndex = 0;
        monthIndex = 0;
        monthIndex = 0;

    }

}
