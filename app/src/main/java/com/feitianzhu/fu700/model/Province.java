package com.feitianzhu.fu700.model;

import java.util.List;

public class Province {

  public String areaId;
  public String areaName;

  public Province() {
  }

  public Province(String mName, String mCity_name, String mPid, String mId) {
    name = mName;
    city_name = mCity_name;
    pid = mPid;
    id = mId;
  }

  public Province(String mProvince_name, String mCity_name, String mCity_id, String mProvince_id,
      String mName, String mId) {
    name = mProvince_name;
    city_name = mCity_name;
    pid = mCity_id;
    id = mProvince_id;
    areaName = mName;
    areaId = mId;
  }

  public String name;
  public String city_name;
  public String pid;
  public String id;

  public List<CityListBean> cityList;

  public void setProvince(String province) {
    this.name = province;
  }

  public void setCitys(List<CityListBean> citys) {
    this.cityList = citys;
  }

  public String getProvince() {
    return name;
  }

  public List<CityListBean> getCitys() {
    return cityList;
  }

  public static class CityListBean {

    public String name;
    public String pid;
    public String id;
    public List<AreaListBean> areaList;
  }

  public static class AreaListBean {

    public String name;
    public String id;
  }
}
