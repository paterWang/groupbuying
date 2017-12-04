package com.feitianzhu.fu700.me.helper;

/**
 * Created by Vya on 2017/9/7 0007.
 */

public class CityModel extends DialogModel {

    public String selectName;
    public String agentType; //市代3  区代4

    public String city;  //省
    public String cityId;
    public String area;  //市
    public String areaId;
    public String local;
    public String localId; //区


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }
}
